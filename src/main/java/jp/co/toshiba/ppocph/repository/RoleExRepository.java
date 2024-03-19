package jp.co.toshiba.ppocph.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jp.co.toshiba.ppocph.dto.RoleAuthIds;
import jp.co.toshiba.ppocph.entity.RoleAuth;

/**
 * 役割権限連携リポジトリ
 *
 * @author ArkamaHozota
 * @since 5.77
 */
public interface RoleExRepository extends JpaRepository<RoleAuth, RoleAuthIds>, JpaSpecificationExecutor<RoleAuth> {
}
