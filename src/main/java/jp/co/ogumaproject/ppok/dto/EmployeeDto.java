package jp.co.ogumaproject.ppok.dto;

/**
 * 社員情報転送クラス
 *
 * @author ArkamaHozota
 * @since 3.57
 */
public record EmployeeDto(

		/**
		 * ID
		 */
		Long id,

		/**
		 * アカウント
		 */
		String loginAccount,

		/**
		 * ユーザ名称
		 */
		String username,

		/**
		 * パスワード
		 */
		String password,

		/**
		 * メール
		 */
		String email,

		/**
		 * 生年月日
		 */
		String dateOfBirth,

		/**
		 * 役割ID
		 */
		Long roleId) {
}
