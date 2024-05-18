package jp.co.ogumaproject.ppog.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

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

	@Override
	public List<Role> getListByForeignKey(final Long foreignKey) {
		// TODO Auto-generated method stub
		return null;
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
