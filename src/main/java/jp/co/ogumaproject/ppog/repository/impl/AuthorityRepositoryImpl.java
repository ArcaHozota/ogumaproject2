package jp.co.ogumaproject.ppog.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppog.entity.Authority;
import jp.co.ogumaproject.ppog.repository.AuthorityRepository;

/**
 * 権限リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
@Repository
public class AuthorityRepositoryImpl implements AuthorityRepository {

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	@Deprecated
	@Override
	public Integer countByName(final String name) {
		return null;
	}

	@Override
	public List<Authority> getListByForeignKey(final Long foreignKey) {
		return this.jdbcClient.sql(
				"SELECT PAV.* FROM PPOG_AUTHORITY_VIEW PAV INNER JOIN PPOG_ROLE_AUTH_VIEW PRAV ON PRAV.AUTH_ID = PAV.ID WHERE PRAV.ROLE_ID = ?")
				.param(foreignKey).query(Authority.class).list();
	}

	@Override
	public List<Authority> getListByIds(final List<Long> ids) {
		return this.jdbcClient.sql("SELECT PAV.* FROM PPOG_AUTHORITY_VIEW PAV WHERE PAV.ID IN (?)").params(ids)
				.query(Authority.class).list();
	}

	@Override
	public Authority getOneById(final Long id) {
		return this.jdbcClient.sql("SELECT PAV.* FROM PPOG_AUTHORITY_VIEW PAV WHERE PAV.ID = ?").param(id)
				.query(Authority.class).single();
	}

	@Deprecated
	@Override
	public void removeById(final Authority aEntity) {
	}

	@Deprecated
	@Override
	public void saveById(final Authority aEntity) {
	}

	@Deprecated
	@Override
	public void updateById(final Authority aEntity) {
	}
}
