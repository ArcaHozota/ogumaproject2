package jp.co.toshiba.ppocph.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;

public class Testclass {

	@Test
	public void test() {
		final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(BCryptVersion.$2A);
		final String encode = passwordEncoder.encode("123456");
		System.out.print(encode);
	}

}
