package jp.co.toshiba.ppocph.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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
	 * @param session  セッション
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
	 * キーワードによってページング検索
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return ResultDto<List<Employee>>
	 */
	@GetMapping("/pages")
	@ResponseBody
	public ResultDto<List<Employee>> pagination(
			@RequestParam(name = "page-num", defaultValue = "1") final Integer pageNum,
			@RequestParam(name = "keyword", defaultValue = StringUtils.EMPTY_STRING) final String keyword) {
		final List<Employee> employees = this.iEmployeeService.getEmployeesByKeyword(pageNum, keyword);
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

	/**
	 * 社員情報初期表示
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return ModelAndView
	 */
	@GetMapping("/to/pages")
	public ModelAndView toPages(@RequestParam("username") final String username) {
		final Employee employee = this.iEmployeeService.getEmployeeByUsername(username);
		final List<Employee> employees = this.iEmployeeService.getEmployeesByKeyword(1, StringUtils.EMPTY_STRING);
		final ModelAndView modelAndView = new ModelAndView("admin-pages");
		modelAndView.addObject(PgCrowdConstants.ATTRNAME_LOGIN_ADMIN, employee);
		modelAndView.addObject(PgCrowdConstants.ATTRNAME_PAGE_INFO, employees);
		return modelAndView;
	}
}
