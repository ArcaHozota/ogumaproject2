package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;

import gaarason.database.annotation.Primary;
import gaarason.database.annotation.Table;
import jp.co.toshiba.ppocph.utils.OgumaPrimaryKeyGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 役割権限連携エンティティ
 *
 * @author ArkamaHozota
 * @since 5.76
 */
@Data
@Table(name = "role_auth")
@EqualsAndHashCode(callSuper = false)
public final class RoleAuth implements Serializable {

	private static final long serialVersionUID = 4995165208601855074L;

	/**
	 * 権限ID
	 */
	@Primary(idGenerator = OgumaPrimaryKeyGenerator.class)
	private Long authId;

	/**
	 * 役割ID
	 */
	@Primary(idGenerator = OgumaPrimaryKeyGenerator.class)
	private Long roleId;
}
