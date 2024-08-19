package jp.co.ogumaproject.ppok.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jp.co.ogumaproject.ppok.entity.City;
import jp.co.ogumaproject.ppok.repository.CityRepository;
import oracle.jdbc.driver.OracleSQLException;

/**
 * 都市リポジトリ
 *
 * @author ArkamaHozota
 * @since 10.0.1
 */
@Repository
@Transactional(rollbackFor = OracleSQLException.class)
public class CityRepositoryImpl extends CommonRepositoryImpl<City> implements CityRepository {

	@Override
	public Long countByKeyword(final String keyword) {
		final String sql = "SELECT COUNT(1) FROM PPOG_CITIES_VIEW PCV INNER JOIN PPOG_DISTRICTS_VIEW PDV ON PDV.ID = PCV.DISTRICT_ID "
				+ "WHERE PCV.NAME LIKE ? OR PCV.PRONUNCIATION LIKE ? OR PDV.NAME LIKE ?";
		return this.commonCountByKeywords(sql, keyword, keyword, keyword);
	}

	@Override
	public Long countByName(final String name, final Long districtId) {
		final String sql = "SELECT COUNT(1) FROM PPOG_CITIES_VIEW PCV WHERE PCV.NAME = ? AND PCV.DISTRICT_ID = ?";
		return this.commonCountByKeywords(sql, name, districtId);
	}

	@Deprecated
	@Override
	public List<City> getList() {
		return null;
	}

	@Override
	public List<City> getListByForeignKey(final Long foreignKey) {
		final String sql = "SELECT PCV.* FROM PPOG_CITIES_VIEW PCV INNER JOIN PPOG_DISTRICTS_VIEW PDV ON PDV.ID = PCV.DISTRICT_ID WHERE PDV.ID = ?";
		return this.getCommonListByForeignKey(sql, foreignKey);
	}

	@Deprecated
	@Override
	public List<City> getListByIds(final List<Long> ids) {
		return null;
	}

	@Override
	public City getOneById(final Long id) {
		final String sql = "SELECT PCV.* FROM PPOG_CITIES_VIEW PCV WHERE PCV.ID = ?";
		return this.getCommonOneById(sql, id);
	}

	/**
	 * イニシャル
	 */
	@PostConstruct
	private void initial() {
		this.setEntityClass(City.class);
	}

	@Override
	public List<City> pagination(final Integer offset, final Integer pageSize, final String keyword) {
		final String sql = "SELECT PCV.*, PDV.NAME AS DISTRICT_NAME FROM PPOG_CITIES_VIEW PCV INNER JOIN PPOG_DISTRICTS_VIEW PDV ON PDV.ID = PCV.DISTRICT_ID "
				+ "WHERE PCV.NAME LIKE ? OR PCV.PRONUNCIATION LIKE ? OR PDV.NAME LIKE ? OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		return this.getCommonListByKeywords(sql, keyword, keyword, keyword, offset, pageSize);
	}

	@Override
	public void removeById(final City aEntity) {
		final String sql = "UPDATE PPOG_CITIES PC SET PC.DEL_FLG =:delFlg WHERE PC.ID =:id";
		this.commonModifyById(sql, aEntity);
	}

	@Override
	public void saveById(final City aEntity) {
		final String sql = "INSERT INTO PPOG_CITIES PC (PC.ID, PC.NAME, PC.PRONUNCIATION, PC.DISTRICT_ID, PC.CITY_FLAG, PC.POPULATION, PC.DEL_FLG) "
				+ "VALUES (:id, :name, :pronunciation, :districtId, :cityFlag, :population, :delFlg)";
		this.commonModifyById(sql, aEntity);
	}

	@Override
	public void updateById(final City aEntity) {
		final String sql = "UPDATE PPOG_CITIES PC SET PC.NAME =:name, PC.PRONUNCIATION =:pronunciation, PC.DISTRICT_ID =:districtId, "
				+ "PC.CITY_FLAG =:cityFlag, PC.POPULATION =:population WHERE PC.DEL_FLG =:delFlg AND PC.ID =:id";
		this.commonModifyById(sql, aEntity);
	}
}
