package jp.co.toshiba.ppocph.controller;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.service.IEmployeeService;
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
	 * ログイン処理
	 *
	 * @param account  アカウント
	 * @param password パスワード
	 * @return ModelAndView
	 */
	@PostMapping("/do/login")
	public ModelAndView doLogin(@RequestParam("loginAcct") final String account,
			@RequestParam("userPswd") final String password) {
		// EmployeeServiceメソッドを呼び出して、ログインチェックを実行します。このメソッドがEmployeeオブジェクトを返すことができれば、ログインは成功です。アカウントとパスワードが間違っている場合は、例外がスローされます。
		final Employee employee = this.iEmployeeService.getAdminByLoginAccount(account, password);
		// 成功したログインによって返された管理オブジェクトをセッションドメインに保存します。
		final ModelAndView modelAndView = new ModelAndView("admin-main");
		modelAndView.addObject(PgCrowdConstants.ATTRNAME_LOGIN_ADMIN, employee);
		return modelAndView;
	}

	/**
	 * ログアウト処理
	 *
	 * @param session セッション
	 * @return String
	 */
	@PostMapping("/logout")
	public String doLogout(final HttpSession session) {
		// セッションを無効化する
		session.invalidate();
		return "redirect:/pgcrowd/employee/login";
	}

	/**
	 * 社員情報初期表示
	 *
	 * @param username ユーザ名称
	 * @return ModelAndView
	 */
	@GetMapping("/to/pages")
	public ModelAndView initialPages(@RequestParam("username") final String username) {
		final Employee employee = this.iEmployeeService.getEmployeeByUsername(username);
		final ModelAndView modelAndView = new ModelAndView("admin-pages");
		modelAndView.addObject(PgCrowdConstants.ATTRNAME_LOGIN_ADMIN, employee);
		return modelAndView;
	}

	/**
	 * キーワードによってページング検索
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return ResultDto<List<Employee>>
	 */
	@GetMapping("/pagination")
	@ResponseBody
	public ResultDto<Page<Employee>> pagination(
			@RequestParam(name = "pageNum", defaultValue = "1") final Integer pageNum,
			@RequestParam(name = "keyword", defaultValue = StringUtils.EMPTY_STRING) final String keyword) {
		final Page<Employee> employees = this.iEmployeeService.getEmployeesByKeyword(pageNum, keyword);
		return ResultDto.successWithData(employees);
	}

	/**
	 * メインメニューへの移動
	 *
	 * @param username ユーザ名称
	 * @return ModelAndView
	 */
	@GetMapping("/to/mainmenu")
	public ModelAndView toMainmenu(@RequestParam("username") final String username) {
		final Employee employee = this.iEmployeeService.getEmployeeByUsername(username);
		final ModelAndView modelAndView = new ModelAndView("admin-main");
		modelAndView.addObject(PgCrowdConstants.ATTRNAME_LOGIN_ADMIN, employee);
		return modelAndView;
	}
}
