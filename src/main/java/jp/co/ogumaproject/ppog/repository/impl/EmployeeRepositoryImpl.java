package jp.co.ogumaproject.ppog.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppog.entity.Employee;
import jp.co.ogumaproject.ppog.repository.EmployeeRepository;

/**
 * 社員リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	@Override
	public List<Employee> getListByForeignKey(final Long foreignKey) {
		return this.jdbcClient.sql(
				"SELECT PEV.* FROM PPOG_EMPLOYEE_VIEW PEV INNER JOIN PPOG_EMPLOYEE_ROLE_VIEW PERV ON PERV.EMPLOYEE_ID = PEV.ID　"
						+ "WHERE PERV.ROLE_ID = ?")
				.param(foreignKey).query(Employee.class).list();
	}

	@Override
	public List<Employee> getListByIds(final List<Long> ids) {
		return this.jdbcClient.sql("SELECT PEV.* FROM PPOG_EMPLOYEE_VIEW PEV WHERE PEV.ID IN (?)").params(ids)
				.query(Employee.class).list();
	}

	@Override
	public Employee getOneById(final Long id) {
		return this.jdbcClient.sql("SELECT PEV.* FROM PPOG_EMPLOYEE_VIEW PEV WHERE PEV.ID = ?").param(id)
				.query(Employee.class).single();
	}

	@Override
	public Employee getOneByLoginAccount(final String loginAccount) {
		return this.jdbcClient
				.sql("SELECT PEV.* FROM PPOG_EMPLOYEE_VIEW PEV WHERE PEV.LOGIN_ACCOUNT = ? OR PEV.USERNAME = ?")
				.params(loginAccount, loginAccount).query(Employee.class).single();
	}
}
