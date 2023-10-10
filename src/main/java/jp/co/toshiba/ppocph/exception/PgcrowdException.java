package jp.co.toshiba.ppocph.exception;

/**
 * プロジェクト業務ロジック例外
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
public class PgcrowdException extends RuntimeException {

	private static final long serialVersionUID = 8469408957890840211L;

	public PgcrowdException() {
		super();
	}

	public PgcrowdException(final String message) {
		super(message);
	}
}
