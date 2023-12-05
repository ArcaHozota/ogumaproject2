package jp.co.toshiba.ppocph.dto;

import lombok.Data;

/**
 * 役割情報転送クラス
 */
@Data
public final class RoleDto {

	/**
	 * ID
	 */
	private Integer id;

	/**
	 * アカウント
	 */
	private String name;
}
