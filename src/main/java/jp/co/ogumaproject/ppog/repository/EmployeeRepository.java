package jp.co.ogumaproject.ppog.repository;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppog.entity.Employee;

/**
 * 社員リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
@Repository
public final class EmployeeRepository implements CommonRepository<Employee> {

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	@Override
	public List<Employee> getListByForeignKey(final Long foreignId) {
		return null;
	}

	@Override
	public Employee getOneById(final Long id) {
		return this.jdbcClient.sql("SELECT PEV.* FROM PPOG_EMPLOYEE_VIEW PEV WHERE PEV.ID = ?").param(id)
				.query(Employee.class).single();
	}

	/**
	 * アカウントによって1件を抽出する
	 *
	 * @param loginAccount アカウント
	 * @return Employee
	 */
	public Employee getOneByLoginAccount(final String loginAccount) {
		return this.jdbcClient
				.sql("SELECT PEV.* FROM PPOG_EMPLOYEE_VIEW PEV WHERE PEV.LOGIN_ACCOUNT = ? OR PEV.USERNAME = ?")
				.params(loginAccount, loginAccount).query(Employee.class).single();
	}
}
