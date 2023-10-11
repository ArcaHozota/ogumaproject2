package jp.co.toshiba.ppocph.listener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.AsyncHandlerInterceptor;

import jp.co.toshiba.ppocph.common.PgcrowdConstants;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.exception.LoginFailedException;

/**
 * アノテーションに基づく例外ハンドラークラス
 *
 * @author ArkamaHozota
 * @since 1.17
 */
public final class LoginInterceptor implements AsyncHandlerInterceptor {

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
			final Object object) {
		// リクエストを通じてセッションのオブジェクトを取得する
		final HttpSession session = request.getSession();
		// セッションドメインから社員オブジェクトを取得する
		final Employee employee = (Employee) session.getAttribute(PgcrowdConstants.ATTRNAME_LOGIN_ADMIN);
		// 社員オブジェクトが空かどうかを判断する
		if (employee == null) {
			// 例外をスローする
			throw new LoginFailedException(PgcrowdConstants.MESSAGE_STRING_NOTLOGIN);
		}
		return true;
	}
}
