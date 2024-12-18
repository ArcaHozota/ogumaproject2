package jp.co.ogumaproject.ppok.listener;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jp.co.ogumaproject.ppok.common.OgumaProjectConstants;
import jp.co.ogumaproject.ppok.dto.EmployeeDto;
import jp.co.ogumaproject.ppok.entity.Authority;
import jp.co.ogumaproject.ppok.entity.Employee;
import jp.co.ogumaproject.ppok.entity.EmployeeRole;
import jp.co.ogumaproject.ppok.exception.OgumaProjectException;
import jp.co.ogumaproject.ppok.repository.AuthorityRepository;
import jp.co.ogumaproject.ppok.repository.EmployeeRepository;
import jp.co.ogumaproject.ppok.repository.EmployeeRoleRepository;
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
	 * 権限リポジトリ
	 */
	private final AuthorityRepository authorityRepository;

	/**
	 * 権限リポジトリ
	 */
	private final EmployeeRepository employeeRepository;

	/**
	 * 権限リポジトリ
	 */
	private final EmployeeRoleRepository employeeRoleRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Employee employee = this.employeeRepository.getOneByLoginAccount(username);
		if (employee == null) {
			throw new DisabledException(OgumaProjectConstants.MESSAGE_SPRINGSECURITY_LOGINERROR1);
		}
		final EmployeeRole employeeRole = this.employeeRoleRepository.getOneById(employee.getId());
		if (employeeRole == null) {
			throw new OgumaProjectException(OgumaProjectConstants.MESSAGE_SPRINGSECURITY_LOGINERROR2);
		}
		final List<Authority> authorities = this.authorityRepository.getListByForeignKey(employeeRole.getRoleId());
		if (authorities.isEmpty()) {
			throw new OgumaProjectException(OgumaProjectConstants.MESSAGE_SPRINGSECURITY_LOGINERROR3);
		}
		final EmployeeDto employeeDto = new EmployeeDto(employee.getId(), employee.getLoginAccount(),
				employee.getUsername(), employee.getPassword(), employee.getEmail(),
				DateTimeFormatter.ofPattern("yyyy-MM-dd").format(employee.getDateOfBirth()), employeeRole.getRoleId());
		final List<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
				.map(item -> new SimpleGrantedAuthority(item.getName())).toList();
		return new SecurityAdmin(employeeDto, simpleGrantedAuthorities);
	}
}
