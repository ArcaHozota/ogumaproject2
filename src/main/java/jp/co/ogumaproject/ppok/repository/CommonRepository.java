package jp.co.ogumaproject.ppok.repository;

import java.util.List;

/**
 * 共通リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
public interface CommonRepository<T> {

	/**
	 * 全件検索
	 *
	 * @return List<T>
	 */
	List<T> getList();

	/**
	 * 外部キーによってリストを取得する
	 *
	 * @param foreignKey 外部キー
	 * @return List<T>
	 */
	List<T> getListByForeignKey(Long foreignKey);

	/**
	 * IDリストによってリストを取得する
	 *
	 * @param ids IDリスト
	 * @return List<T>
	 */
	List<T> getListByIds(List<Long> ids);

	/**
	 * IDによって1件抽出する
	 *
	 * @param id ID
	 * @return T
	 */
	T getOneById(Long id);

	/**
	 * IDによって情報を削除する
	 *
	 * @param aEntity エンティティ
	 */
	void removeById(T aEntity);

	/**
	 * 1件情報を保存する
	 *
	 * @param aEntity エンティティ
	 */
	void saveById(T aEntity);

	/**
	 * 1件情報を更新する
	 *
	 * @param aEntity エンティティ
	 */
	void updateById(T aEntity);
}
