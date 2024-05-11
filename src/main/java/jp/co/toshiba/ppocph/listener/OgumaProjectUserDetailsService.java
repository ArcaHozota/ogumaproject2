package jp.co.toshiba.ppocph.listener;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jp.co.toshiba.ppocph.common.OgumaProjectConstants;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.entity.EmployeeRole;
import jp.co.toshiba.ppocph.entity.RoleAuth;
import jp.co.toshiba.ppocph.repository.AuthorityRepository;
import jp.co.toshiba.ppocph.repository.EmployeeRepository;
import jp.co.toshiba.ppocph.repository.EmployeeRoleRepository;
import jp.co.toshiba.ppocph.repository.RoleAuthRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * ログインコントローラ(SpringSecurity関連)
 *
 * @author ArkamaHozota
 * @since 6.07
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OgumaProjectUserDetailsService implements UserDetailsService {

	/**
	 * 社員管理リポジトリ
	 */
	private final EmployeeRepository employeeRepository;

	/**
	 * 社員役割連携リポジトリ
	 */
	private final EmployeeRoleRepository employeeRoleRepository;

	/**
	 * 役割権限連携リポジトリ
	 */
	private final RoleAuthRepository roleAuthRepository;

	/**
	 * 権限管理リポジトリ
	 */
	private final AuthorityRepository authorityRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Employee employee = this.employeeRepository.newQuery().where("login_account", username)
				.orWhere(builder -> builder.where("email", username)).firstOrFail().getEntity();
		if (employee == null) {
			throw new DisabledException(OgumaProjectConstants.MESSAGE_SPRINGSECURITY_LOGINERROR1);
		}
		final EmployeeRole employeeRoleEntity = new EmployeeRole();
		employeeRoleEntity.setEmployeeId(employee.getId());
		final EmployeeRole employeeRole = this.employeeRoleRepository.findByPrimaryKeyOrNew(employeeRoleEntity)
				.getEntity();
		if (employeeRole == null) {
			throw new InsufficientAuthenticationException(OgumaProjectConstants.MESSAGE_SPRINGSECURITY_LOGINERROR2);
		}
		final List<RoleAuth> roleAuths = this.roleAuthRepository.newQuery().where("role_id", employeeRole.getRoleId())
				.get().toObjectList();
		if (roleAuths.isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException(
					OgumaProjectConstants.MESSAGE_SPRINGSECURITY_LOGINERROR3);
		}
		final List<Long> authIds = roleAuths.stream().map(RoleAuth::getAuthId).toList();
		final List<GrantedAuthority> authorities = new ArrayList<>();
		this.authorityRepository.findMany(authIds).toObjectList().stream()
				.map(item -> new SimpleGrantedAuthority(item.getName())).forEach(authorities::add);
		return new SecurityAdmin(employee, authorities);
	}
}
