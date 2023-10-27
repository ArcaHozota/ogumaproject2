package jp.co.toshiba.ppocph.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.exception.LoginFailedException;
import jp.co.toshiba.ppocph.repository.EmployeeRepository;
import jp.co.toshiba.ppocph.service.IEmployeeService;
import jp.co.toshiba.ppocph.utils.Pagination;
import jp.co.toshiba.ppocph.utils.PgCrowdUtils;
import jp.co.toshiba.ppocph.utils.StringUtils;
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
	public Pagination<Employee> getEmployeesByKeyword(final Integer pageNum, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, PgCrowdConstants.DEFAULT_PAGE_SIZE,
				Sort.by(Direction.ASC, "id"));
		final String searchStr = "%" + keyword + "%";
		final Specification<Employee> where1 = StringUtils.isEmpty(keyword) ? null
				: (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("loginAccount"), searchStr);
		final Specification<Employee> where2 = StringUtils.isEmpty(keyword) ? null
				: (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), searchStr);
		final Specification<Employee> where3 = StringUtils.isEmpty(keyword) ? null
				: (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), searchStr);
		final Specification<Employee> specification = Specification.where(where1).or(where2).or(where3);
		final Page<Employee> pages = this.employeeRepository.findAll(specification, pageRequest);
		return Pagination.of(pages.getContent(), pages.getTotalElements(), pageNum, PgCrowdConstants.DEFAULT_PAGE_SIZE);
	}

	@Override
	public void saveInfo(final Employee employee) {
		final Integer saibanId = this.employeeRepository.saiban();
		employee.setId(saibanId);
		employee.setCreatedTime(LocalDateTime.now());
		this.employeeRepository.save(employee);
	}
}
