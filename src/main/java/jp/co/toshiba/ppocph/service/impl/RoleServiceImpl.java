package jp.co.toshiba.ppocph.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jp.co.toshiba.ppocph.common.PgCrowdConstants;
import jp.co.toshiba.ppocph.dto.RoleDto;
import jp.co.toshiba.ppocph.entity.EmployeeEx;
import jp.co.toshiba.ppocph.entity.PgAuth;
import jp.co.toshiba.ppocph.entity.Role;
import jp.co.toshiba.ppocph.entity.RoleEx;
import jp.co.toshiba.ppocph.exception.PgCrowdException;
import jp.co.toshiba.ppocph.repository.EmployeeExRepository;
import jp.co.toshiba.ppocph.repository.PgAuthRepository;
import jp.co.toshiba.ppocph.repository.RoleExRepository;
import jp.co.toshiba.ppocph.repository.RoleRepository;
import jp.co.toshiba.ppocph.service.IRoleService;
import jp.co.toshiba.ppocph.utils.Pagination;
import jp.co.toshiba.ppocph.utils.ResultDto;
import jp.co.toshiba.ppocph.utils.SecondBeanUtils;
import jp.co.toshiba.ppocph.utils.SnowflakeUtils;
import jp.co.toshiba.ppocph.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 役割サービス実装クラス
 *
 * @author ArkamaHozota
 * @since 4.46
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoleServiceImpl implements IRoleService {

	private static final String DELETE_FLG = "deleteFlg";

	private static final String ROLE_ID = "roleId";

	private static final String ROLE_NAME = "name";

	/**
	 * 役割管理リポジトリ
	 */
	private final RoleRepository roleRepository;

	/**
	 * 権限管理リポジトリ
	 */
	private final PgAuthRepository pgAuthRepository;

	/**
	 * 社員役割連携リポジトリ
	 */
	private final EmployeeExRepository employeeExRepository;

	/**
	 * 役割権限連携リポジトリ
	 */
	private final RoleExRepository roleExRepository;

	@Override
	public ResultDto<String> check(final String name) {
		final Specification<Role> where1 = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(DELETE_FLG),
				PgCrowdConstants.LOGIC_DELETE_INITIAL);
		final Specification<Role> where2 = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(ROLE_NAME),
				name);
		final Specification<Role> specification = Specification.where(where1).and(where2);
		return this.roleRepository.findOne(specification).isPresent()
				? ResultDto.failed(PgCrowdConstants.MESSAGE_ROLE_NAME_DUPLICATED)
				: ResultDto.successWithoutData();
	}

	@Override
	public ResultDto<String> doAssignment(final Map<String, List<Long>> paramMap) {
		final Long roleId = paramMap.get(ROLE_ID).get(0);
		final Specification<RoleEx> where = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(ROLE_ID),
				roleId);
		final Specification<RoleEx> specification = Specification.where(where);
		final List<RoleEx> findAll = this.roleExRepository.findAll(specification);
		this.roleExRepository.deleteAll(findAll);
		final List<Long> authIds = paramMap.get("authIdArray");
		final List<RoleEx> list = authIds.stream().filter(a -> (!Objects.equals(a, 1L) && !Objects.equals(a, 5L)))
				.map(item -> {
					final RoleEx roleEx = new RoleEx();
					roleEx.setAuthId(item);
					roleEx.setRoleId(roleId);
					return roleEx;
				}).toList();
		try {
			this.roleExRepository.saveAllAndFlush(list);
		} catch (final Exception e) {
			return ResultDto.failed(PgCrowdConstants.MESSAGE_STRING_FORBIDDEN2);
		}
		return ResultDto.successWithoutData();
	}

	@Override
	public List<Long> getAuthIdListByRoleId(final Long roleId) {
		final Specification<RoleEx> where = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(ROLE_ID),
				roleId);
		final Specification<RoleEx> specification = Specification.where(where);
		return this.roleExRepository.findAll(specification).stream().map(RoleEx::getAuthId).toList();
	}

	@Override
	public List<PgAuth> getAuthlist() {
		return this.pgAuthRepository.findAll().stream().sorted(Comparator.comparing(PgAuth::getId)).toList();
	}

	@Override
	public List<Role> getEmployeeRolesById(final Long id) {
		final List<Role> secondRoles = new ArrayList<>();
		final Role secondRole = new Role();
		secondRole.setId(0L);
		secondRole.setName(PgCrowdConstants.DEFAULT_ROLE_NAME);
		final Specification<Role> where1 = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(DELETE_FLG),
				PgCrowdConstants.LOGIC_DELETE_INITIAL);
		final Specification<Role> specification1 = Specification.where(where1);
		final List<Role> roles = this.roleRepository.findAll(specification1);
		secondRoles.add(secondRole);
		secondRoles.addAll(roles);
		if (id == null) {
			return secondRoles;
		}
		final Optional<EmployeeEx> roledOptional = this.employeeExRepository.findById(id);
		if (roledOptional.isEmpty()) {
			return secondRoles;
		}
		secondRoles.clear();
		final Long roleId = roledOptional.get().getRoleId();
		final List<Role> selectedRole = roles.stream().filter(a -> Objects.equals(a.getId(), roleId)).toList();
		secondRoles.addAll(selectedRole);
		secondRoles.addAll(roles);
		return secondRoles.stream().distinct().toList();
	}

	@Override
	public Role getRoleById(final Long roleId) {
		return this.roleRepository.findById(roleId).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_NOTEXISTS);
		});
	}

	@Override
	public Pagination<Role> getRolesByKeyword(final Integer pageNum, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, PgCrowdConstants.DEFAULT_PAGE_SIZE,
				Sort.by(Direction.ASC, "id"));
		final Specification<Role> where1 = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(DELETE_FLG),
				PgCrowdConstants.LOGIC_DELETE_INITIAL);
		final Specification<Role> specification = Specification.where(where1);
		if (StringUtils.isEmpty(keyword)) {
			final Page<Role> pages = this.roleRepository.findAll(specification, pageRequest);
			return Pagination.of(pages.getContent(), pages.getTotalElements(), pageNum,
					PgCrowdConstants.DEFAULT_PAGE_SIZE);
		}
		if (StringUtils.isDigital(keyword)) {
			final Page<Role> byIdLike = this.roleRepository.findByIdLike(keyword, PgCrowdConstants.LOGIC_DELETE_INITIAL,
					pageRequest);
			return Pagination.of(byIdLike.getContent(), byIdLike.getTotalElements(), pageNum,
					PgCrowdConstants.DEFAULT_PAGE_SIZE);
		}
		final String searchStr = StringUtils.getDetailKeyword(keyword);
		final Specification<Role> where2 = (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(ROLE_NAME),
				searchStr);
		final Page<Role> pages = this.roleRepository.findAll(specification.and(where2), pageRequest);
		return Pagination.of(pages.getContent(), pages.getTotalElements(), pageNum, PgCrowdConstants.DEFAULT_PAGE_SIZE);
	}

	@Override
	public ResultDto<String> removeById(final Long roleId) {
		final Specification<EmployeeEx> where = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get(ROLE_ID), roleId);
		final Specification<EmployeeEx> specification = Specification.where(where);
		final List<EmployeeEx> list = this.employeeExRepository.findAll(specification);
		if (!list.isEmpty()) {
			return ResultDto.failed(PgCrowdConstants.MESSAGE_STRING_FORBIDDEN);
		}
		final Role role = this.roleRepository.findById(roleId).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_NOTEXISTS);
		});
		role.setDeleteFlg(PgCrowdConstants.LOGIC_DELETE_FLG);
		this.roleRepository.saveAndFlush(role);
		return ResultDto.successWithoutData();
	}

	@Override
	public void save(final RoleDto roleDto) {
		final Role role = new Role();
		SecondBeanUtils.copyNullableProperties(roleDto, role);
		role.setId(SnowflakeUtils.snowflakeId());
		role.setDeleteFlg(PgCrowdConstants.LOGIC_DELETE_INITIAL);
		this.roleRepository.saveAndFlush(role);
	}

	@Override
	public ResultDto<String> update(final RoleDto roleDto) {
		final Role role = this.roleRepository.findById(roleDto.id()).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_NOTEXISTS);
		});
		SecondBeanUtils.copyNullableProperties(roleDto, role);
		try {
			this.roleRepository.saveAndFlush(role);
		} catch (final DataIntegrityViolationException e) {
			return ResultDto.failed(PgCrowdConstants.MESSAGE_ROLE_NAME_DUPLICATED);
		}
		return ResultDto.successWithoutData();
	}
}
