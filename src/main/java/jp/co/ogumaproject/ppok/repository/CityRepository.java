package jp.co.ogumaproject.ppok.repository;

import java.util.List;

import jp.co.ogumaproject.ppok.entity.City;

/**
 * 都市リポジトリ
 *
 * @author ArkamaHozota
 * @since 10.0.1
 */
public interface CityRepository extends CommonRepository<City> {

	/**
	 * キーワードによって件数をカウントする
	 *
	 * @param keyword 検索キーワード
	 * @return Integer
	 */
	Integer countByKeyword(String keyword);

	/**
	 * 名称によってレコード数を計算する
	 *
	 * @param name 社員名称
	 * @return Integer
	 */
	Integer countByName(String name, Long districtId);

	/**
	 * パージング検索
	 *
	 * @param offset   オフセット
	 * @param pageSize ページサイズ
	 * @param keyword  検索キーワード
	 * @return List<District>
	 */
	List<City> pagination(Integer offset, Integer pageSize, String keyword);
}
