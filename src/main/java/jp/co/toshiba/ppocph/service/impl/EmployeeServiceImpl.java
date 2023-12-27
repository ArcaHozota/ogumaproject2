package jp.co.toshiba.ppocph.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.stereotype.Service;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.dto.EmployeeDto;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.entity.EmployeeEx;
import jp.co.toshiba.ppocph.exception.PgCrowdException;
import jp.co.toshiba.ppocph.repository.EmployeeExRepository;
import jp.co.toshiba.ppocph.repository.EmployeeRepository;
import jp.co.toshiba.ppocph.service.IEmployeeService;
import jp.co.toshiba.ppocph.utils.Pagination;
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
public final class EmployeeServiceImpl implements IEmployeeService {

	/**
	 * 社員管理リポジトリ
	 */
	private final EmployeeRepository employeeRepository;

	/**
	 * 社員役割連携リポジトリ
	 */
	private final EmployeeExRepository employeeExRepository;

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
	public Employee getEmployeeById(final Long id) {
		return this.employeeRepository.findById(id).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_PROHIBITED);
		});
	}

	@Override
	public Pagination<Employee> getEmployeesByKeyword(final Integer pageNum, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, PgCrowdConstants.DEFAULT_PAGE_SIZE,
				Sort.by(Direction.ASC, "id"));
		final Specification<Employee> status = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("deleteFlg"), PgCrowdConstants.LOGIC_DELETE_INITIAL);
		if (StringUtils.isEmpty(keyword)) {
			final Specification<Employee> specification = Specification.where(status);
			final Page<Employee> pages = this.employeeRepository.findAll(specification, pageRequest);
			return Pagination.of(pages.getContent(), pages.getTotalElements(), pageNum,
					PgCrowdConstants.DEFAULT_PAGE_SIZE);
		}
		final String searchStr = StringUtils.getDetailKeyword(keyword);
		final Specification<Employee> where1 = (root, query, criteriaBuilder) -> criteriaBuilder
				.like(root.get("loginAccount"), searchStr);
		final Specification<Employee> where2 = (root, query, criteriaBuilder) -> criteriaBuilder
				.like(root.get("username"), searchStr);
		final Specification<Employee> where3 = (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"),
				searchStr);
		final Specification<Employee> specification = Specification.where(status)
				.and(Specification.anyOf(where1, where2, where3));
		final Page<Employee> pages = this.employeeRepository.findAll(specification, pageRequest);
		return Pagination.of(pages.getContent(), pages.getTotalElements(), pageNum, PgCrowdConstants.DEFAULT_PAGE_SIZE);
	}

	@Override
	public void removeById(final Long userId) {
		final Employee employee = this.employeeRepository.findById(userId).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_PROHIBITED);
		});
		employee.setDeleteFlg(PgCrowdConstants.LOGIC_DELETE_FLG);
		this.employeeRepository.saveAndFlush(employee);
	}

	@Override
	public void save(final EmployeeDto employeeDto) {
		final Long saibanId = this.employeeRepository.saiban();
		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCryptVersion.$2A, 7);
		final String password = encoder.encode(employeeDto.password());
		final Employee employee = new Employee();
		SecondBeanUtils.copyNullableProperties(employeeDto, employee);
		employee.setId(saibanId);
		employee.setPassword(password);
		employee.setDeleteFlg(PgCrowdConstants.LOGIC_DELETE_INITIAL);
		employee.setCreatedTime(LocalDateTime.now());
		this.employeeRepository.saveAndFlush(employee);
		if (employeeDto.roleId() != null && !Objects.equals(Long.valueOf(0L), employeeDto.roleId())) {
			final EmployeeEx employeeEx = new EmployeeEx();
			employeeEx.setEmployeeId(employee.getId());
			employeeEx.setRoleId(employeeDto.roleId());
			this.employeeExRepository.saveAndFlush(employeeEx);
		}
	}

	@Override
	public void update(final EmployeeDto employeeDto) {
		final Employee employee = this.employeeRepository.findById(employeeDto.id()).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_PROHIBITED);
		});
		if (!Objects.equals(employeeDto.roleId(), 0L)) {
			this.employeeExRepository.findById(employeeDto.id()).ifPresentOrElse(value -> {
				value.setRoleId(employeeDto.roleId());
				this.employeeExRepository.saveAndFlush(value);
			}, () -> {
				final EmployeeEx employeeEx = new EmployeeEx();
				employeeEx.setEmployeeId(employeeDto.id());
				employeeEx.setRoleId(employeeDto.roleId());
				this.employeeExRepository.saveAndFlush(employeeEx);
			});
		}
		SecondBeanUtils.copyNullableProperties(employeeDto, employee);
		if (StringUtils.isNotEmpty(employeeDto.password())) {
			final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCryptVersion.$2A, 7);
			final String encoded = encoder.encode(employeeDto.password());
			employee.setPassword(encoded);
		}
		this.employeeRepository.saveAndFlush(employee);
	}
}
