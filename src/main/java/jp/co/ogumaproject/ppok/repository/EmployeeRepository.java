package jp.co.ogumaproject.ppok.repository;

import java.util.List;

import jp.co.ogumaproject.ppok.entity.Employee;

/**
 * 社員リポジトリ
 *
 * @author ArkamaHozota
 * @since 9.64
 */
public interface EmployeeRepository extends CommonRepository<Employee> {

	/**
	 * キーワードによって件数をカウントする
	 *
	 * @param keyword 検索キーワード
	 * @return Integer
	 */
	Integer countByKeyword(String keyword);

	/**
	 * 名称によってレコード数を計算する
	 *
	 * @param name 社員名称
	 * @return Integer
	 */
	Integer countByName(String name);

	/**
	 * エンティティによって1件を抽出する
	 *
	 * @param aEntity エンティティ
	 * @return Employee
	 */
	Employee getOneByEntity(Employee aEntity);

	/**
	 * アカウントによって1件を抽出する
	 *
	 * @param loginAccount アカウント
	 * @return Employee
	 */
	Employee getOneByLoginAccount(String loginAccount);

	/**
	 * パージング検索
	 *
	 * @param offset   オフセット
	 * @param pageSize ページサイズ
	 * @param keyword  検索キーワード
	 * @return List<DistrictDto>
	 */
	List<Employee> pagination(Integer offset, Integer pageSize, String keyword);
}
