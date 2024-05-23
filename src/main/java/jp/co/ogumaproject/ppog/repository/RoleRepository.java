package jp.co.ogumaproject.ppog.repository;

import jp.co.ogumaproject.ppog.entity.Role;

/**
 * 役割リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.73
 */
public interface RoleRepository extends CommonRepository<Role> {

	/**
	 * 名称によって1件を抽出する
	 *
	 * @param name 名称
	 * @return Employee
	 */
	Role getOneByName(String name);
}
