package jp.co.ogumaproject.ppok.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import jp.co.ogumaproject.ppok.common.OgumaProjectConstants;
import jp.co.ogumaproject.ppok.dto.AuthorityDto;
import jp.co.ogumaproject.ppok.dto.RoleDto;
import jp.co.ogumaproject.ppok.entity.EmployeeRole;
import jp.co.ogumaproject.ppok.entity.Role;
import jp.co.ogumaproject.ppok.entity.RoleAuth;
import jp.co.ogumaproject.ppok.repository.AuthorityRepository;
import jp.co.ogumaproject.ppok.repository.EmployeeRoleRepository;
import jp.co.ogumaproject.ppok.repository.RoleAuthRepository;
import jp.co.ogumaproject.ppok.repository.RoleRepository;
import jp.co.ogumaproject.ppok.service.IRoleService;
import jp.co.ogumaproject.ppok.utils.OgumaProjectUtils;
import jp.co.ogumaproject.ppok.utils.Pagination;
import jp.co.ogumaproject.ppok.utils.ResultDto;
import jp.co.ogumaproject.ppok.utils.SecondBeanUtils;
import jp.co.ogumaproject.ppok.utils.SnowflakeUtils;
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

	/**
	 * ページサイズ
	 */
	private static final Integer PAGE_SIZE = OgumaProjectConstants.DEFAULT_PAGE_SIZE;

	/**
	 * 共通リポジトリ
	 */
	private final Jdbi jdbi;

	@Override
	public ResultDto<String> checkDuplicated(final String name) {
		return this.jdbi.onDemand(RoleRepository.class).countByName(name) > 0
				? ResultDto.failed(OgumaProjectConstants.MESSAGE_ROLE_NAME_DUPLICATED)
				: ResultDto.successWithoutData();
	}

	@Override
	public ResultDto<String> doAssignment(final Map<String, List<Long>> paramMap) {
		final Long roleId = paramMap.get("roleIds").get(0);
		final Long[] authIdArray = { 1L, 5L, 9L, 12L };
		final List<Long> authIds = paramMap.get("authIds").stream().filter(a -> !Arrays.asList(authIdArray).contains(a))
				.toList();
		final List<Long> list = this.jdbi.onDemand(RoleAuthRepository.class).getListByForeignKey(roleId).stream()
				.map(RoleAuth::getAuthId).toList();
		if (OgumaProjectUtils.isEqual(list, authIds)) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_NOCHANGE);
		}
		this.jdbi.onDemand(RoleAuthRepository.class).batchRemoveByForeignKey(roleId);
		final List<RoleAuth> roleAuths = authIds.stream().map(item -> {
			final RoleAuth roleAuth = new RoleAuth();
			roleAuth.setRoleId(roleId);
			roleAuth.setAuthId(item);
			return roleAuth;
		}).toList();
		roleAuths.forEach(roleAuth -> this.jdbi.onDemand(RoleAuthRepository.class).insertById(roleAuth));
		return ResultDto.successWithoutData();
	}

	@Override
	public List<Long> getAuthIdsById(final Long id) {
		return this.jdbi.onDemand(RoleAuthRepository.class).getListByForeignKey(id).stream().map(RoleAuth::getAuthId)
				.toList();
	}

	@Override
	public List<AuthorityDto> getAuthList() {
		return this.jdbi.onDemand(AuthorityRepository.class).getList().stream()
				.map(item -> new AuthorityDto(item.getId(), item.getName(), item.getTitle(), item.getCategoryId()))
				.toList();
	}

	@Override
	public RoleDto getRoleById(final Long id) {
		final Role role = this.jdbi.onDemand(RoleRepository.class).getOneById(id);
		return new RoleDto(role.getId(), role.getName());
	}

	@Override
	public List<RoleDto> getRolesByEmployeeId(final Long employeeId) {
		final List<Role> roleDtos = new ArrayList<>();
		final List<Role> roles = this.jdbi.onDemand(RoleRepository.class).getList();
		if (employeeId == null) {
			final Role role = new Role();
			role.setId(0L);
			role.setName(OgumaProjectConstants.DEFAULT_ROLE_NAME);
			roleDtos.add(role);
		} else {
			final EmployeeRole employeeRole = this.jdbi.onDemand(EmployeeRoleRepository.class).getOneById(employeeId);
			if (employeeRole == null) {
				final Role role = new Role();
				role.setId(0L);
				role.setName(OgumaProjectConstants.DEFAULT_ROLE_NAME);
				roleDtos.add(role);
			} else {
				final Role selectedRole = roles.stream()
						.filter(a -> OgumaProjectUtils.isEqual(a.getId(), employeeRole.getRoleId())).findFirst().get();
				roleDtos.add(selectedRole);
			}
		}
		roleDtos.addAll(roles);
		return roleDtos.stream().distinct().map(item -> new RoleDto(item.getId(), item.getName())).toList();
	}

	@Override
	public Pagination<RoleDto> getRolesByKeyword(final Integer pageNum, final String keyword) {
		final int offset = (pageNum - 1) * PAGE_SIZE;
		final String detailKeyword = OgumaProjectUtils.getDetailKeyword(keyword);
		final Long totalRecords = this.jdbi.onDemand(RoleRepository.class).countByKeyword(detailKeyword);
		final List<Role> roles = this.jdbi.onDemand(RoleRepository.class).pagination(offset, PAGE_SIZE, detailKeyword);
		final List<RoleDto> roleDtos = roles.stream().map(item -> new RoleDto(item.getId(), item.getName())).toList();
		return Pagination.of(roleDtos, totalRecords, pageNum, PAGE_SIZE);
	}

	@Override
	public ResultDto<String> remove(final Long id) {
		final List<EmployeeRole> listByForeignKey = this.jdbi.onDemand(EmployeeRoleRepository.class)
				.getListByForeignKey(id);
		if (!listByForeignKey.isEmpty()) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_FORBIDDEN);
		}
		final Role role = new Role();
		role.setId(id);
		role.setDelFlg(OgumaProjectConstants.LOGIC_DELETE_FLG);
		this.jdbi.onDemand(RoleRepository.class).removeById(role);
		return ResultDto.successWithoutData(OgumaProjectConstants.MESSAGE_STRING_DELETED);
	}

	@Override
	public void save(final RoleDto roleDto) {
		final Role role = new Role();
		SecondBeanUtils.copyNullableProperties(roleDto, role);
		role.setId(SnowflakeUtils.snowflakeId());
		role.setDelFlg(OgumaProjectConstants.LOGIC_DELETE_INITIAL);
		this.jdbi.onDemand(RoleRepository.class).insertById(role);
	}

	@Override
	public ResultDto<String> update(final RoleDto roleDto) {
		final Role originalEntity = new Role();
		final Role role = this.jdbi.onDemand(RoleRepository.class).getOneById(roleDto.id());
		SecondBeanUtils.copyNullableProperties(role, originalEntity);
		SecondBeanUtils.copyNullableProperties(roleDto, role);
		if (OgumaProjectUtils.isEqual(originalEntity, role)) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_NOCHANGE);
		}
		this.jdbi.onDemand(RoleRepository.class).updateById(role);
		return ResultDto.successWithoutData();
	}
}
