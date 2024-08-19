package jp.co.ogumaproject.ppok.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jp.co.ogumaproject.ppok.entity.Employee;
import jp.co.ogumaproject.ppok.repository.EmployeeRepository;
import jp.co.ogumaproject.ppok.utils.OgumaProjectUtils;
import oracle.jdbc.driver.OracleSQLException;

/**
 * 社員リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
@Repository
@Transactional(rollbackFor = OracleSQLException.class)
public class EmployeeRepositoryImpl extends CommonRepositoryImpl<Employee> implements EmployeeRepository {

	@Override
	public Long countByKeyword(final String keyword) {
		final String sql = "SELECT COUNT(1) FROM PPOG_EMPLOYEES_VIEW PEV WHERE PEV.LOGIN_ACCOUNT LIKE ? OR PEV.USERNAME LIKE ? OR PEV.EMAIL LIKE ?";
		return this.commonCountByKeywords(sql, keyword, keyword, keyword);
	}

	@Override
	public Long countByName(final String name) {
		final String sql = "SELECT COUNT(1) FROM PPOG_EMPLOYEES PE WHERE PE.LOGIN_ACCOUNT = ?";
		return this.commonCountByKeywords(sql, name);
	}

	@Deprecated
	@Override
	public List<Employee> getList() {
		return null;
	}

	@Override
	public List<Employee> getListByForeignKey(final Long foreignKey) {
		final String sql = "SELECT PEV.* FROM PPOG_EMPLOYEES_VIEW PEV INNER JOIN PPOG_EMPLOYEES_ROLE_VIEW PERV ON PERV.EMPLOYEE_ID = PEV.ID　"
				+ "WHERE PERV.ROLE_ID = ?";
		return this.getCommonListByForeignKey(sql, foreignKey);
	}

	@Override
	public List<Employee> getListByIds(final List<Long> ids) {
		final String sql = "SELECT PEV.* FROM PPOG_EMPLOYEES_VIEW PEV WHERE PEV.ID IN (?)";
		return this.getCommonListByIds(sql, ids);
	}

	@Override
	public Employee getOneByEntity(final Employee aEntity) {
		final String sql = "SELECT PEV.* FROM PPOG_EMPLOYEES_VIEW PEV WHERE PEV.LOGIN_ACCOUNT =:loginAccount AND PEV.EMAIL =:email "
				+ "AND PEV.DATE_OF_BIRTH =:dateOfBirth";
		final Map<String, Object> paramMap = OgumaProjectUtils.getParamMap(aEntity);
		return this.getCommonOneByKeywords(sql, paramMap);
	}

	@Override
	public Employee getOneById(final Long id) {
		final String sql = "SELECT PEV.* FROM PPOG_EMPLOYEES_VIEW PEV WHERE PEV.ID = ?";
		return this.getCommonOneById(sql, id);
	}

	@Override
	public Employee getOneByLoginAccount(final String loginAccount) {
		final String sql = "SELECT PEV.* FROM PPOG_EMPLOYEES_VIEW PEV WHERE PEV.LOGIN_ACCOUNT = ? OR PEV.EMAIL = ?";
		return this.getCommonOneByKeywords(sql, loginAccount);
	}

	/**
	 * イニシャル
	 */
	@PostConstruct
	private void initial() {
		this.setEntityClass(Employee.class);
	}

	@Override
	public List<Employee> pagination(final Integer offset, final Integer pageSize, final String keyword) {
		final String sql = "SELECT PEV.* FROM PPOG_EMPLOYEES_VIEW PEV WHERE PEV.LOGIN_ACCOUNT LIKE ? OR PEV.USERNAME LIKE ? "
				+ "OR PEV.EMAIL LIKE ? ORDER BY PEV.ID ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		return this.getCommonListByKeywords(sql, keyword, keyword, keyword, offset, pageSize);
	}

	@Override
	public void removeById(final Employee aEntity) {
		final String sql = "UPDATE PPOG_EMPLOYEES PE SET PE.DEL_FLG =:delFlg WHERE PE.ID =:id";
		this.commonModifyById(sql, aEntity);
	}

	@Override
	public void saveById(final Employee aEntity) {
		final String sql = "INSERT INTO PPOG_EMPLOYEES PE (PE.ID, PE.LOGIN_ACCOUNT, PE.PASSWORD, PE.USERNAME, PE.EMAIL, PE.CREATED_TIME, PE.DATE_OF_BIRTH, PE.DEL_FLG) "
				+ "VALUES (:id, :loginAccount, :password, :username, :email, :createdTime, :dateOfBirth, :delFlg)";
		this.commonModifyById(sql, aEntity);
	}

	@Override
	public void updateById(final Employee aEntity) {
		final String sql = "UPDATE PPOG_EMPLOYEES PE SET PE.LOGIN_ACCOUNT =:loginAccount, PE.PASSWORD =:password, PE.USERNAME =:username, "
				+ "PE.EMAIL =:email, PE.CREATED_TIME =:createdTime, PE.DATE_OF_BIRTH =:dateOfBirth WHERE PE.DEL_FLG =:delFlg AND PE.ID =:id";
		this.commonModifyById(sql, aEntity);
	}
}
