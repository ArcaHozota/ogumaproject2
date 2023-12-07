package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 社員役割情報ID
 *
 * @author ArkamaHozota
 * @since 5.00
 */
@Data
public final class EmployeeIds implements Serializable {

	private static final long serialVersionUID = -1725153510130655853L;

	private Long employeeId;

	private Long roleId;
}
