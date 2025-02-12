package jp.co.ogumaproject.ppok.repository;

import java.util.List;

import org.jdbi.v3.core.result.NoResultsException;
import org.jdbi.v3.core.transaction.TransactionException;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import jp.co.ogumaproject.ppok.entity.District;

/**
 * 地域リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.95
 */
@RegisterBeanMapper(District.class)
public interface DistrictRepository {

	/**
	 * キーワードによるカウント
	 *
	 * @param keyword キーワード
	 * @return Long
	 */
	@SqlQuery("SELECT COUNT(1) FROM PPOG_DISTRICTS_VIEW PDV INNER JOIN PPOG_CHIHOS_VIEW PCHV ON PCHV.ID = PDV.CHIHO_ID "
			+ "INNER JOIN PPOG_CITIES_VIEW PCV ON PCV.ID = PDV.SHUTO_ID "
			+ "WHERE PDV.NAME LIKE :keyword OR PCV.NAME LIKE :keyword OR PCHV.NAME LIKE :keyword")
	Long countByKeyword(@Bind("keyword") String keyword);

	/**
	 * 州都IDによるカウント
	 *
	 * @param shutoId 州都ID
	 * @return Long
	 */
	@SqlQuery("SELECT COUNT(1) FROM PPOG_DISTRICTS_VIEW PDV WHERE PDV.SHUTO_ID =:shutoId")
	Long countByShutoId(@Bind("shutoId") Long shutoId);

	/**
	 * 全件検索
	 *
	 * @return List<District>
	 */
	@SqlQuery("SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV ORDER BY PDV.ID ASC")
	List<District> getList();

	/**
	 * 外部キーによる検索
	 *
	 * @param foreignKey 外部キー
	 * @return List<District>
	 */
	@SqlQuery("SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV INNER JOIN PPOG_CHIHOS_VIEW PCHV ON PCHV.ID = PDV.CHIHO_ID "
			+ "WHERE PCHV.ID =:chihoId ORDER BY PDV.ID ASC")
	List<District> getListByForeignKey(@Bind("chihoId") Long foreignKey);

	/**
	 * IDリストによる検索
	 *
	 * @param ids IDリスト
	 * @return List<District>
	 * @throws NoResultsException
	 */
	@SqlQuery("SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV WHERE PDV.ID IN (<ids>) ORDER BY PDV.ID ASC")
	List<District> getListByIds(@BindList("ids") List<Long> ids) throws NoResultsException;

	/**
	 * IDによる検索
	 *
	 * @param id ID
	 * @return District
	 * @throws NoResultsException
	 */
	@SqlQuery("SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV WHERE PDV.ID =:id")
	District getOneById(@Bind("id") Long id) throws NoResultsException;

	/**
	 * パージング検索
	 *
	 * @param offset   オフセット
	 * @param pageSize ページサイズ
	 * @param keyword  キーワード
	 * @return List<District>
	 */
	@SqlQuery("SELECT PDV.* FROM PPOG_DISTRICTS_VIEW PDV INNER JOIN PPOG_CHIHOS_VIEW PCHV ON PCHV.ID = PDV.CHIHO_ID "
			+ "INNER JOIN PPOG_CITIES_VIEW PCV ON PCV.ID = PDV.SHUTO_ID "
			+ "WHERE PDV.NAME LIKE :keyword OR PCV.NAME LIKE :keyword OR PCHV.NAME LIKE :keyword "
			+ "ORDER BY PDV.ID ASC OFFSET :offset ROWS FETCH NEXT :pageSize ROWS ONLY")
	List<District> pagination(@Bind("offset") Integer offset, @Bind("pageSize") Integer pageSize,
			@Bind("keyword") String keyword);

	/**
	 * 更新
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("UPDATE PPOG_DISTRICTS PD SET PD.NAME =:name, PD.CHIHO_ID =:chihoId, PD.SHUTO_ID =:shutoId, PD.DISTRICT_FLAG =:districtFlag "
			+ "WHERE PD.DEL_FLG =:delFlg AND PD.ID =:id")
	void updateById(@BindBean District aEntity) throws TransactionException;
}
