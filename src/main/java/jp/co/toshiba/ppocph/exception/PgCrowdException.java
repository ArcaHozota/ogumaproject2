package jp.co.toshiba.ppocph.exception;

/**
 * プロジェクト業務ロジック例外
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
public class PgCrowdException extends RuntimeException {

	private static final long serialVersionUID = 8469408957890840211L;

	public PgCrowdException() {
		super();
	}

	public PgCrowdException(final String message) {
		super(message);
	}
}
