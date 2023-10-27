package jp.co.toshiba.ppocph.repository;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.toshiba.ppocph.entity.Employee;

/**
 * 社員管理リポジトリ
 *
 * @author ArkamaHozota
 * @since 1.16
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

	/**
	 * 社員情報論理削除
	 *
	 * @param id 社員ID
	 */
	@Modifying
	@Transactional(rollbackFor = PSQLException.class)
	void removeById(@Param("id") Integer id);

	/**
	 * ID採番を取得する
	 *
	 * @return 採番ID
	 */
	Integer saiban();
}
