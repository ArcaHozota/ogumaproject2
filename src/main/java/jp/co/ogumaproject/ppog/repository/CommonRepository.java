package jp.co.ogumaproject.ppog.repository;

import java.util.List;

/**
 * 共通リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
public interface CommonRepository<T> {

	/**
	 * 名称によって件数をカウントする
	 *
	 * @param name 名称
	 * @return Integer
	 */
	Integer countByName(String name);

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
	 * パージング検索
	 *
	 * @param offset   オフセット
	 * @param pageSize ページサイズ
	 * @param keyword  検索キーワード
	 * @return List<T>
	 */
	List<T> pagination(Integer offset, Integer pageSize, String keyword);

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
