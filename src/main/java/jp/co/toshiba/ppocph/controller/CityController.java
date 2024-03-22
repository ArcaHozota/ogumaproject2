package jp.co.toshiba.ppocph.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.toshiba.ppocph.common.PgCrowdURLConstants;
import jp.co.toshiba.ppocph.dto.CityDto;
import jp.co.toshiba.ppocph.utils.Pagination;
import jp.co.toshiba.ppocph.utils.ResultDto;
import jp.co.toshiba.ppocph.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 都市コントローラ
 *
 * @author ArkamaHozota
 * @since 7.89
 */
@RestController
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CityController {

	/**
	 * 都市サービスインターフェス
	 */
	private final ICitySeivice iCityService;

	/**
	 * キーワードによってページング検索
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return ResultDto<Pagination<Role>>
	 */
	@GetMapping(PgCrowdURLConstants.URL_DISTRICT_PAGINATION)
	public ResultDto<Pagination<CityDto>> pagination(
			@RequestParam(name = "pageNum", defaultValue = "1") final Integer pageNum,
			@RequestParam(name = "keyword", defaultValue = StringUtils.EMPTY_STRING) final String keyword) {
		final Pagination<CityDto> cities = this.iCityService.getCitiesByKeyword(pageNum, keyword);
		return ResultDto.successWithData(cities);
	}
}
