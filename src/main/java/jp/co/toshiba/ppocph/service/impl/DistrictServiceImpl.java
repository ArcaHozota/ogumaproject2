package jp.co.toshiba.ppocph.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.dto.DistrictDto;
import jp.co.toshiba.ppocph.entity.City;
import jp.co.toshiba.ppocph.entity.District;
import jp.co.toshiba.ppocph.exception.PgCrowdException;
import jp.co.toshiba.ppocph.repository.CityRepository;
import jp.co.toshiba.ppocph.repository.DistrictRepository;
import jp.co.toshiba.ppocph.service.IDistrictService;
import jp.co.toshiba.ppocph.utils.Pagination;
import jp.co.toshiba.ppocph.utils.ResultDto;
import jp.co.toshiba.ppocph.utils.SecondBeanUtils;
import jp.co.toshiba.ppocph.utils.StringUtils;
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
	 * 地域管理リポジトリ
	 */
	private final DistrictRepository districtRepository;

	/**
	 * 都市管理リポジトリ
	 */
	private final CityRepository cityRepository;

	@Override
	public List<DistrictDto> getDistrictsByCityId(final String cityId) {
		final List<District> districts = this.districtRepository.findAll();
		final List<DistrictDto> districtDtos1 = districts.stream()
				.map(item -> new DistrictDto(item.getId(), item.getName(), item.getShutoId(),
						item.getCities().stream().filter(a -> Objects.equals(a.getId(), item.getShutoId())).toList()
								.get(0).getName(),
						item.getChiho(),
						item.getCities().stream().map(City::getPopulation).reduce((a, v) -> a + v).get(),
						item.getDistrictFlag()))
				.sorted().toList();
		if (StringUtils.isEmpty(cityId)) {
			return districtDtos1;
		}
		final City city = this.cityRepository.findById(Long.parseLong(cityId)).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_FATAL_ERROR);
		});
		final List<DistrictDto> districtDtos2 = new ArrayList<>();
		final District district = districts.stream().filter(a -> Objects.equals(a.getId(), city.getDistrictId()))
				.toList().get(0);
		districtDtos2.add(new DistrictDto(district.getId(), district.getName(), district.getShutoId(),
				district.getCities().stream().filter(a -> Objects.equals(a.getId(), district.getShutoId())).toList()
						.get(0).getName(),
				district.getChiho(),
				district.getCities().stream().map(City::getPopulation).reduce((a, v) -> a + v).get(),
				district.getDistrictFlag()));
		districtDtos2.addAll(districtDtos1);
		return districtDtos2.stream().distinct().toList();
	}

	@Override
	public Pagination<DistrictDto> getDistrictsByKeyword(final Integer pageNum, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, PgCrowdConstants.DEFAULT_PAGE_SIZE,
				Sort.by(Direction.ASC, "id"));
		final Specification<District> where1 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("deleteFlg"), PgCrowdConstants.LOGIC_DELETE_INITIAL);
		final Specification<District> specification = Specification.where(where1);
		if (StringUtils.isEmpty(keyword)) {
			final Page<District> pages = this.districtRepository.findAll(specification, pageRequest);
			final List<DistrictDto> districtDtos = pages.stream()
					.map(item -> new DistrictDto(item.getId(), item.getName(), item.getShutoId(),
							item.getCities().stream().filter(a -> Objects.equals(a.getId(), item.getShutoId())).toList()
									.get(0).getName(),
							item.getChiho(),
							item.getCities().stream().map(City::getPopulation).reduce((a, v) -> a + v).get(),
							item.getDistrictFlag()))
					.toList();
			return Pagination.of(districtDtos, pages.getTotalElements(), pageNum, PgCrowdConstants.DEFAULT_PAGE_SIZE);
		}
		final String searchStr = StringUtils.getDetailKeyword(keyword);
		final Page<District> pages = this.districtRepository.findByShutoLike(searchStr, pageRequest);
		final List<DistrictDto> districtDtos = pages.stream()
				.map(item -> new DistrictDto(item.getId(), item.getName(), item.getShutoId(),
						item.getCities().stream().filter(a -> Objects.equals(a.getId(), item.getShutoId())).toList()
								.get(0).getName(),
						item.getChiho(),
						item.getCities().stream().map(City::getPopulation).reduce((a, v) -> a + v).get(),
						item.getDistrictFlag()))
				.toList();
		return Pagination.of(districtDtos, pages.getTotalElements(), pageNum, PgCrowdConstants.DEFAULT_PAGE_SIZE);
	}

	@Override
	public ResultDto<String> update(final DistrictDto districtDto) {
		final District district = this.districtRepository.findById(districtDto.id()).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_FATAL_ERROR);
		});
		final District originalEntity = new District();
		SecondBeanUtils.copyNullableProperties(district, originalEntity);
		SecondBeanUtils.copyNullableProperties(districtDto, district);
		if (originalEntity.equals(district)) {
			return ResultDto.failed(PgCrowdConstants.MESSAGE_STRING_NOCHANGE);
		}
		try {
			this.districtRepository.saveAndFlush(district);
		} catch (final DataIntegrityViolationException e) {
			return ResultDto.failed(PgCrowdConstants.MESSAGE_DISTRICT_NAME_DUPLICATED);
		}
		return ResultDto.successWithoutData();
	}
}