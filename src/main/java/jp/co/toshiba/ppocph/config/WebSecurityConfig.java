package jp.co.toshiba.ppocph.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import jakarta.annotation.Resource;
import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.listener.PgCrowdAccessDeniedHandler;
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
@EnableMethodSecurity
public class WebSecurityConfig {

	/**
	 * ログインサービス
	 */
	@Resource
	private PgCrowdUserDetailsService pgCrowdUserDetailsService;

	/**
	 * 権限検査サービス
	 */
	@Resource
	private PgCrowdAccessDeniedHandler pgCrowdAccessDeniedHandler;

	@Bean
	protected AuthenticationManager authenticationManager(final AuthenticationManagerBuilder auth) {
		return auth.authenticationProvider(this.daoAuthenticationProvider()).getObject();
	}

	@Bean
	protected DaoAuthenticationProvider daoAuthenticationProvider() {
		final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.pgCrowdUserDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder(BCryptVersion.$2A, 7));
		return provider;
	}

	@Bean
	protected SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeHttpRequests(
						authorize -> authorize.requestMatchers("/static/**").permitAll().anyRequest().authenticated())
				.csrf(csrf -> csrf.csrfTokenRepository(new CookieCsrfTokenRepository())).exceptionHandling(handling -> {
					handling.authenticationEntryPoint((request, response, authenticationException) -> {
						final ResponseLoginDto responseResult = new ResponseLoginDto(HttpStatus.UNAUTHORIZED.value(),
								authenticationException.getMessage());
						PgCrowdUtils.renderString(response, responseResult);
					});
					handling.accessDeniedHandler(this.pgCrowdAccessDeniedHandler);
				})
				.formLogin(formLogin -> formLogin.loginPage("/pgcrowd/employee/login")
						.loginProcessingUrl("/pgcrowd/employee/do/login").defaultSuccessUrl("/pgcrowd/to/mainmenu")
						.permitAll().usernameParameter("loginAcct").passwordParameter("userPswd"))
				.logout(logout -> logout.logoutUrl("/pgcrowd/employee/logout")
						.logoutSuccessUrl("/pgcrowd/employee/login"))
				.httpBasic(Customizer.withDefaults());
		log.info(PgCrowdConstants.MESSAGE_SPRING_SECURITY);
		return httpSecurity.build();
	}
}
