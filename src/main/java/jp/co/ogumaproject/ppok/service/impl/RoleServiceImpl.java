package jp.co.ogumaproject.ppok.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
	 * 社員役割リポジトリ
	 */
	private final EmployeeRoleRepository employeeRoleRepository;

	/**
	 * 役割リポジトリ
	 */
	private final RoleRepository roleRepository;

	/**
	 * 役割権限リポジトリ
	 */
	private final RoleAuthRepository roleAuthRepository;

	/**
	 * 権限リポジトリ
	 */
	private final AuthorityRepository authorityRepository;

	@Override
	public ResultDto<String> checkDuplicated(final String name) {
		return this.roleRepository.countByName(name) > 0
				? ResultDto.failed(OgumaProjectConstants.MESSAGE_ROLE_NAME_DUPLICATED)
				: ResultDto.successWithoutData();
	}

	@Override
	public ResultDto<String> doAssignment(final Map<String, List<Long>> paramMap) {
		final Long roleId = paramMap.get("roleIds").get(0);
		final Long[] authIdArray = { 1L, 5L, 9L, 12L };
		final List<Long> authIds = paramMap.get("authIds").stream().filter(a -> !Arrays.asList(authIdArray).contains(a))
				.toList();
		final List<Long> list = this.roleAuthRepository.getListByForeignKey(roleId).stream().map(RoleAuth::getAuthId)
				.toList();
		if (OgumaProjectUtils.isEqual(list, authIds)) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_NOCHANGE);
		}
		this.roleAuthRepository.batchRemoveByForeignKey(roleId);
		final List<RoleAuth> roleAuths = authIds.stream().map(item -> {
			final RoleAuth roleAuth = new RoleAuth();
			roleAuth.setRoleId(roleId);
			roleAuth.setAuthId(item);
			return roleAuth;
		}).toList();
		roleAuths.forEach(roleAuth -> this.roleAuthRepository.saveById(roleAuth));
		return ResultDto.successWithoutData();
	}

	@Override
	public List<Long> getAuthIdsById(final Long id) {
		return this.roleAuthRepository.getListByForeignKey(id).stream().map(RoleAuth::getAuthId).toList();
	}

	@Override
	public List<AuthorityDto> getAuthList() {
		return this.authorityRepository.getList().stream()
				.map(item -> new AuthorityDto(item.getId(), item.getName(), item.getTitle(), item.getCategoryId()))
				.toList();
	}

	@Override
	public RoleDto getRoleById(final Long id) {
		final Role role = this.roleRepository.getOneById(id);
		return new RoleDto(role.getId(), role.getName());
	}

	@Override
	public List<RoleDto> getRolesByEmployeeId(final Long employeeId) {
		final List<Role> roleDtos = new ArrayList<>();
		final List<Role> roles = this.roleRepository.getList();
		if (employeeId == null) {
			final Role role = new Role();
			role.setId(0L);
			role.setName(OgumaProjectConstants.DEFAULT_ROLE_NAME);
			roleDtos.add(role);
		} else {
			final EmployeeRole employeeRole = this.employeeRoleRepository.getOneById(employeeId);
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
		final Long totalRecords = this.roleRepository.countByKeyword(detailKeyword);
		final List<Role> roles = this.roleRepository.pagination(offset, PAGE_SIZE, detailKeyword);
		final List<RoleDto> roleDtos = roles.stream().map(item -> new RoleDto(item.getId(), item.getName())).toList();
		return Pagination.of(roleDtos, totalRecords, pageNum, PAGE_SIZE);
	}

	@Override
	public ResultDto<String> remove(final Long id) {
		final List<EmployeeRole> listByForeignKey = this.employeeRoleRepository.getListByForeignKey(id);
		if (!listByForeignKey.isEmpty()) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_FORBIDDEN);
		}
		final Role role = new Role();
		role.setId(id);
		role.setDelFlg(OgumaProjectConstants.LOGIC_DELETE_FLG);
		this.roleRepository.removeById(role);
		return ResultDto.successWithoutData();
	}

	@Override
	public void save(final RoleDto roleDto) {
		final Role role = new Role();
		SecondBeanUtils.copyNullableProperties(roleDto, role);
		role.setId(SnowflakeUtils.snowflakeId());
		role.setDelFlg(OgumaProjectConstants.LOGIC_DELETE_INITIAL);
		this.roleRepository.saveById(role);
	}

	@Override
	public ResultDto<String> update(final RoleDto roleDto) {
		final Role originalEntity = new Role();
		final Role role = this.roleRepository.getOneById(roleDto.id());
		SecondBeanUtils.copyNullableProperties(role, originalEntity);
		SecondBeanUtils.copyNullableProperties(roleDto, role);
		if (OgumaProjectUtils.isEqual(originalEntity, role)) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_NOCHANGE);
		}
		this.roleRepository.updateById(role);
		return ResultDto.successWithoutData();
	}
}
