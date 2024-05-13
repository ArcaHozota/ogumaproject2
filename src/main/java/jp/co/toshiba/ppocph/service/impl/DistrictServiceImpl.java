package jp.co.toshiba.ppocph.service.impl;

import static jp.co.toshiba.ppocph.jooq.Tables.CITIES;
import static jp.co.toshiba.ppocph.jooq.Tables.DISTRICTS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jooq.DSLContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jp.co.toshiba.ppocph.common.OgumaProjectConstants;
import jp.co.toshiba.ppocph.dto.DistrictDto;
import jp.co.toshiba.ppocph.jooq.tables.records.DistrictsRecord;
import jp.co.toshiba.ppocph.service.IDistrictService;
import jp.co.toshiba.ppocph.utils.OgumaProjectUtils;
import jp.co.toshiba.ppocph.utils.Pagination;
import jp.co.toshiba.ppocph.utils.ResultDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 地域サービス実装クラス
 *
 * @author ArkamaHozota
 * @since 7.81
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DistrictServiceImpl implements IDistrictService {

	/**
	 * 共通リポジトリ
	 */
	private final DSLContext dslContext;

	@Override
	public List<DistrictDto> getDistrictsByCityId(final String cityId) {
		final List<DistrictDto> districtDtos = new ArrayList<>();
		final List<DistrictsRecord> districtRecords = this.dslContext.selectFrom(DISTRICTS)
				.where(DISTRICTS.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL))
				.fetchInto(DistrictsRecord.class);
		final List<DistrictDto> districtDtos1 = districtRecords
				.stream().map(item -> new DistrictDto(item.getId(), item.getName(), item.getShutoId(), null,
						item.getChiho(), null, item.getDistrictFlag()))
				.sorted(Comparator.comparingLong(DistrictDto::id)).toList();
		if (!OgumaProjectUtils.isDigital(cityId)) {
			final DistrictDto districtDto = new DistrictDto(0L, OgumaProjectConstants.DEFAULT_ROLE_NAME, 0L,
					OgumaProjectUtils.EMPTY_STRING, OgumaProjectUtils.EMPTY_STRING, null,
					OgumaProjectUtils.EMPTY_STRING);
			districtDtos.add(districtDto);
			districtDtos.addAll(districtDtos1);
			return districtDtos;
		}
		final DistrictsRecord districtsRecord = this.dslContext.select(DISTRICTS).from(DISTRICTS).innerJoin(CITIES)
				.on(CITIES.DISTRICT_ID.eq(DISTRICTS.ID))
				.where(DISTRICTS.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL))
				.and(CITIES.ID.eq(Long.parseLong(cityId))).fetchSingle().into(DistrictsRecord.class);
		districtDtos
				.add(new DistrictDto(districtsRecord.getId(), districtsRecord.getName(), districtsRecord.getShutoId(),
						null, districtsRecord.getChiho(), null, districtsRecord.getDistrictFlag()));
		districtDtos.addAll(districtDtos1);
		return districtDtos.stream().distinct().toList();
	}

	@Override
	public Pagination<DistrictDto> getDistrictsByKeyword(final Integer pageNum, final String keyword) {
//		final PageRequest pageRequest = PageRequest.of(pageNum - 1, OgumaProjectConstants.DEFAULT_PAGE_SIZE,
//				Sort.by(Direction.ASC, "id"));
//		final Specification<District> where1 = (root, query, criteriaBuilder) -> criteriaBuilder
//				.equal(root.get("deleteFlg"), OgumaProjectConstants.LOGIC_DELETE_INITIAL);
//		final Specification<District> specification = Specification.where(where1);
//		if (OgumaProjectUtils.isEmpty(keyword)) {
//			final Page<District> pages = this.districtRepository.findAll(specification, pageRequest);
//			final List<DistrictDto> districtDtos = pages.stream()
//					.map(item -> new DistrictDto(item.getId(), item.getName(), item.getShutoId(),
//							item.getCities().stream().filter(a -> Objects.equals(a.getId(), item.getShutoId())).toList()
//									.get(0).getName(),
//							item.getChiho(),
//							item.getCities().stream().map(City::getPopulation).reduce((a, v) -> a + v).get(),
//							item.getDistrictFlag()))
//					.toList();
//			return Pagination.of(districtDtos, pages.getTotalElements(), pageNum,
//					OgumaProjectConstants.DEFAULT_PAGE_SIZE);
//		}
//		final String searchStr = OgumaProjectUtils.getDetailKeyword(keyword);
//		final Page<District> pages = this.districtRepository.findByShutoLike(searchStr, pageRequest);
//		final List<DistrictDto> districtDtos = pages.stream()
//				.map(item -> new DistrictDto(item.getId(), item.getName(), item.getShutoId(),
//						item.getCities().stream().filter(a -> Objects.equals(a.getId(), item.getShutoId())).toList()
//								.get(0).getName(),
//						item.getChiho(),
//						item.getCities().stream().map(City::getPopulation).reduce((a, v) -> a + v).get(),
//						item.getDistrictFlag()))
//				.toList();
//		return Pagination.of(districtDtos, pages.getTotalElements(), pageNum, OgumaProjectConstants.DEFAULT_PAGE_SIZE);
		return null;
	}

	@Override
	public ResultDto<String> update(final DistrictDto districtDto) {
		final DistrictsRecord districtsRecord = this.dslContext.selectFrom(DISTRICTS)
				.where(DISTRICTS.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL))
				.and(DISTRICTS.ID.eq(districtDto.id())).fetchSingle().into(DistrictsRecord.class);
		final DistrictDto aDistrictDto = new DistrictDto(districtsRecord.getId(), districtsRecord.getName(), null, null,
				districtsRecord.getChiho(), null, null);
		if (aDistrictDto.equals(districtDto)) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_NOCHANGE);
		}
		districtsRecord.setName(districtDto.name());
		districtsRecord.setChiho(districtDto.chiho());
		try {
			this.dslContext.update(DISTRICTS).set(districtsRecord)
					.where(DISTRICTS.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL))
					.and(DISTRICTS.ID.eq(districtsRecord.getId())).execute();
		} catch (final DataIntegrityViolationException e) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_DISTRICT_NAME_DUPLICATED);
		}
		return ResultDto.successWithoutData();
	}
}
