package jp.co.ogumaproject.ppog.service;

import java.util.List;

import jp.co.ogumaproject.ppog.dto.DistrictDto;
import jp.co.ogumaproject.ppog.utils.Pagination;
import jp.co.ogumaproject.ppog.utils.ResultDto;

/**
 * 地域サービスインターフェス
 *
 * @author ArkamaHozota
 * @since 7.81
 */
public interface IDistrictService {

	/**
	 * 都市IDによって地域一覧を取得する
	 *
	 * @param cityId 都市ID
	 * @return List<DistrictDto>
	 */
	List<DistrictDto> getDistrictsByCityId(String cityId);

	/**
	 * キーワードによって地域情報を取得する
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return Pagination<DistrictDto>
	 */
	Pagination<DistrictDto> getDistrictsByKeyword(Integer pageNum, String keyword);

	/**
	 * 地域情報更新
	 *
	 * @param districtDto 地域情報転送クラス
	 * @return ResultDto<String>
	 */
	ResultDto<String> update(DistrictDto districtDto);
}
