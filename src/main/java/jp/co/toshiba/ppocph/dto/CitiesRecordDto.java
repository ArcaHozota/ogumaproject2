package jp.co.toshiba.ppocph.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 都市情報データ転送クラス
 */
@Data
public final class CitiesRecordDto implements Serializable {

	private static final long serialVersionUID = 2138976388159938175L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 都道府県ID
	 */
	private Long districtId;

	/**
	 * 読み方
	 */
	private String pronunciation;

	/**
	 * 都道府県名称
	 */
	private String districtName;

	/**
	 * 人口数量
	 */
	private Long population;

	/**
	 * 市町村旗
	 */
	private String cityFlag;

	/**
	 * 論理削除フラグ
	 */
	private String deleteFlg;
}
