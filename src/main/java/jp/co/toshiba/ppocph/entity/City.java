package jp.co.toshiba.ppocph.entity;

import java.io.Serializable;

import gaarason.database.annotation.BelongsTo;
import gaarason.database.annotation.Column;
import gaarason.database.annotation.Primary;
import gaarason.database.annotation.Table;
import jp.co.toshiba.ppocph.utils.OgumaPrimaryKeyGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 都市エンティティ
 *
 * @author ArkamaHozota
 * @since 7.84
 */
@Data
@Table(name = "cities")
@EqualsAndHashCode(callSuper = false)
public class City implements Serializable {

	private static final long serialVersionUID = -4552061040586886910L;

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
	 * 読み方
	 */
	private String pronunciation;

	/**
	 * 人口数量
	 */
	@Column(nullable = false)
	private Long population;

	/**
	 * 市町村旗
	 */
	private String cityFlag;

	/**
	 * 地域ID
	 */
	@Column(nullable = false)
	private Long districtId;

	/**
	 * 論理削除フラグ
	 */
	@Column(nullable = false)
	private String deleteFlg;

	/**
	 * 都市地域関連
	 */
	@BelongsTo(localModelForeignKey = "district_id")
	private District district;
}
