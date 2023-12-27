package jp.co.toshiba.ppocph.listener;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.utils.StringUtils;
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

	private final Employee originalAdmin;

	SecurityAdmin(final Employee admin, final Collection<GrantedAuthority> authorities) {
		super(admin.getLoginAccount(), admin.getPassword(), true, true, true, true, authorities);
		this.originalAdmin = admin;
		this.originalAdmin.setPassword(StringUtils.EMPTY_STRING);
	}

	public Employee getOriginalAdmin() {
		return this.originalAdmin;
	}
}
