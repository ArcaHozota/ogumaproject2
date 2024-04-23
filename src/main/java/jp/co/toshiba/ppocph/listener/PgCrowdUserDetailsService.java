package jp.co.toshiba.ppocph.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.entity.EmployeeRole;
import jp.co.toshiba.ppocph.entity.RoleAuth;
import jp.co.toshiba.ppocph.repository.EmployeeExRepository;
import jp.co.toshiba.ppocph.repository.EmployeeRepository;
import jp.co.toshiba.ppocph.repository.PgAuthRepository;
import jp.co.toshiba.ppocph.repository.RoleExRepository;
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
public final class PgCrowdUserDetailsService implements UserDetailsService {

	/**
	 * 社員管理リポジトリ
	 */
	private final EmployeeRepository employeeRepository;

	/**
	 * 社員役割連携リポジトリ
	 */
	private final EmployeeExRepository employeeExRepository;

	/**
	 * 役割権限連携リポジトリ
	 */
	private final RoleExRepository roleExRepository;

	/**
	 * 権限管理リポジトリ
	 */
	private final PgAuthRepository pgAuthRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Specification<Employee> status = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("deleteFlg"), PgCrowdConstants.LOGIC_DELETE_INITIAL);
		final Specification<Employee> where1 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("loginAccount"), username);
		final Specification<Employee> where2 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("email"), username);
		final Specification<Employee> specification1 = Specification.where(status)
				.and(Specification.anyOf(where1, where2));
		final Employee employee = this.employeeRepository.findOne(specification1).orElseThrow(() -> {
			throw new DisabledException(PgCrowdConstants.MESSAGE_SPRINGSECURITY_LOGINERROR1);
		});
		final Optional<EmployeeRole> roleOptional = this.employeeExRepository.findById(employee.getId());
		if (roleOptional.isEmpty()) {
			throw new InsufficientAuthenticationException(PgCrowdConstants.MESSAGE_SPRINGSECURITY_LOGINERROR2);
		}
		final Specification<RoleAuth> where3 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("roleId"), roleOptional.get().getRoleId());
		final Specification<RoleAuth> specification2 = Specification.where(where3);
		final List<Long> authIds = this.roleExRepository.findAll(specification2).stream().map(RoleAuth::getAuthId)
				.toList();
		if (authIds.isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException(PgCrowdConstants.MESSAGE_SPRINGSECURITY_LOGINERROR3);
		}
		final List<GrantedAuthority> authorities = new ArrayList<>();
		this.pgAuthRepository.findAllById(authIds).stream().map(item -> new SimpleGrantedAuthority(item.getName()))
				.toList().forEach(authorities::add);
		return new SecurityAdmin(employee, authorities);
	}
}
