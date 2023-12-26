package jp.co.toshiba.ppocph.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.annotation.Resource;
import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.listener.PgCrowdUserDetailsService;

/**
 * ログイン認証サービス
 *
 * @author ArkamaHozota
 * @since 6.96
 */
public final class PgCrowdDaoAuthenticationProvider extends DaoAuthenticationProvider {

	/**
	 * ログインサービス
	 */
	@Resource
	private PgCrowdUserDetailsService pgCrowdUserDetailsService;

	/**
	 * コンストラクタ
	 */
	protected PgCrowdDaoAuthenticationProvider() {
		this.setUserDetailsService(this.pgCrowdUserDetailsService);
		this.setPasswordEncoder(new PgCrowdPasswordEncoder());
	}

	@Override
	protected void additionalAuthenticationChecks(final UserDetails userDetails,
			final UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			this.logger.debug("Failed to authenticate since no credentials provided");
			throw new BadCredentialsException(
					this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
							PgCrowdConstants.MESSAGE_SPRINGSECURITY_REQUIREDAUTH));
		}
		final String presentedPassword = authentication.getCredentials().toString();
		if (!this.getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
			this.logger.debug("Failed to authenticate since password does not match stored value");
			throw new BadCredentialsException(
					this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
							PgCrowdConstants.MESSAGE_SPRINGSECURITY_REQUIREDAUTH));
		}
	}
}
