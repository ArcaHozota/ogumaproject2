package jp.co.ogumaproject.ppok.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppok.entity.Authority;
import jp.co.ogumaproject.ppok.repository.AuthorityRepository;
import oracle.jdbc.driver.OracleSQLException;

/**
 * 権限リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
@Repository
@Transactional(rollbackFor = OracleSQLException.class)
public class AuthorityRepositoryImpl extends CommonRepositoryImpl<Authority> implements AuthorityRepository {

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	@Override
	public List<Authority> getList() {
		final String aSQL = "SELECT PAV.* FROM PPOG_AUTHORITIES_VIEW PAV ORDER BY PAV.ID ASC";
		return this.getCommonList(aSQL, Authority.class);
	}

	@Override
	public List<Authority> getListByForeignKey(final Long foreignKey) {
		return this.jdbcClient.sql(
				"SELECT PAV.* FROM PPOG_AUTHORITIES_VIEW PAV INNER JOIN PPOG_ROLE_AUTH_VIEW PRAV ON PRAV.AUTH_ID = PAV.ID WHERE PRAV.ROLE_ID = ?")
				.param(foreignKey).query(Authority.class).list();
	}

	@Override
	public List<Authority> getListByIds(final List<Long> ids) {
		return this.jdbcClient.sql("SELECT PAV.* FROM PPOG_AUTHORITIES_VIEW PAV WHERE PAV.ID IN (?)").params(ids)
				.query(Authority.class).list();
	}

	@Override
	public Authority getOneById(final Long id) {
		return this.jdbcClient.sql("SELECT PAV.* FROM PPOG_AUTHORITIES_VIEW PAV WHERE PAV.ID = ?").param(id)
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
