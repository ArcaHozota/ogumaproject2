package jp.co.toshiba.ppocph.dto;

import java.io.Serializable;

import jp.co.toshiba.ppocph.jooq.tables.records.CitiesRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 都市情報データ転送クラス
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class CitiesRecordDto extends CitiesRecord implements Serializable {

	private static final long serialVersionUID = 2138976388159938175L;

	/**
	 * 都道府県名称
	 */
	private String districtName;
}
