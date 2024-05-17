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
	 * 外部キーによってリストを取得する
	 *
	 * @param foreignId
	 * @return List<T>
	 */
	List<T> getListByForeignKey(Long foreignId);

	/**
	 * IDによって1件抽出する
	 *
	 * @param id ID
	 * @return T
	 */
	T getOneById(Long id);
}
