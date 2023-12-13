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
		final AntPathRequestMatcher[] pathMatchers = { new AntPathRequestMatcher("/pgcrowd/employee/login"),
				new AntPathRequestMatcher("/bootstrap/**", "GET"), new AntPathRequestMatcher("/css/**", "GET"),
				new AntPathRequestMatcher("/customizes/**", "GET"), new AntPathRequestMatcher("/fonts/**", "GET"),
				new AntPathRequestMatcher("/jquery/**", "GET"), new AntPathRequestMatcher("/layer/**", "GET"),
				new AntPathRequestMatcher("/script/**", "GET"), new AntPathRequestMatcher("/treeview/**", "GET"),
				new AntPathRequestMatcher("/ztree/**", "GET") };
		http.authorizeHttpRequests(
				authorize -> authorize.requestMatchers(pathMatchers).permitAll().anyRequest().authenticated())
				.formLogin(formLogin -> formLogin.loginPage("/pgcrowd/employee/do/login").permitAll())
				.rememberMe(Customizer.withDefaults());
		return http.build();
	}

}
