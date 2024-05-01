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
public final class OgumaProjectURLConstants {

	public static final String URL_STATIC_RESOURCE = "/static/**";

	public static final String URL_EMPLOYEE_PAGINATION = "/pgcrowd/employee/pagination";

	public static final String URL_EMPLOYEE_CHECK = "/pgcrowd/employee/check";

	public static final String URL_EMPLOYEE_DELETE = "/pgcrowd/employee/infoDelete/{userId}";

	public static final String URL_EMPLOYEE_TO_ADDITION = "/pgcrowd/employee/toAddition";

	public static final String URL_EMPLOYEE_TO_EDITION = "/pgcrowd/employee/toEdition";

	public static final String URL_EMPLOYEE_INSERT = "/pgcrowd/employee/infoSave";

	public static final String URL_EMPLOYEE_UPDATE = "/pgcrowd/employee/infoUpdate";

	public static final String URL_EMPLOYEE_RESTORE = "/pgcrowd/employee/infoRestore";

	public static final String URL_EMPLOYEE_TO_PAGES = "/pgcrowd/employee/toPages";

	public static final String URL_ROLE_PAGINATION = "/pgcrowd/role/pagination";

	public static final String URL_ROLE_INSERT = "/pgcrowd/role/infoSave";

	public static final String URL_ROLE_UPDATE = "/pgcrowd/role/infoUpdate";

	public static final String URL_ROLE_DELETE = "/pgcrowd/role/infoDelete/{roleId}";

	public static final String URL_ROLE_CHECK = "/pgcrowd/role/check";

	public static final String URL_ROLE_GET_ASSIGNED = "/pgcrowd/role/getAssigned";

	public static final String URL_ROLE_ASSIGNMENT = "/pgcrowd/role/doAssignment";

	public static final String URL_ROLE_AUTHLIST = "/pgcrowd/role/getAuthlist";

	public static final String URL_ROLE_TO_PAGES = "/pgcrowd/role/toPages";

	public static final String URL_ROLE_CHECK_EDITION = "/pgcrowd/role/checkEdition";

	public static final String URL_TO_SIGN_UP = "/pgcrowd/employee/toSignUp";

	public static final String URL_DO_SIGN_UP = "/pgcrowd/employee/toroku";

	public static final String URL_TO_LOGIN = "/pgcrowd/employee/login";

	public static final String URL_DO_LOGIN = "/pgcrowd/employee/doLogin";

	public static final String URL_LOG_OUT = "/pgcrowd/employee/logout";

	public static final String URL_TO_MAINMENU = "/pgcrowd/category/toMainmenu";

	public static final String URL_MENU_INITIAL = "/pgcrowd/category/menuInitial";

	public static final String URL_CATEGORY_INITIAL = "/pgcrowd/category/initial";

	public static final String URL_TO_DISTRICT_PAGES = "/pgcrowd/category/toDistrictPages";

	public static final String URL_DISTRICT_PAGINATION = "/pgcrowd/district/pagination";

	public static final String URL_DISTRICT_UPDATE = "/pgcrowd/district/infoUpdate";

	public static final String URL_DISTRICT_CHECK_EDITION = "/pgcrowd/district/checkEdition";

	public static final String URL_TO_CITY_PAGES = "/pgcrowd/category/toCityPages";

	public static final String URL_CITY_CHECK = "/pgcrowd/city/check";

	public static final String URL_CITY_PAGINATION = "/pgcrowd/city/pagination";

	public static final String URL_CITY_DISTRICTS = "/pgcrowd/city/getDistricts";

	public static final String URL_CITY_INSERT = "/pgcrowd/city/infoSave";

	public static final String URL_CITY_UPDATE = "/pgcrowd/city/infoUpdate";

	public static final String URL_CITY_DELETE = "/pgcrowd/city/infoDelete/{cityId}";

	public static final String URL_CITY_CHECK_EDITION = "/pgcrowd/city/checkEdition";

	public static final String URL_FORGET_PASSWORD = "/pgcrowd/employee/forget/password";

	public static final String URL_RESET_PASSWORD = "/pgcrowd/employee/reset/password";
}
