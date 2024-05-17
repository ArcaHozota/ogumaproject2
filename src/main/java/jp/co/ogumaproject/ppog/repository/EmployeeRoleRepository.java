package jp.co.ogumaproject.ppog.repository;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppog.entity.EmployeeRole;

/**
 * 社員役割リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
@Repository
public class EmployeeRoleRepository implements CommonRepository<EmployeeRole> {

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	@Override
	public List<EmployeeRole> getListByIds(final List<Long> ids) {
		return this.jdbcClient.sql("SELECT PERV.* FROM PPOG_EMPLOYEE_ROLE_VIEW PERV WHERE PERV.EMPLOYEE_ID IN (?)")
				.params(ids).query(EmployeeRole.class).list();
	}

	@Override
	public EmployeeRole getOneById(final Long id) {
		return this.jdbcClient.sql("SELECT PERV.* FROM PPOG_EMPLOYEE_ROLE_VIEW PERV WHERE PERV.EMPLOYEE_ID = ?")
				.param(id).query(EmployeeRole.class).single();
	}
}
