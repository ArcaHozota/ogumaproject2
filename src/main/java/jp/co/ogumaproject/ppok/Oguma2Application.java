package jp.co.ogumaproject.ppok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import jp.co.ogumaproject.ppok.common.OgumaProjectConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * Ogumaアプリケーション
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
public class Oguma2Application {
	public static void main(final String[] args) {
		SpringApplication.run(Oguma2Application.class, args);
		log.info(OgumaProjectConstants.MESSAGE_SPRING_APPLICATION);
	}
}
