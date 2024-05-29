package jp.co.ogumaproject.ppok.service.impl;

import org.springframework.stereotype.Service;

import jp.co.ogumaproject.ppok.common.OgumaProjectConstants;
import jp.co.ogumaproject.ppok.dto.CityDto;
import jp.co.ogumaproject.ppok.repository.CityRepository;
import jp.co.ogumaproject.ppok.service.ICityService;
import jp.co.ogumaproject.ppok.utils.Pagination;
import jp.co.ogumaproject.ppok.utils.ResultDto;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultDto<String> remove(final Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(final CityDto cityDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public ResultDto<String> update(final CityDto cityDto) {
		// TODO Auto-generated method stub
		return null;
	}
}
