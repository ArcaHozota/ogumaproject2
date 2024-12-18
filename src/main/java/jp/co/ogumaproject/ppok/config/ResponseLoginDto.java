package jp.co.ogumaproject.ppok.config;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * リスポンスエンティティ
 *
 * @author ArkamaHozota
 * @since 1.50
 */
@Getter
@Setter
public final class ResponseLoginDto implements Serializable {

	private static final long serialVersionUID = -7552597224614068953L;

	/**
	 * HTTPステータスコード
	 */
	private final Integer code;

	/**
	 * エラーメッセージ
	 */
	private String message;

	/**
	 * コンストラクタ
	 */
	ResponseLoginDto() {
		this.code = HttpStatus.OK.value();
	}

	/**
	 * パラメータつきコンストラクタ
	 *
	 * @param code    コード
	 * @param message メッセージ
	 */
	ResponseLoginDto(final Integer code, final String message) {
		this.code = code;
		this.message = message;
	}
}
