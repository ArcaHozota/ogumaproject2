package jp.co.toshiba.ppocph.listener;

import javax.annotation.Resource;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.exception.PgCrowdException;
import jp.co.toshiba.ppocph.repository.EmployeeRepository;

/**
 * ログインコントローラ(SpringSecurity関連)
 *
 * @author Arkamahozota
 * @since 6.07
 */
@Component
public final class PgCrowdUserDetailsService implements UserDetailsService {

	/**
	 * 社員管理リポジトリ
	 */
	@Resource
	private EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Specification<Employee> where = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("loginAccount"), username);
		final Specification<Employee> specification = Specification.where(where);
		final Employee employee = this.employeeRepository.findOne(specification).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_PROHIBITED);
		});
		employee.getClass();
		return null;
	}

}
