package jp.co.toshiba.ppocph.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
import jp.co.toshiba.ppocph.utils.SecondBeanUtils;
import jp.co.toshiba.ppocph.utils.SnowflakeUtils;
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
							item.getPronunciation(), item.getDistrict().getName(), item.getPopulation(),
							item.getCityFlag()))
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
		if (!CollectionUtils.isEmpty(districts)) {
			final List<City> cities = new ArrayList<>();
			districts.forEach(item -> {
				final List<City> cities2 = item.getCities();
				cities.addAll(cities2);
			});
			final List<CityDto> cityDtos = cities.stream()
					.map(item -> new CityDto(item.getId(), item.getName(), item.getDistrictId(),
							item.getPronunciation(), item.getDistrict().getName(), item.getPopulation(),
							item.getCityFlag()))
					.distinct().sorted(Comparator.nullsLast(Comparator.comparingLong(CityDto::id))).toList();
			final Integer pageMin = (pageNum - 1) * PgCrowdConstants.DEFAULT_PAGE_SIZE;
			final Integer pageMax = (pageNum * PgCrowdConstants.DEFAULT_PAGE_SIZE) >= cityDtos.size() ? cityDtos.size()
					: pageNum * PgCrowdConstants.DEFAULT_PAGE_SIZE;
			return Pagination.of(cityDtos.subList(pageMin, pageMax), cityDtos.size(), pageNum,
					PgCrowdConstants.DEFAULT_PAGE_SIZE);
		}
		final Specification<City> where4 = (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),
				searchStr);
		final Specification<City> where5 = (root, query, criteriaBuilder) -> criteriaBuilder
				.like(root.get("pronunciation"), searchStr);
		final Specification<City> specification3 = Specification.where(where1).and(Specification.anyOf(where4, where5));
		final Page<City> pages = this.cityRepository.findAll(specification3, pageRequest);
		final List<CityDto> cityDtos = pages.stream()
				.map(item -> new CityDto(item.getId(), item.getName(), item.getDistrictId(), item.getPronunciation(),
						item.getDistrict().getName(), item.getPopulation(), item.getCityFlag()))
				.toList();
		return Pagination.of(cityDtos, pages.getTotalElements(), pageNum, PgCrowdConstants.DEFAULT_PAGE_SIZE);
	}

	@Override
	public void save(final CityDto cityDto) {
		final City city = new City();
		SecondBeanUtils.copyNullableProperties(cityDto, city);
		city.setId(SnowflakeUtils.snowflakeId());
		city.setDeleteFlg(PgCrowdConstants.LOGIC_DELETE_INITIAL);
		this.cityRepository.saveAndFlush(city);
	}

	@Override
	public ResultDto<String> update(final CityDto cityDto) {
		final City city = this.cityRepository.findById(cityDto.id()).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_FATAL_ERROR);
		});
		final City originalEntity = new City();
		SecondBeanUtils.copyNullableProperties(city, originalEntity);
		SecondBeanUtils.copyNullableProperties(cityDto, city);
		if (originalEntity.equals(city)) {
			return ResultDto.failed(PgCrowdConstants.MESSAGE_STRING_NOCHANGE);
		}
		try {
			this.cityRepository.saveAndFlush(city);
		} catch (final DataIntegrityViolationException e) {
			return ResultDto.failed(PgCrowdConstants.MESSAGE_CITY_NAME_DUPLICATED);
		}
		return ResultDto.successWithoutData();
	}
}
