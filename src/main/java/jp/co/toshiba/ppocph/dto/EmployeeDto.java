package jp.co.toshiba.ppocph.dto;

import lombok.Data;

/**
 * 社員情報転送クラス
 */
@Data
public class EmployeeDto {

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
}
