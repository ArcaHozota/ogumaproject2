package jp.co.toshiba.ppocph.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import jakarta.annotation.Resource;
import jp.co.toshiba.ppocph.common.OgumaProjectConstants;
import jp.co.toshiba.ppocph.common.OgumaProjectURLConstants;
import jp.co.toshiba.ppocph.listener.OgumaProjectUserDetailsService;
import jp.co.toshiba.ppocph.utils.OgumaProjectUtils;
import lombok.extern.log4j.Log4j2;

/**
 * SpringSecurity配置クラス
 *
 * @author ArkamaHozota
 * @since 5.99
 */
@Log4j2
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	/**
	 * ログインサービス
	 */
	@Resource
	private OgumaProjectUserDetailsService pgCrowdUserDetailsService;

	@Bean
	protected AuthenticationManager authenticationManager(final AuthenticationManagerBuilder auth) {
		return auth.authenticationProvider(this.daoAuthenticationProvider()).getObject();
	}

	@Bean
	protected DaoAuthenticationProvider daoAuthenticationProvider() {
		final OgumaDaoAuthenticationProvider daoAuthenticationProvider = new OgumaDaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.pgCrowdUserDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(new OgumaPasswordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	protected SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(OgumaProjectURLConstants.URL_STATIC_RESOURCE, OgumaProjectURLConstants.URL_INDEX,
						OgumaProjectURLConstants.URL_TO_SIGN_UP, OgumaProjectURLConstants.URL_DO_SIGN_UP,
						OgumaProjectURLConstants.URL_FORGET_PASSWORD, OgumaProjectURLConstants.URL_RESET_PASSWORD)
				.permitAll()
				.requestMatchers(OgumaProjectURLConstants.URL_EMPLOYEE_TO_PAGES,
						OgumaProjectURLConstants.URL_EMPLOYEE_PAGINATION,
						OgumaProjectURLConstants.URL_EMPLOYEE_TO_EDITION, OgumaProjectURLConstants.URL_EMPLOYEE_UPDATE)
				.hasAuthority("employee%retrieve")
				.requestMatchers(OgumaProjectURLConstants.URL_EMPLOYEE_TO_ADDITION,
						OgumaProjectURLConstants.URL_EMPLOYEE_INSERT)
				.hasAuthority("employee%edition").requestMatchers(OgumaProjectURLConstants.URL_EMPLOYEE_DELETE)
				.hasAuthority("employee%delete")
				.requestMatchers(OgumaProjectURLConstants.URL_ROLE_TO_PAGES,
						OgumaProjectURLConstants.URL_ROLE_PAGINATION, OgumaProjectURLConstants.URL_ROLE_GET_ASSIGNED)
				.hasAuthority("role%retrieve")
				.requestMatchers(OgumaProjectURLConstants.URL_ROLE_INSERT, OgumaProjectURLConstants.URL_ROLE_UPDATE,
						OgumaProjectURLConstants.URL_ROLE_AUTHLIST, OgumaProjectURLConstants.URL_ROLE_CHECK_EDITION)
				.hasAuthority("role%edition")
				.requestMatchers(OgumaProjectURLConstants.URL_ROLE_ASSIGNMENT, OgumaProjectURLConstants.URL_ROLE_DELETE)
				.hasAuthority("role%delete").requestMatchers(OgumaProjectURLConstants.URL_DISTRICT_PAGINATION)
				.hasAuthority("district%retrieve").requestMatchers(OgumaProjectURLConstants.URL_DISTRICT_UPDATE)
				.hasAuthority("district%edition").anyRequest().authenticated())
				.csrf(csrf -> csrf.ignoringRequestMatchers(OgumaProjectURLConstants.URL_STATIC_RESOURCE)
						.csrfTokenRepository(new CookieCsrfTokenRepository()))
				.exceptionHandling(handling -> {
					handling.authenticationEntryPoint((request, response, authenticationException) -> {
						final ResponseLoginDto responseResult = new ResponseLoginDto(HttpStatus.UNAUTHORIZED.value(),
								authenticationException.getMessage());
						OgumaProjectUtils.renderString(response, responseResult);
						log.error(responseResult.getMessage());
					});
					handling.accessDeniedHandler((request, response, accessDeniedException) -> {
						final ResponseLoginDto responseResult = new ResponseLoginDto(HttpStatus.FORBIDDEN.value(),
								OgumaProjectConstants.MESSAGE_SPRINGSECURITY_REQUIRED_AUTH);
						OgumaProjectUtils.renderString(response, responseResult);
						log.error(responseResult.getMessage());
					});
				})
				.formLogin(formLogin -> formLogin.loginPage(OgumaProjectURLConstants.URL_TO_LOGIN)
						.loginProcessingUrl(OgumaProjectURLConstants.URL_DO_LOGIN)
						.defaultSuccessUrl(OgumaProjectURLConstants.URL_TO_MAINMENU).permitAll()
						.usernameParameter("loginAcct").passwordParameter("userPswd"))
				.logout(logout -> logout.logoutUrl(OgumaProjectURLConstants.URL_LOG_OUT)
						.logoutSuccessUrl(OgumaProjectURLConstants.URL_INDEX))
				.rememberMe(remember -> remember.key(UUID.randomUUID().toString())
						.tokenValiditySeconds(OgumaProjectConstants.DEFAULT_TOKEN_EXPIRED));
		log.info(OgumaProjectConstants.MESSAGE_SPRING_SECURITY);
		return httpSecurity.build();
	}
}
