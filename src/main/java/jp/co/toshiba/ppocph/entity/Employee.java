package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import gaarason.database.annotation.Column;
import gaarason.database.annotation.Primary;
import gaarason.database.annotation.Table;
import jp.co.toshiba.ppocph.utils.OgumaPrimaryKeyGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 社員エンティティ
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Data
@Table(name = "employees")
@EqualsAndHashCode(callSuper = false)
public final class Employee implements Serializable {

	private static final long serialVersionUID = -7478708453453699683L;

	/**
	 * ID
	 */
	@Primary(idGenerator = OgumaPrimaryKeyGenerator.class)
	private Long id;

	/**
	 * アカウント
	 */
	@Column(nullable = false)
	private String loginAccount;

	/**
	 * ユーザ名称
	 */
	@Column(nullable = false)
	private String username;

	/**
	 * パスワード
	 */
	@Column(nullable = false)
	private String password;

	/**
	 * メール
	 */
	@Column(nullable = false)
	private String email;

	/**
	 * 生年月日
	 */
	@Column(nullable = false)
	private LocalDate dateOfBirth;

	/**
	 * 作成時間
	 */
	@Column(nullable = false)
	private LocalDateTime createdTime;

	/**
	 * 論理削除フラグ
	 */
	@Column(nullable = false)
	private String deleteFlg;
}
