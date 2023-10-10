package jp.co.toshiba.ppocph.service;

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
}
