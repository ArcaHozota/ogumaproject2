package jp.co.toshiba.ppocph.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.dto.EmployeeDto;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.entity.EmployeeEx;
import jp.co.toshiba.ppocph.entity.Role;
import jp.co.toshiba.ppocph.exception.LoginFailedException;
import jp.co.toshiba.ppocph.exception.PgCrowdException;
import jp.co.toshiba.ppocph.repository.EmployeeExRepository;
import jp.co.toshiba.ppocph.repository.EmployeeRepository;
import jp.co.toshiba.ppocph.repository.RoleRepository;
import jp.co.toshiba.ppocph.service.IEmployeeService;
import jp.co.toshiba.ppocph.utils.Pagination;
import jp.co.toshiba.ppocph.utils.PgCrowdUtils;
import jp.co.toshiba.ppocph.utils.ResultDto;
import jp.co.toshiba.ppocph.utils.SecondBeanUtils;
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

	/**
	 * 社員役割連携リポジトリ
	 */
	private final EmployeeExRepository employeeExRepository;

	/**
	 * 役割管理リポジトリ
	 */
	private final RoleRepository roleRepository;

	@Override
	public ResultDto<String> check(final String loginAccount) {
		final Employee employee = new Employee();
		employee.setLoginAccount(loginAccount);
		final Example<Employee> example = Example.of(employee, ExampleMatcher.matching());
		return this.employeeRepository.findOne(example).isPresent()
				? ResultDto.failed(PgCrowdConstants.MESSAGE_STRING_DUPLICATED)
				: ResultDto.successWithoutData();
	}

	@Override
	public Employee getAdminByLoginAccount(final String account, final String password) {
		final String plainToMD5 = PgCrowdUtils.plainToMD5(password);
		final Employee employee = new Employee();
		employee.setLoginAccount(account);
		employee.setPassword(plainToMD5);
		final Example<Employee> example = Example.of(employee, ExampleMatcher.matchingAll());
		return this.employeeRepository.findOne(example).orElseThrow(() -> {
			throw new LoginFailedException(PgCrowdConstants.MESSAGE_STRING_PROHIBITED);
		});
	}

	@Override
	public Employee getEmployeeById(final Long id) {
		return this.employeeRepository.findById(id).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_PROHIBITED);
		});
	}

	@Override
	public List<Role> getEmployeeRolesById(final Long id) {
		final List<Role> secondRoles = new ArrayList<>();
		final Role secondRole = new Role();
		secondRole.setId(0L);
		secondRole.setName(PgCrowdConstants.DEFAULT_ROLE_NAME);
		final Specification<Role> where1 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("deleteFlg"), PgCrowdConstants.LOGIC_DELETE_INITIAL);
		final Specification<Role> specification1 = Specification.where(where1);
		final List<Role> roles = this.roleRepository.findAll(specification1);
		if (id == null) {
			secondRoles.add(secondRole);
			secondRoles.addAll(roles);
			return secondRoles;
		}
		final Specification<EmployeeEx> where2 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("employeeId"), id);
		final Specification<EmployeeEx> specification2 = Specification.where(where2);
		final Optional<EmployeeEx> roledOptional = this.employeeExRepository.findOne(specification2);
		if (roledOptional.isEmpty()) {
			secondRoles.add(secondRole);
			secondRoles.addAll(roles);
			return secondRoles;
		}
		final Long roleId = roledOptional.get().getRoleId();
		final List<Role> selectedRole = roles.stream().filter(a -> Objects.equals(a.getId(), roleId))
				.collect(Collectors.toList());
		secondRoles.addAll(selectedRole);
		secondRoles.addAll(roles);
		return secondRoles.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public Pagination<Employee> getEmployeesByKeyword(final Integer pageNum, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, PgCrowdConstants.DEFAULT_PAGE_SIZE,
				Sort.by(Direction.ASC, "id"));
		final String searchStr = "%" + keyword + "%";
		final Specification<Employee> status = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("status"), PgCrowdConstants.EMPLOYEE_NORMAL_STATUS);
		final Specification<Employee> where1 = StringUtils.isEmpty(keyword) ? null
				: (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("loginAccount"), searchStr);
		final Specification<Employee> where2 = StringUtils.isEmpty(keyword) ? null
				: (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), searchStr);
		final Specification<Employee> where3 = StringUtils.isEmpty(keyword) ? null
				: (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), searchStr);
		final Specification<Employee> specification = Specification.where(status)
				.and(Specification.where(where1).or(where2).or(where3));
		final Page<Employee> pages = this.employeeRepository.findAll(specification, pageRequest);
		return Pagination.of(pages.getContent(), pages.getTotalElements(), pageNum, PgCrowdConstants.DEFAULT_PAGE_SIZE);
	}

	@Override
	public void removeById(final Long userId) {
		final Employee employee = this.employeeRepository.findById(userId).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_PROHIBITED);
		});
		employee.setStatus(PgCrowdConstants.EMPLOYEE_ABNORMAL_STATUS);
		this.employeeRepository.saveAndFlush(employee);
	}

	@Override
	public void save(final EmployeeDto employeeDto) {
		final Long saibanId = this.employeeRepository.saiban();
		final String plainToMD5 = PgCrowdUtils.plainToMD5(employeeDto.getPassword());
		final Employee employee = new Employee();
		SecondBeanUtils.copyNullableProperties(employeeDto, employee);
		employee.setId(saibanId);
		employee.setPassword(plainToMD5);
		employee.setStatus(PgCrowdConstants.EMPLOYEE_NORMAL_STATUS);
		employee.setCreatedTime(LocalDateTime.now());
		if ((employeeDto.getRoleId() != null) && !Objects.equals(Long.valueOf(0L), employeeDto.getRoleId())) {
			final EmployeeEx employeeEx = new EmployeeEx();
			employeeEx.setEmployeeId(employeeDto.getId());
			employeeEx.setRoleId(employeeDto.getRoleId());
			this.employeeExRepository.saveAndFlush(employeeEx);
		}
		this.employeeRepository.saveAndFlush(employee);
	}

	@Override
	public void update(final EmployeeDto employeeDto) {
		final String password = employeeDto.getPassword();
		final Employee employee = this.employeeRepository.findById(employeeDto.getId()).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_PROHIBITED);
		});
		final EmployeeEx employeeEx = new EmployeeEx();
		employeeEx.setEmployeeId(employeeDto.getId());
		employeeEx.setRoleId(employeeDto.getRoleId());
		this.employeeExRepository.saveAndFlush(employeeEx);
		SecondBeanUtils.copyNullableProperties(employeeDto, employee);
		if (StringUtils.isNotEmpty(password)) {
			final String plainToMD5 = PgCrowdUtils.plainToMD5(password);
			employee.setPassword(plainToMD5);
		}
		this.employeeRepository.saveAndFlush(employee);
	}
}
