package jp.co.toshiba.ppocph.service;

import jp.co.toshiba.ppocph.dto.EmployeeDto;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.utils.Pagination;

/**
 * 社員サービスインターフェス
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
public interface IEmployeeService {

	/**
	 * ログインアカウントを重複するかどうかを確認する
	 *
	 * @param loginAccount ログインアカウント
	 */
	boolean check(String loginAccount);

	/**
	 * 社員情報削除
	 *
	 * @param userId 社員ID
	 */
	void deleteById(Integer userId);

	/**
	 * ログイン処理
	 *
	 * @param account  アカウント
	 * @param password パスワード
	 * @return Employee
	 */
	Employee getAdminByLoginAccount(String account, String password);

	/**
	 * IDによって社員情報を取得する
	 *
	 * @param id 社員ID
	 * @return Employee
	 */
	Employee getEmployeeById(Integer id);

	/**
	 * キーワードによって社員情報を取得する
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return List<Employee>
	 */
	Pagination<Employee> getEmployeesByKeyword(Integer pageNum, String keyword);

	/**
	 * 社員情報追加
	 *
	 * @param employeeDto 社員情報転送クラス
	 */
	void save(EmployeeDto employeeDto);
}
