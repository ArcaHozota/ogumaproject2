package jp.co.ogumaproject.ppog.service.impl;

import org.springframework.stereotype.Service;

import jp.co.ogumaproject.ppog.dto.CityDto;
import jp.co.ogumaproject.ppog.service.ICityService;
import jp.co.ogumaproject.ppog.utils.Pagination;
import jp.co.ogumaproject.ppog.utils.ResultDto;
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
	@Override
	public ResultDto<String> checkDuplicated(final String name, final Long districtId) {
		// TODO Auto-generated method stub
		return null;
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
