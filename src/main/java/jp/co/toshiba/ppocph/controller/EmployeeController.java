package jp.co.toshiba.ppocph.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.toshiba.ppocph.common.PgcrowdConstants;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmployeeController {

	/**
	 * 社員サービスインターフェス
	 */
	private final EmployeeService employeeService;

	/**
	 * ログイン処理
	 *
	 * @param account  アカウント
	 * @param password パスワード
	 * @param session  セッション
	 * @return String
	 */
	@RequestMapping("/admin/do/login.html")
	public String doLogin(@RequestParam("loginAcct") final String account,
			@RequestParam("userPswd") final String password, final HttpSession session) {
		// EmployeeServiceメソッドを呼び出して、ログインチェックを実行します。このメソッドがEmployeeオブジェクトを返すことができれば、ログインは成功です。アカウントとパスワードが間違っている場合は、例外がスローされます。
		final Employee employee = this.employeeService.getAdminByLoginAccount(account, password);
		// 成功したログインによって返された管理オブジェクトをセッションドメインに保存します。
		session.setAttribute(PgcrowdConstants.ATTRNAME_LOGIN_ADMIN, employee);
		return "redirect:/admin/to/main/page.html";
	}
}
