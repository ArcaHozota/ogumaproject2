package jp.co.toshiba.ppocph.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.toshiba.ppocph.dto.CityDto;
import jp.co.toshiba.ppocph.dto.DistrictDto;
import jp.co.toshiba.ppocph.service.ICityService;
import jp.co.toshiba.ppocph.service.IDistrictService;
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
	private final ICityService iCityService;

	/**
	 * 地域サービスインターフェス
	 */
	private final IDistrictService iDistrictService;

	/**
	 * 都市名称重複チェック
	 *
	 * @param cityDto 都市情報転送クラス
	 * @return ResultDto<String>
	 */
	@GetMapping("/check")
	public ResultDto<String> checkDuplicated(
			@RequestParam(value = "name", defaultValue = StringUtils.EMPTY_STRING) final String name,
			@RequestParam("districtId") final Long districtId) {
		return this.iCityService.check(name, districtId);
	}

	/**
	 * 編集権限チェック
	 *
	 * @return ResultDto<String>
	 */
	@GetMapping("/checkEdition")
	public ResultDto<String> checkEdition() {
		return ResultDto.successWithoutData();
	}

	/**
	 * 地域情報初期表示
	 *
	 * @param cityId 都市ID
	 * @return ResultDto<String>
	 */
	@GetMapping("/districtlist")
	public ResultDto<List<DistrictDto>> getDistrictList(@RequestParam(value = "cityId") final String cityId) {
		final List<DistrictDto> districtDtos = this.iDistrictService.getDistrictsByCityId(cityId);
		return ResultDto.successWithData(districtDtos);
	}

	/**
	 * キーワードによってページング検索
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return ResultDto<Pagination<CityDto>>
	 */
	@GetMapping("/pagination")
	public ResultDto<Pagination<CityDto>> pagination(
			@RequestParam(name = "pageNum", defaultValue = "1") final Integer pageNum,
			@RequestParam(name = "keyword", defaultValue = StringUtils.EMPTY_STRING) final String keyword) {
		final Pagination<CityDto> cities = this.iCityService.getCitiesByKeyword(pageNum, keyword);
		return ResultDto.successWithData(cities);
	}

	/**
	 * 情報追加
	 *
	 * @param cityDto 都市情報DTO
	 * @return ResultDto<String>
	 */
	@PostMapping("/infosave")
	public ResultDto<String> saveInfo(@RequestBody final CityDto cityDto) {
		this.iCityService.save(cityDto);
		return ResultDto.successWithoutData();
	}

	/**
	 * 情報更新
	 *
	 * @param cityDto 都市情報DTO
	 * @return ResultDto<String>
	 */
	@PutMapping("/infoupd")
	public ResultDto<String> updateInfo(@RequestBody final CityDto cityDto) {
		return this.iCityService.update(cityDto);
	}
}
