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

	public static final String URL_EMPLOYEE_DELETE = "/pgcrowd/employee/delete/**";

	public static final String URL_EMPLOYEE_TO_ADDITION = "/pgcrowd/employee/to/addition";

	public static final String URL_EMPLOYEE_TO_EDITION = "/pgcrowd/employee/to/edition";

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

	public static final String URL_ROLE_CHECK_EDITION = "/pgcrowd/role/checkEdition";

	public static final String URL_STATIC_RESOURCE = "/static/**";

	public static final String URL_TO_SIGN_UP = "/pgcrowd/employee/to/signup";

	public static final String URL_DO_SIGN_UP = "/pgcrowd/employee/toroku";

	public static final String URL_TO_LOGIN = "/pgcrowd/employee/login";

	public static final String URL_DO_LOGIN = "/pgcrowd/employee/do/login";

	public static final String URL_LOG_OUT = "/pgcrowd/employee/logout";

	public static final String URL_TO_MAINMENU = "/pgcrowd/to/mainmenu";

	public static final String URL_MENU_INITIAL = "/pgcrowd/menu/initial";

	public static final String URL_CATEGORY_INITIAL = "/pgcrowd/category/initial";

	public static final String URL_TO_DISTRICT_PAGES = "/pgcrowd/category/to/districtPages";

	public static final String URL_DISTRICT_PAGINATION = "/pgcrowd/district/pagination";

	public static final String URL_DISTRICT_UPDATE = "/pgcrowd/district/infoupd";

	public static final String URL_TO_CITY_PAGES = "/pgcrowd/category/to/cityPages";
}
