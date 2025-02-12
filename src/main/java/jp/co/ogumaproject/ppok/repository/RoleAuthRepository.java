package jp.co.ogumaproject.ppok.repository;

import java.util.List;

import org.jdbi.v3.core.transaction.TransactionException;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import jp.co.ogumaproject.ppok.entity.RoleAuth;

/**
 * 役割権限リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.77
 */
@RegisterBeanMapper(RoleAuth.class)
public interface RoleAuthRepository {

	/**
	 * 外部キーによるバッチ削除
	 *
	 * @param foreignKey 外部キー
	 */
	@SqlUpdate("DELETE FROM PPOG_ROLE_AUTH PRA WHERE PRA.ROLE_ID =:roleId")
	void batchRemoveByForeignKey(@Bind("roleId") Long foreignKey);

	/**
	 * 外部キーによる検索
	 *
	 * @param foreignKey 外部キー
	 * @return List<RoleAuth>
	 */
	@SqlQuery("SELECT PRAV.* FROM PPOG_ROLE_AUTH_VIEW PRAV WHERE PRAV.ROLE_ID =:roleId")
	List<RoleAuth> getListByForeignKey(@Bind("roleId") Long foreignKey);

	/**
	 * 挿入
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("INSERT INTO PPOG_ROLE_AUTH PRA (PRA.ROLE_ID, PRA.AUTH_ID) VALUES (:roleId, :authId)")
	void insertById(@BindBean RoleAuth aEntity) throws TransactionException;
}
