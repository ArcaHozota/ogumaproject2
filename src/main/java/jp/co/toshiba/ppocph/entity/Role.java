package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
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
@Table(name = "role")
@NamedQuery(name = "Role.findByIdLike", query = "select ac from Role as ac where ac.deleteFlg = 'approved' and ac.id like:idLike")
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
