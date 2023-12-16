package jp.co.toshiba.ppocph.controller;

import java.util.List;

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
import jp.co.toshiba.ppocph.dto.EmployeeDto;
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
 * 社員コントローラ
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Controller
@RequestMapping("/pgcrowd/employee")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmployeeController {

	/**
	 * 社員サービスインターフェス
	 */
	private final IEmployeeService iEmployeeService;

	/**
	 * 役割サービスインターフェス
	 */
	private final IRoleService iRoleService;

	/**
	 * ログインアカウントを重複するかどうかを確認する
	 *
	 * @param loginAccount ログインアカウント
	 * @return ResultDto<String>
	 */
	@GetMapping("/check")
	@ResponseBody
	public ResultDto<String> checkDuplicated(@RequestParam("loginAcct") final String loginAccount) {
		return this.iEmployeeService.check(loginAccount);
	}

	/**
	 * IDによって社員情報を削除する
	 *
	 * @param userId 社員ID
	 * @return ResultDto<String>
	 */
	@DeleteMapping("/delete/{userId}")
	@ResponseBody
	public ResultDto<String> deleteInfo(@PathVariable("userId") final Long userId) {
		this.iEmployeeService.removeById(userId);
		return ResultDto.successWithoutData();
	}

//	/**
//	 * ログイン処理
//	 *
//	 * @param account  アカウント
//	 * @param password パスワード
//	 * @return ModelAndView
//	 */
//	@PostMapping("/do/login")
//	public ModelAndView doLogin(@RequestParam("loginAcct") final String account,
//			@RequestParam("userPswd") final String password) {
//		// EmployeeServiceメソッドを呼び出して、ログインチェックを実行します。このメソッドがEmployeeオブジェクトを返すことができれば、ログインは成功です。アカウントとパスワードが間違っている場合は、例外がスローされます。
//		final Employee employee = this.iEmployeeService.getAdminByLoginAccount(account, password);
//		// 成功したログインによって返された管理オブジェクトをセッションドメインに保存します。
//		final ModelAndView modelAndView = new ModelAndView("mainmenu");
//		modelAndView.addObject(PgCrowdConstants.ATTRNAME_LOGIN_ADMIN, employee);
//		return modelAndView;
//	}

//	/**
//	 * ログアウト処理
//	 *
//	 * @param session セッション
//	 * @return String
//	 */
//	@PostMapping("/logout")
//	public String doLogout(final HttpSession session) {
//		// セッションを無効化する
//		session.invalidate();
//		return "redirect:/pgcrowd/employee/login";
//	}

	/**
	 * 社員情報初期表示
	 *
	 * @param userId  ユーザID
	 * @param pageNum ページナンバー
	 * @return ModelAndView
	 */
	@GetMapping("/to/pages")
	public ModelAndView initialPages(@RequestParam(name = "pageNum") final Integer pageNum) {
		final ModelAndView modelAndView = new ModelAndView("admin-pages");
		modelAndView.addObject(PgCrowdConstants.ATTRNAME_PAGE_NUMBER, pageNum);
		return modelAndView;
	}

	/**
	 * キーワードによってページング検索
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return ResultDto<Pagination<Employee>>
	 */
	@GetMapping("/pagination")
	@ResponseBody
	public ResultDto<Pagination<Employee>> pagination(
			@RequestParam(name = "pageNum", defaultValue = "1") final Integer pageNum,
			@RequestParam(name = "keyword", defaultValue = StringUtils.EMPTY_STRING) final String keyword) {
		final Pagination<Employee> employees = this.iEmployeeService.getEmployeesByKeyword(pageNum, keyword);
		return ResultDto.successWithData(employees);
	}

	/**
	 * ログインアカウントによって社員情報を取得する
	 *
	 * @param loginAccount ログインアカウント
	 * @return ResultDto<String>
	 */
	@GetMapping("/inforestore")
	@ResponseBody
	public ResultDto<Employee> restoreInfo(@RequestParam("userId") final Long userId) {
		final Employee employee = this.iEmployeeService.getEmployeeById(userId);
		return ResultDto.successWithData(employee);
	}

	/**
	 * 情報追加
	 *
	 * @param employeeDto 社員情報DTO
	 * @return ResultDto<String>
	 */
	@PostMapping("/infosave")
	@ResponseBody
	public ResultDto<String> saveInfo(@RequestBody final EmployeeDto employeeDto) {
		this.iEmployeeService.save(employeeDto);
		return ResultDto.successWithoutData();
	}

	/**
	 * 情報追加初期表示
	 *
	 * @param userId ユーザID
	 * @return ModelAndView
	 */
	@GetMapping("/to/addition")
	public ModelAndView toAddition() {
		final List<Role> employeeRolesById = this.iRoleService.getEmployeeRolesById(null);
		final ModelAndView modelAndView = new ModelAndView("admin-addinfo");
		modelAndView.addObject(PgCrowdConstants.ATTRNAME_EMPLOYEEROLES, employeeRolesById);
		return modelAndView;
	}

	/**
	 * 情報更新初期表示
	 *
	 * @param id 社員ID
	 * @return ModelAndView
	 */
	@GetMapping("/to/edition")
	public ModelAndView toEdition(@RequestParam("editId") final Long id) {
		final Employee employee = this.iEmployeeService.getEmployeeById(id);
		final List<Role> employeeRolesById = this.iRoleService.getEmployeeRolesById(id);
		final ModelAndView modelAndView = new ModelAndView("admin-editinfo");
		modelAndView.addObject(PgCrowdConstants.ATTRNAME_EDITED_INFO, employee);
		modelAndView.addObject(PgCrowdConstants.ATTRNAME_EMPLOYEEROLES, employeeRolesById);
		return modelAndView;
	}

//	/**
//	 * メインメニューへの移動
//	 *
//	 * @param userId ユーザID
//	 * @return ModelAndView
//	 */
//	@GetMapping("/to/mainmenu")
//	public ModelAndView toMainmenu(@RequestParam("userId") final Long userId) {
//		final Employee employee = this.iEmployeeService.getEmployeeById(userId);
//		final ModelAndView modelAndView = new ModelAndView("mainmenu");
//		modelAndView.addObject(PgCrowdConstants.ATTRNAME_LOGIN_ADMIN, employee);
//		return modelAndView;
//	}

	/**
	 * 情報更新
	 *
	 * @param employeeDto 社員情報DTO
	 * @return ResultDto<String>
	 */
	@PutMapping("/infoupd")
	@ResponseBody
	public ResultDto<String> updateInfo(@RequestBody final EmployeeDto employeeDto) {
		this.iEmployeeService.update(employeeDto);
		return ResultDto.successWithoutData();
	}
}
