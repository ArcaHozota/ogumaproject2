package jp.co.toshiba.ppocph.service;

import jp.co.toshiba.ppocph.dto.CityDto;
import jp.co.toshiba.ppocph.utils.Pagination;
import jp.co.toshiba.ppocph.utils.ResultDto;

/**
 * 地域サービスインターフェス
 *
 * @author ArkamaHozota
 * @since 7.89
 */
public interface ICityService {

	ResultDto<String> check(String name, Long districtId);

	Pagination<CityDto> getCitiesByKeyword(Integer pageNum, String keyword);

	void save(CityDto cityDto);

	ResultDto<String> update(CityDto cityDto);
}
