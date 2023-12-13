package jp.co.toshiba.ppocph.listener;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.dto.SecurityAdmin;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.entity.EmployeeEx;
import jp.co.toshiba.ppocph.entity.RoleEx;
import jp.co.toshiba.ppocph.exception.PgCrowdException;
import jp.co.toshiba.ppocph.repository.EmployeeExRepository;
import jp.co.toshiba.ppocph.repository.EmployeeRepository;
import jp.co.toshiba.ppocph.repository.PgAuthRepository;
import jp.co.toshiba.ppocph.repository.RoleExRepository;
import lombok.RequiredArgsConstructor;

/**
 * ログインコントローラ(SpringSecurity関連)
 *
 * @author ArkamaHozota
 * @since 6.07
 */
@RequiredArgsConstructor
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
		final Specification<Employee> where1 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("loginAccount"), username);
		final Specification<Employee> specification1 = Specification.where(where1);
		final Employee employee = this.employeeRepository.findOne(specification1).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_PROHIBITED);
		});
		final Specification<EmployeeEx> where2 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("employeeId"), employee.getId());
		final Specification<EmployeeEx> specification2 = Specification.where(where2);
		final Optional<EmployeeEx> roleOptional = this.employeeExRepository.findOne(specification2);
		if (roleOptional.isEmpty()) {
			return null;
		}
		final Specification<RoleEx> where3 = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("roleId"),
				roleOptional.get().getRoleId());
		final Specification<RoleEx> specification3 = Specification.where(where3);
		final List<Long> authIds = this.roleExRepository.findAll(specification3).stream().map(RoleEx::getAuthId)
				.collect(Collectors.toList());
		if (authIds.isEmpty()) {
			return null;
		}
		final List<GrantedAuthority> authorities = this.pgAuthRepository.findAllById(authIds).stream()
				.map(item -> new SimpleGrantedAuthority(item.getName())).collect(Collectors.toList());
		return new SecurityAdmin(employee, authorities);
	}

}
