package jp.co.toshiba.ppocph.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
	protected SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		final AntPathRequestMatcher[] pathMatchers = { new AntPathRequestMatcher("/static/**", "GET"),
				new AntPathRequestMatcher("/pgcrowd/employee/do/login", "POST") };
		http.authorizeHttpRequests(
				authorize -> authorize.requestMatchers(pathMatchers).permitAll().anyRequest().authenticated())
				.formLogin(formLogin -> formLogin.loginPage("/pgcrowd/employee/login").permitAll())
				.rememberMe(Customizer.withDefaults());
		return http.build();
	}

}