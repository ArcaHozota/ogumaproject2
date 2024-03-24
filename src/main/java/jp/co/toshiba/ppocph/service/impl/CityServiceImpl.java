package jp.co.toshiba.ppocph.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.dto.CityDto;
import jp.co.toshiba.ppocph.entity.City;
import jp.co.toshiba.ppocph.entity.District;
import jp.co.toshiba.ppocph.exception.PgCrowdException;
import jp.co.toshiba.ppocph.repository.CityRepository;
import jp.co.toshiba.ppocph.repository.DistrictRepository;
import jp.co.toshiba.ppocph.service.ICityService;
import jp.co.toshiba.ppocph.utils.Pagination;
import jp.co.toshiba.ppocph.utils.ResultDto;
import jp.co.toshiba.ppocph.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 都市サービス実装クラス
 *
 * @author ArkamaHozota
 * @since 7.89
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CityServiceImpl implements ICityService {

	/**
	 * 都市管理リポジトリ
	 */
	private final CityRepository cityRepository;

	/**
	 * 地域管理リポジトリ
	 */
	private final DistrictRepository districtRepository;

	@Override
	public ResultDto<String> check(final String name, final Long districtId) {
		final District district = this.districtRepository.findById(districtId).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_FATAL_ERROR);
		});
		final List<String> list = district.getCities().stream().map(City::getName).toList();
		if (list.contains(name)) {
			return ResultDto.failed(PgCrowdConstants.MESSAGE_CITY_NAME_DUPLICATED);
		}
		return ResultDto.successWithoutData();
	}

	@Override
	public Pagination<CityDto> getCitiesByKeyword(final Integer pageNum, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, PgCrowdConstants.DEFAULT_PAGE_SIZE,
				Sort.by(Direction.ASC, "id"));
		final Specification<City> where1 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("deleteFlg"), PgCrowdConstants.LOGIC_DELETE_INITIAL);
		final Specification<City> specification = Specification.where(where1);
		if (StringUtils.isEmpty(keyword)) {
			final Page<City> pages = this.cityRepository.findAll(specification, pageRequest);
			final List<CityDto> cityDtos = pages.stream()
					.map(item -> new CityDto(item.getId(), item.getName(), item.getDistrictId(),
							item.getPronunciation(),
							this.districtRepository.findById(item.getDistrictId()).orElseGet(District::new).getName(),
							item.getPopulation(), item.getCityFlag()))
					.toList();
			return Pagination.of(cityDtos, pages.getTotalElements(), pageNum, PgCrowdConstants.DEFAULT_PAGE_SIZE);
		}
		final String searchStr = StringUtils.getDetailKeyword(keyword);
		final Specification<District> where2 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("deleteFlg"), PgCrowdConstants.LOGIC_DELETE_INITIAL);
		final Specification<District> where3 = (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),
				searchStr);
		final Specification<District> specification2 = Specification.where(where2).and(where3);
		final List<District> districts = this.districtRepository.findAll(specification2);
		final Specification<City> where4 = (root, query, criteriaBuilder) -> criteriaBuilder
				.in(root.get("districtId").in(districts.stream().map(District::getId).toList()));
		final Specification<City> where5 = (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),
				searchStr);
		final Specification<City> where6 = (root, query, criteriaBuilder) -> criteriaBuilder
				.like(root.get("pronunciation"), searchStr);
		final Specification<City> specification3 = Specification.where(where1)
				.and(Specification.anyOf(where4, where5, where6));
		final Page<City> pages = this.cityRepository.findAll(specification3, pageRequest);
		final List<CityDto> cityDtos = pages.stream()
				.map(item -> new CityDto(item.getId(), item.getName(), item.getDistrictId(), item.getPronunciation(),
						this.districtRepository.findById(item.getDistrictId()).orElseGet(District::new).getName(),
						item.getPopulation(), item.getCityFlag()))
				.toList();
		return Pagination.of(cityDtos, pages.getTotalElements(), pageNum, PgCrowdConstants.DEFAULT_PAGE_SIZE);
	}

	@Override
	public void save(final CityDto cityDto) {
	}

	@Override
	public ResultDto<String> update(final CityDto cityDto) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
