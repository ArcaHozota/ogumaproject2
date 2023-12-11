package jp.co.toshiba.ppocph.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jp.co.toshiba.ppocph.entity.PgAuth;

/**
 * 権限管理リポジトリ
 *
 * @author ArkamaHozota
 * @since 5.41
 */
@Repository
public interface PgAuthRepository extends JpaRepository<PgAuth, Long>, JpaSpecificationExecutor<PgAuth> {
}
