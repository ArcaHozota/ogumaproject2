package jp.co.toshiba.ppocph.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.dto.RoleDto;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.entity.PgAuth;
import jp.co.toshiba.ppocph.entity.Role;
import jp.co.toshiba.ppocph.service.IEmployeeService;
import jp.co.toshiba.ppocph.service.IRoleService;
import jp.co.toshiba.ppocph.utils.Pagination;
import jp.co.toshiba.ppocph.utils.ResultDto;
import jp.co.toshiba.ppocph.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 役割コントローラ
 *
 * @author ArkamaHozota
 * @since 4.44
 */
@Controller
@RequestMapping("/pgcrowd/role")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoleController {

	/**
	 * 社員サービスインターフェス
	 */
	private final IEmployeeService iEmployeeService;

	/**
	 * 役割サービスインターフェス
	 */
	private final IRoleService iRoleService;

	/**
	 * 権限付与画面初期表示
	 *
	 * @return ResultDto<List<PgAuth>>
	 */
	@GetMapping("/authlists")
	@ResponseBody
	public ResultDto<List<PgAuth>> authlists() {
		final List<PgAuth> list = this.iRoleService.getAuthlist();
		return ResultDto.successWithData(list);
	}

	/**
	 * 役割名称重複チェック
	 *
	 * @param name 役割名称
	 * @return ResultDto<String>
	 */
	@GetMapping("/checkname")
	@ResponseBody
	public ResultDto<String> checkDuplicated(
			@RequestParam(name = "name", defaultValue = StringUtils.EMPTY_STRING) final String name) {
		return this.iRoleService.check(name);
	}

	/**
	 * 役割情報削除
	 *
	 * @param roleId 役割ID
	 * @return ResultDto<String>
	 */
	@DeleteMapping("/delete/{roleId}")
	@ResponseBody
	public ResultDto<String> deleteInfo(@PathVariable("roleId") final Long roleId) {
		return this.iRoleService.removeById(roleId);
	}

	/**
	 * 権限付与実行
	 *
	 * @param paramMap パラメータ
	 * @return ResultDto<String>
	 */
	@PutMapping("/do/assignment")
	@ResponseBody
	public ResultDto<String> doAssignment(@RequestBody final Map<String, List<Long>> paramMap) {
		return this.iRoleService.doAssignment(paramMap);
	}

	/**
	 * 付与された権限を表示する
	 *
	 * @return ResultDto<List<Long>>
	 */
	@GetMapping("/getAssigned")
	@ResponseBody
	public ResultDto<List<Long>> getAssignedAuth(@RequestParam("fuyoId") final Long roleId) {
		final List<Long> authIds = this.iRoleService.getAuthIdListByRoleId(roleId);
		return ResultDto.successWithData(authIds);
	}

	/**
	 * 役割情報初期表示
	 *
	 * @param userId  ユーザID
	 * @param pageNum ページナンバー
	 * @return ModelAndView
	 */
	@GetMapping("/to/pages")
	public ModelAndView initialPages(@RequestParam("userId") final Long userId,
			@RequestParam(name = "pageNum") final Integer pageNum) {
		final Employee employee = this.iEmployeeService.getEmployeeById(userId);
		final ModelAndView modelAndView = new ModelAndView("role-pages");
		modelAndView.addObject(PgCrowdConstants.ATTRNAME_LOGIN_ADMIN, employee);
		modelAndView.addObject(PgCrowdConstants.ATTRNAME_PAGE_NUMBER, pageNum);
		return modelAndView;
	}

	/**
	 * キーワードによってページング検索
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return ResultDto<Pagination<Role>>
	 */
	@GetMapping("/pagination")
	@ResponseBody
	public ResultDto<Pagination<Role>> pagination(
			@RequestParam(name = "pageNum", defaultValue = "1") final Integer pageNum,
			@RequestParam(name = "keyword", defaultValue = StringUtils.EMPTY_STRING) final String keyword) {
		final Pagination<Role> roles = this.iRoleService.getRolesByKeyword(pageNum, keyword);
		return ResultDto.successWithData(roles);
	}

	/**
	 * 情報追加
	 *
	 * @param roleDto 役割情報DTO
	 * @return ResultDto<String>
	 */
	@PostMapping("/infosave")
	@ResponseBody
	public ResultDto<String> saveInfo(@RequestBody final RoleDto roleDto) {
		this.iRoleService.save(roleDto);
		return ResultDto.successWithoutData();
	}

	/**
	 * 情報更新
	 *
	 * @param roleDto 役割情報DTO
	 * @return ResultDto<String>
	 */
	@PutMapping("/infoupd")
	@ResponseBody
	public ResultDto<String> updateInfo(@RequestBody final RoleDto roleDto) {
		return this.iRoleService.update(roleDto);
	}
}
