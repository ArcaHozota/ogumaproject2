package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;
import java.util.List;

import gaarason.database.annotation.Column;
import gaarason.database.annotation.HasOneOrMany;
import gaarason.database.annotation.Primary;
import gaarason.database.annotation.Table;
import jp.co.toshiba.ppocph.utils.OgumaPrimaryKeyGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 地域エンティティ
 *
 * @author ArkamaHozota
 * @since 7.80
 */
@Data
@Table(name = "districts")
@EqualsAndHashCode(callSuper = false)
public class District implements Serializable {

	private static final long serialVersionUID = -3956143435944525913L;

	/**
	 * ID
	 */
	@Primary(idGenerator = OgumaPrimaryKeyGenerator.class)
	private Long id;

	/**
	 * 名称
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 地方名称
	 */
	@Column(nullable = false)
	private String chiho;

	/**
	 * 都道府県旗
	 */
	private String districtFlag;

	/**
	 * 州都ID
	 */
	@Column(nullable = false)
	private Long shutoId;

	/**
	 * 論理削除フラグ
	 */
	@Column(nullable = false)
	private String deleteFlg;

	/**
	 * 地域都市関連
	 */
	@HasOneOrMany(sonModelForeignKey = "district_id")
	private List<City> cities;
}
