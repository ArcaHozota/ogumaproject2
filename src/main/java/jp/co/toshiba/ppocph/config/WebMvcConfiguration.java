package jp.co.toshiba.ppocph.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import jp.co.toshiba.ppocph.common.OgumaProjectConstants;
import jp.co.toshiba.ppocph.common.OgumaProjectURLConstants;
import lombok.extern.log4j.Log4j2;

/**
 * SpringMVC配置クラス
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Log4j2
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

	/**
	 * 静的なリソースのマッピングを設定する
	 *
	 * @param registry レジストリ
	 */
	@Override
	protected void addResourceHandlers(final ResourceHandlerRegistry registry) {
		log.info(OgumaProjectConstants.MESSAGE_SPRING_MAPPER);
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

	/**
	 * ビューのコントローラを定義する
	 *
	 * @param registry
	 */
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		registry.addViewController(OgumaProjectURLConstants.URL_TO_SIGN_UP).setViewName("admin-toroku");
		registry.addViewController(OgumaProjectURLConstants.URL_TO_LOGIN).setViewName("admin-login");
		registry.addViewController(OgumaProjectURLConstants.URL_FORGET_PASSWORD).setViewName("admin-forgot");
		registry.addViewController(OgumaProjectURLConstants.URL_TO_MAINMENU).setViewName("mainmenu");
		registry.addViewController(OgumaProjectURLConstants.URL_MENU_INITIAL).setViewName("menukanri");
		registry.addViewController(OgumaProjectURLConstants.URL_ROLE_TO_PAGES).setViewName("role-pages");
		registry.addViewController(OgumaProjectURLConstants.URL_CATEGORY_INITIAL).setViewName("categorykanri");
		registry.addViewController(OgumaProjectURLConstants.URL_TO_DISTRICT_PAGES).setViewName("district-pages");
		registry.addViewController(OgumaProjectURLConstants.URL_TO_CITY_PAGES).setViewName("city-pages");
	}

	/**
	 * SpringMVCフレームワークを拡張するメッセージ・コンバーター
	 *
	 * @param converters コンバーター
	 */
	@Override
	protected void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
		log.info(OgumaProjectConstants.MESSAGE_SPRING_MVCCONVERTOR);
		// メッセージコンバータオブジェクトを作成する。
		final MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
		// オブジェクトコンバータを設定し、Jacksonを使用してJavaオブジェクトをJSONに変換する。
		messageConverter.setObjectMapper(new JacksonObjectMapper());
		// 上記のメッセージコンバータをSpringMVCフレームワークのコンバータコンテナに追加する。
		converters.add(0, messageConverter);
	}
}
