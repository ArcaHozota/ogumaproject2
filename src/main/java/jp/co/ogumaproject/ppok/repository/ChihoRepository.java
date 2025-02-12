package jp.co.ogumaproject.ppok.repository;

import java.util.List;

import org.jdbi.v3.core.result.NoResultsException;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import jp.co.ogumaproject.ppok.entity.Chiho;

/**
 * 地方リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.95
 */
@RegisterBeanMapper(Chiho.class)
public interface ChihoRepository {

	/**
	 * 全件検索
	 *
	 * @return List<Chiho>
	 */
	@SqlQuery("SELECT PCHV.* FROM PPOG_CHIHOS_VIEW PCHV ORDER BY PCHV.ID ASC")
	List<Chiho> getList();

	/**
	 * IDによる検索
	 *
	 * @param id ID
	 * @return Chiho
	 * @throws NoResultsException
	 */
	@SqlQuery("SELECT PCHV.* FROM PPOG_CHIHOS_VIEW PCHV WHERE PCHV.ID =:id")
	Chiho getOneById(@Bind("id") Long id) throws NoResultsException;
}
