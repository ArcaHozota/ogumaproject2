package jp.co.ogumaproject.ppok.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ogumaproject.ppok.common.OgumaProjectConstants;
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
		chihos.addAll(list.stream().filter(a -> OgumaProjectUtils.isEqual(a.getName(), chihoName)).toList());
		chihos.addAll(list);
		return chihos.stream().distinct().toList();
	}

	@Override
	public List<DistrictDto> getDistrictsByCityId(final String cityId) {
		final List<District> districts = this.districtRepository.getList();
		if (!OgumaProjectUtils.isDigital(cityId)) {
			return districts.stream().map(item -> {
				final Chiho chiho = this.chihoRepository.getOneById(item.getChihoId());
				return new DistrictDto(item.getId(), item.getName(), null, null, null, chiho.getName(), null, null);
			}).toList();
		}
		final List<District> aDistricts = new ArrayList<>();
		final City city = this.cityRepository.getOneById(Long.parseLong(cityId));
		aDistricts.add(districts.stream().filter(a -> OgumaProjectUtils.isEqual(a.getId(), city.getDistrictId()))
				.toList().get(0));
		aDistricts.addAll(districts);
		return aDistricts.stream().distinct().map(item -> {
			final Chiho chiho = this.chihoRepository.getOneById(item.getChihoId());
			return new DistrictDto(item.getId(), item.getName(), null, null, null, chiho.getName(), null, null);
		}).toList();
	}

	@Override
	public Pagination<DistrictDto> getDistrictsByKeyword(final Integer pageNum, final String keyword) {
		final int offset = (pageNum - 1) * PAGE_SIZE;
		final Integer totalRecords = this.districtRepository.countByKeyword(keyword);
		final List<DistrictDto> districtDtos = this.districtRepository.pagination(offset, PAGE_SIZE, keyword).stream()
				.map(item -> {
					final Chiho chiho = this.chihoRepository.getOneById(item.getChihoId());
					final City city = this.cityRepository.getOneById(item.getShutoId());
					final Long population = this.cityRepository.getListByForeignKey(item.getId()).stream()
							.map(City::getPopulation).reduce((a, v) -> (a + v)).get();
					return new DistrictDto(item.getId(), item.getName(), item.getShutoId(), city.getName(),
							item.getChihoId(), chiho.getName(), population, item.getDistrictFlag());
				}).toList();
		return Pagination.of(districtDtos, totalRecords, pageNum, PAGE_SIZE);
	}

	@Override
	public ResultDto<String> update(final DistrictDto districtDto) {
		// TODO Auto-generated method stub
		return null;
	}
}
