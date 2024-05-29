package jp.co.ogumaproject.ppok.repository;

import java.util.List;

import jp.co.ogumaproject.ppok.entity.Role;

/**
 * 役割リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.73
 */
public interface RoleRepository extends CommonRepository<Role> {

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
	Integer countByName(String name);

	/**
	 * 名称によって1件を抽出する
	 *
	 * @param name 名称
	 * @return Employee
	 */
	Role getOneByName(String name);

	/**
	 * パージング検索
	 *
	 * @param offset   オフセット
	 * @param pageSize ページサイズ
	 * @param keyword  検索キーワード
	 * @return List<DistrictDto>
	 */
	List<Role> pagination(Integer offset, Integer pageSize, String keyword);
}
