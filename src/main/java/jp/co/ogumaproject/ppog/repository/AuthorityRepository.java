package jp.co.ogumaproject.ppog.repository;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppog.entity.Authority;

/**
 * 権限リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
@Repository
public class AuthorityRepository implements CommonRepository<Authority> {

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	@Override
	public List<Authority> getListByForeignKey(final Long foreignId) {
		return this.jdbcClient.sql(
				"SELECT PAV.* FROM PPOG_AUTHORITY_VIEW PAV INNER JOIN PPOG_ROLE_AUTH_VIEW PRAV ON PRAV.AUTH_ID = PAV.ID WHERE PRAV.ROLE_ID = ?")
				.param(foreignId).query(Authority.class).list();
	}

	@Override
	public Authority getOneById(final Long id) {
		return this.jdbcClient.sql("SELECT PAV.* FROM PPOG_AUTHORITY_VIEW PAV WHERE PAV.ID = ?").param(id)
				.query(Authority.class).single();
	}
}
