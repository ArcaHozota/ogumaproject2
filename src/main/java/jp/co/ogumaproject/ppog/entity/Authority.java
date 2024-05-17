package jp.co.ogumaproject.ppog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 権限エンティティ
 *
 * @author ArkamaHozota
 * @since 9.63
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class Authority extends CommonEntity {

	private static final long serialVersionUID = 3532185211842763340L;

	/**
	 * 権限名称
	 */
	private String name;

	/**
	 * 権限論理名称
	 */
	private String title;

	/**
	 * 権限親ID
	 */
	private Long categoryId;
}
