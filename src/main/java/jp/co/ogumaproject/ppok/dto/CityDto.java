package jp.co.ogumaproject.ppok.dto;

/**
 * 都市情報転送クラス
 *
 * @author ArkamaHozota
 * @since 7.89
 */
public record CityDto(

		/**
		 * ID
		 */
		Long id,

		/**
		 * 名称
		 */
		String name,

		/**
		 * 都道府県ID
		 */
		Long districtId,

		/**
		 * 読み方
		 */
		String pronunciation,

		/**
		 * 都道府県名称
		 */
		String districtName,

		/**
		 * 人口数量
		 */
		Long population,

		/**
		 * 市町村旗
		 */
		String cityFlag) {
}
