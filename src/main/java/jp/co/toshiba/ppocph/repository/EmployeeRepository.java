package jp.co.toshiba.ppocph.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jp.co.toshiba.ppocph.entity.Employee;

/**
 * 社員管理リポジトリ
 *
 * @author ArkamaHozota
 * @since 1.16
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

	/**
	 * ID採番を取得する
	 *
	 * @return 採番ID
	 */
	Long saiban();
}
