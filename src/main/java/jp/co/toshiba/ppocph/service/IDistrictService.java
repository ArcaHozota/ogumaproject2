package jp.co.toshiba.ppocph.service;

import jp.co.toshiba.ppocph.dto.DistrictDto;
import jp.co.toshiba.ppocph.utils.Pagination;

/**
 * 地域サービスインターフェス
 *
 * @author ArkamaHozota
 * @since 7.81
 */
public interface IDistrictService {

	/**
	 * キーワードによって地域情報を取得する
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return Pagination<DistrictDto>
	 */
	Pagination<DistrictDto> getDistrictsByKeyword(Integer pageNum, String keyword);
}
