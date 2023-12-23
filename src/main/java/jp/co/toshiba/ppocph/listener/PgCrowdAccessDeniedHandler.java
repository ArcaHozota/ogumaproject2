package jp.co.toshiba.ppocph.listener;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.toshiba.ppocph.common.PgCrowdConstants;

/**
 * 権限検査コントローラ(SpringSecurity関連)
 *
 * @author ArkamaHozota
 * @since 6.89
 */
@Component
public class PgCrowdAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(final HttpServletRequest request, final HttpServletResponse response,
			final AccessDeniedException accessDeniedException) throws IOException, ServletException {
		final JSONObject json = new JSONObject();
		json.put("403", PgCrowdConstants.MESSAGE_SPRINGSECURITY_REQUIREDAUTH);
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().append(json.toString());
	}
}
