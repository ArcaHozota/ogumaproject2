package jp.co.ogumaproject.ppok.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jp.co.ogumaproject.ppok.entity.Role;
import jp.co.ogumaproject.ppok.repository.RoleRepository;
import oracle.jdbc.driver.OracleSQLException;

/**
 * 役割リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.73
 */
@Repository
@Transactional(rollbackFor = OracleSQLException.class)
public class RoleRepositoryImpl extends CommonRepositoryImpl<Role> implements RoleRepository {

	@Override
	public Long countByKeyword(final String keyword) {
		final String sql = "SELECT COUNT(1) FROM PPOG_ROLES_VIEW PRV WHERE PRV.NAME LIKE ?";
		return this.commonCountByKeywords(sql, keyword);
	}

	@Override
	public Long countByName(final String name) {
		final String sql = "SELECT COUNT(1) FROM PPOG_ROLES PR WHERE PR.NAME = ?";
		return this.commonCountByKeywords(sql, name);
	}

	@Override
	public List<Role> getList() {
		final String sql = "SELECT PRV.* FROM PPOG_ROLES_VIEW PRV ORDER BY PRV.ID ASC";
		return this.getCommonListByKeywords(sql);
	}

	@Override
	public List<Role> getListByForeignKey(final Long foreignKey) {
		final String sql = "SELECT PRV.* FROM PPOG_ROLES_VIEW PRV INNER JOIN PPOG_ROLES_AUTH_VIEW PRAV ON PRAV.ROLE_ID = PRV.ID WHERE PRAV.AUTH_ID = ?";
		return this.getCommonListByKeywords(sql, foreignKey);
	}

	@Override
	public List<Role> getListByIds(final List<Long> ids) {
		final String sql = "SELECT PRV.* FROM PPOG_ROLES_VIEW PRV WHERE PRV.ID IN (?)";
		return this.getCommonListByIds(sql, ids);
	}

	@Override
	public Role getOneById(final Long id) {
		final String sql = "SELECT PRV.* FROM PPOG_ROLES_VIEW PRV WHERE PRV.ID = ?";
		return this.getCommonOneById(sql, id);
	}

	@Override
	public Role getOneByName(final String name) {
		final String sql = "SELECT PRV.* FROM PPOG_ROLES_VIEW PRV WHERE PRV.NAME = ?";
		return this.getCommonOneByKeywords(sql, name);
	}

	/**
	 * イニシャル
	 */
	@PostConstruct
	private void initial() {
		this.setEntityClass(Role.class);
	}

	@Override
	public List<Role> pagination(final Integer offset, final Integer pageSize, final String keyword) {
		final String sql = "SELECT PRV.* FROM PPOG_ROLES_VIEW PRV WHERE PRV.NAME LIKE ? ORDER BY PRV.ID ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		return this.getCommonListByKeywords(sql, keyword, offset, pageSize);
	}

	@Override
	public void removeById(final Role aEntity) {
		final String sql = "UPDATE PPOG_ROLES PR SET PR.DEL_FLG =:delFlg WHERE PR.ID =:id";
		this.commonModifyById(sql, aEntity);
	}

	@Override
	public void saveById(final Role aEntity) {
		final String sql = "INSERT INTO PPOG_ROLES PR (PR.ID, PR.NAME, PR.DEL_FLG) VALUES (:id, :name, :delFlg)";
		this.commonModifyById(sql, aEntity);
	}

	@Override
	public void updateById(final Role aEntity) {
		final String sql = "UPDATE PPOG_ROLES PR SET PR.NAME =:name WHERE PR.DEL_FLG =:delFlg AND PR.ID =:id";
		this.commonModifyById(sql, aEntity);
	}
}
