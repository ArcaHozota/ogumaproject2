package jp.co.toshiba.ppocph.dto;

/**
 * 社員情報転送クラス
 *
 * @author ArkamaHozota
 * @since 3.57
 */
public record EmployeeDto(Long id, String loginAccount, String username, String password, String email,
		String dateOfBirth, Long roleId) {
}
