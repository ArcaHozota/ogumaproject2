package jp.co.ogumaproject.ppok.repository;

import jp.co.ogumaproject.ppok.entity.Employee;

/**
 * 社員リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
public interface EmployeeRepository extends CommonRepository<Employee> {

	/**
	 * 名称によってレコード数を計算する
	 *
	 * @param name 社員名称
	 * @return Integer
	 */
	Integer countByName(String name);

	/**
	 * エンティティによって1件を抽出する
	 *
	 * @param aEntity エンティティ
	 * @return Employee
	 */
	Employee getOneByEntity(Employee aEntity);

	/**
	 * アカウントによって1件を抽出する
	 *
	 * @param loginAccount アカウント
	 * @return Employee
	 */
	Employee getOneByLoginAccount(String loginAccount);
}
