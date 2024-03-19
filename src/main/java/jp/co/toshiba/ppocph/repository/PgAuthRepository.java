package jp.co.toshiba.ppocph.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jp.co.toshiba.ppocph.entity.Authority;

/**
 * 権限管理リポジトリ
 *
 * @author ArkamaHozota
 * @since 5.41
 */
public interface PgAuthRepository extends JpaRepository<Authority, Long>, JpaSpecificationExecutor<Authority> {
}
