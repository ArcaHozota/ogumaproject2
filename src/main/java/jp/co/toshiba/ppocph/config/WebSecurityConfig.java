package jp.co.toshiba.ppocph.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.exception.PgCrowdException;
import jp.co.toshiba.ppocph.listener.PgCrowdUserDetailsService;

/**
 * SpringSecurity配置クラス
 *
 * @author ArkamaHozota
 * @since 5.99
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

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
		final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.pgCrowdUserDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder(BCryptVersion.$2A, 7));
		return provider;
	}

	@Bean
	protected SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		final AntPathRequestMatcher[] pathMatchers = { new AntPathRequestMatcher("/static/**", "GET") };
		http.authorizeHttpRequests(authorize -> {
			authorize.requestMatchers(pathMatchers).permitAll().anyRequest().authenticated();
			try {
				authorize.and().csrf(CsrfConfigurer::disable);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}).formLogin(formLogin -> {
			formLogin.loginPage("/pgcrowd/employee/login").loginProcessingUrl("/pgcrowd/employee/do/login")
					.defaultSuccessUrl("/pgcrowd/to/mainmenu").permitAll().usernameParameter("loginAcct")
					.passwordParameter("userPswd");
			try {
				formLogin.and().logout(logout -> logout.logoutUrl("/pgcrowd/employee/logout")
						.logoutSuccessUrl("/pgcrowd/employee/login"));
			} catch (final Exception e) {
				throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_FATALERROR);
			}
		}).httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
