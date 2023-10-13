package jp.co.toshiba.ppocph.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.exception.LoginFailedException;
import jp.co.toshiba.ppocph.repository.EmployeeRepository;
import jp.co.toshiba.ppocph.service.IEmployeeService;
import jp.co.toshiba.ppocph.utils.PgCrowdUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 社員サービス実装クラス
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeServiceImpl implements IEmployeeService {

	/**
	 * 社員管理リポジトリ
	 */
	private final EmployeeRepository employeeRepository;

	@Override
	public Employee getAdminByLoginAccount(final String account, final String password) {
		final String plainToMD5 = PgCrowdUtils.plainToMD5(password);
		final Employee employee = new Employee();
		employee.setLoginAccount(account);
		employee.setPassword(plainToMD5);
		final Example<Employee> example = Example.of(employee, ExampleMatcher.matchingAll());
		return this.employeeRepository.findOne(example).orElseGet(() -> {
			throw new LoginFailedException(PgCrowdConstants.MESSAGE_STRING_PROHIBITED);
		});
	}

	@Override
	public Employee getEmployeeByUsername(final String username) {
		final Employee employee = new Employee();
		employee.setUsername(username);
		final Example<Employee> example = Example.of(employee, ExampleMatcher.matching());
		return this.employeeRepository.findOne(example).orElseGet(Employee::new);
	}

	@Override
	public Page<Employee> getEmployeesByKeyword(final Integer pageNum, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, 5, Sort.by(Direction.ASC, "id"));
		final Employee employee = new Employee();
		employee.setLoginAccount(keyword);
		employee.setUsername(keyword);
		employee.setEmail(keyword);
		final ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("username", GenericPropertyMatchers.contains())
				.withMatcher("loginAccount", GenericPropertyMatchers.contains())
				.withMatcher("email", GenericPropertyMatchers.contains());
		final Example<Employee> example = Example.of(employee, matcher);
		return this.employeeRepository.findAll(example, pageRequest);
	}
}
