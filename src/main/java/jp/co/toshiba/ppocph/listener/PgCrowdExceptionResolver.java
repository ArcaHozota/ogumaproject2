package jp.co.toshiba.ppocph.listener;

import java.io.IOException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson2.JSON;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
		// 1.リクエストが「通常のリクエスト」であるか「AJAXリクエスト」であるかを判断する。
		final boolean ajaxOrNot = PgCrowdUtils.discernRequestType(request);
		// 2.AJAXリクエストの場合。
		if (ajaxOrNot) {
			// 3.例外オブジェクトから例外情報を取得する。
			final String message = exception.getMessage();
			// 4.ResultDtoオブジェクトを作成する。
			final ResultDto<Object> resultEntity = ResultDto.failed(message);
			// 5.GSONオブジェクトを作成する。
			// 6.JSONストリングに変換する。
			final String json = JSON.toJSONString(resultEntity);
			// 7.PrintWriterオブジェクトを取得する。
			// 8.JSONデータをライトしてNULLを返却する。
			response.getWriter().write(json);
			return null;
		}
		// 9.ModelAndViewオブジェクトを作成する。
		final ModelAndView modelAndView = new ModelAndView();
		// 10.ターゲットビューの名称を設定する。
		modelAndView.setViewName(viewName);
		// 11.例外オブジェクトをモデルに保存する。
		modelAndView.addObject(PgCrowdConstants.ATTRNAME_EXCEPTION, exception);
		// 12.ModelAndViewオブジェクトを返却する。
		return modelAndView;
	}

	/**
	 * ログイン失敗例外を処理する
	 *
	 * @param exception 例外名
	 * @param request   リクエスト
	 * @param response  リスポンス
	 * @return ModelAndView モデルビューオブジェクト
	 * @throws IOException
	 */
	@ExceptionHandler(value = LoginFailedException.class)
	public ModelAndView resolveLoginFailedException(final LoginFailedException exception,
			final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		// 現在の例外に対応するページを指定する
		final String viewName = "admin-login";
		return this.commonResolveException(exception, request, response, viewName);
	}

	/**
	 * 業務ロジック例外を処理する
	 *
	 * @param exception 例外名
	 * @param request   リクエスト
	 * @param response  リスポンス
	 * @return ModelAndView モデルビューオブジェクト
	 * @throws IOException
	 */
	@ExceptionHandler(value = PgCrowdException.class)
	public ModelAndView resolvePgCrowdException(final Exception exception, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		// 現在の例外に対応するページを指定する
		final String viewName = "system-error";
		return this.commonResolveException(exception, request, response, viewName);
	}
}
