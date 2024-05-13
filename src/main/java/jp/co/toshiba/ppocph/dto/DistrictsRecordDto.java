package jp.co.toshiba.ppocph.dto;

import java.io.Serializable;

import jp.co.toshiba.ppocph.jooq.tables.records.DistrictsRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 地域情報データ転送クラス
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class DistrictsRecordDto extends DistrictsRecord implements Serializable {

	private static final long serialVersionUID = 4901833523366402492L;

	/**
	 * 州都名称
	 */
	private String shutoName;
}
