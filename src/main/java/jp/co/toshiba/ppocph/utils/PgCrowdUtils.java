package jp.co.toshiba.ppocph.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import jp.co.toshiba.ppocph.common.PgcrowdConstants;
import jp.co.toshiba.ppocph.exception.PgCrowdException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * プロジェクト共通ツールクラス
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PgCrowdUtils {

	/**
	 * 現在のリクエストがAJAXリクエストであるかどうかを判断する
	 *
	 * @param request リクエスト
	 * @return true: ajax-request, false: no-ajax
	 */
	public static boolean discernRequestType(final HttpServletRequest request) {
		// リクエストヘッダー情報の取得する
		final String acceptInformation = request.getHeader("Accept");
		final String xRequestInformation = request.getHeader("X-Requested-With");
		// 判断して返却する
		return ((acceptInformation != null) && (acceptInformation.length() > 0)
				&& acceptInformation.contains("application/json"))
				|| ((xRequestInformation != null) && (xRequestInformation.length() > 0)
						&& "XMLHttpRequest".equals(xRequestInformation));
	}

	/**
	 * 平文文字列をMD5暗号化する
	 *
	 * @param source 文字列
	 * @return String
	 */
	public static String plainToMD5(final String source) {
		// 1.判断source 是否有效
		if ((source == null) || (source.length() == 0)) {
			// 2.如果不是有效的字符串抛出异常
			throw new PgCrowdException(PgcrowdConstants.MESSAGE_STRING_INVALIDATE);
		}
		try {
			// 3.获取MessageDigest 对象
			final MessageDigest messageDigest = MessageDigest.getInstance("md5");
			// 4.获取明文字符串对应的字节数组
			final byte[] input = source.getBytes();
			// 5.执行加密
			final byte[] output = messageDigest.digest(input);
			// 6.创建BigInteger 对象
			final int signum = 1;
			final BigInteger bigInteger = new BigInteger(signum, output);
			// 7.按照16 进制将bigInteger 的值转换为字符串
			final int radix = 16;
			return bigInteger.toString(radix).toUpperCase();
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return StringUtils.EMPTY_STRING;
	}
}
