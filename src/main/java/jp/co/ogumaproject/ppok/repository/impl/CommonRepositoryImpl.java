package jp.co.ogumaproject.ppok.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import jp.co.ogumaproject.ppok.utils.OgumaProjectUtils;

/**
 * 共通リポジトリ
 *
 * @author ArkamaHozota
 * @since 10.34
 */
public abstract class CommonRepositoryImpl<T> {

	/**
	 * エンティティ
	 */
	private T entity;

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
	 * リストを取得する
	 *
	 * @param aSql         SQL文
	 * @param aEntityClass エンティティクラス
	 * @return List<T>
	 */
	protected List<T> getCommonList(final String aSql) {
		final List<T> list = this.jdbcClient.sql(aSql).query(this.getEntityClass()).list();
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		return list;
	}

	/**
	 * 外部キーによってリストを取得する
	 *
	 * @param aSql         SQL文
	 * @param aForeignKey  外部キー
	 * @param aEntityClass エンティティクラス
	 * @return List<T>
	 */
	protected List<T> getCommonListByForeignKey(final String aSql, final Long aForeignKey) {
		final List<T> list = this.jdbcClient.sql(aSql).param(aForeignKey).query(this.getEntityClass()).list();
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		return list;
	}

	/**
	 * IDリストによってリストを取得する
	 *
	 * @param aSql         SQL文
	 * @param aIdList      IDリスト
	 * @param aEntityClass エンティティクラス
	 * @return List<T>
	 */
	protected List<T> getCommonListByIds(final String aSql, final List<Long> aIdList) {
		final List<T> list = this.jdbcClient.sql(aSql).param(aIdList).query(this.getEntityClass()).list();
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		return list;
	}

	/**
	 * IDによって一件レコードを検索する
	 *
	 * @param aSql         SQL文
	 * @param aId          ID
	 * @param aEntityClass エンティティクラス
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	protected T getCommonOneById(final String aSql, final Long aId) {
		try {
			return this.jdbcClient.sql(aSql).param(aId).query(this.getEntityClass()).single();
		} catch (final EmptyResultDataAccessException e) {
			return (T) new BeanWrapperImpl(this.getEntity()).getWrappedInstance();
		}
	}

	/**
	 * getter of entity
	 *
	 * @return T
	 */
	public T getEntity() {
		return this.entity;
	}

	/**
	 * getter of entityClass
	 *
	 * @return Class<T>
	 */
	public Class<T> getEntityClass() {
		return this.entityClass;
	}

	/**
	 * setter of entity
	 *
	 * @return T
	 */
	public void setEntity(final T entity) {
		this.entity = entity;
	}

	/**
	 * setter of entityClass
	 *
	 * @return Class<T>
	 */
	public void setEntityClass(final Class<T> entityClass) {
		this.entityClass = entityClass;
	}

}
