package jp.co.toshiba.ppocph.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 地域情報データ転送クラス
 */
@Data
public final class DistrictsRecordDto implements Serializable {

	private static final long serialVersionUID = 4901833523366402492L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 地方名称
	 */
	private String chiho;

	/**
	 * 都道府県旗
	 */
	private String districtFlag;

	/**
	 * 州都ID
	 */
	private Long shutoId;

	/**
	 * 州都名称
	 */
	private String shutoName;

	/**
	 * 論理削除フラグ
	 */
	private String deleteFlg;
}
