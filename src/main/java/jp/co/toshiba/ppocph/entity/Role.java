package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;

import gaarason.database.annotation.Column;
import gaarason.database.annotation.Primary;
import gaarason.database.annotation.Table;
import jp.co.toshiba.ppocph.utils.OgumaPrimaryKeyGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 役割エンティティ
 *
 * @author ArkamaHozota
 * @since 4.45
 */
@Data
@Table(name = "roles")
@EqualsAndHashCode(callSuper = false)
public final class Role implements Serializable {

	private static final long serialVersionUID = 4360593022825424340L;

	/**
	 * ID
	 */
	@Primary(idGenerator = OgumaPrimaryKeyGenerator.class)
	private Long id;

	/**
	 * 名称
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 論理削除フラグ
	 */
	@Column(nullable = false)
	private String deleteFlg;
}
