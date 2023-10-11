package jp.co.toshiba.ppocph.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.toshiba.ppocph.common.PgcrowdConstants;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.service.IEmployeeService;
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
		modelAndView.addObject(PgcrowdConstants.ATTRNAME_LOGIN_ADMIN, employee);
		return modelAndView;
	}

	/**
	 * ログアウト処理
	 *
	 * @param session セッション
	 * @return String
	 */
	@PostMapping("/do/logout")
	public String doLogout(final HttpSession session) {
		// セッションを無効化する
		session.invalidate();
		return "redirect:/pgcrowd/employee/login";
	}
}
