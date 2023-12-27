package jp.co.toshiba.ppocph.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * プロジェクトURLコンスタント
 *
 * @author ArkamaHozota
 * @since 7.07
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PgCrowdURIConstants {

	public static final String EMPLOYEE_PAGINATION = "/pgcrowd/employee/pagination";

	public static final String EMPLOYEE_CHECK = "/pgcrowd/employee/check";

	public static final String EMPLOYEE_DELETE = "/pgcrowd/employee/delete/{userId}";

	public static final String EMPLOYEE_ADDITION = "/pgcrowd/employee/to/addition";

	public static final String EMPLOYEE_EDITION = "/pgcrowd/employee/to/edition";

	public static final String EMPLOYEE_INSERT = "/pgcrowd/employee/infosave";

	public static final String EMPLOYEE_UPDATE = "/pgcrowd/employee/infoupd";

	public static final String EMPLOYEE_RESTORE = "/pgcrowd/employee/inforestore";

	public static final String ROLE_PAGINATION = "/pgcrowd/role/pagination";

	public static final String ROLE_INSERT = "/pgcrowd/role/infosave";

	public static final String ROLE_UPDATE = "/pgcrowd/role/infoupd";

	public static final String ROLE_DELETE = "/pgcrowd/role/delete/{roleId}";

	public static final String ROLE_CHECK = "/pgcrowd/role/check";

	public static final String ROLE_GETASSIGNED = "/pgcrowd/role/getAssigned";

	public static final String ROLE_ASSIGNMENT = "/pgcrowd/role/do/assignment";

	public static final String ROLE_AUTHLIST = "/pgcrowd/role/authlists";
}
