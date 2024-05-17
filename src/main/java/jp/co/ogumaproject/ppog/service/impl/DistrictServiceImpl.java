package jp.co.ogumaproject.ppog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ogumaproject.ppog.dto.DistrictDto;
import jp.co.ogumaproject.ppog.service.IDistrictService;
import jp.co.ogumaproject.ppog.utils.Pagination;
import jp.co.ogumaproject.ppog.utils.ResultDto;
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
	@Override
	public List<DistrictDto> getDistrictsByCityId(final String cityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pagination<DistrictDto> getDistrictsByKeyword(final Integer pageNum, final String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultDto<String> update(final DistrictDto districtDto) {
		// TODO Auto-generated method stub
		return null;
	}
}
