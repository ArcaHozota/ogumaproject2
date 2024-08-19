package jp.co.ogumaproject.ppok.repository;

import java.util.List;

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
	Long countByKeyword(String keyword);

	/**
	 * 州都IDによって件数をカウントする
	 *
	 * @param shutoId 州都ID
	 * @return Integer
	 */
	Long countByShutoId(Long shutoId);

	/**
	 * パージング検索
	 *
	 * @param offset   オフセット
	 * @param pageSize ページサイズ
	 * @param keyword  検索キーワード
	 * @return List<District>
	 */
	List<District> pagination(Integer offset, Integer pageSize, String keyword);
}
