package jp.co.ogumaproject.ppok.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 社員エンティティ
 *
 * @author ArkamaHozota
 * @since 9.63
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class Employee extends CommonEntity {

	private static final long serialVersionUID = -907215202672333880L;

	/**
	 * アカウント
	 */
	private String loginAccount;

	/**
	 * パスワード
	 */
	private String password;

	/**
	 * 社員名称
	 */
	private String username;

	/**
	 * メール
	 */
	private String email;

	/**
	 * 作成時間
	 */
	private LocalDateTime createdTime;

	/**
	 * 生年月日
	 */
	private LocalDate dateOfBirth;
}
