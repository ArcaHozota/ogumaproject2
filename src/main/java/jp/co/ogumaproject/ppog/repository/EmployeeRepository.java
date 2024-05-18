package jp.co.ogumaproject.ppog.repository;

import jp.co.ogumaproject.ppog.entity.Employee;

/**
 * 社員リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
public interface EmployeeRepository extends CommonRepository<Employee> {

	/**
	 * アカウントによって1件を抽出する
	 *
	 * @param loginAccount アカウント
	 * @return Employee
	 */
	Employee getOneByLoginAccount(String loginAccount);
}
