package jp.co.toshiba.ppocph.listener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import jp.co.toshiba.ppocph.common.PgcrowdConstants;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.exception.LoginFailedException;

/**
 * ログインインターセプター
 *
 * @author ArkamaHozota
 * @since 1.17
 */
@Component
@Order(10)
public final class LoginInterceptor implements AsyncHandlerInterceptor {

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
			final Object object) {
		// パスマッチャーを定義する
		final PathMatcher pathMatcher = new AntPathMatcher();
		// リクエストを通じてセッションのオブジェクトを取得する
		final HttpSession session = request.getSession();
		// 获取请求的路径
		final String path = request.getServletPath();
		// 定义一个数组，存放不需要拦截的路径
		final String[] excludes = { "/pgcrowd/employee/login", "/pgcrowd/employee/do/login", "/pgcrowd/employee/logout",
				"/pgcrowd/to/index" };
		// 遍历数组，判断当前路径是否在其中
		for (final String exclude : excludes) {
			// 如果匹配，返回 true，表示不拦截
			if (pathMatcher.match(exclude, path)) {
				return true;
			}
		}
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
