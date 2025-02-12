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

import jp.co.ogumaproject.ppok.entity.Role;

/**
 * 役割リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.73
 */
@RegisterBeanMapper(Role.class)
public interface RoleRepository {

	/**
	 * キーワードによるカウント
	 *
	 * @param keyword キーワード
	 * @return Long
	 */
	@SqlQuery("SELECT COUNT(1) FROM PPOG_ROLES_VIEW PRV WHERE PRV.NAME LIKE :keyword")
	Long countByKeyword(@Bind("keyword") String keyword);

	/**
	 * ロール名によるカウント
	 *
	 * @param name ロール名
	 * @return Long
	 */
	@SqlQuery("SELECT COUNT(1) FROM PPOG_ROLES PR WHERE PR.NAME =:name")
	Long countByName(@Bind("name") String name);

	/**
	 * 全件検索
	 *
	 * @return List<Role>
	 */
	@SqlQuery("SELECT PRV.* FROM PPOG_ROLES_VIEW PRV ORDER BY PRV.ID ASC")
	List<Role> getList();

	/**
	 * 外部キーによる検索
	 *
	 * @param foreignKey 外部キー
	 * @return List<Role>
	 */
	@SqlQuery("SELECT PRV.* FROM PPOG_ROLES_VIEW PRV INNER JOIN PPOG_ROLES_AUTH_VIEW PRAV ON PRAV.ROLE_ID = PRV.ID WHERE PRAV.AUTH_ID =:authId")
	List<Role> getListByForeignKey(@Bind("authId") Long foreignKey);

	/**
	 * IDリストによる検索
	 *
	 * @param ids IDリスト
	 * @return List<Role>
	 * @throws NoResultsException
	 */
	@SqlQuery("SELECT PRV.* FROM PPOG_ROLES_VIEW PRV WHERE PRV.ID IN (<ids>)")
	List<Role> getListByIds(@BindList("ids") List<Long> ids) throws NoResultsException;

	/**
	 * IDによる検索
	 *
	 * @param id ID
	 * @return Role
	 * @throws NoResultsException
	 */
	@SqlQuery("SELECT PRV.* FROM PPOG_ROLES_VIEW PRV WHERE PRV.ID =:id")
	Role getOneById(@Bind("id") Long id) throws NoResultsException;

	/**
	 * ロール名による検索
	 *
	 * @param name ロール名
	 * @return Role
	 */
	@SqlQuery("SELECT PRV.* FROM PPOG_ROLES_VIEW PRV WHERE PRV.NAME =:name")
	Role getOneByName(@Bind("name") String name);

	/**
	 * 挿入
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("INSERT INTO PPOG_ROLES PR (PR.ID, PR.NAME, PR.DEL_FLG) VALUES (:id, :name, :delFlg)")
	void insertById(@BindBean Role aEntity) throws TransactionException;

	/**
	 * パージング検索
	 *
	 * @param offset   オフセット
	 * @param pageSize ページサイズ
	 * @param keyword  キーワード
	 * @return List<Role>
	 */
	@SqlQuery("SELECT PRV.* FROM PPOG_ROLES_VIEW PRV WHERE PRV.NAME LIKE :keyword ORDER BY PRV.ID ASC OFFSET :offset ROWS FETCH NEXT :pageSize ROWS ONLY")
	List<Role> pagination(@Bind("offset") Integer offset, @Bind("pageSize") Integer pageSize,
			@Bind("keyword") String keyword);

	/**
	 * 論理削除
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("UPDATE PPOG_ROLES PR SET PR.DEL_FLG =:delFlg WHERE PR.ID =:id")
	void removeById(@BindBean Role aEntity) throws TransactionException;

	/**
	 * 更新
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("UPDATE PPOG_ROLES PR SET PR.NAME =:name WHERE PR.DEL_FLG =:delFlg AND PR.ID =:id")
	void updateById(@BindBean Role aEntity) throws TransactionException;
}
