package jp.co.toshiba.ppocph.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.config.PgCrowdPasswordEncoder;
import jp.co.toshiba.ppocph.dto.EmployeeDto;
import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.entity.EmployeeRole;
import jp.co.toshiba.ppocph.exception.PgCrowdException;
import jp.co.toshiba.ppocph.repository.EmployeeExRepository;
import jp.co.toshiba.ppocph.repository.EmployeeRepository;
import jp.co.toshiba.ppocph.service.IEmployeeService;
import jp.co.toshiba.ppocph.utils.Pagination;
import jp.co.toshiba.ppocph.utils.ResultDto;
import jp.co.toshiba.ppocph.utils.SecondBeanUtils;
import jp.co.toshiba.ppocph.utils.SnowflakeUtils;
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

	/**
	 * エンコーダ
	 */
	private final PasswordEncoder encoder = new PgCrowdPasswordEncoder();

	/**
	 * 日時フォマーター
	 */
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
	public EmployeeDto getEmployeeById(final Long id) {
		final Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_FATAL_ERROR);
		});
		return new EmployeeDto(employee.getId(), employee.getLoginAccount(), employee.getUsername(),
				employee.getPassword(), employee.getEmail(), this.formatter.format(employee.getDateOfBirth()), null);
	}

	@Override
	public Pagination<EmployeeDto> getEmployeesByKeyword(final Integer pageNum, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, PgCrowdConstants.DEFAULT_PAGE_SIZE,
				Sort.by(Direction.ASC, "id"));
		final Specification<Employee> status = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("deleteFlg"), PgCrowdConstants.LOGIC_DELETE_INITIAL);
		if (StringUtils.isEmpty(keyword)) {
			final Specification<Employee> specification = Specification.where(status);
			final Page<Employee> pages = this.employeeRepository.findAll(specification, pageRequest);
			final List<EmployeeDto> employeeDtos = pages.stream()
					.map(item -> new EmployeeDto(item.getId(), item.getLoginAccount(), item.getUsername(),
							item.getPassword(), item.getEmail(), this.formatter.format(item.getDateOfBirth()), null))
					.toList();
			return Pagination.of(employeeDtos, pages.getTotalElements(), pageNum, PgCrowdConstants.DEFAULT_PAGE_SIZE);
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
		final List<EmployeeDto> employeeDtos = pages.stream()
				.map(item -> new EmployeeDto(item.getId(), item.getLoginAccount(), item.getUsername(),
						item.getPassword(), item.getEmail(), this.formatter.format(item.getDateOfBirth()), null))
				.toList();
		return Pagination.of(employeeDtos, pages.getTotalElements(), pageNum, PgCrowdConstants.DEFAULT_PAGE_SIZE);
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
		final String password = this.encoder.encode(employeeDto.password());
		final Employee employee = new Employee();
		SecondBeanUtils.copyNullableProperties(employeeDto, employee);
		employee.setId(SnowflakeUtils.snowflakeId());
		employee.setPassword(password);
		employee.setDeleteFlg(PgCrowdConstants.LOGIC_DELETE_INITIAL);
		employee.setCreatedTime(LocalDateTime.now());
		employee.setDateOfBirth(LocalDate.parse(employeeDto.dateOfBirth(), this.formatter));
		this.employeeRepository.saveAndFlush(employee);
		if ((employeeDto.roleId() != null) && !Objects.equals(Long.valueOf(0L), employeeDto.roleId())) {
			final EmployeeRole employeeEx = new EmployeeRole();
			employeeEx.setEmployeeId(employee.getId());
			employeeEx.setRoleId(employeeDto.roleId());
			this.employeeExRepository.saveAndFlush(employeeEx);
		}
	}

	@Override
	public ResultDto<String> update(final EmployeeDto employeeDto) {
		final Employee employee = this.employeeRepository.findById(employeeDto.id()).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_FATAL_ERROR);
		});
		final Employee originalEntity = new Employee();
		SecondBeanUtils.copyNullableProperties(employee, originalEntity);
		final EmployeeRole employeeRole = this.employeeExRepository.findById(employeeDto.id())
				.orElseGet(EmployeeRole::new);
		SecondBeanUtils.copyNullableProperties(employeeDto, employee);
		if (StringUtils.isNotEmpty(employeeDto.password())) {
			employee.setPassword(this.encoder.encode(employeeDto.password()));
		}
		employee.setDateOfBirth(LocalDate.parse(employeeDto.dateOfBirth(), this.formatter));
		if (originalEntity.equals(employee) && Objects.equals(employeeRole.getRoleId(), employeeDto.roleId())) {
			return ResultDto.failed(PgCrowdConstants.MESSAGE_STRING_NOCHANGE);
		}
		this.employeeRepository.saveAndFlush(employee);
		return ResultDto.successWithoutData();
	}
}
