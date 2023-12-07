package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

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
@IdClass(EmployeeIds.class)
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
	@Id
	private Long roleId;
}
