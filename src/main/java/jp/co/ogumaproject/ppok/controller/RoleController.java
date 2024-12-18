package jp.co.ogumaproject.ppok.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ogumaproject.ppok.common.OgumaProjectConstants;
import jp.co.ogumaproject.ppok.common.OgumaProjectURLConstants;
import jp.co.ogumaproject.ppok.dto.AuthorityDto;
import jp.co.ogumaproject.ppok.dto.RoleDto;
import jp.co.ogumaproject.ppok.service.IRoleService;
import jp.co.ogumaproject.ppok.utils.OgumaProjectUtils;
import jp.co.ogumaproject.ppok.utils.Pagination;
import jp.co.ogumaproject.ppok.utils.ResultDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 役割コントローラ
 *
 * @author ArkamaHozota
 * @since 4.44
 */
@RestController
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoleController {

	/**
	 * 役割サービスインターフェス
	 */
	private final IRoleService iRoleService;

	/**
	 * 削除権限チェック
	 *
	 * @return ResultDto<String>
	 */
	@GetMapping(OgumaProjectURLConstants.URL_ROLE_CHECK_DELETE)
	public ResultDto<String> checkDelete() {
		return ResultDto.successWithoutData();
	}

	/**
	 * 役割名称重複チェック
	 *
	 * @param name 役割名称
	 * @return ResultDto<String>
	 */
	@GetMapping(OgumaProjectURLConstants.URL_ROLE_CHECK)
	public ResultDto<String> checkDuplicated(
			@RequestParam(name = "name", defaultValue = OgumaProjectUtils.EMPTY_STRING) final String name) {
		return this.iRoleService.checkDuplicated(name);
	}

	/**
	 * 編集権限チェック
	 *
	 * @return ResultDto<String>
	 */
	@GetMapping(OgumaProjectURLConstants.URL_ROLE_CHECK_EDITION)
	public ResultDto<String> checkEdition() {
		return ResultDto.successWithoutData();
	}

	/**
	 * 役割情報削除
	 *
	 * @param roleId 役割ID
	 * @return ResultDto<String>
	 */
	@DeleteMapping(OgumaProjectURLConstants.URL_ROLE_DELETE)
	public ResultDto<String> deleteInfo(@PathVariable("roleId") final Long roleId) {
		return this.iRoleService.remove(roleId);
	}

	/**
	 * 権限付与実行
	 *
	 * @param paramMap パラメータ
	 * @return ResultDto<String>
	 */
	@PutMapping(OgumaProjectURLConstants.URL_ROLE_ASSIGNMENT)
	public ResultDto<String> doAssignment(@RequestBody final Map<String, List<Long>> paramMap) {
		return this.iRoleService.doAssignment(paramMap);
	}

	/**
	 * 付与された権限を表示する
	 *
	 * @return ResultDto<List<Long>>
	 */
	@GetMapping(OgumaProjectURLConstants.URL_ROLE_GET_ASSIGNED)
	public ResultDto<List<Long>> getAssignedAuth(@RequestParam("fuyoId") final Long roleId) {
		final List<Long> authIds = this.iRoleService.getAuthIdsById(roleId);
		return ResultDto.successWithData(authIds);
	}

	/**
	 * 権限付与モダルを初期表示する
	 *
	 * @return ResultDto<List<PgAuth>>
	 */
	@GetMapping(OgumaProjectURLConstants.URL_ROLE_AUTHLIST)
	public ResultDto<List<AuthorityDto>> getAuthList() {
		final List<AuthorityDto> list = this.iRoleService.getAuthList();
		return ResultDto.successWithData(list);
	}

	/**
	 * キーワードによってページング検索
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return ResultDto<Pagination<Role>>
	 */
	@GetMapping(OgumaProjectURLConstants.URL_ROLE_PAGINATION)
	public ResultDto<Pagination<RoleDto>> pagination(
			@RequestParam(name = "pageNum", defaultValue = "1") final Integer pageNum,
			@RequestParam(name = "keyword", defaultValue = OgumaProjectUtils.EMPTY_STRING) final String keyword) {
		final Pagination<RoleDto> roles = this.iRoleService.getRolesByKeyword(pageNum, keyword);
		return ResultDto.successWithData(roles);
	}

	/**
	 * 情報追加
	 *
	 * @param roleDto 役割情報DTO
	 * @return ResultDto<String>
	 */
	@PostMapping(OgumaProjectURLConstants.URL_ROLE_INSERT)
	public ResultDto<String> saveInfo(@RequestBody final RoleDto roleDto) {
		this.iRoleService.save(roleDto);
		return ResultDto.successWithoutData(OgumaProjectConstants.MESSAGE_STRING_INSERTED);
	}

	/**
	 * 情報更新
	 *
	 * @param roleDto 役割情報DTO
	 * @return ResultDto<String>
	 */
	@PutMapping(OgumaProjectURLConstants.URL_ROLE_UPDATE)
	public ResultDto<String> updateInfo(@RequestBody final RoleDto roleDto) {
		return this.iRoleService.update(roleDto);
	}
}
