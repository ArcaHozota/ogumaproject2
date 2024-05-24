package jp.co.ogumaproject.ppok.repository;

import jp.co.ogumaproject.ppok.entity.RoleAuth;

/**
 * 役割権限リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.77
 */
public interface RoleAuthRepository extends CommonRepository<RoleAuth> {

	/**
	 * 外部キーによってバッチ削除する
	 *
	 * @param foreignKey 外部キー
	 */
	void batchRemoveByForeignKey(Long foreignKey);
}
