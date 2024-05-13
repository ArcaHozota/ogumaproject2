package jp.co.toshiba.ppocph.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.toshiba.ppocph.common.OgumaProjectURLConstants;
import jp.co.toshiba.ppocph.dto.DistrictDto;
import jp.co.toshiba.ppocph.service.IDistrictService;
import jp.co.toshiba.ppocph.utils.CommonProjectUtils;
import jp.co.toshiba.ppocph.utils.Pagination;
import jp.co.toshiba.ppocph.utils.ResultDto;
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
	 * キーワードによってページング検索
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return ResultDto<Pagination<Role>>
	 */
	@GetMapping(OgumaProjectURLConstants.URL_DISTRICT_PAGINATION)
	public ResultDto<Pagination<DistrictDto>> pagination(
			@RequestParam(name = "pageNum", defaultValue = "1") final Integer pageNum,
			@RequestParam(name = "keyword", defaultValue = CommonProjectUtils.EMPTY_STRING) final String keyword) {
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
