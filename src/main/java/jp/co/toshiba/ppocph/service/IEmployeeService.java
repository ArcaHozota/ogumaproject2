package jp.co.toshiba.ppocph.service;

import java.util.List;

import jp.co.toshiba.ppocph.dto.EmployeeDto;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.entity.Role;
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
	Employee getEmployeeById(Long id);

	/**
	 * 社員役割連携情報を取得する
	 *
	 * @param id 社員ID
	 * @return List<String>
	 */
	List<Role> getEmployeeRolesById(Long id);

	/**
	 * キーワードによって社員情報を取得する
	 *
	 * @param pageNum ページ数
	 * @param keyword キーワード
	 * @return Pagination<Employee>
	 */
	Pagination<Employee> getEmployeesByKeyword(Integer pageNum, String keyword);

	/**
	 * 社員情報削除
	 *
	 * @param userId 社員ID
	 */
	void removeById(Long userId);

	/**
	 * 社員情報追加
	 *
	 * @param employeeDto 社員情報転送クラス
	 */
	void save(EmployeeDto employeeDto);

	/**
	 * 社員情報行更新
	 *
	 * @param employeeDto 社員情報転送クラス
	 */
	void update(EmployeeDto employeeDto);
}
