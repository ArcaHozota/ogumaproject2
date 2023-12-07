package jp.co.toshiba.ppocph.dto;

import jp.co.toshiba.ppocph.entity.Role;
import lombok.Data;

/**
 * 社員情報転送クラス
 *
 * @author ArkamaHozota
 * @since 3.57
 */
@Data
public final class EmployeeDto {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * アカウント
	 */
	private String loginAccount;

	/**
	 * ユーザ名称
	 */
	private String username;

	/**
	 * パスワード
	 */
	private String password;

	/**
	 * メール
	 */
	private String email;

	/**
	 * 役割
	 */
	private Role role;
}
