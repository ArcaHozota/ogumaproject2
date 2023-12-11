package jp.co.toshiba.ppocph.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * プロジェクトコンスタント
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PgCrowdConstants {

	public static final Integer DEFAULT_PAGE_SIZE = 7;

	public static final String MSG001 = "拡張メッセージコンバーターの設置は完了しました。";

	public static final String MSG002 = "静的リソースのマッピングが開始しました。";

	public static final String MSG003 = "アプリは正常に起動しました。";

	public static final String ATTRNAME_EXCEPTION = "exception";

	public static final String ATTRNAME_LOGIN_ADMIN = "employee";

	public static final String ATTRNAME_EDITED_INFO = "arawaseta";

	public static final String ATTRNAME_EMPLOYEEROLES = "employeeRoles";

	public static final String ATTRNAME_PAGE_INFO = "pageInfo";

	public static final String ATTRNAME_PAGE_NUMBER = "pageNum";

	public static final String LOGIC_DELETE_FLG = "removed";

	public static final String LOGIC_DELETE_INITIAL = "visible";

	public static final String EMPLOYEE_ABNORMAL_STATUS = "rejected";

	public static final String EMPLOYEE_NORMAL_STATUS = "approved";

	public static final String MESSAGE_STRING_INVALIDATE = "Invalid password string";

	public static final String MESSAGE_STRING_NOTLOGIN = "ログインしてください";

	public static final String MESSAGE_STRING_PROHIBITED = "ユーザは存在しません、もう一度やり直してください";

	public static final String MESSAGE_STRING_NOTEXISTS = "役割は存在しません、もう一度やり直してください";

	public static final String MESSAGE_STRING_FORBIDDEN = "役割は利用されています、削除できません。";

	public static final String MESSAGE_STRING_DUPLICATED = "ログインアカウントがすでに存在します。";

	public static final String MESSAGE_ROLE_NAME_DUPLICATED = "役割名称がすでに存在します。";

	public static final String DEFAULT_ROLE_NAME = "---------------------------";
}
