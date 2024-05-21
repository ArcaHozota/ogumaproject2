package jp.co.ogumaproject.ppog.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppog.entity.RoleAuth;
import jp.co.ogumaproject.ppog.repository.RoleAuthRepository;
import jp.co.ogumaproject.ppog.utils.CommonProjectUtils;

/**
 * 役割権限リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.77
 */
@Repository
public class RoleAuthRepositoryImpl implements RoleAuthRepository {

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	@Override
	public List<RoleAuth> getListByForeignKey(final Long foreignKey) {
		return this.jdbcClient.sql("SELECT PRAV.* FROM PPOG_ROLE_AUTH_VIEW PRAV WHERE PRAV.ROLE_ID = ?")
				.param(foreignKey).query(RoleAuth.class).list();
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

	@Override
	public void saveById(final RoleAuth aEntity) {
		final Map<String, Object> paramMap = CommonProjectUtils.getParamMap(aEntity);
		this.jdbcClient.sql("INSERT INTO PPOG_ROLE_AUTH PRA (PRA.ROLE_ID, PRA.AUTH_ID) VALUES (:roleId, :authId)")
				.params(paramMap).update();
	}

	@Deprecated
	@Override
	public void updateById(final RoleAuth aEntity) {
	}

}
