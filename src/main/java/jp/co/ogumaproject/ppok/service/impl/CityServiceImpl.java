package jp.co.ogumaproject.ppok.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ogumaproject.ppok.common.OgumaProjectConstants;
import jp.co.ogumaproject.ppok.dto.CityDto;
import jp.co.ogumaproject.ppok.entity.City;
import jp.co.ogumaproject.ppok.entity.District;
import jp.co.ogumaproject.ppok.repository.CityRepository;
import jp.co.ogumaproject.ppok.repository.DistrictRepository;
import jp.co.ogumaproject.ppok.service.ICityService;
import jp.co.ogumaproject.ppok.utils.OgumaProjectUtils;
import jp.co.ogumaproject.ppok.utils.Pagination;
import jp.co.ogumaproject.ppok.utils.ResultDto;
import jp.co.ogumaproject.ppok.utils.SecondBeanUtils;
import jp.co.ogumaproject.ppok.utils.SnowflakeUtils;
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
	 * ページサイズ
	 */
	private static final Integer PAGE_SIZE = OgumaProjectConstants.DEFAULT_PAGE_SIZE;

	/**
	 * 地域リポジトリ
	 */
	private final DistrictRepository districtRepository;

	/**
	 * 都市リポジトリ
	 */
	private final CityRepository cityRepository;

	@Override
	public ResultDto<String> checkDuplicated(final String name, final Long districtId) {
		return this.cityRepository.countByName(name, districtId) > 0
				? ResultDto.failed(OgumaProjectConstants.MESSAGE_CITY_NAME_DUPLICATED)
				: ResultDto.successWithoutData();
	}

	@Override
	public Pagination<CityDto> getCitiesByKeyword(final Integer pageNum, final String keyword) {
		final int offset = (pageNum - 1) * PAGE_SIZE;
		final String detailKeyword = OgumaProjectUtils.getDetailKeyword(keyword);
		final Long totalRecords = this.cityRepository.countByKeyword(detailKeyword);
		final List<CityDto> cityDtos = this.cityRepository.pagination(offset, PAGE_SIZE, detailKeyword).stream()
				.map(item -> {
					final District district = this.districtRepository.getOneById(item.getDistrictId());
					return new CityDto(item.getId(), item.getName(), item.getDistrictId(), item.getPronunciation(),
							district.getName(), item.getPopulation(), item.getCityFlag());
				}).toList();
		return Pagination.of(cityDtos, totalRecords, pageNum, PAGE_SIZE);
	}

	@Override
	public ResultDto<String> remove(final Long id) {
		final Long countByShutoId = this.districtRepository.countByShutoId(id);
		if (countByShutoId > 0) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_FORBIDDEN3);
		}
		final City city = new City();
		city.setId(id);
		city.setDelFlg(OgumaProjectConstants.LOGIC_DELETE_FLG);
		this.cityRepository.updateById(city);
		return ResultDto.successWithoutData(OgumaProjectConstants.MESSAGE_STRING_DELETED);
	}

	@Override
	public void save(final CityDto cityDto) {
		final City city = new City();
		SecondBeanUtils.copyNullableProperties(cityDto, city);
		city.setId(SnowflakeUtils.snowflakeId());
		city.setDelFlg(OgumaProjectConstants.LOGIC_DELETE_INITIAL);
		this.cityRepository.saveById(city);
	}

	@Override
	public ResultDto<String> update(final CityDto cityDto) {
		final City originalEntity = new City();
		final City city = this.cityRepository.getOneById(cityDto.id());
		SecondBeanUtils.copyNullableProperties(city, originalEntity);
		SecondBeanUtils.copyNullableProperties(cityDto, city);
		if (OgumaProjectUtils.isEqual(originalEntity, city)) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_NOCHANGE);
		}
		this.cityRepository.updateById(city);
		return ResultDto.successWithoutData();
	}
}
