package jp.co.toshiba.ppocph.service;

import org.springframework.data.domain.Page;

import jp.co.toshiba.ppocph.entity.Employee;

/**
 * 社員サービスインターフェス
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
public interface IEmployeeService {

	/**
	 * ログイン処理
	 *
	 * @param account  アカウント
	 * @param password パスワード
	 * @return Employee
	 */
	Employee getAdminByLoginAccount(String account, String password);

	/**
	 * ユーザ名称によって社員情報を取得する
	 *
	 * @param username ユーザ名称
	 * @return Employee
	 */
	Employee getEmployeeByUsername(String username);

	/**
	 * キーワードによって社員情報を取得する
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return List<Employee>
	 */
	Page<Employee> getEmployeesByKeyword(Integer pageNum, String keyword);
}
