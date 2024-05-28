package jp.co.ogumaproject.ppok.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ogumaproject.ppok.dto.DistrictDto;
import jp.co.ogumaproject.ppok.entity.Chiho;
import jp.co.ogumaproject.ppok.entity.District;
import jp.co.ogumaproject.ppok.repository.ChihoRepository;
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
	 * 地方リポジトリ
	 */
	private final ChihoRepository chihoRepository;

	/**
	 * 地域リポジトリ
	 */
	private final DistrictRepository districtRepository;

	@Override
	public List<DistrictDto> getDistrictsByCityId(final String cityId) {
		final List<District> districts = this.districtRepository.getList();
		if (!OgumaProjectUtils.isDigital(cityId)) {
			return districts.stream().map(item -> {
				final Chiho chiho = this.chihoRepository.getOneById(item.getChihoId());
				return new DistrictDto(item.getId(), item.getName(), null, null, null, chiho.getName(), null, null);
			}).toList();
		}
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
