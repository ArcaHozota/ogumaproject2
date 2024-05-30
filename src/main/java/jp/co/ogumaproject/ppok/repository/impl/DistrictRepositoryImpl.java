package jp.co.ogumaproject.ppok.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppok.entity.District;
import jp.co.ogumaproject.ppok.repository.DistrictRepository;
import jp.co.ogumaproject.ppok.utils.OgumaProjectUtils;

/**
 * 地域リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.97
 */
@Repository
public class DistrictRepositoryImpl implements DistrictRepository {

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	@Override
	public Integer countByKeyword(final String keyword) {
		return this.jdbcClient.sql(
				"SELECT COUNT(1) FROM PPOG_DISTRICTS_VIEW PDV INNER JOIN PPOG_CHIHOS_VIEW PCHV ON PCHV.ID = PDV.CHIHO_ID "
						+ "INNER JOIN PPOG_CITIES_VIEW PCV ON PCV.ID = PDV.SHUTO_ID WHERE PDV.NAME LIKE ? OR PCV.NAME LIKE ? "
						+ "OR PCHV.NAME LIKE ?")
				.params(keyword, keyword, keyword).query(Integer.class).single();
	}

	@Override
	public Integer countByShutoId(final Long shutoId) {
		return this.jdbcClient.sql("SELECT COUNT(1) FROM PPOG_DISTRICTS_VIEW PDV WHERE PDV.SHUTO_ID = ?").param(shutoId)
				.query(Integer.class).single();
	}

	@Override
	public List<District> getList() {
		return this.jdbcClient.sql("SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV ORDER BY PDV.ID ASC")
				.query(District.class).list();
	}

	@Override
	public List<District> getListByForeignKey(final Long foreignKey) {
		return this.jdbcClient.sql(
				"SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV INNER JOIN PPOG_CHIHOS_VIEW PCHV ON PCHV.ID = PDV.CHIHO_ID "
						+ "WHERE PCHV.ID = ? ORDER BY PDV.ID ASC")
				.param(foreignKey).query(District.class).list();
	}

	@Override
	public List<District> getListByIds(final List<Long> ids) {
		return this.jdbcClient.sql("SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV WHERE PDV.ID IN (?) ORDER BY PDV.ID ASC")
				.params(ids).query(District.class).list();
	}

	@Override
	public District getOneById(final Long id) {
		return this.jdbcClient.sql("SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV WHERE PDV.ID = ?").param(id)
				.query(District.class).single();
	}

	@Override
	public List<District> pagination(final Integer offset, final Integer pageSize, final String keyword) {
		return this.jdbcClient.sql("SELECT PDV.*, PCHV.NAME AS CHIHO_NAME, PCV.NAME AS SHUTO_NAME, SUBQUERY.POPULATION "
				+ "FROM PPOG_DISTRICTS_VIEW PDV INNER JOIN PPOG_CHIHOS_VIEW PCHV ON PCHV.ID = PDV.CHIHO_ID "
				+ "INNER JOIN PPOG_CITIES_VIEW PCV ON PCV.ID = PDV.SHUTO_ID "
				+ "LEFT JOIN (SELECT PCV.DISTRICT_ID, SUM(PCV.POPULATION) AS POPULATION FROM PPOG_CITIES_VIEW PCV "
				+ "GROUP BY PCV.DISTRICT_ID) SUBQUERY ON SUBQUERY.DISTRICT_ID = PDV.ID "
				+ "WHERE PDV.NAME LIKE ? OR PCV.NAME LIKE ? OR PCHV.NAME LIKE ? ORDER BY PDV.ID ASC "
				+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY").params(keyword, keyword, keyword, offset, pageSize)
				.query(District.class).list();
	}

	@Deprecated
	@Override
	public void removeById(final District aEntity) {
	}

	@Deprecated
	@Override
	public void saveById(final District aEntity) {
	}

	@Override
	public void updateById(final District aEntity) {
		final Map<String, Object> paramMap = OgumaProjectUtils.getParamMap(aEntity);
		this.jdbcClient.sql(
				"UPDATE PPOG_DISTRICTS PD SET PD.NAME =:name, PD.CHIHO_ID =:chihoId, PD.SHUTO_ID =:shutoId, PD.DISTRICT_FLAG =:districtFlag "
						+ "WHERE PD.DEL_FLG =:delFlg AND PD.ID =:id")
				.params(paramMap).update();
	}

}
