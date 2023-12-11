package jp.co.toshiba.ppocph.listener;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.exception.LoginFailedException;
import jp.co.toshiba.ppocph.exception.PgCrowdException;
import jp.co.toshiba.ppocph.utils.PgCrowdUtils;
import jp.co.toshiba.ppocph.utils.ResultDto;

/**
 * アノテーションに基づく例外ハンドラークラス
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@ControllerAdvice
public final class PgCrowdExceptionResolver {

	/**
	 * コア例外処理メソッド
	 *
	 * @param exception 例外
	 * @param request   リクエスト
	 * @param response  リスポンス
	 * @param viewName  ビュー名称
	 * @return ModelAndView
	 * @throws IOException
	 */
	private ModelAndView commonResolveException(final Exception exception, final HttpServletRequest request,
			final HttpServletResponse response, final String viewName) throws IOException {
		// 1.判断当前请求是“普通请求”还是“Ajax 请求”
		final boolean ajaxOrNot = PgCrowdUtils.discernRequestType(request);
		// 2.如果是Ajax 请求
		if (ajaxOrNot) {
			// 3.从当前异常对象中获取异常信息
			final String message = exception.getMessage();
			// 4.创建ResultEntity
			final ResultDto<Object> resultEntity = ResultDto.failed(message);
			// 5.创建Gson 对象
			final Gson gson = new Gson();
			// 6.将resultEntity 转化为JSON 字符串
			final String json = gson.toJson(resultEntity);
			// 7.把当前JSON 字符串作为当前请求的响应体数据返回给浏览器
			// ①获取Writer 对象
			final PrintWriter writer = response.getWriter();
			// ②写入数据
			writer.write(json);
			// 8.返回null，不给SpringMVC 提供ModelAndView 对象
			// 这样SpringMVC 就知道不需要框架解析视图来提供响应，而是程序员自己提供了响应
			return null;
		}
		// 9.创建ModelAndView 对象
		final ModelAndView modelAndView = new ModelAndView();
		// 10.设置目标视图名称
		modelAndView.setViewName(viewName);
		// 11.将Exception 对象存入模型
		modelAndView.addObject(PgCrowdConstants.ATTRNAME_EXCEPTION, exception);
		// 12.返回ModelAndView 对象
		return modelAndView;
	}

	// 表示捕获到LoginFailedException 类型的异常对象由当前方法处理
	@ExceptionHandler(value = LoginFailedException.class)
	public ModelAndView resolveLoginFailedException(final LoginFailedException exception,
			final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		// 現在の例外に対応するページを指定する
		final String viewName = "admin-login";
		return this.commonResolveException(exception, request, response, viewName);
	}

	@ExceptionHandler(value = PgCrowdException.class)
	public ModelAndView resolvePgCrowdException(final Exception exception, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		// 現在の例外に対応するページを指定する
		final String viewName = "system-error";
		return this.commonResolveException(exception, request, response, viewName);
	}
}
