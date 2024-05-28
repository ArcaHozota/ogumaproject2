package jp.co.ogumaproject.ppok.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppok.entity.City;
import jp.co.ogumaproject.ppok.repository.CityRepository;

/**
 * 都市リポジトリ
 *
 * @author ArkamaHozota
 * @since 10.0.1
 */
@Repository
public class CityRepositoryImpl implements CityRepository {

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	@Override
	public Integer countByKeyword(final String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getListByForeignKey(final Long foreignKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getListByIds(final List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public City getOneById(final Long id) {
		return this.jdbcClient.sql("SELECT PCV.* FROM PPOG_CITIES_VIEW PCV WHERE PCV.ID = ?").param(id)
				.query(City.class).single();
	}

	@Override
	public List<City> pagination(final Integer offset, final Integer pageSize, final String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeById(final City aEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveById(final City aEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateById(final City aEntity) {
		// TODO Auto-generated method stub

	}

}
