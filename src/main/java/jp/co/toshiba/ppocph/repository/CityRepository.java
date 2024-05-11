package jp.co.toshiba.ppocph.repository;

import org.springframework.stereotype.Repository;

import gaarason.database.contract.eloquent.Builder;
import jp.co.toshiba.ppocph.common.OgumaProjectConstants;
import jp.co.toshiba.ppocph.entity.City;

/**
 * 都市管理リポジトリ
 *
 * @author ArkamaHozota
 * @since 7.88
 */
@Repository
public class CityRepository extends BasicModel<City, Long> {

	/**
	 * 删除(软/硬删除)
	 *
	 * @param builder 查询构造器
	 * @return 删除的行数
	 */
	@Override
	public int delete(final Builder<City, Long> builder) {
		return this.softDeleting() ? this.softDelete(builder) : builder.forceDelete();
	}

	/**
	 * 恢复软删除
	 *
	 * @param builder 查询构造器
	 * @return 删除的行数
	 */
	@Override
	public int restore(final Builder<City, Long> builder) {
		return this.softDeleteRestore(builder);
	}

	/**
	 * 软删除查询作用域
	 *
	 * @param builder 查询构造器
	 */
	@Override
	protected void scopeSoftDelete(final Builder<City, Long> builder) {
		builder.where("delete_flg", OgumaProjectConstants.LOGIC_DELETE_FLG);
	}

	/**
	 * 软删除查询作用域(反)
	 *
	 * @param builder 查询构造器
	 */
	@Override
	protected void scopeSoftDeleteOnlyTrashed(final Builder<City, Long> builder) {
		builder.where("delete_flg", OgumaProjectConstants.LOGIC_DELETE_INITIAL);
	}

	/**
	 * 软删除查询作用域(反)
	 *
	 * @param builder 查询构造器
	 */
	@Override
	protected void scopeSoftDeleteWithTrashed(final Builder<City, Long> builder) {
		builder.where("delete_flg", OgumaProjectConstants.LOGIC_DELETE_INITIAL);
	}

	/**
	 * 软删除实现
	 *
	 * @param builder 查询构造器
	 * @return 删除的行数
	 */
	@Override
	protected int softDelete(final Builder<City, Long> builder) {
		return builder.data("delete_flg", OgumaProjectConstants.LOGIC_DELETE_FLG).update();
	}

	/**
	 * 恢复软删除实现
	 *
	 * @param builder 查询构造器
	 * @return 恢复的行数
	 */
	@Override
	protected int softDeleteRestore(final Builder<City, Long> builder) {
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
