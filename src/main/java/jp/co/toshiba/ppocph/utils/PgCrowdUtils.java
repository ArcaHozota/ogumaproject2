package jp.co.toshiba.ppocph.utils;

import java.io.IOException;

import com.alibaba.fastjson2.JSON;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.toshiba.ppocph.config.ResponseLoginDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * プロジェクト共通ツールクラス
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PgCrowdUtils {

	/**
	 * 現在のリクエストがAJAXリクエストであるかどうかを判断する
	 *
	 * @param request リクエスト
	 * @return true: ajax-request, false: no-ajax
	 */
	public static boolean discernRequestType(final HttpServletRequest request) {
		// リクエストヘッダー情報の取得する
		final String acceptInformation = request.getHeader("Accept");
		final String xRequestInformation = request.getHeader("X-Requested-With");
		// 判断して返却する
		return acceptInformation != null && acceptInformation.length() > 0
				&& acceptInformation.contains("application/json")
				|| xRequestInformation != null && xRequestInformation.length() > 0
						&& "XMLHttpRequest".equals(xRequestInformation);
	}

	/**
	 * 文字列をクライアントにレンダリングする
	 *
	 * @param response リスポンス
	 * @param string   ストリング
	 */
	public static void renderString(final HttpServletResponse response, final ResponseLoginDto aResult) {
		try {
			response.setStatus(aResult.getCode());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(JSON.toJSONString(aResult));
		} catch (final IOException e) {
			// do nothing
		}
	}
}
