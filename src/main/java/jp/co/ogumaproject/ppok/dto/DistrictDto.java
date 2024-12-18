package jp.co.ogumaproject.ppok.dto;

/**
 * 地域情報転送クラス
 *
 * @author ArkamaHozota
 * @since 7.85
 */
public record DistrictDto(

		/**
		 * ID
		 */
		Long id,

		/**
		 * 名称
		 */
		String name,

		/**
		 * 州都ID
		 */
		Long shutoId,

		/**
		 * 州都名称
		 */
		String shutoName,

		/**
		 * 地方ID
		 */
		Long chihoId,

		/**
		 * 地方名称
		 */
		String chihoName,

		/**
		 * 人口数量
		 */
		Long population,

		/**
		 * 都道府県旗
		 */
		String districtFlag) {
}
