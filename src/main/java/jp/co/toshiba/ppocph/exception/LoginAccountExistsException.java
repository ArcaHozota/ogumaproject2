package jp.co.toshiba.ppocph.exception;

/**
 * アカウント既に存在したことの例外
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
public class LoginAccountExistsException extends RuntimeException {

	private static final long serialVersionUID = 2601059598321686014L;

	public LoginAccountExistsException() {
		super();
	}

	public LoginAccountExistsException(final String message) {
		super(message);
	}
}