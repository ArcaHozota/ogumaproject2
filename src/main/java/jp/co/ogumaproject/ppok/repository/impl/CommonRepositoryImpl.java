package jp.co.ogumaproject.ppok.repository.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppok.common.OgumaProjectConstants;
import jp.co.ogumaproject.ppok.exception.OgumaProjectException;
import jp.co.ogumaproject.ppok.utils.OgumaProjectUtils;

/**
 * 共通リポジトリ
 *
 * @author ArkamaHozota
 * @since 10.34
 */
public abstract class CommonRepositoryImpl<T> {

	/**
	 * エンティティタイプ
	 */
	private final Type type;

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	/**
	 * コンストラクタ
	 */
	protected CommonRepositoryImpl() {
		final Type superClass = this.getClass().getGenericSuperclass();
		this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
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
	@SuppressWarnings("unchecked")
	protected List<T> getCommonListByIds(final String aSql, final List<Long> aIdList) {
		Class<T> aClass;
		try {
			aClass = (Class<T>) Class.forName(this.getType().toString());
		} catch (final ClassNotFoundException e) {
			throw new OgumaProjectException(OgumaProjectConstants.MESSAGE_STRING_FATAL_ERROR);
		}
		final List<T> list = this.jdbcClient.sql(aSql).params(aIdList).query(aClass).list();
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
	@SuppressWarnings("unchecked")
	protected List<T> getCommonListByKeywords(final String aSql, final Object... aKeywords) {
		Class<T> aClass;
		try {
			aClass = (Class<T>) Class.forName(this.getType().toString());
		} catch (final ClassNotFoundException e) {
			throw new OgumaProjectException(OgumaProjectConstants.MESSAGE_STRING_FATAL_ERROR);
		}
		final List<T> list = this.jdbcClient.sql(aSql).params(aKeywords).query(aClass).list();
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
	@SuppressWarnings("unchecked")
	protected T getCommonOneById(final String aSql, final Long aId) {
		try {
			final Class<T> aClass = (Class<T>) Class.forName(this.getType().toString());
			return this.jdbcClient.sql(aSql).param(aId).query(aClass).single();
		} catch (final ClassNotFoundException e) {
			throw new OgumaProjectException(OgumaProjectConstants.MESSAGE_STRING_FATAL_ERROR);
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
	@SuppressWarnings("unchecked")
	protected T getCommonOneByKeywords(final String aSql, final Object... aKeywords) {
		try {
			final Class<T> aClass = (Class<T>) Class.forName(this.getType().toString());
			return this.jdbcClient.sql(aSql).params(aKeywords).query(aClass).single();
		} catch (final ClassNotFoundException e) {
			throw new OgumaProjectException(OgumaProjectConstants.MESSAGE_STRING_FATAL_ERROR);
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * getter of type
	 *
	 * @return Type
	 */
	public Type getType() {
		return this.type;
	}

}
