package jp.co.ogumaproject.ppog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jp.co.ogumaproject.ppog.common.OgumaProjectConstants;
import jp.co.ogumaproject.ppog.dto.AuthorityDto;
import jp.co.ogumaproject.ppog.dto.RoleDto;
import jp.co.ogumaproject.ppog.entity.EmployeeRole;
import jp.co.ogumaproject.ppog.entity.Role;
import jp.co.ogumaproject.ppog.repository.EmployeeRoleRepository;
import jp.co.ogumaproject.ppog.repository.RoleRepository;
import jp.co.ogumaproject.ppog.service.IRoleService;
import jp.co.ogumaproject.ppog.utils.CommonProjectUtils;
import jp.co.ogumaproject.ppog.utils.Pagination;
import jp.co.ogumaproject.ppog.utils.ResultDto;
import jp.co.ogumaproject.ppog.utils.SecondBeanUtils;
import jp.co.ogumaproject.ppog.utils.SnowflakeUtils;
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

	private static final Integer PAGE_SIZE = OgumaProjectConstants.DEFAULT_PAGE_SIZE;

//	/**
//	 * 社員リポジトリ
//	 */
//	private final EmployeeRepository employeeRepository;

	/**
	 * 社員リポジトリ
	 */
	private final EmployeeRoleRepository employeeRoleRepository;

	/**
	 * 役割リポジトリ
	 */
	private final RoleRepository roleRepository;

	@Override
	public ResultDto<String> checkDuplicated(final String name) {
		return this.roleRepository.countByName(name) > 0
				? ResultDto.failed(OgumaProjectConstants.MESSAGE_ROLE_NAME_DUPLICATED)
				: ResultDto.successWithoutData();
	}

	@Override
	public ResultDto<String> doAssignment(final Map<String, List<Long>> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> getAuthIdsById(final Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AuthorityDto> getAuthList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleDto getRoleById(final Long id) {
		final Role role = this.roleRepository.getOneById(id);
		return new RoleDto(role.getId(), role.getName());
	}

	@Override
	public List<RoleDto> getRolesByEmployeeId(final Long employeeId) {
		final List<Role> roleDtos = new ArrayList<>();
		final EmployeeRole employeeRole = this.employeeRoleRepository.getOneById(employeeId);
		final List<Role> roles = this.roleRepository.getList();
		final List<Role> selectedRole = roles.stream()
				.filter(a -> CommonProjectUtils.isEqual(a.getId(), employeeRole.getRoleId()))
				.collect(Collectors.toList());
		roleDtos.addAll(selectedRole);
		roleDtos.addAll(roles);
		return roleDtos.stream().distinct().map(item -> new RoleDto(item.getId(), item.getName()))
				.collect(Collectors.toList());
	}

	@Override
	public Pagination<RoleDto> getRolesByKeyword(final Integer pageNum, final String keyword) {
		final int offset = (pageNum - 1) * PAGE_SIZE;
		final String detailKeyword = CommonProjectUtils.getDetailKeyword(keyword);
		final Integer totalRecords = this.roleRepository.countByKeyword(detailKeyword);
		final List<Role> roles = this.roleRepository.pagination(offset, PAGE_SIZE, detailKeyword);
		final List<RoleDto> roleDtos = roles.stream().map(item -> new RoleDto(item.getId(), item.getName()))
				.collect(Collectors.toList());
		return Pagination.of(roleDtos, totalRecords, pageNum, PAGE_SIZE);
	}

	@Override
	public ResultDto<String> remove(final Long id) {
		final List<EmployeeRole> listByForeignKey = this.employeeRoleRepository.getListByForeignKey(id);
		if (!listByForeignKey.isEmpty()) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_FORBIDDEN);
		}
		final Role role = this.roleRepository.getOneById(id);
		role.setDelFlg(OgumaProjectConstants.LOGIC_DELETE_FLG);
		this.roleRepository.updateById(role);
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
		if (CommonProjectUtils.isEqual(originalEntity, role)) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_NOCHANGE);
		}
		this.roleRepository.updateById(role);
		return ResultDto.successWithoutData();
	}
}
