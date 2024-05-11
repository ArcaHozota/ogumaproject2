package jp.co.toshiba.ppocph.utils;

import gaarason.database.contract.support.IdGenerator;

/**
 * カストマイズプライマリーキー作成クラス
 *
 * @author ArkamaHozota
 * @since 9.22
 */
public class OgumaPrimaryKeyGenerator implements IdGenerator<Long> {

	@Override
	public Long nextId() {
		return SnowflakeUtils.snowflakeId();
	}

}
