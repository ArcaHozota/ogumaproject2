package jp.co.ogumaproject.ppog.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppog.entity.Role;
import jp.co.ogumaproject.ppog.repository.RoleRepository;
import jp.co.ogumaproject.ppog.utils.CommonProjectUtils;

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
		return this.jdbcClient.sql("SELECT PRV.* FROM PPOG_ROLE_VIEW PRV WHERE PRV.ID IN (?)").params(ids)
				.query(Role.class).list();
	}

	@Override
	public Role getOneById(final Long id) {
		return this.jdbcClient.sql("SELECT PRV.* FROM PPOG_ROLE_VIEW PRV WHERE PRV.ID = ?").param(id).query(Role.class)
				.single();
	}

	@Override
	public void removeById(final Role aEntity) {
		final Map<String, Object> paramMap = CommonProjectUtils.getParamMap(aEntity);
		this.jdbcClient.sql("UPDATE PPOG_ROLE PR SET PR.DEL_FLG =:delFlg WHERE PR.ID =:id").params(paramMap).update();
	}

	@Override
	public void saveById(final Role aEntity) {
		final Map<String, Object> paramMap = CommonProjectUtils.getParamMap(aEntity);
		this.jdbcClient.sql("INSERT INTO PPOG_ROLE PR (PR.ID, PR.NAME, PR.DEL_FLG) VALUES (:id, :name, :delFlg)")
				.params(paramMap).update();
	}

	@Override
	public void updateById(final Role aEntity) {
		final Map<String, Object> paramMap = CommonProjectUtils.getParamMap(aEntity);
		this.jdbcClient.sql("UPDATE PPOG_ROLE PR SET PR.NAME =:name WHERE PR.DEL_FLG =:delFlg AND PR.ID =:id")
				.params(paramMap).update();
	}
}
