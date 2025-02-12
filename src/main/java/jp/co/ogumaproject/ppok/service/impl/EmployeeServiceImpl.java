package jp.co.ogumaproject.ppok.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jdbi.v3.core.Jdbi;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.ogumaproject.ppok.common.OgumaProjectConstants;
import jp.co.ogumaproject.ppok.config.OgumaPasswordEncoder;
import jp.co.ogumaproject.ppok.dto.EmployeeDto;
import jp.co.ogumaproject.ppok.entity.Employee;
import jp.co.ogumaproject.ppok.entity.EmployeeRole;
import jp.co.ogumaproject.ppok.entity.Role;
import jp.co.ogumaproject.ppok.repository.EmployeeRepository;
import jp.co.ogumaproject.ppok.repository.EmployeeRoleRepository;
import jp.co.ogumaproject.ppok.repository.RoleRepository;
import jp.co.ogumaproject.ppok.service.IEmployeeService;
import jp.co.ogumaproject.ppok.utils.OgumaProjectUtils;
import jp.co.ogumaproject.ppok.utils.Pagination;
import jp.co.ogumaproject.ppok.utils.ResultDto;
import jp.co.ogumaproject.ppok.utils.SecondBeanUtils;
import jp.co.ogumaproject.ppok.utils.SnowflakeUtils;
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
	 * ページサイズ
	 */
	private static final Integer PAGE_SIZE = OgumaProjectConstants.DEFAULT_PAGE_SIZE;

	/**
	 * 日時フォマーター
	 */
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * エンコーダ
	 */
	private static final PasswordEncoder ENCODER = new OgumaPasswordEncoder();

	/**
	 * エンコーダ
	 */
	private static final Random RANDOM = new Random();

	/**
	 * 共通リポジトリ
	 */
	private final Jdbi jdbi;

	@Override
	public ResultDto<String> checkDuplicated(final String loginAccount) {
		return this.jdbi.onDemand(EmployeeRepository.class).countByName(loginAccount) > 0
				? ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_DUPLICATED)
				: ResultDto.successWithoutData();
	}

	@Override
	public EmployeeDto getEmployeeById(final Long id) {
		final Employee employee = this.jdbi.onDemand(EmployeeRepository.class).getOneById(id);
		return new EmployeeDto(employee.getId(), employee.getLoginAccount(), employee.getUsername(),
				OgumaProjectConstants.DEFAULT_ROLE_NAME, employee.getEmail(),
				FORMATTER.format(employee.getDateOfBirth()), null);
	}

	@Override
	public Pagination<EmployeeDto> getEmployeesByKeyword(final Integer pageNum, final String keyword, final Long userId,
			final String authChkFlag) {
		if (Boolean.FALSE.equals(Boolean.valueOf(authChkFlag))) {
			final Employee employee = this.jdbi.onDemand(EmployeeRepository.class).getOneById(userId);
			final EmployeeDto employeeDto = new EmployeeDto(employee.getId(), employee.getLoginAccount(),
					employee.getUsername(), employee.getPassword(), employee.getEmail(),
					FORMATTER.format(employee.getDateOfBirth()), null);
			final List<EmployeeDto> employeeDtos = new ArrayList<>();
			employeeDtos.add(employeeDto);
			return Pagination.of(employeeDtos, employeeDtos.size(), pageNum, PAGE_SIZE);
		}
		final int offset = (pageNum - 1) * PAGE_SIZE;
		final String detailKeyword = OgumaProjectUtils.getDetailKeyword(keyword);
		final Long totalRecords = this.jdbi.onDemand(EmployeeRepository.class).countByKeyword(detailKeyword);
		final List<Employee> employees = this.jdbi.onDemand(EmployeeRepository.class).pagination(offset, PAGE_SIZE,
				detailKeyword);
		final List<EmployeeDto> employeeDtos = employees.stream()
				.map(item -> new EmployeeDto(item.getId(), item.getLoginAccount(), item.getUsername(),
						item.getPassword(), item.getEmail(), FORMATTER.format(item.getDateOfBirth()), null))
				.toList();
		return Pagination.of(employeeDtos, totalRecords, pageNum, PAGE_SIZE);
	}

	/**
	 * デフォルトのアカウントを取得する
	 *
	 * @return String
	 */
	private String getRandomStr() {
		final String stry = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final char[] cr1 = stry.toCharArray();
		final char[] cr2 = stry.toLowerCase().toCharArray();
		final StringBuilder builder = new StringBuilder();
		builder.append(cr1[RANDOM.nextInt(cr1.length)]);
		for (int i = 0; i < 7; i++) {
			builder.append(cr2[RANDOM.nextInt(cr2.length)]);
		}
		return builder.toString();
	}

	@Override
	public Boolean register(final EmployeeDto employeeDto) {
		final Long emailCount = this.jdbi.onDemand(EmployeeRepository.class).countByKeyword(employeeDto.email());
		if (emailCount > 0) {
			return Boolean.FALSE;
		}
		final Employee employee = new Employee();
		SecondBeanUtils.copyNullableProperties(employeeDto, employee);
		employee.setId(SnowflakeUtils.snowflakeId());
		employee.setLoginAccount(this.getRandomStr());
		employee.setPassword(ENCODER.encode(employeeDto.password()));
		employee.setUsername(employeeDto.email());
		employee.setDateOfBirth(LocalDate.parse(employeeDto.dateOfBirth(), FORMATTER));
		employee.setCreatedTime(LocalDateTime.now());
		employee.setDelFlg(OgumaProjectConstants.LOGIC_DELETE_INITIAL);
		final Role role = this.jdbi.onDemand(RoleRepository.class).getOneByName("正社員");
		final EmployeeRole employeeRole = new EmployeeRole();
		employeeRole.setEmployeeId(employee.getId());
		employeeRole.setRoleId(role.getId());
		this.jdbi.onDemand(EmployeeRoleRepository.class).insertById(employeeRole);
		this.jdbi.onDemand(EmployeeRepository.class).insertById(employee);
		return Boolean.TRUE;
	}

	@Override
	public void remove(final Long userId) {
		final Employee employee = new Employee();
		employee.setId(userId);
		employee.setDelFlg(OgumaProjectConstants.LOGIC_DELETE_FLG);
		this.jdbi.onDemand(EmployeeRepository.class).removeById(employee);
	}

	@Override
	public Boolean resetPassword(final EmployeeDto employeeDto) {
		final Employee aEntity = new Employee();
		SecondBeanUtils.copyNullableProperties(employeeDto, aEntity);
		final Employee employee = this.jdbi.onDemand(EmployeeRepository.class).getOneByEntity(aEntity);
		if (employee == null) {
			return Boolean.FALSE;
		}
		employee.setPassword(ENCODER.encode(OgumaProjectConstants.DEFAULT_PASSWORD));
		this.jdbi.onDemand(EmployeeRepository.class).updateById(employee);
		return Boolean.TRUE;
	}

	@Override
	public void save(final EmployeeDto employeeDto) {
		final Employee employee = new Employee();
		SecondBeanUtils.copyNullableProperties(employeeDto, employee);
		employee.setId(SnowflakeUtils.snowflakeId());
		employee.setLoginAccount(this.getRandomStr());
		employee.setPassword(ENCODER.encode(employeeDto.password()));
		employee.setUsername(employeeDto.email());
		employee.setDateOfBirth(LocalDate.parse(employeeDto.dateOfBirth(), FORMATTER));
		employee.setCreatedTime(LocalDateTime.now());
		employee.setDelFlg(OgumaProjectConstants.LOGIC_DELETE_INITIAL);
		if (employeeDto.roleId() != null) {
			final EmployeeRole employeeRole = new EmployeeRole();
			employeeRole.setEmployeeId(employee.getId());
			employeeRole.setRoleId(employeeDto.roleId());
			this.jdbi.onDemand(EmployeeRoleRepository.class).insertById(employeeRole);
		}
		this.jdbi.onDemand(EmployeeRepository.class).insertById(employee);
	}

	@Override
	public ResultDto<String> update(final EmployeeDto employeeDto) {
		final Employee originalEntity = new Employee();
		final Employee employee = this.jdbi.onDemand(EmployeeRepository.class).getOneById(employeeDto.id());
		SecondBeanUtils.copyNullableProperties(employee, originalEntity);
		final String password = employeeDto.password();
		final String originalPass = employee.getPassword();
		boolean passwordMatch = false;
		if (OgumaProjectUtils.isNotEmpty(password)) {
			passwordMatch = ENCODER.matches(password, originalPass);
		} else {
			passwordMatch = true;
		}
		SecondBeanUtils.copyNullableProperties(employeeDto, employee);
		employee.setPassword(OgumaProjectUtils.EMPTY_STRING);
		originalEntity.setPassword(OgumaProjectUtils.EMPTY_STRING);
		final EmployeeRole employeeRole = this.jdbi.onDemand(EmployeeRoleRepository.class).getOneById(employee.getId());
		if (OgumaProjectUtils.isEqual(originalEntity, employee) && passwordMatch) {
			if (((employeeRole == null) && (employeeDto.roleId() == 0)) || ((employeeRole != null)
					&& OgumaProjectUtils.isEqual(employeeDto.roleId(), employeeRole.getRoleId()))) {
				return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_NOCHANGE);
			} else {
				final EmployeeRole employeeRole2 = new EmployeeRole();
				employeeRole2.setEmployeeId(employee.getId());
				employeeRole2.setRoleId(employeeDto.roleId());
				this.jdbi.onDemand(EmployeeRoleRepository.class).updateById(employeeRole2);
			}
		}
		if (!passwordMatch) {
			employee.setPassword(ENCODER.encode(password));
		} else {
			employee.setPassword(originalPass);
		}
		employee.setDateOfBirth(LocalDate.parse(employeeDto.dateOfBirth(), FORMATTER));
		this.jdbi.onDemand(EmployeeRepository.class).updateById(employee);
		return ResultDto.successWithoutData();
	}
}
