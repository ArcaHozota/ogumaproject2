package jp.co.ogumaproject.ppok.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ogumaproject.ppok.common.OgumaProjectURLConstants;
import jp.co.ogumaproject.ppok.dto.CityDto;
import jp.co.ogumaproject.ppok.dto.DistrictDto;
import jp.co.ogumaproject.ppok.entity.Chiho;
import jp.co.ogumaproject.ppok.service.IDistrictService;
import jp.co.ogumaproject.ppok.utils.OgumaProjectUtils;
import jp.co.ogumaproject.ppok.utils.Pagination;
import jp.co.ogumaproject.ppok.utils.ResultDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 地域コントローラ
 *
 * @author ArkamaHozota
 * @since 7.82
 */
@RestController
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DistrictController {

	/**
	 * 地域サービスインターフェス
	 */
	private final IDistrictService iDistrictService;

	/**
	 * 編集権限チェック
	 *
	 * @return ResultDto<String>
	 */
	@GetMapping(OgumaProjectURLConstants.URL_DISTRICT_CHECK_EDITION)
	public ResultDto<String> checkEdition() {
		return ResultDto.successWithoutData();
	}

	/**
	 * 地方リストを取得する
	 *
	 * @param chihoName 地方名称
	 * @return ResultDto<List<Chiho>>
	 */
	@GetMapping(OgumaProjectURLConstants.URL_DISTRICT_CHIHOS)
	public ResultDto<List<Chiho>> getChihos(@RequestParam("chihoName") final String chihoName) {
		final List<Chiho> chihos = this.iDistrictService.getChihos(chihoName);
		return ResultDto.successWithData(chihos);
	}

	/**
	 * 州都リストを取得する
	 *
	 * @param districtDto 地域情報転送クラス
	 * @return ResultDto<List<CityDto>>
	 */
	@PostMapping(OgumaProjectURLConstants.URL_DISTRICT_SHUTOS)
	public ResultDto<List<CityDto>> getShutos(@RequestBody final DistrictDto districtDto) {
		final List<CityDto> cityDtos = this.iDistrictService.getShutos(districtDto);
		return ResultDto.successWithData(cityDtos);
	}

	/**
	 * キーワードによってページング検索
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return ResultDto<Pagination<Role>>
	 */
	@GetMapping(OgumaProjectURLConstants.URL_DISTRICT_PAGINATION)
	public ResultDto<Pagination<DistrictDto>> pagination(
			@RequestParam(name = "pageNum", defaultValue = "1") final Integer pageNum,
			@RequestParam(name = "keyword", defaultValue = OgumaProjectUtils.EMPTY_STRING) final String keyword) {
		final Pagination<DistrictDto> districts = this.iDistrictService.getDistrictsByKeyword(pageNum, keyword);
		return ResultDto.successWithData(districts);
	}

	/**
	 * 地域情報更新
	 *
	 * @param districtDto 地域情報転送クラス
	 * @return ResultDto<String>
	 */
	@PutMapping(OgumaProjectURLConstants.URL_DISTRICT_UPDATE)
	public ResultDto<String> updateInfo(@RequestBody final DistrictDto districtDto) {
		return this.iDistrictService.update(districtDto);
	}
}
