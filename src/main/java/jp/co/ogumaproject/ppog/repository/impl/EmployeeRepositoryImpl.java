package jp.co.ogumaproject.ppog.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppog.entity.Employee;
import jp.co.ogumaproject.ppog.repository.EmployeeRepository;
import jp.co.ogumaproject.ppog.utils.CommonProjectUtils;

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
	public Integer countByKeyword(final String keyword) {
		return this.jdbcClient.sql(
				"SELECT COUNT(1) FROM PPOG_EMPLOYEE_VIEW PEV WHERE PEV.LOGIN_ACCOUNT LIKE ? OR PEV.USERNAME LIKE ? OR PEV.EMAIL LIKE ?")
				.param(keyword).query(Integer.class).single();
	}

	@Override
	public Integer countByName(final String name) {
		return this.jdbcClient.sql("SELECT COUNT(1) FROM PPOG_EMPLOYEE PE WHERE PE.LOGIN_ACCOUNT = ?").param(name)
				.query(Integer.class).single();
	}

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
				.param(loginAccount).query(Employee.class).single();
	}

	@Override
	public List<Employee> pagination(final Integer offset, final Integer pageSize, final String keyword) {
		return this.jdbcClient
				.sql("SELECT PEV.* FROM PPOG_EMPLOYEE_VIEW PEV WHERE PEV.LOGIN_ACCOUNT LIKE ? OR PEV.USERNAME LIKE ? "
						+ "OR PEV.EMAIL LIKE ? ORDER BY PEV.ID ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY")
				.params(keyword, keyword, keyword, offset, pageSize).query(Employee.class).list();
	}

	@Override
	public void removeById(final Employee aEntity) {
		final Map<String, Object> paramMap = CommonProjectUtils.getParamMap(aEntity);
		this.jdbcClient.sql("UPDATE PPOG_EMPLOYEE PE SET PE.DEL_FLG =:delFlg WHERE PE.ID =:id").params(paramMap)
				.update();
	}

	@Override
	public void saveById(final Employee aEntity) {
		final Map<String, Object> paramMap = CommonProjectUtils.getParamMap(aEntity);
		this.jdbcClient.sql(
				"INSERT INTO PPOG_EMPLOYEE PE (PE.ID, PE.LOGIN_ACCOUNT, PE.PASSWORD, PE.USERNAME, PE.EMAIL, PE.CREATED_TIME, PE.DATE_OF_BIRTH, PE.DEL_FLG) "
						+ "VALUES (:id, :loginAccount, :password, :username, :email, :createdTime, :dateOfBirth, :delFlg)")
				.params(paramMap).update();
	}

	@Override
	public void updateById(final Employee aEntity) {
		final Map<String, Object> paramMap = CommonProjectUtils.getParamMap(aEntity);
		this.jdbcClient.sql(
				"UPDATE PPOG_EMPLOYEE PE SET PE.LOGIN_ACCOUNT =:loginAccount, PE.PASSWORD =:password, PE.USERNAME =:username, "
						+ "PE.EMAIL =:email, PE.CREATED_TIME =:createdTime, PE.DATE_OF_BIRTH =:dateOfBirth WHERE PE.DEL_FLG =:delFlg AND PE.ID =:id")
				.params(paramMap).update();
	}
}
