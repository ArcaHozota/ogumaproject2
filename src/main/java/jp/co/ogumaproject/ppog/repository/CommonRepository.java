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
}
