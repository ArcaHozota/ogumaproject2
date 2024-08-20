package jp.co.ogumaproject.ppok.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ogumaproject.ppok.entity.EmployeeRole;
import jp.co.ogumaproject.ppok.repository.EmployeeRoleRepository;
import oracle.jdbc.driver.OracleSQLException;

/**
 * 社員役割リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
@Repository
@Transactional(rollbackFor = OracleSQLException.class)
public class EmployeeRoleRepositoryImpl extends CommonRepositoryImpl<EmployeeRole> implements EmployeeRoleRepository {

	/**
	 * コンストラクタ
	 *
	 * @param aClass エンティティクラス
	 */
	protected EmployeeRoleRepositoryImpl(final Class<EmployeeRole> aClass) {
		super(aClass);
	}

	@Deprecated
	@Override
	public List<EmployeeRole> getList() {
		return null;
	}

	@Deprecated
	@Override
	public List<EmployeeRole> getListByForeignKey(final Long foreignKey) {
		return null;
	}

	@Override
	public List<EmployeeRole> getListByIds(final List<Long> ids) {
		final String sql = "SELECT PERV.* FROM PPOG_EMPLOYEE_ROLE_VIEW PERV WHERE PERV.EMPLOYEE_ID IN (?)";
		return this.getCommonListByIds(sql, ids);
	}

	@Override
	public EmployeeRole getOneById(final Long id) {
		final String sql = "SELECT PERV.* FROM PPOG_EMPLOYEE_ROLE_VIEW PERV WHERE PERV.EMPLOYEE_ID = ?";
		return this.getCommonOneById(sql, id);
	}

	@Override
	public void removeById(final EmployeeRole aEntity) {
		final String sql = "DELETE FROM PPOG_EMPLOYEE_ROLE PER WHERE PER.EMPLOYEE_ID =:employeeId";
		this.commonModifyById(sql, aEntity);
	}

	@Override
	public void saveById(final EmployeeRole aEntity) {
		final String sql = "INSERT INTO PPOG_EMPLOYEE_ROLE PER (PER.EMPLOYEE_ID, PER.ROLE_ID) VALUES (:employeeId, :roleId)";
		this.commonModifyById(sql, aEntity);
	}

	@Override
	public void updateById(final EmployeeRole aEntity) {
		final String sql = "UPDATE PPOG_EMPLOYEE_ROLE PER SET PER.ROLE_ID =:roleId WHERE PER.EMPLOYEE_ID =:employeeId";
		this.commonModifyById(sql, aEntity);
	}
}
