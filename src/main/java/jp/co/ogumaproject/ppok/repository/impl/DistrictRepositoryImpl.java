package jp.co.ogumaproject.ppok.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import jp.co.ogumaproject.ppok.entity.District;
import jp.co.ogumaproject.ppok.repository.DistrictRepository;

/**
 * 地域リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.97
 */
@Repository
public class DistrictRepositoryImpl extends CommonRepositoryImpl<District> implements DistrictRepository {

	@Override
	public Integer countByKeyword(final String keyword) {
		final String sql = "SELECT COUNT(1) FROM PPOG_DISTRICTS_VIEW PDV INNER JOIN PPOG_CHIHOS_VIEW PCHV ON PCHV.ID = PDV.CHIHO_ID "
				+ "INNER JOIN PPOG_CITIES_VIEW PCV ON PCV.ID = PDV.SHUTO_ID WHERE PDV.NAME LIKE ? OR PCV.NAME LIKE ? "
				+ "OR PCHV.NAME LIKE ?";
		return this.commonCountByKeywords(sql, keyword, keyword, keyword).intValue();
	}

	@Override
	public Integer countByShutoId(final Long shutoId) {
		final String sql = "SELECT COUNT(1) FROM PPOG_DISTRICTS_VIEW PDV WHERE PDV.SHUTO_ID = ?";
		return this.commonCountByKeywords(sql, shutoId).intValue();
	}

	@Override
	public List<District> getList() {
		final String sql = "SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV ORDER BY PDV.ID ASC";
		return this.getCommonListByKeywords(sql);
	}

	@Override
	public List<District> getListByForeignKey(final Long foreignKey) {
		final String sql = "SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV INNER JOIN PPOG_CHIHOS_VIEW PCHV ON PCHV.ID = PDV.CHIHO_ID "
				+ "WHERE PCHV.ID = ? ORDER BY PDV.ID ASC";
		return this.getCommonListByForeignKey(sql, foreignKey);
	}

	@Override
	public List<District> getListByIds(final List<Long> ids) {
		final String sql = "SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV WHERE PDV.ID IN (?) ORDER BY PDV.ID ASC";
		return this.getCommonListByIds(sql, ids);
	}

	@Override
	public District getOneById(final Long id) {
		final String sql = "SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV WHERE PDV.ID = ?";
		return this.getCommonOneById(sql, id);
	}

	/**
	 * イニシャル
	 */
	@PostConstruct
	private void initial() {
		this.setEntityClass(District.class);
	}

	@Override
	public List<District> pagination(final Integer offset, final Integer pageSize, final String keyword) {
		final String sql = "SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV INNER JOIN PPOG_CHIHOS_VIEW PCHV "
				+ "ON PCHV.ID = PDV.CHIHO_ID INNER JOIN PPOG_CITIES_VIEW PCV ON PCV.ID = PDV.SHUTO_ID "
				+ "WHERE PDV.NAME LIKE ? OR PCV.NAME LIKE ? OR PCHV.NAME LIKE ? ORDER BY PDV.ID ASC "
				+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		return this.getCommonListByKeywords(sql, keyword, keyword, keyword, offset, pageSize);
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
		final String sql = "UPDATE PPOG_DISTRICTS PD SET PD.NAME =:name, PD.CHIHO_ID =:chihoId, PD.SHUTO_ID =:shutoId, PD.DISTRICT_FLAG =:districtFlag "
				+ "WHERE PD.DEL_FLG =:delFlg AND PD.ID =:id";
		this.commonModifyById(sql, aEntity);
	}

}
