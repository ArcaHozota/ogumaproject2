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

import jp.co.ogumaproject.ppok.entity.Employee;

/**
 * 社員リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
@RegisterBeanMapper(Employee.class)
public interface EmployeeRepository {

	/**
	 * キーワードによるカウント
	 *
	 * @param keyword キーワード
	 * @return Long
	 */
	@SqlQuery("SELECT COUNT(1) FROM PPOG_EMPLOYEES_VIEW PEV WHERE PEV.LOGIN_ACCOUNT LIKE ? OR PEV.USERNAME LIKE ? OR PEV.EMAIL LIKE ?")
	Long countByKeyword(@Bind("keyword") String keyword);

	/**
	 * アカウント名によるカウント
	 *
	 * @param name アカウント名
	 * @return Long
	 */
	@SqlQuery("SELECT COUNT(1) FROM PPOG_EMPLOYEES PE WHERE PE.LOGIN_ACCOUNT =:name")
	Long countByName(@Bind("name") String name);

	/**
	 * 外部キーによる検索
	 *
	 * @param foreignKey 外部キー
	 * @return List<Employee>
	 */
	@SqlQuery("SELECT PEV.* FROM PPOG_EMPLOYEES_VIEW PEV INNER JOIN PPOG_EMPLOYEES_ROLE_VIEW PERV ON PERV.EMPLOYEE_ID = PEV.ID　"
			+ "WHERE PERV.ROLE_ID =:roleId")
	List<Employee> getListByForeignKey(@Bind("roleId") Long foreignKey);

	/**
	 * IDリストによる検索
	 *
	 * @param ids IDリスト
	 * @return List<Employee>
	 * @throws NoResultsException
	 */
	@SqlQuery("SELECT PEV.* FROM PPOG_EMPLOYEES_VIEW PEV WHERE PEV.ID IN (<ids>)")
	List<Employee> getListByIds(@BindList("ids") List<Long> ids) throws NoResultsException;

	/**
	 * エンティティによる検索
	 *
	 * @param aEntity エンティティ
	 * @return Employee
	 */
	@SqlQuery("SELECT PEV.* FROM PPOG_EMPLOYEES_VIEW PEV "
			+ "WHERE PEV.LOGIN_ACCOUNT =:loginAccount AND PEV.EMAIL =:email AND PEV.DATE_OF_BIRTH =:dateOfBirth")
	Employee getOneByEntity(@BindBean Employee aEntity);

	/**
	 * IDによる検索
	 *
	 * @param id ID
	 * @return Employee
	 * @throws NoResultsException
	 */
	@SqlQuery("SELECT PEV.* FROM PPOG_EMPLOYEES_VIEW PEV WHERE PEV.ID =:id")
	Employee getOneById(@Bind("id") Long id) throws NoResultsException;

	/**
	 * アカウントによる検索
	 *
	 * @param loginAccount アカウント
	 * @return Employee
	 */
	@SqlQuery("SELECT PEV.* FROM PPOG_EMPLOYEES_VIEW PEV WHERE PEV.LOGIN_ACCOUNT =:loginAccount OR PEV.EMAIL =:loginAccount")
	Employee getOneByLoginAccount(@Bind("loginAccount") String loginAccount);

	/**
	 * 挿入
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("INSERT INTO PPOG_EMPLOYEES PE (PE.ID, PE.LOGIN_ACCOUNT, PE.PASSWORD, PE.USERNAME, PE.EMAIL, PE.CREATED_TIME, PE.DATE_OF_BIRTH, PE.DEL_FLG) "
			+ "VALUES (:id, :loginAccount, :password, :username, :email, :createdTime, :dateOfBirth, :delFlg)")
	void insertById(@BindBean Employee aEntity) throws TransactionException;

	/**
	 * パージング検索
	 *
	 * @param offset   オフセット
	 * @param pageSize ページサイズ
	 * @param keyword  キーワード
	 * @return List<Employee>
	 */
	@SqlQuery("SELECT PEV.* FROM PPOG_EMPLOYEES_VIEW PEV WHERE PEV.LOGIN_ACCOUNT LIKE :keyword OR PEV.USERNAME LIKE :keyword "
			+ "OR PEV.EMAIL LIKE :keyword ORDER BY PEV.ID ASC OFFSET :offset ROWS FETCH NEXT :pageSize ROWS ONLY")
	List<Employee> pagination(@Bind("offset") Integer offset, @Bind("pageSize") Integer pageSize,
			@Bind("keyword") String keyword);

	/**
	 * 論理削除
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("UPDATE PPOG_EMPLOYEES PE SET PE.DEL_FLG =:delFlg WHERE PE.ID =:id")
	void removeById(@BindBean Employee aEntity) throws TransactionException;

	/**
	 * 更新
	 *
	 * @param aEntity エンティティ
	 * @throws TransactionException
	 */
	@SqlUpdate("UPDATE PPOG_EMPLOYEES PE SET PE.LOGIN_ACCOUNT =:loginAccount, PE.PASSWORD =:password, PE.USERNAME =:username, "
			+ "PE.EMAIL =:email, PE.CREATED_TIME =:createdTime, PE.DATE_OF_BIRTH =:dateOfBirth "
			+ "WHERE PE.DEL_FLG =:delFlg AND PE.ID =:id")
	void updateById(@BindBean Employee aEntity) throws TransactionException;
}
