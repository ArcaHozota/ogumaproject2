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
import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.common.PgCrowdURLConstants;
import jp.co.toshiba.ppocph.listener.PgCrowdUserDetailsService;
import jp.co.toshiba.ppocph.utils.PgCrowdUtils;
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
	private PgCrowdUserDetailsService pgCrowdUserDetailsService;

	@Bean
	protected AuthenticationManager authenticationManager(final AuthenticationManagerBuilder auth) {
		return auth.authenticationProvider(this.daoAuthenticationProvider()).getObject();
	}

	@Bean
	protected DaoAuthenticationProvider daoAuthenticationProvider() {
		final PgCrowdDaoAuthenticationProvider daoAuthenticationProvider = new PgCrowdDaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.pgCrowdUserDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(new PgCrowdPasswordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	protected SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(PgCrowdURLConstants.URL_STATIC_RESOURCE, PgCrowdURLConstants.URL_TO_SIGN_UP,
								PgCrowdURLConstants.URL_DO_SIGN_UP, PgCrowdURLConstants.URL_FORGET_PASSWORD)
						.permitAll()
						.requestMatchers(PgCrowdURLConstants.URL_EMPLOYEE_TO_PAGES,
								PgCrowdURLConstants.URL_EMPLOYEE_PAGINATION,
								PgCrowdURLConstants.URL_EMPLOYEE_TO_EDITION, PgCrowdURLConstants.URL_EMPLOYEE_UPDATE)
						.hasAuthority("employee%retrieve")
						.requestMatchers(PgCrowdURLConstants.URL_EMPLOYEE_TO_ADDITION,
								PgCrowdURLConstants.URL_EMPLOYEE_INSERT)
						.hasAuthority("employee%edition").requestMatchers(PgCrowdURLConstants.URL_EMPLOYEE_DELETE)
						.hasAuthority("employee%delete")
						.requestMatchers(PgCrowdURLConstants.URL_ROLE_TO_PAGES, PgCrowdURLConstants.URL_ROLE_PAGINATION,
								PgCrowdURLConstants.URL_ROLE_GET_ASSIGNED)
						.hasAuthority("role%retrieve")
						.requestMatchers(PgCrowdURLConstants.URL_ROLE_INSERT, PgCrowdURLConstants.URL_ROLE_UPDATE,
								PgCrowdURLConstants.URL_ROLE_AUTHLIST, PgCrowdURLConstants.URL_ROLE_CHECK_EDITION)
						.hasAuthority("role%edition")
						.requestMatchers(PgCrowdURLConstants.URL_ROLE_ASSIGNMENT, PgCrowdURLConstants.URL_ROLE_DELETE)
						.hasAuthority("role%delete").requestMatchers(PgCrowdURLConstants.URL_DISTRICT_PAGINATION)
						.hasAuthority("district%retrieve").requestMatchers(PgCrowdURLConstants.URL_DISTRICT_UPDATE)
						.hasAuthority("district%edition").anyRequest().authenticated())
				.csrf(csrf -> csrf.ignoringRequestMatchers(PgCrowdURLConstants.URL_STATIC_RESOURCE)
						.csrfTokenRepository(new CookieCsrfTokenRepository()))
				.exceptionHandling(handling -> {
					handling.authenticationEntryPoint((request, response, authenticationException) -> {
						final ResponseLoginDto responseResult = new ResponseLoginDto(HttpStatus.UNAUTHORIZED.value(),
								authenticationException.getMessage());
						PgCrowdUtils.renderString(response, responseResult);
					});
					handling.accessDeniedHandler((request, response, accessDeniedException) -> {
						final ResponseLoginDto responseResult = new ResponseLoginDto(HttpStatus.FORBIDDEN.value(),
								PgCrowdConstants.MESSAGE_SPRINGSECURITY_REQUIRED_AUTH);
						PgCrowdUtils.renderString(response, responseResult);
					});
				})
				.formLogin(formLogin -> formLogin.loginPage(PgCrowdURLConstants.URL_TO_LOGIN)
						.loginProcessingUrl(PgCrowdURLConstants.URL_DO_LOGIN)
						.defaultSuccessUrl(PgCrowdURLConstants.URL_TO_MAINMENU).permitAll()
						.usernameParameter("loginAcct").passwordParameter("userPswd"))
				.logout(logout -> logout.logoutUrl(PgCrowdURLConstants.URL_LOG_OUT)
						.logoutSuccessUrl(PgCrowdURLConstants.URL_TO_LOGIN))
				.rememberMe(remember -> remember.key(UUID.randomUUID().toString())
						.tokenValiditySeconds(PgCrowdConstants.DEFAULT_TOKEN_EXPIRED));
		log.info(PgCrowdConstants.MESSAGE_SPRING_SECURITY);
		return httpSecurity.build();
	}
}
