package jp.co.ogumaproject.ppok.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ogumaproject.ppok.common.OgumaProjectConstants;
import jp.co.ogumaproject.ppok.dto.CityDto;
import jp.co.ogumaproject.ppok.dto.DistrictDto;
import jp.co.ogumaproject.ppok.entity.Chiho;
import jp.co.ogumaproject.ppok.entity.City;
import jp.co.ogumaproject.ppok.entity.District;
import jp.co.ogumaproject.ppok.repository.ChihoRepository;
import jp.co.ogumaproject.ppok.repository.CityRepository;
import jp.co.ogumaproject.ppok.repository.DistrictRepository;
import jp.co.ogumaproject.ppok.service.IDistrictService;
import jp.co.ogumaproject.ppok.utils.OgumaProjectUtils;
import jp.co.ogumaproject.ppok.utils.Pagination;
import jp.co.ogumaproject.ppok.utils.ResultDto;
import jp.co.ogumaproject.ppok.utils.SecondBeanUtils;
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
	 * ページサイズ
	 */
	private static final Integer PAGE_SIZE = OgumaProjectConstants.DEFAULT_PAGE_SIZE;

	/**
	 * 地方リポジトリ
	 */
	private final ChihoRepository chihoRepository;

	/**
	 * 地域リポジトリ
	 */
	private final DistrictRepository districtRepository;

	/**
	 * 都市リポジトリ
	 */
	private final CityRepository cityRepository;

	@Override
	public List<Chiho> getChihos(final String chihoName) {
		final List<Chiho> chihos = new ArrayList<>();
		final List<Chiho> list = this.chihoRepository.getList();
		chihos.add(list.stream().filter(a -> OgumaProjectUtils.isEqual(a.getName(), chihoName)).findFirst().get());
		chihos.addAll(list);
		return chihos.stream().distinct().toList();
	}

	@Override
	public List<DistrictDto> getDistrictsByCityId(final String cityId) {
		final List<District> districts = this.districtRepository.getList();
		if (!OgumaProjectUtils.isDigital(cityId)) {
			return districts.stream().map(item -> new DistrictDto(item.getId(), item.getName(), null, null, null,
					item.getChihoName(), null, item.getDistrictFlag())).toList();
		}
		final List<District> aDistricts = new ArrayList<>();
		final City city = this.cityRepository.getOneById(Long.parseLong(cityId));
		aDistricts.add(districts.stream().filter(a -> OgumaProjectUtils.isEqual(a.getId(), city.getDistrictId()))
				.findFirst().get());
		aDistricts.addAll(districts);
		return aDistricts.stream().distinct().map(item -> new DistrictDto(item.getId(), item.getName(), null, null,
				null, item.getChihoName(), null, item.getDistrictFlag())).toList();
	}

	@Override
	public Pagination<DistrictDto> getDistrictsByKeyword(final Integer pageNum, final String keyword) {
		final int offset = (pageNum - 1) * PAGE_SIZE;
		final String detailKeyword = OgumaProjectUtils.getDetailKeyword(keyword);
		final Integer totalRecords = this.districtRepository.countByKeyword(detailKeyword);
		final List<DistrictDto> districtDtos = this.districtRepository.pagination(offset, PAGE_SIZE, detailKeyword)
				.stream()
				.map(item -> new DistrictDto(item.getId(), item.getName(), item.getShutoId(), item.getShutoName(),
						item.getChihoId(), item.getChihoName(), item.getPopulation(), item.getDistrictFlag()))
				.toList();
		return Pagination.of(districtDtos, totalRecords, pageNum, PAGE_SIZE);
	}

	@Override
	public List<CityDto> getShutos(final DistrictDto districtDto) {
		final List<CityDto> cityDtos = new ArrayList<>();
		final List<City> cities = this.cityRepository.getListByForeignKey(districtDto.id());
		cityDtos.add(cities.stream().filter(a -> OgumaProjectUtils.isEqual(a.getName(), districtDto.shutoName()))
				.map(item -> new CityDto(item.getId(), item.getName(), null, null, null, null, null)).findFirst()
				.get());
		cityDtos.addAll(cities.stream().sorted(Comparator.comparingLong(City::getId))
				.map(item -> new CityDto(item.getId(), item.getName(), null, null, null, null, null)).toList());
		return cityDtos.stream().distinct().toList();
	}

	@Override
	public ResultDto<String> update(final DistrictDto districtDto) {
		final District originalEntity = new District();
		final District district = this.districtRepository.getOneById(districtDto.id());
		SecondBeanUtils.copyNullableProperties(district, originalEntity);
		SecondBeanUtils.copyNullableProperties(districtDto, district);
		if (OgumaProjectUtils.isEqual(originalEntity, district)) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_NOCHANGE);
		}
		this.districtRepository.updateById(district);
		return ResultDto.successWithoutData();
	}
}
