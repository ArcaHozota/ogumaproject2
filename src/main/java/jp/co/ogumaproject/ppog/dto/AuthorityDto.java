package jp.co.ogumaproject.ppog.dto;

/**
 * 権限情報転送クラス
 *
 * @author ArkamaHozota
 * @since 9.25
 */
public record AuthorityDto(

		/**
		 * ID
		 */
		Long id,

		/**
		 * 名称
		 */
		String name,

		/**
		 * 漢字名称
		 */
		String title,

		/**
		 * 分類ID
		 */
		Long categoryId) {
}
