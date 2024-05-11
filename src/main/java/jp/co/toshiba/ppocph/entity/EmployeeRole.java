package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;

import gaarason.database.annotation.Column;
import gaarason.database.annotation.Primary;
import gaarason.database.annotation.Table;
import jp.co.toshiba.ppocph.utils.OgumaPrimaryKeyGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 社員役割連携エンティティ
 *
 * @author ArkamaHozota
 * @since 5.00
 */
@Data
@Table(name = "employee_role")
@EqualsAndHashCode(callSuper = false)
public final class EmployeeRole implements Serializable {

	private static final long serialVersionUID = 8049959021603519067L;

	/**
	 * 社員ID
	 */
	@Primary(idGenerator = OgumaPrimaryKeyGenerator.class)
	private Long employeeId;

	/**
	 * 役割ID
	 */
	@Column(nullable = false)
	private Long roleId;
}
