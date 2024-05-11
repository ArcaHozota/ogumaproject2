package jp.co.toshiba.ppocph.repository;

import org.springframework.stereotype.Repository;

import jp.co.toshiba.ppocph.entity.EmployeeRole;

/**
 * 社員役割連携リポジトリ
 *
 * @author ArkamaHozota
 * @since 5.00
 */
@Repository
public class EmployeeRoleRepository extends BasicModel<EmployeeRole, Long> {

	/**
	 * 是否启用软删除
	 */
	@Override
	protected boolean softDeleting() {
		return false;
	}
}
