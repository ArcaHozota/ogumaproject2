package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 役割エンティティ
 *
 * @author ArkamaHozota
 * @since 4.45
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "roles")
@EqualsAndHashCode(callSuper = false)
public final class Role implements Serializable {

	private static final long serialVersionUID = 4360593022825424340L;

	/**
	 * ID
	 */
	@Id
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
