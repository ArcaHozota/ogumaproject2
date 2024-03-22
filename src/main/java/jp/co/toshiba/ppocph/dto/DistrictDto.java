package jp.co.toshiba.ppocph.dto;

/**
 * 地域情報転送クラス
 *
 * @author ArkamaHozota
 * @since 7.85
 */
public record DistrictDto(Long id, String name, Long shutoId, String shutoName, String chiho, Long population,
		String districtFlag) {
}
