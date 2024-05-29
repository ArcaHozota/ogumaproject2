package jp.co.ogumaproject.ppok.repository;

import java.util.List;

import jp.co.ogumaproject.ppok.dto.DistrictDto;
import jp.co.ogumaproject.ppok.entity.District;

/**
 * 地域リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.95
 */
public interface DistrictRepository extends CommonRepository<District> {

	/**
	 * キーワードによって件数をカウントする
	 *
	 * @param keyword 検索キーワード
	 * @return Integer
	 */
	Integer countByKeyword(String keyword);

	/**
	 * パージング検索
	 *
	 * @param offset   オフセット
	 * @param pageSize ページサイズ
	 * @param keyword  検索キーワード
	 * @return List<DistrictDto>
	 */
	List<DistrictDto> pagination(Integer offset, Integer pageSize, String keyword);
}
