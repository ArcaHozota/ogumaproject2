package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 役割権限情報ID
 *
 * @author ArkamaHozota
 * @since 5.76
 */
@Data
public final class RoleIds implements Serializable {

	private static final long serialVersionUID = -297785511370318383L;

	private Long roleId;

	private Long authId;
}
