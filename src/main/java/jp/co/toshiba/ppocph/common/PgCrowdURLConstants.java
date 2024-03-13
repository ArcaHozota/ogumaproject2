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
public final class PgCrowdURLConstants {

	public static final String URL_EMPLOYEE_PAGINATION = "/pgcrowd/employee/pagination";

	public static final String URL_EMPLOYEE_CHECK = "/pgcrowd/employee/check";

	public static final String URL_EMPLOYEE_DELETE = "/pgcrowd/employee/delete/{userId}";

	public static final String URL_EMPLOYEE_ADDITION = "/pgcrowd/employee/to/addition";

	public static final String URL_EMPLOYEE_EDITION = "/pgcrowd/employee/to/edition";

	public static final String URL_EMPLOYEE_INSERT = "/pgcrowd/employee/infosave";

	public static final String URL_EMPLOYEE_UPDATE = "/pgcrowd/employee/infoupd";

	public static final String URL_EMPLOYEE_RESTORE = "/pgcrowd/employee/inforestore";

	public static final String URL_EMPLOYEE_TO_PAGES = "/pgcrowd/employee/to/pages";

	public static final String URL_ROLE_PAGINATION = "/pgcrowd/role/pagination";

	public static final String URL_ROLE_INSERT = "/pgcrowd/role/infosave";

	public static final String URL_ROLE_UPDATE = "/pgcrowd/role/infoupd";

	public static final String URL_ROLE_DELETE = "/pgcrowd/role/delete/**";

	public static final String URL_ROLE_CHECK = "/pgcrowd/role/check";

	public static final String URL_ROLE_GET_ASSIGNED = "/pgcrowd/role/getAssigned";

	public static final String URL_ROLE_ASSIGNMENT = "/pgcrowd/role/do/assignment";

	public static final String URL_ROLE_AUTHLIST = "/pgcrowd/role/authlists";

	public static final String URL_ROLE_TO_PAGES = "/pgcrowd/role/to/pages";
}
