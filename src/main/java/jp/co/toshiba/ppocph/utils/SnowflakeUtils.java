package jp.co.toshiba.ppocph.utils;

import java.util.Random;

/**
 * 雪花のアルゴリズムID生成ツール
 *
 * @author ArkamaHozota
 * @since 4.36
 */
public final class SnowflakeUtils {

	private static final Random RANDOM = new Random();

	/**
	 * 次の雪花アルゴリズムIDを取得
	 *
	 * @return long ID
	 */
	public static long nextId() {
		final int nextInt1 = RANDOM.nextInt(31);
		final int nextInt2 = RANDOM.nextInt(31);
		return new SnowflakeIdGenerator(nextInt1, nextInt2).nextId();
	}

	/**
	 * コンストラクタ
	 */
	private SnowflakeUtils() {
		throw new IllegalStateException("Utility class");
	}
}
