package jp.co.toshiba.ppocph.exception;

import jp.co.toshiba.ppocph.utils.CommonProjectUtils;

/**
 * プロジェクト業務ロジック例外
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
public class OgumaProjectException extends RuntimeException {

	private static final long serialVersionUID = 8469408957890840211L;

	/**
	 * メッセージ
	 */
	private final String message;

	public OgumaProjectException() {
		super();
		this.message = CommonProjectUtils.EMPTY_STRING;
	}

	public OgumaProjectException(final String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
