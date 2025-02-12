package jp.co.ogumaproject.ppok.repository;

import java.util.List;

import org.jdbi.v3.core.result.NoResultsException;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import jp.co.ogumaproject.ppok.entity.Authority;

/**
 * 権限リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
@RegisterBeanMapper(Authority.class)
public interface AuthorityRepository {

	/**
	 * 全件検索
	 *
	 * @return List<Authority>
	 */
	@SqlQuery("SELECT PAV.* FROM PPOG_AUTHORITIES_VIEW PAV ORDER BY PAV.ID ASC")
	List<Authority> getList();

	/**
	 * 外部キーによる検索
	 *
	 * @param foreignKey 外部キー
	 * @return List<Authority>
	 */
	@SqlQuery("SELECT PAV.* FROM PPOG_AUTHORITIES_VIEW PAV INNER JOIN PPOG_ROLE_AUTH_VIEW PRAV ON PRAV.AUTH_ID = PAV.ID WHERE PRAV.ROLE_ID =:roleId")
	List<Authority> getListByForeignKey(@Bind("roleId") Long foreignKey);

	/**
	 * IDリストによる検索
	 *
	 * @param ids IDリスト
	 * @return List<Authority>
	 * @throws NoResultsException
	 */
	@SqlQuery("SELECT PAV.* FROM PPOG_AUTHORITIES_VIEW PAV WHERE PAV.ID IN (<ids>)")
	List<Authority> getListByIds(@BindList("ids") List<Long> ids) throws NoResultsException;

	/**
	 * IDによる検索
	 *
	 * @param id ID
	 * @return Authority
	 * @throws NoResultsException
	 */
	@SqlQuery("SELECT PAV.* FROM PPOG_AUTHORITIES_VIEW PAV WHERE PAV.ID =:id")
	Authority getOneById(@Bind("id") Long id) throws NoResultsException;
}
