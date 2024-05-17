package jp.co.ogumaproject.ppog.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * パスワードエンコーダ
 *
 * @author ArkamaHozota
 * @since 6.94
 */
public final class OgumaPasswordEncoder implements PasswordEncoder {

	/**
	 * エンコーダ
	 */
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(BCryptVersion.$2A, 7);

	@Override
	public String encode(final CharSequence rawPassword) {
		return this.passwordEncoder.encode(rawPassword);
	}

	@Override
	public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
		return this.passwordEncoder.matches(rawPassword, encodedPassword);
	}

}
