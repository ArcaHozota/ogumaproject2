package jp.co.ogumaproject.ppok.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 地域エンティティ
 *
 * @author ArkamaHozota
 * @since 9.95
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class District extends CommonEntity {

	private static final long serialVersionUID = -4147005411969906444L;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 州都ID
	 */
	private Long shutoId;

	/**
	 * 地方ID
	 */
	private Long chihoId;

	/**
	 * 州都名称
	 */
	private String shutoName;

	/**
	 * 地方名称
	 */
	private String chihoName;

	/**
	 * 都道府県旗
	 */
	private String districtFlag;
}
