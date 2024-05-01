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

	public static final String URL_INDEX = "/index";

	public static final String URL_EMPLOYEE_PAGINATION = "/oguma/employee/pagination";

	public static final String URL_EMPLOYEE_CHECK = "/oguma/employee/check";

	public static final String URL_EMPLOYEE_DELETE = "/oguma/employee/infoDelete/{userId}";

	public static final String URL_EMPLOYEE_TO_ADDITION = "/oguma/employee/toAddition";

	public static final String URL_EMPLOYEE_TO_EDITION = "/oguma/employee/toEdition";

	public static final String URL_EMPLOYEE_INSERT = "/oguma/employee/infoSave";

	public static final String URL_EMPLOYEE_UPDATE = "/oguma/employee/infoUpdate";

	public static final String URL_EMPLOYEE_TO_PAGES = "/oguma/employee/toPages";

	public static final String URL_ROLE_PAGINATION = "/oguma/role/pagination";

	public static final String URL_ROLE_INSERT = "/oguma/role/infoSave";

	public static final String URL_ROLE_UPDATE = "/oguma/role/infoUpdate";

	public static final String URL_ROLE_DELETE = "/oguma/role/infoDelete/{roleId}";

	public static final String URL_ROLE_CHECK = "/oguma/role/check";

	public static final String URL_ROLE_GET_ASSIGNED = "/oguma/role/getAssigned";

	public static final String URL_ROLE_ASSIGNMENT = "/oguma/role/doAssignment";

	public static final String URL_ROLE_AUTHLIST = "/oguma/role/getAuthlist";

	public static final String URL_ROLE_TO_PAGES = "/oguma/role/toPages";

	public static final String URL_ROLE_CHECK_EDITION = "/oguma/role/checkEdition";

	public static final String URL_TO_SIGN_UP = "/oguma/employee/toSignUp";

	public static final String URL_DO_SIGN_UP = "/oguma/employee/toroku";

	public static final String URL_TO_LOGIN = "/oguma/employee/login";

	public static final String URL_DO_LOGIN = "/oguma/employee/doLogin";

	public static final String URL_LOG_OUT = "/oguma/employee/logout";

	public static final String URL_TO_MAINMENU = "/oguma/category/toMainmenu";

	public static final String URL_MENU_INITIAL = "/oguma/category/menuInitial";

	public static final String URL_CATEGORY_INITIAL = "/oguma/category/initial";

	public static final String URL_TO_DISTRICT_PAGES = "/oguma/category/toDistrictPages";

	public static final String URL_DISTRICT_PAGINATION = "/oguma/district/pagination";

	public static final String URL_DISTRICT_UPDATE = "/oguma/district/infoUpdate";

	public static final String URL_DISTRICT_CHECK_EDITION = "/oguma/district/checkEdition";

	public static final String URL_TO_CITY_PAGES = "/oguma/category/toCityPages";

	public static final String URL_CITY_CHECK = "/oguma/city/check";

	public static final String URL_CITY_PAGINATION = "/oguma/city/pagination";

	public static final String URL_CITY_DISTRICTS = "/oguma/city/getDistricts";

	public static final String URL_CITY_INSERT = "/oguma/city/infoSave";

	public static final String URL_CITY_UPDATE = "/oguma/city/infoUpdate";

	public static final String URL_CITY_DELETE = "/oguma/city/infoDelete/{cityId}";

	public static final String URL_CITY_CHECK_EDITION = "/oguma/city/checkEdition";

	public static final String URL_FORGET_PASSWORD = "/oguma/employee/forget/password";

	public static final String URL_RESET_PASSWORD = "/oguma/employee/reset/password";
}
