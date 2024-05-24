package jp.co.ogumaproject.ppok.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 役割エンティティ
 *
 * @author ArkamaHozota
 * @since 9.73
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class Role extends CommonEntity {

	private static final long serialVersionUID = 7411663286924761234L;

	/**
	 * 名称
	 */
	private String name;
}
