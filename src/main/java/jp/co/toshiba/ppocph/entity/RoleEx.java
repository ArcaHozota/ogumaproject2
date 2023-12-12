package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 役割権限連携エンティティ
 *
 * @author ArkamaHozota
 * @since 5.76
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "role_auth")
@IdClass(RoleIds.class)
public final class RoleEx implements Serializable {

	private static final long serialVersionUID = 4995165208601855074L;

	/**
	 * 権限ID
	 */
	@Id
	private Long authId;

	/**
	 * 役割ID
	 */
	@Id
	private Long roleId;
}
