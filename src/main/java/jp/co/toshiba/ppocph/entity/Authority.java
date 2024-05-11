package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;

import gaarason.database.annotation.Column;
import gaarason.database.annotation.Primary;
import gaarason.database.annotation.Table;
import jp.co.toshiba.ppocph.utils.OgumaPrimaryKeyGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 権限エンティティ
 *
 * @author ArkamaHozota
 * @since 5.41
 */
@Data
@Table(name = "authorities")
@EqualsAndHashCode(callSuper = false)
public final class Authority implements Serializable {

	private static final long serialVersionUID = -1152271767975364197L;

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
	 * タイトル
	 */
	@Column(nullable = false)
	private String title;

	/**
	 * 親ディレクトリID
	 */
	private Long categoryId;
}
