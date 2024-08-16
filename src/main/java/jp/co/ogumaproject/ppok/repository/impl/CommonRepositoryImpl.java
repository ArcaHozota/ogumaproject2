package jp.co.ogumaproject.ppok.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;

public abstract class CommonRepositoryImpl<T> {

	/**
	 * JDBCクライアント
	 */
	@Resource
	private JdbcClient jdbcClient;

	/**
	 * リストを取得する
	 *
	 * @param aSql
	 * @param aEntityClass
	 * @return
	 */
	protected List<T> getCommonList(final String aSql, final Class<T> aEntityClass) {
		final List<T> list = this.jdbcClient.sql(aSql).query(aEntityClass).list();
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		return list;
	}

	protected List<T> getCommonListByForeignKey(final String aSql, final Long foreignKey, final Class<T> aEntityClass) {
		final List<T> list = this.jdbcClient.sql(aSql).param(foreignKey).query(aEntityClass).list();
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		return list;
	}

}
