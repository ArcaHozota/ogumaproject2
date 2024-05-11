package jp.co.toshiba.ppocph.repository;

import org.springframework.stereotype.Repository;

import gaarason.database.contract.eloquent.Builder;
import jp.co.toshiba.ppocph.common.OgumaProjectConstants;
import jp.co.toshiba.ppocph.entity.Employee;

/**
 * 社員管理リポジトリ
 *
 * @author ArkamaHozota
 * @since 1.16
 */
@Repository
public class EmployeeRepository extends BasicModel<Employee, Long> {

	/**
	 * 删除(软/硬删除)
	 *
	 * @param builder 查询构造器
	 * @return 删除的行数
	 */
	@Override
	public int delete(final Builder<Employee, Long> builder) {
		return this.softDeleting() ? this.softDelete(builder) : builder.forceDelete();
	}

	/**
	 * 恢复软删除
	 *
	 * @param builder 查询构造器
	 * @return 删除的行数
	 */
	@Override
	public int restore(final Builder<Employee, Long> builder) {
		return this.softDeleteRestore(builder);
	}

	/**
	 * 软删除查询作用域
	 *
	 * @param builder 查询构造器
	 */
	@Override
	protected void scopeSoftDelete(final Builder<Employee, Long> builder) {
		builder.where("delete_flg", OgumaProjectConstants.LOGIC_DELETE_FLG);
	}

	/**
	 * 软删除查询作用域(反)
	 *
	 * @param builder 查询构造器
	 */
	@Override
	protected void scopeSoftDeleteOnlyTrashed(final Builder<Employee, Long> builder) {
		builder.where("delete_flg", OgumaProjectConstants.LOGIC_DELETE_INITIAL);
	}

	/**
	 * 软删除查询作用域(反)
	 *
	 * @param builder 查询构造器
	 */
	@Override
	protected void scopeSoftDeleteWithTrashed(final Builder<Employee, Long> builder) {
		builder.where("delete_flg", OgumaProjectConstants.LOGIC_DELETE_INITIAL);
	}

	/**
	 * 软删除实现
	 *
	 * @param builder 查询构造器
	 * @return 删除的行数
	 */
	@Override
	protected int softDelete(final Builder<Employee, Long> builder) {
		return builder.data("delete_flg", OgumaProjectConstants.LOGIC_DELETE_FLG).update();
	}

	/**
	 * 恢复软删除实现
	 *
	 * @param builder 查询构造器
	 * @return 恢复的行数
	 */
	@Override
	protected int softDeleteRestore(final Builder<Employee, Long> builder) {
		return builder.data("delete_flg", OgumaProjectConstants.LOGIC_DELETE_INITIAL).update();
	}

	/**
	 * 是否启用软删除
	 */
	@Override
	protected boolean softDeleting() {
		return true;
	}
}
