package jp.co.ogumaproject.ppok.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppok.entity.Chiho;
import jp.co.ogumaproject.ppok.repository.ChihoRepository;

/**
 * 地方リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.97
 */
@Repository
public class ChihoRepositoryImpl implements ChihoRepository {

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	@Override
	public List<Chiho> getList() {
		return this.jdbcClient.sql("SELECT PCHV.* FROM PPOG_CHIHOS_VIEW PCHV ORDER BY PCHV.ID ASC").query(Chiho.class)
				.list();
	}

	@Deprecated
	@Override
	public List<Chiho> getListByForeignKey(final Long foreignKey) {
		return null;
	}

	@Deprecated
	@Override
	public List<Chiho> getListByIds(final List<Long> ids) {
		return null;
	}

	@Override
	public Chiho getOneById(final Long id) {
		return this.jdbcClient.sql("SELECT PCHV.* FROM PPOG_CHIHOS_VIEW PCHV WHERE PCHV.ID = ?").param(id)
				.query(Chiho.class).single();
	}

	@Deprecated
	@Override
	public void removeById(final Chiho aEntity) {
	}

	@Deprecated
	@Override
	public void saveById(final Chiho aEntity) {
	}

	@Deprecated
	@Override
	public void updateById(final Chiho aEntity) {
	}
}
