package jp.co.ogumaproject.ppok.listener;

import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import jp.co.ogumaproject.ppok.dto.EmployeeDto;
import lombok.EqualsAndHashCode;

/**
 * User拡張クラス(SpringSecurity関連)
 *
 * @author ArkamaHozota
 * @since 6.05
 */
@EqualsAndHashCode(callSuper = false)
public final class SecurityAdmin extends User {

	private static final long serialVersionUID = 3827955098466369880L;

	/**
	 * 社員管理DTO
	 */
	private final EmployeeDto originalAdmin;

	/**
	 * コンストラクタ
	 *
	 * @param admin       社員管理DTO
	 * @param authorities 権限リスト
	 */
	SecurityAdmin(final EmployeeDto admin, final Collection<SimpleGrantedAuthority> authorities) {
		super(admin.loginAccount(), admin.password(), true, true, true, true, authorities);
		this.originalAdmin = admin;
	}

	/**
	 * getter for originalAdmin
	 *
	 * @return EmployeeDto
	 */
	public EmployeeDto getOriginalAdmin() {
		return this.originalAdmin;
	}
}
