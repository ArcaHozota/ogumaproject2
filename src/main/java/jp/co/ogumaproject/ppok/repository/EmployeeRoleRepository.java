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

import jp.co.ogumaproject.ppok.entity.EmployeeRole;

/**
 * 社員役割リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
@RegisterBeanMapper(EmployeeRole.class)
public interface EmployeeRoleRepository {

	/**
	 * 外部キーによる検索
	 *
	 * @param foreignKey 外部キー
	 * @return List<EmployeeRole>
	 */
	@SqlQuery("SELECT PERV.* FROM PPOG_EMPLOYEE_ROLE_VIEW PERV WHERE PERV.ROLE_ID =:roleId")
	List<EmployeeRole> getListByForeignKey(@Bind("roleId") Long foreignKey);

	/**
	 * IDリストによる検索
	 *
	 * @param ids IDリスト
	 * @return List<EmployeeRole>
	 * @throws NoResultsException
	 */
	@SqlQuery("SELECT PERV.* FROM PPOG_EMPLOYEE_ROLE_VIEW PERV WHERE PERV.EMPLOYEE_ID IN (<ids>)")
	List<EmployeeRole> getListByIds(@BindList("ids") List<Long> ids) throws NoResultsException;

	/**
	 * IDによる検索
	 *
	 * @param id ID
	 * @return EmployeeRole
	 * @throws NoResultsException
	 */
	@SqlQuery("SELECT PERV.* FROM PPOG_EMPLOYEE_ROLE_VIEW PERV WHERE PERV.EMPLOYEE_ID =:id")
	EmployeeRole getOneById(@Bind("id") Long id) throws NoResultsException;

	/**
	 * 挿入
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("INSERT INTO PPOG_EMPLOYEE_ROLE PER (PER.EMPLOYEE_ID, PER.ROLE_ID) VALUES (:employeeId, :roleId)")
	void insertById(@BindBean EmployeeRole aEntity) throws TransactionException;

	/**
	 * 物理削除
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("DELETE FROM PPOG_EMPLOYEE_ROLE PER WHERE PER.EMPLOYEE_ID =:employeeId")
	void removeById(@BindBean EmployeeRole aEntity) throws TransactionException;

	/**
	 * 更新
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("UPDATE PPOG_EMPLOYEE_ROLE PER SET PER.ROLE_ID =:roleId WHERE PER.EMPLOYEE_ID =:employeeId")
	void updateById(@BindBean EmployeeRole aEntity) throws TransactionException;
}
