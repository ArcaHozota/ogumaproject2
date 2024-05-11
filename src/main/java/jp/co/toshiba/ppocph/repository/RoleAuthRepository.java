package jp.co.toshiba.ppocph.repository;

import org.springframework.stereotype.Repository;

import jp.co.toshiba.ppocph.entity.RoleAuth;

/**
 * 役割権限連携リポジトリ
 *
 * @author ArkamaHozota
 * @since 5.77
 */
@Repository
public class RoleAuthRepository extends BasicModel<RoleAuth, Long> {

	/**
	 * 是否启用软删除
	 */
	@Override
	protected boolean softDeleting() {
		return false;
	}
}
