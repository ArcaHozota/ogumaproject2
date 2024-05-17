package jp.co.ogumaproject.ppog.listener;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jp.co.ogumaproject.ppog.common.OgumaProjectConstants;
import jp.co.ogumaproject.ppog.dto.EmployeeDto;
import jp.co.ogumaproject.ppog.entity.Employee;
import jp.co.ogumaproject.ppog.exception.OgumaProjectException;
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
	 * 共通リポジトリ
	 */
	private final JdbcClient jdbcClient;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Employee employee = this.jdbcClient.sql(
				"SELECT PEV.* FROM PPOG_EMPLOYEE_VIEW PEV WHERE PEV.DEL_FLG = ? AND (PEV.LOGIN_ACCOUNT = ? OR PEV.USERNAME = ?)")
				.params(OgumaProjectConstants.LOGIC_DELETE_INITIAL, username, username).query(Employee.class).single();
		if (employee == null) {
			throw new DisabledException(OgumaProjectConstants.MESSAGE_SPRINGSECURITY_LOGINERROR1);
		}
		final Long roleId = this.jdbcClient
				.sql("SELECT PERV.ROLE_ID FROM PPOG_EMPLOYEE_ROLE_VIEW PERV WHERE PERV.EMPLOYEE_ID = ?")
				.param(employee.getId()).query(Long.class).single();
		if (roleId == null) {
			throw new OgumaProjectException(OgumaProjectConstants.MESSAGE_SPRINGSECURITY_LOGINERROR2);
		}
		final List<AuthoritiesRecord> authoritiesRecords = this.dslContext.select(AUTHORITIES.ID, AUTHORITIES.NAME)
				.from(ROLE_AUTH).innerJoin(AUTHORITIES).onKey(Keys.ROLE_AUTH__FK_RA_AUTHORITIES)
				.where(ROLE_AUTH.ROLE_ID.eq(employeeRoleRecord.getRoleId())).fetchInto(AuthoritiesRecord.class);
		if (authoritiesRecords.isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException(
					OgumaProjectConstants.MESSAGE_SPRINGSECURITY_LOGINERROR3);
		}
		final EmployeeDto employeeDto = new EmployeeDto(employee.getId(), employee.getLoginAccount(),
				employee.getUsername(), employee.getPassword(), employee.getEmail(),
				DateTimeFormatter.ofPattern("yyyy-MM-dd").format(employee.getDateOfBirth()), roleId);
		final List<SimpleGrantedAuthority> authorities = authoritiesRecords.stream()
				.map(item -> new SimpleGrantedAuthority(item.getName())).toList();
		return new SecurityAdmin(employeeDto, authorities);
	}
}
