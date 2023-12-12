package jp.co.toshiba.ppocph.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jp.co.toshiba.ppocph.entity.RoleEx;

/**
 * 役割権限連携リポジトリ
 *
 * @author ArkamaHozota
 * @since 5.77
 */
@Repository
public interface RoleExRepository extends JpaRepository<RoleEx, Long>, JpaSpecificationExecutor<RoleEx> {
}
