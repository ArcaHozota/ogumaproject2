package jp.co.ogumaproject.ppog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 社員役割エンティティ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeeRole extends CommonEntity {

	private static final long serialVersionUID = -4412331507806836307L;

	/**
	 * 社員ID
	 */
	private Long employeeId;

	/**
	 * 役割ID
	 */
	private Long roleId;
}
