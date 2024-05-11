package jp.co.toshiba.ppocph.repository;

import org.springframework.stereotype.Repository;

import jp.co.toshiba.ppocph.entity.Authority;

/**
 * 権限管理リポジトリ
 *
 * @author ArkamaHozota
 * @since 5.41
 */
@Repository
public class AuthorityRepository extends BasicModel<Authority, Long> {

	/**
	 * 是否启用软删除
	 */
	@Override
	protected boolean softDeleting() {
		return false;
	}
}
