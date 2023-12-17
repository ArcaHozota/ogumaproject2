package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 社員役割連携エンティティ
 *
 * @author ArkamaHozota
 * @since 5.00
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "employee_role")
public final class EmployeeEx implements Serializable {

	private static final long serialVersionUID = 8049959021603519067L;

	/**
	 * 社員ID
	 */
	@Id
	private Long employeeId;

	/**
	 * 役割ID
	 */
	@Column(nullable = false)
	private Long roleId;
}
