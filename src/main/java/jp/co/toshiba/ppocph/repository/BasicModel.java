package jp.co.toshiba.ppocph.repository;

import gaarason.database.contract.connection.GaarasonDataSource;
import gaarason.database.eloquent.Model;
import jakarta.annotation.Resource;

/**
 * 基本モデルクラス
 *
 * @param <T> エンティティ
 * @param <K> プライマリーキー
 */
public abstract class BasicModel<T, K> extends Model<T, K> {

	@Resource
	private GaarasonDataSource gaarasonDataSource;

	@Override
	public GaarasonDataSource getGaarasonDataSource() {
		return this.gaarasonDataSource;
	}
}
