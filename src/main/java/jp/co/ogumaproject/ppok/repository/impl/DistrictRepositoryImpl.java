package jp.co.ogumaproject.ppok.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppok.entity.District;
import jp.co.ogumaproject.ppok.repository.DistrictRepository;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer countByName(final String name) {
		// TODO Auto-generated method stub
		return null;
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
		return this.jdbcClient.sql(
				"SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV INNER JOIN PPOG_CHIHOS_VIEW PCHV ON PCHV.ID = PDV.CHIHO_ID"
						+ "INNER JOIN PPOG_CITIES_VIEW PCV ON PCV.ID = PDV.SHUTO_ID WHERE PDV.NAME LIKE ? OR PCV.NAME LIKE ? "
						+ "OR PCHV.NAME LIKE ? ORDER BY PDV.ID ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY")
				.params(keyword, keyword, keyword, offset, pageSize).query(District.class).list();
	}

	@Override
	public void removeById(final District aEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveById(final District aEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateById(final District aEntity) {
		// TODO Auto-generated method stub

	}

}
