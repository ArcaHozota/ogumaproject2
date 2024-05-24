package jp.co.ogumaproject.ppok.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 共通エンティティ
 *
 * @author ArkamaHozota
 * @since 9.63
 */
@Data
public abstract class CommonEntity implements Serializable {

	private static final long serialVersionUID = -8670508359179225927L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 論理削除フラグ
	 */
	private String delFlg;
}
