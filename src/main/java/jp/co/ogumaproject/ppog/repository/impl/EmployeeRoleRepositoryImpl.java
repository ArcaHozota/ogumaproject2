package jp.co.ogumaproject.ppog.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppog.entity.EmployeeRole;
import jp.co.ogumaproject.ppog.repository.EmployeeRoleRepository;
import jp.co.ogumaproject.ppog.utils.CommonProjectUtils;

/**
 * 社員役割リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
@Repository
public class EmployeeRoleRepositoryImpl implements EmployeeRoleRepository {

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	@Deprecated
	@Override
	public List<EmployeeRole> getListByForeignKey(final Long foreignKey) {
		return null;
	}

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

	@Override
	public void removeById(final EmployeeRole aEntity) {
		final Map<String, Object> paramMap = CommonProjectUtils.getParamMap(aEntity);
		this.jdbcClient.sql("DELETE FROM PPOG_EMPLOYEE_ROLE PER WHERE PER.EMPLOYEE_ID =:employeeId").params(paramMap)
				.update();
	}

	@Override
	public void saveById(final EmployeeRole aEntity) {
		final Map<String, Object> paramMap = CommonProjectUtils.getParamMap(aEntity);
		this.jdbcClient
				.sql("INSERT INTO PPOG_EMPLOYEE_ROLE PER (PER.EMPLOYEE_ID, PER.ROLE_ID) VALUES (:employeeId, :roleId)")
				.params(paramMap).update();
	}

	@Override
	public void updateById(final EmployeeRole aEntity) {
		final Map<String, Object> paramMap = CommonProjectUtils.getParamMap(aEntity);
		this.jdbcClient.sql("UPDATE PPOG_EMPLOYEE_ROLE PER SET PER.ROLE_ID =:roleId WHERE PER.EMPLOYEE_ID =:employeeId")
				.params(paramMap).update();
	}
}
