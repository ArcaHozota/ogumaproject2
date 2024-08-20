package jp.co.ogumaproject.ppok.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ogumaproject.ppok.entity.RoleAuth;
import jp.co.ogumaproject.ppok.repository.RoleAuthRepository;
import oracle.jdbc.driver.OracleSQLException;

/**
 * 役割権限リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.77
 */
@Repository
@Transactional(rollbackFor = OracleSQLException.class)
public class RoleAuthRepositoryImpl extends CommonRepositoryImpl<RoleAuth> implements RoleAuthRepository {

	@Override
	public void batchRemoveByForeignKey(final Long foreignKey) {
		final String sql = "DELETE FROM PPOG_ROLE_AUTH PRA WHERE PRA.ROLE_ID = ?";
		this.commonModifyByKeywords(sql, foreignKey);
	}

	@Deprecated
	@Override
	public List<RoleAuth> getList() {
		return null;
	}

	@Override
	public List<RoleAuth> getListByForeignKey(final Long foreignKey) {
		final String sql = "SELECT PRAV.* FROM PPOG_ROLE_AUTH_VIEW PRAV WHERE PRAV.ROLE_ID = ?";
		return this.getCommonListByKeywords(sql, foreignKey);
	}

	@Deprecated
	@Override
	public List<RoleAuth> getListByIds(final List<Long> ids) {
		return null;
	}

	@Deprecated
	@Override
	public RoleAuth getOneById(final Long id) {
		return null;
	}

	@Deprecated
	@Override
	public void removeById(final RoleAuth aEntity) {
	}

	@Override
	public void saveById(final RoleAuth aEntity) {
		final String sql = "INSERT INTO PPOG_ROLE_AUTH PRA (PRA.ROLE_ID, PRA.AUTH_ID) VALUES (:roleId, :authId)";
		this.commonModifyById(sql, aEntity);
	}

	@Deprecated
	@Override
	public void updateById(final RoleAuth aEntity) {
	}
}
