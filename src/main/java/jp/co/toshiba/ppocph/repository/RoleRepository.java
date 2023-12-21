package jp.co.toshiba.ppocph.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import jp.co.toshiba.ppocph.entity.Role;

/**
 * 役割管理リポジトリ
 *
 * @author ArkamaHozota
 * @since 4.45
 */
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

	/**
	 * IDによって役割情報を取得する
	 *
	 * @param keyword  検索文
	 * @param pageable ページング条件
	 * @return Page<Role>
	 */
	Page<Role> findByIdLike(@Param("idLike") String keyword, Pageable pageable);
}
