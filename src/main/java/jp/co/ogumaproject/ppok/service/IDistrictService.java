package jp.co.ogumaproject.ppok.service;

import java.util.List;

import jp.co.ogumaproject.ppok.dto.CityDto;
import jp.co.ogumaproject.ppok.dto.DistrictDto;
import jp.co.ogumaproject.ppok.entity.Chiho;
import jp.co.ogumaproject.ppok.utils.Pagination;
import jp.co.ogumaproject.ppok.utils.ResultDto;

/**
 * 地域サービスインターフェス
 *
 * @author ArkamaHozota
 * @since 7.81
 */
public interface IDistrictService {

	/**
	 * 地方リストを取得する
	 *
	 * @param chihoName 地方名称
	 * @return List<Chiho>
	 */
	List<Chiho> getChihos(String chihoName);

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
	 * 州都リストを取得する
	 *
	 * @param districtDto 地域情報転送クラス
	 * @return List<CityDto>
	 */
	List<CityDto> getShutos(DistrictDto districtDto);

	/**
	 * 地域情報更新
	 *
	 * @param districtDto 地域情報転送クラス
	 * @return ResultDto<String>
	 */
	ResultDto<String> update(DistrictDto districtDto);
}
