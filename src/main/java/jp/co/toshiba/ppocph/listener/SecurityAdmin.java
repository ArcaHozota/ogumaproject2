package jp.co.toshiba.ppocph.listener;

import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import jp.co.toshiba.ppocph.dto.EmployeeDto;
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

	private final EmployeeDto originalAdmin;

	SecurityAdmin(final EmployeeDto admin, final Collection<SimpleGrantedAuthority> authorities) {
		super(admin.loginAccount(), admin.password(), true, true, true, true, authorities);
		this.originalAdmin = admin;
	}

	public EmployeeDto getOriginalAdmin() {
		return this.originalAdmin;
	}
}
