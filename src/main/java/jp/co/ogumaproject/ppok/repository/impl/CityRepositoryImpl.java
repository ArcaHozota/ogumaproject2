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
		return this.jdbcClient.sql(
				"SELECT COUNT(1) FROM PPOG_CITIES_VIEW PCV INNER JOIN PPOG_DISTRICTS_VIEW PDV ON PDV.ID = PCV.DISTRICT_ID "
						+ "WHERE PCV.NAME LIKE ? OR PCV.PRONUNCIATION LIKE ? OR PDV.NAME LIKE ?")
				.params(keyword, keyword, keyword).query(Integer.class).single();
	}

	@Override
	public Integer countByName(final String name, final Long districtId) {
		return this.jdbcClient.sql("SELECT COUNT(1) FROM PPOG_CITIES_VIEW PCV WHERE EXISTS ("
				+ "SELECT 1 FROM PPOG_DISTRICTS_VIEW PDV WHERE PCV.DISTRICT_ID = PDV.ID AND PDV.ID = ? AND PCV.NAME = ?)")
				.params(name, districtId).query(Integer.class).single();
	}

	@Deprecated
	@Override
	public List<City> getList() {
		return null;
	}

	@Override
	public List<City> getListByForeignKey(final Long foreignKey) {
		return this.jdbcClient.sql(
				"SELECT PCV.* FROM PPOG_CITIES_VIEW PCV INNER JOIN PPOG_DISTRICTS_VIEW PDV ON PDV.ID = PCV.DISTRICT_ID WHERE PDV.ID = ?")
				.param(foreignKey).query(City.class).list();
	}

	@Deprecated
	@Override
	public List<City> getListByIds(final List<Long> ids) {
		return null;
	}

	@Override
	public City getOneById(final Long id) {
		return this.jdbcClient.sql("SELECT PCV.* FROM PPOG_CITIES_VIEW PCV WHERE PCV.ID = ?").param(id)
				.query(City.class).single();
	}

	@Override
	public List<City> pagination(final Integer offset, final Integer pageSize, final String keyword) {
		return this.jdbcClient.sql(
				"SELECT PCV.*, PDV.NAME AS SHUTO_NAME FROM PPOG_CITIES_VIEW PCV INNER JOIN PPOG_DISTRICTS_VIEW PDV ON PDV.ID = PCV.DISTRICT_ID "
						+ "WHERE PCV.NAME LIKE ? OR PCV.PRONUNCIATION LIKE ? OR PDV.NAME LIKE ? OFFSET ? ROWS FETCH NEXT ? ROWS ONLY")
				.params(keyword, keyword, keyword, offset, pageSize).query(City.class).list();
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
