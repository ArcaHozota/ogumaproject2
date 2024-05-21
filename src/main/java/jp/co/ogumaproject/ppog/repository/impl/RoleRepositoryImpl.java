package jp.co.ogumaproject.ppog.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppog.entity.Role;
import jp.co.ogumaproject.ppog.repository.RoleRepository;

/**
 * 役割リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.73
 */
@Repository
public class RoleRepositoryImpl implements RoleRepository {

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	@Override
	public List<Role> getListByForeignKey(final Long foreignKey) {
		return this.jdbcClient.sql(
				"SELECT PRV.* FROM PPOG_ROLE_VIEW PRV INNER JOIN PPOG_ROLE_AUTH_VIEW PRAV ON PRAV.ROLE_ID = PRV.ID WHERE PRAV.AUTH_ID = ?")
				.params(foreignKey).query(Role.class).list();
	}

	@Override
	public List<Role> getListByIds(final List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role getOneById(final Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveById(final Role aEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateById(final Role aEntity) {
		// TODO Auto-generated method stub

	}

}
