package jp.co.toshiba.ppocph.listener;

import static jp.co.toshiba.ppocph.jooq.Tables.AUTHORITIES;
import static jp.co.toshiba.ppocph.jooq.Tables.EMPLOYEES;
import static jp.co.toshiba.ppocph.jooq.Tables.EMPLOYEE_ROLE;
import static jp.co.toshiba.ppocph.jooq.Tables.ROLE_AUTH;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.exception.NoDataFoundException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jp.co.toshiba.ppocph.common.OgumaProjectConstants;
import jp.co.toshiba.ppocph.dto.EmployeeDto;
import jp.co.toshiba.ppocph.jooq.Keys;
import jp.co.toshiba.ppocph.jooq.tables.records.AuthoritiesRecord;
import jp.co.toshiba.ppocph.jooq.tables.records.EmployeeRoleRecord;
import jp.co.toshiba.ppocph.jooq.tables.records.EmployeesRecord;
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
	private final DSLContext dslContext;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final EmployeesRecord employeesRecord = this.dslContext.selectFrom(EMPLOYEES)
				.where(EMPLOYEES.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL))
				.and(EMPLOYEES.LOGIN_ACCOUNT.eq(username).or(EMPLOYEES.EMAIL.eq(username))).fetchOne();
		if (employeesRecord == null) {
			throw new DisabledException(OgumaProjectConstants.MESSAGE_SPRINGSECURITY_LOGINERROR1);
		}
		final EmployeeDto employeeDto = new EmployeeDto(employeesRecord.getId(), employeesRecord.getLoginAccount(),
				employeesRecord.getUsername(), employeesRecord.getPassword(), employeesRecord.getEmail(),
				DateTimeFormatter.ofPattern("yyyy-MM-dd").format(employeesRecord.getDateOfBirth()), 0L);
		EmployeeRoleRecord employeeRoleRecord;
		try {
			employeeRoleRecord = this.dslContext.selectFrom(EMPLOYEE_ROLE)
					.where(EMPLOYEE_ROLE.EMPLOYEE_ID.eq(employeesRecord.getId())).fetchSingle();
		} catch (final NoDataFoundException e) {
			throw new InsufficientAuthenticationException(OgumaProjectConstants.MESSAGE_SPRINGSECURITY_LOGINERROR2);
		} catch (final Exception e) {
			throw new InsufficientAuthenticationException(OgumaProjectConstants.MESSAGE_STRING_FATAL_ERROR);
		}
		final List<AuthoritiesRecord> authoritiesRecords = this.dslContext.select(AUTHORITIES.ID, AUTHORITIES.NAME)
				.from(ROLE_AUTH).innerJoin(AUTHORITIES).onKey(Keys.ROLE_AUTH__FK4)
				.where(ROLE_AUTH.ROLE_ID.eq(employeeRoleRecord.getRoleId())).fetchInto(AuthoritiesRecord.class);
		if (authoritiesRecords.isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException(
					OgumaProjectConstants.MESSAGE_SPRINGSECURITY_LOGINERROR3);
		}
		final List<SimpleGrantedAuthority> authorities = authoritiesRecords.stream()
				.map(item -> new SimpleGrantedAuthority(item.getName())).toList();
		return new SecurityAdmin(employeeDto, authorities);
	}
}
