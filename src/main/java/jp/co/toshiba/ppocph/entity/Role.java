package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
public final class Role implements Serializable {

	private static final long serialVersionUID = 4360593022825424340L;

	/**
	 * ID
	 */
	@Id
	private Integer id;

	/**
	 * 名称
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 作成時間
	 */
	@Column(nullable = false)
	private String createdTime;

	/**
	 * 論理削除フラグ
	 */
	@Column(nullable = false)
	private String deleteFlg;
}
