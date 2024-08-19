package jp.co.ogumaproject.ppok.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	public List<Authority> getList() {
		final String sql = "SELECT PAV.* FROM PPOG_AUTHORITIES_VIEW PAV ORDER BY PAV.ID ASC";
		return this.getCommonList(sql);
	}

	@Override
	public List<Authority> getListByForeignKey(final Long foreignKey) {
		final String sql = "SELECT PAV.* FROM PPOG_AUTHORITIES_VIEW PAV INNER JOIN PPOG_ROLE_AUTH_VIEW PRAV ON PRAV.AUTH_ID = PAV.ID WHERE PRAV.ROLE_ID = ?";
		return this.getCommonListByForeignKey(sql, foreignKey);
	}

	@Override
	public List<Authority> getListByIds(final List<Long> ids) {
		final String sql = "SELECT PAV.* FROM PPOG_AUTHORITIES_VIEW PAV WHERE PAV.ID IN (?)";
		return this.getCommonListByIds(sql, ids);
	}

	@Override
	public Authority getOneById(final Long id) {
		final String sql = "SELECT PAV.* FROM PPOG_AUTHORITIES_VIEW PAV WHERE PAV.ID = ?";
		return this.getCommonOneById(sql, id);
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
