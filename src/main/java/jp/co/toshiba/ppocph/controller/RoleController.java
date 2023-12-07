package jp.co.toshiba.ppocph.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	 * 役割名称重複チェック
	 *
	 * @param name 役割名称
	 * @return ResultDto<String>
	 */
	@GetMapping("/checkname")
	@ResponseBody
	public ResultDto<String> checkDuplicated(
			@RequestParam(name = "name", defaultValue = StringUtils.EMPTY_STRING) final String name) {
		final boolean checkDuplicated = this.iRoleService.check(name);
		if (checkDuplicated) {
			return ResultDto.failed(PgCrowdConstants.MESSAGE_ROLE_NAME_DUPLICATED);
		}
		return ResultDto.successWithoutData();
	}

	/**
	 * 役割情報削除
	 *
	 * @param roleId 役割ID
	 * @return ResultDto<String>
	 */
	@DeleteMapping("/remove")
	@ResponseBody
	public ResultDto<String> deleteInfo(@RequestParam("roleId") final Long roleId) {
		this.iRoleService.removeById(roleId);
		return ResultDto.successWithoutData();
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
		try {
			this.iRoleService.update(roleDto);
			return ResultDto.successWithoutData();
		} catch (final DataIntegrityViolationException e) {
			return ResultDto.failed(PgCrowdConstants.MESSAGE_ROLE_NAME_DUPLICATED);
		}
	}
}
