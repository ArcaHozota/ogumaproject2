package jp.co.ogumaproject.ppok.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jp.co.ogumaproject.ppok.entity.Chiho;
import jp.co.ogumaproject.ppok.repository.ChihoRepository;
import oracle.jdbc.driver.OracleSQLException;

/**
 * 地方リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.97
 */
@Repository
@Transactional(rollbackFor = OracleSQLException.class)
public class ChihoRepositoryImpl extends CommonRepositoryImpl<Chiho> implements ChihoRepository {

	@Override
	public List<Chiho> getList() {
		final String sql = "SELECT PCHV.* FROM PPOG_CHIHOS_VIEW PCHV ORDER BY PCHV.ID ASC";
		return this.getCommonList(sql);
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
		final String sql = "SELECT PCHV.* FROM PPOG_CHIHOS_VIEW PCHV WHERE PCHV.ID = ?";
		return this.getCommonOneById(sql, id);
	}

	/**
	 * イニシャル
	 */
	@PostConstruct
	private void initial() {
		this.setEntityClass(Chiho.class);
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
