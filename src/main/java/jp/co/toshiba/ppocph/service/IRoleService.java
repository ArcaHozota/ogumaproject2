package jp.co.toshiba.ppocph.service;

import org.springframework.dao.DataIntegrityViolationException;

import jp.co.toshiba.ppocph.dto.RoleDto;
import jp.co.toshiba.ppocph.entity.Role;
import jp.co.toshiba.ppocph.utils.Pagination;

/**
 * 役割サービスインターフェス
 *
 * @author ArkamaHozota
 * @since 4.46
 */
public interface IRoleService {

	/**
	 * 役割名称が重複するかどうかをチェックする
	 *
	 * @param name 役割名称
	 * @return true:重複する; false: 重複しない;
	 */
	boolean check(String name);

	/**
	 * キーワードによって役割情報を取得する
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return Pagination<Role>
	 */
	Pagination<Role> getRolesByKeyword(Integer pageNum, String keyword);

	/**
	 * 役割IDによって情報を削除する
	 *
	 * @param roleId 役割ID
	 */
	void removeById(Long roleId);

	/**
	 * 役割情報追加
	 *
	 * @param roleDto 役割情報転送クラス
	 */
	void save(RoleDto roleDto);

	/**
	 * 役割情報更新
	 *
	 * @param roleDto 役割情報転送クラス
	 */
	void update(RoleDto roleDto) throws DataIntegrityViolationException;
}
