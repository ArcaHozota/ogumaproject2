package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 社員エンティティ
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "employee")
public final class Employee implements Serializable {

	private static final long serialVersionUID = -7478708453453699683L;

	/**
	 * ID
	 */
	@Id
	private Integer id;

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
	 * 作成時間
	 */
	@Column(nullable = false)
	private LocalDateTime createdTime;
}
