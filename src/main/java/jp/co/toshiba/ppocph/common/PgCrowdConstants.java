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

	public static final String MSG001 = "拡張メッセージコンバーターの設置は完了しました。";

	public static final String MSG002 = "静的リソースのマッピングが開始しました。";

	public static final String MSG003 = "アプリは正常に起動しました!";

	public static final String MSG004 = "visible";

	public static final String MSG005 = "removed";

	public static final String ATTRNAME_EXCEPTION = "exception";

	public static final String ATTRNAME_LOGIN_ADMIN = "employee";

	public static final String ATTRNAME_PAGE_INFO = "pageInfo";

	public static final String MESSAGE_STRING_INVALIDATE = "Invalid password string";

	public static final String MESSAGE_STRING_NOTLOGIN = "ログインしてください";

	public static final String MESSAGE_STRING_PROHIBITED = "ユーザは存在しません、もう一度やり直してください";
}