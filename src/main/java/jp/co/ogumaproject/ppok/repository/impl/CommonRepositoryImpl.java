package jp.co.ogumaproject.ppok.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppok.utils.OgumaProjectUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 共通リポジトリ
 *
 * @author ArkamaHozota
 * @since 10.34
 */
@Getter
@Setter
public abstract class CommonRepositoryImpl<T> {

	/**
	 * エンティティクラス
	 */
	private Class<T> entityClass;

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	/**
	 * コンストラクタ
	 *
	 * @param aClass エンティティクラス
	 */
	protected CommonRepositoryImpl(final Class<T> aClass) {
		this.entityClass = aClass;
	}

	/**
	 * キーワードによってレコード数を取得する
	 *
	 * @param aSQL      SQL文
	 * @param aKeywords キーワード
	 */
	protected Long commonCountByKeywords(final String aSQL, final Object... aKeywords) {
		return this.jdbcClient.sql(aSQL).params(aKeywords).query(Long.class).single();
	}

	/**
	 * IDによってデータ修飾を行う
	 *
	 * @param aSQL    SQL文
	 * @param aEntity エンティティオブジェクト
	 */
	protected void commonModifyById(final String aSQL, final T aEntity) {
		final Map<String, Object> paramMap = OgumaProjectUtils.getParamMap(aEntity);
		this.jdbcClient.sql(aSQL).params(paramMap).update();
	}

	/**
	 * キーワードによってデータ修飾を行う
	 *
	 * @param aSQL      SQL文
	 * @param aKeywords キーワード
	 */
	protected void commonModifyByKeywords(final String aSQL, final Object... aKeywords) {
		this.jdbcClient.sql(aSQL).params(aKeywords).update();
	}

	/**
	 * IDリストによってリストを取得する
	 *
	 * @param aSql    SQL文
	 * @param aIdList IDリスト
	 * @return List<T>
	 */
	protected List<T> getCommonListByIds(final String aSql, final List<Long> aIdList) {
		final List<T> list = this.jdbcClient.sql(aSql).params(aIdList).query(this.getEntityClass()).list();
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		return list;
	}

	/**
	 * キーワードによってリストを取得する
	 *
	 * @param aSql      SQL文
	 * @param aKeywords キーワード
	 * @return List<T>
	 */
	protected List<T> getCommonListByKeywords(final String aSql, final Object... aKeywords) {
		final List<T> list = this.jdbcClient.sql(aSql).params(aKeywords).query(this.getEntityClass()).list();
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		return list;
	}

	/**
	 * IDによって一件レコードを検索する
	 *
	 * @param aSql SQL文
	 * @param aId  ID
	 * @return T
	 */
	protected T getCommonOneById(final String aSql, final Long aId) {
		try {
			return this.jdbcClient.sql(aSql).param(aId).query(this.getEntityClass()).single();
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * キーワードによって一件レコードを検索する
	 *
	 * @param aSql      SQL文
	 * @param aKeywords キーワード
	 * @return T
	 */
	protected T getCommonOneByKeywords(final String aSql, final Object... aKeywords) {
		try {
			return this.jdbcClient.sql(aSql).params(aKeywords).query(this.getEntityClass()).single();
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}

}
