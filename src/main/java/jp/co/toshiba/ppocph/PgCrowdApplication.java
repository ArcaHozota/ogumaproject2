package jp.co.toshiba.ppocph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import jp.co.toshiba.ppocph.common.PgcrowdConstants;
import lombok.extern.log4j.Log4j2;

/**
 * PgCrowdアプリケーション
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Log4j2
@SpringBootApplication
@ServletComponentScan
public class PgCrowdApplication {
	public static void main(final String[] args) {
		SpringApplication.run(PgCrowdApplication.class, args);
		log.info(PgcrowdConstants.MSG003);
	}
}
