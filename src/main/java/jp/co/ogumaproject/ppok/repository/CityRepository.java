package jp.co.ogumaproject.ppok.repository;

import java.util.List;

import org.jdbi.v3.core.result.NoResultsException;
import org.jdbi.v3.core.transaction.TransactionException;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import jp.co.ogumaproject.ppok.entity.City;

/**
 * 都市リポジトリ
 *
 * @author ArkamaHozota
 * @since 10.0.1
 */
@RegisterBeanMapper(City.class)
public interface CityRepository {

	/**
	 * キーワードによるカウント
	 *
	 * @param keyword キーワード
	 * @return Long
	 */
	@SqlQuery("SELECT COUNT(1) FROM PPOG_CITIES_VIEW PCV INNER JOIN PPOG_DISTRICTS_VIEW PDV ON PDV.ID = PCV.DISTRICT_ID "
			+ "WHERE PCV.NAME LIKE :keyword OR PCV.PRONUNCIATION LIKE :keyword OR PDV.NAME LIKE :keyword")
	Long countByKeyword(@Bind("keyword") String keyword);

	/**
	 * 都市名によるカウント
	 *
	 * @param name       都市名
	 * @param districtId 地域ID
	 * @return Long
	 */
	@SqlQuery("SELECT COUNT(1) FROM PPOG_CITIES_VIEW PCV WHERE PCV.NAME =:name AND PCV.DISTRICT_ID =:districtId")
	Long countByName(@Bind("name") String name, @Bind("districtId") Long districtId);

	/**
	 * 外部キーによる検索
	 *
	 * @param foreignKey 外部キー
	 * @return List<City>
	 */
	@SqlQuery("SELECT PCV.* FROM PPOG_CITIES_VIEW PCV INNER JOIN PPOG_DISTRICTS_VIEW PDV ON PDV.ID = PCV.DISTRICT_ID WHERE PDV.ID =:districtId")
	List<City> getListByForeignKey(@Bind("districtId") Long foreignKey);

	/**
	 * IDによる検索
	 *
	 * @param id ID
	 * @return City
	 * @throws NoResultsException
	 */
	@SqlQuery("SELECT PCV.* FROM PPOG_CITIES_VIEW PCV WHERE PCV.ID =:id")
	City getOneById(@Bind("id") Long id) throws NoResultsException;

	/**
	 * 挿入
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("INSERT INTO PPOG_CITIES PC (PC.ID, PC.NAME, PC.PRONUNCIATION, PC.DISTRICT_ID, PC.CITY_FLAG, PC.POPULATION, PC.DEL_FLG) "
			+ "VALUES (:id, :name, :pronunciation, :districtId, :cityFlag, :population, :delFlg)")
	void insertById(@BindBean City aEntity) throws TransactionException;

	/**
	 * パージング検索
	 *
	 * @param offset   オフセット
	 * @param pageSize ページサイズ
	 * @param keyword  キーワード
	 * @return List<City>
	 */
	@SqlQuery("SELECT PCV.* FROM PPOG_CITIES_VIEW PCV INNER JOIN PPOG_DISTRICTS_VIEW PDV ON PDV.ID = PCV.DISTRICT_ID "
			+ "WHERE PCV.NAME LIKE :keyword OR PCV.PRONUNCIATION LIKE :keyword OR PDV.NAME LIKE :keyword OFFSET :offset ROWS FETCH NEXT :pageSize ROWS ONLY")
	List<City> pagination(@Bind("offset") Integer offset, @Bind("pageSize") Integer pageSize,
			@Bind("keyword") String keyword);

	/**
	 * 論理削除
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("UPDATE PPOG_CITIES PC SET PC.DEL_FLG =:delFlg WHERE PC.ID =:id")
	void removeById(@BindBean City aEntity) throws TransactionException;

	/**
	 * 更新
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("UPDATE PPOG_CITIES PC SET PC.NAME =:name, PC.PRONUNCIATION =:pronunciation, PC.DISTRICT_ID =:districtId, "
			+ "PC.CITY_FLAG =:cityFlag, PC.POPULATION =:population WHERE PC.DEL_FLG =:delFlg AND PC.ID =:id")
	void updateById(@BindBean City aEntity) throws TransactionException;
}
