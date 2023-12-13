package jp.co.toshiba.ppocph.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	@Bean
	protected AuthenticationManager authenticationManager(final AuthenticationManagerBuilder auth) throws Exception {
		return auth.authenticationProvider(this.daoAuthenticationProvider()).build();
	}

	@Bean
	protected DaoAuthenticationProvider daoAuthenticationProvider() {
		final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(new PgCrowdUserDetailsService());
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		return provider;
	}

	@Bean
	protected SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		final AntPathRequestMatcher[] pathMatchers = { new AntPathRequestMatcher("/static/**", "GET") };
		http.authorizeHttpRequests(authorize -> {
			authorize.requestMatchers(pathMatchers).permitAll().anyRequest().authenticated();
			try {
				authorize.and().csrf().disable();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}).formLogin(formLogin -> {
			formLogin.loginPage("/pgcrowd/employee/login").loginProcessingUrl("/pgcrowd/employee/do/login").permitAll()
					.usernameParameter("loginAcct").passwordParameter("userPswd");
			try {
				formLogin.and().logout().logoutUrl("/logout").logoutSuccessUrl("/pgcrowd/employee/login");
			} catch (final Exception e) {
				throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_FATALERROR);
			}
		}).rememberMe(Customizer.withDefaults());
		return http.build();
	}
}
