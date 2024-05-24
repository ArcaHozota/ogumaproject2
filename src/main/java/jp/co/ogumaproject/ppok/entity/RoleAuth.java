package jp.co.ogumaproject.ppok.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 役割権限エンティティ
 *
 * @author ArkamaHozota
 * @since 9.73
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class RoleAuth extends CommonEntity {

	private static final long serialVersionUID = -9006557479323417078L;

	/**
	 * 役割ID
	 */
	private Long roleId;

	/**
	 * 権限ID
	 */
	private Long authId;
}
