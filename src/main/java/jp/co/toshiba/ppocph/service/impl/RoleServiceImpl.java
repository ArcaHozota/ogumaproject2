package jp.co.toshiba.ppocph.service.impl;

import static jp.co.toshiba.ppocph.jooq.Tables.AUTHORITIES;
import static jp.co.toshiba.ppocph.jooq.Tables.EMPLOYEE_ROLE;
import static jp.co.toshiba.ppocph.jooq.Tables.ROLES;
import static jp.co.toshiba.ppocph.jooq.Tables.ROLE_AUTH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jp.co.toshiba.ppocph.common.OgumaProjectConstants;
import jp.co.toshiba.ppocph.dto.AuthorityDto;
import jp.co.toshiba.ppocph.dto.RoleDto;
import jp.co.toshiba.ppocph.jooq.tables.records.AuthoritiesRecord;
import jp.co.toshiba.ppocph.jooq.tables.records.EmployeeRoleRecord;
import jp.co.toshiba.ppocph.jooq.tables.records.RoleAuthRecord;
import jp.co.toshiba.ppocph.jooq.tables.records.RolesRecord;
import jp.co.toshiba.ppocph.service.IRoleService;
import jp.co.toshiba.ppocph.utils.CommonProjectUtils;
import jp.co.toshiba.ppocph.utils.Pagination;
import jp.co.toshiba.ppocph.utils.ResultDto;
import jp.co.toshiba.ppocph.utils.SnowflakeUtils;
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
	 * 共通リポジトリ
	 */
	private final DSLContext dslContext;

	@Override
	public ResultDto<String> checkDuplicated(final String name) {
		final Integer roleNameCount = this.dslContext.selectCount().from(ROLES).where(ROLES.NAME.eq(name)).fetchSingle()
				.into(Integer.class);
		return roleNameCount > 0 ? ResultDto.failed(OgumaProjectConstants.MESSAGE_ROLE_NAME_DUPLICATED)
				: ResultDto.successWithoutData();
	}

	@Override
	public ResultDto<String> doAssignment(final Map<String, List<Long>> paramMap) {
		final Long[] idArray = { 1L, 5L, 9L, 12L };
		final Long roleId = paramMap.get("roleId").get(0);
		final List<RoleAuthRecord> roleAuthRecords = this.dslContext.selectFrom(ROLE_AUTH)
				.where(ROLE_AUTH.ROLE_ID.eq(roleId)).fetchInto(RoleAuthRecord.class);
		final List<Long> remnantAuthIds = roleAuthRecords.stream().map(RoleAuthRecord::getAuthId).sorted().toList();
		final List<Long> authIds = paramMap.get("authIdArray").stream().filter(a -> !Arrays.asList(idArray).contains(a))
				.sorted().toList();
		if (CommonProjectUtils.isEqual(remnantAuthIds, authIds)) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_NOCHANGE);
		}
		this.dslContext.deleteFrom(ROLE_AUTH).where(ROLE_AUTH.ROLE_ID.eq(roleId)).execute();
		final List<RoleAuthRecord> newRoleAuthRecords = authIds.stream().map(item -> {
			final RoleAuthRecord roleAuthRecord = this.dslContext.newRecord(ROLE_AUTH);
			roleAuthRecord.setAuthId(item);
			roleAuthRecord.setRoleId(roleId);
			return roleAuthRecord;
		}).toList();
		try {
			this.dslContext.insertInto(ROLE_AUTH).values(newRoleAuthRecords).execute();
		} catch (final Exception e) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_FORBIDDEN2);
		}
		return ResultDto.successWithoutData();
	}

	@Override
	public List<Long> getAuthIdsById(final Long id) {
		final List<RoleAuthRecord> roleAuthRecords = this.dslContext.selectFrom(ROLE_AUTH)
				.where(ROLE_AUTH.ROLE_ID.eq(id)).fetchInto(RoleAuthRecord.class);
		return roleAuthRecords.stream().map(RoleAuthRecord::getAuthId).toList();
	}

	@Override
	public List<AuthorityDto> getAuthList() {
		final List<AuthoritiesRecord> authoritiesRecords = this.dslContext.selectFrom(AUTHORITIES)
				.fetchInto(AuthoritiesRecord.class);
		return authoritiesRecords.stream()
				.map(item -> new AuthorityDto(item.getId(), item.getName(), item.getTitle(), item.getCategoryId()))
				.toList();
	}

	@Override
	public RoleDto getRoleById(final Long id) {
		final RolesRecord rolesRecord = this.dslContext.selectFrom(ROLES)
				.where(ROLES.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL)).and(ROLES.ID.eq(id))
				.fetchSingle();
		return new RoleDto(rolesRecord.getId(), rolesRecord.getName());
	}

	@Override
	public List<RoleDto> getRolesByEmployeeId(final Long employeeId) {
		final List<RoleDto> roleDtos = new ArrayList<>();
		final List<RolesRecord> rolesRecords = this.dslContext.selectFrom(ROLES)
				.where(ROLES.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL)).fetchInto(RolesRecord.class);
		final List<RoleDto> roleDtos1 = rolesRecords.stream().map(item -> new RoleDto(item.getId(), item.getName()))
				.distinct().sorted(Comparator.comparing(RoleDto::id)).toList();
		if (employeeId == null) {
			final RoleDto roleDto = new RoleDto(0L, OgumaProjectConstants.DEFAULT_ROLE_NAME);
			roleDtos.add(roleDto);
			roleDtos.addAll(roleDtos1);
			return roleDtos;
		}
		final EmployeeRoleRecord employeeRoleRecord = this.dslContext.selectFrom(EMPLOYEE_ROLE)
				.where(EMPLOYEE_ROLE.EMPLOYEE_ID.eq(employeeId)).fetchSingle();
		final List<RoleDto> list = roleDtos.stream()
				.filter(a -> CommonProjectUtils.isEqual(a.id(), employeeRoleRecord.getRoleId())).toList();
		roleDtos.addAll(list);
		roleDtos.addAll(roleDtos1);
		return roleDtos.stream().distinct().toList();
	}

	@Override
	public Pagination<RoleDto> getRolesByKeyword(final Integer pageNum, final String keyword) {
		final int offset = (pageNum - 1) * OgumaProjectConstants.DEFAULT_PAGE_SIZE;
		if (CommonProjectUtils.isEmpty(keyword)) {
			final Integer totalRecords = this.dslContext.selectCount().from(ROLES)
					.where(ROLES.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL)).fetchSingle()
					.into(Integer.class);
			final List<RolesRecord> rolesRecords = this.dslContext.selectFrom(ROLES)
					.where(ROLES.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL)).orderBy(ROLES.ID.asc())
					.limit(OgumaProjectConstants.DEFAULT_PAGE_SIZE).offset(offset).fetchInto(RolesRecord.class);
			final List<RoleDto> roleDtos = rolesRecords.stream().map(item -> new RoleDto(item.getId(), item.getName()))
					.toList();
			return Pagination.of(roleDtos, totalRecords, pageNum, OgumaProjectConstants.DEFAULT_PAGE_SIZE);
		}
		if (CommonProjectUtils.isDigital(keyword)) {
			final Integer totalRecords = this.dslContext.selectCount().from(ROLES)
					.where(ROLES.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL)).and(ROLES.ID.like(keyword))
					.fetchSingle().into(Integer.class);
			final List<RolesRecord> rolesRecords = this.dslContext.selectFrom(ROLES)
					.where(ROLES.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL)).and(ROLES.ID.like(keyword))
					.orderBy(ROLES.ID.asc()).limit(OgumaProjectConstants.DEFAULT_PAGE_SIZE).offset(offset)
					.fetchInto(RolesRecord.class);
			final List<RoleDto> roleDtos = rolesRecords.stream().map(item -> new RoleDto(item.getId(), item.getName()))
					.toList();
			return Pagination.of(roleDtos, totalRecords, pageNum, OgumaProjectConstants.DEFAULT_PAGE_SIZE);
		}
		final String searchStr = CommonProjectUtils.getDetailKeyword(keyword);
		final Integer totalRecords = this.dslContext.selectCount().from(ROLES)
				.where(ROLES.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL)).and(ROLES.NAME.like(searchStr))
				.fetchSingle().into(Integer.class);
		final List<RolesRecord> rolesRecords = this.dslContext.selectFrom(ROLES)
				.where(ROLES.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL)).and(ROLES.NAME.like(searchStr))
				.orderBy(ROLES.ID.asc()).limit(OgumaProjectConstants.DEFAULT_PAGE_SIZE).offset(offset)
				.fetchInto(RolesRecord.class);
		final List<RoleDto> roleDtos = rolesRecords.stream().map(item -> new RoleDto(item.getId(), item.getName()))
				.toList();
		return Pagination.of(roleDtos, totalRecords, pageNum, OgumaProjectConstants.DEFAULT_PAGE_SIZE);
	}

	@Override
	public ResultDto<String> remove(final Long id) {
		final EmployeeRoleRecord roleRecord = this.dslContext.selectFrom(EMPLOYEE_ROLE)
				.where(EMPLOYEE_ROLE.ROLE_ID.eq(id)).fetchOne();
		if (roleRecord != null) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_FORBIDDEN);
		}
		this.dslContext.update(ROLES).set(ROLES.DELETE_FLG, OgumaProjectConstants.LOGIC_DELETE_FLG)
				.where(ROLES.ID.eq(id)).execute();
		this.dslContext.deleteFrom(ROLE_AUTH).where(ROLE_AUTH.ROLE_ID.eq(id)).execute();
		return ResultDto.successWithoutData();
	}

	@Override
	public void save(final RoleDto roleDto) {
		final RolesRecord rolesRecord = this.dslContext.newRecord(ROLES);
		rolesRecord.setId(SnowflakeUtils.snowflakeId());
		rolesRecord.setName(roleDto.name());
		rolesRecord.setDeleteFlg(OgumaProjectConstants.LOGIC_DELETE_INITIAL);
		rolesRecord.insert();
	}

	@Override
	public ResultDto<String> update(final RoleDto roleDto) {
		final RolesRecord rolesRecord = this.dslContext.selectFrom(ROLES)
				.where(ROLES.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL)).and(ROLES.ID.eq(roleDto.id()))
				.fetchSingle();
		final RoleDto aRoleDto = new RoleDto(rolesRecord.getId(), rolesRecord.getName());
		if (CommonProjectUtils.isEqual(aRoleDto, roleDto)) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_STRING_NOCHANGE);
		}
		rolesRecord.setName(roleDto.name());
		try {
			this.dslContext.update(ROLES).set(rolesRecord)
					.where(ROLES.DELETE_FLG.eq(OgumaProjectConstants.LOGIC_DELETE_INITIAL))
					.and(ROLES.ID.eq(rolesRecord.getId())).execute();
		} catch (final DataIntegrityViolationException e) {
			return ResultDto.failed(OgumaProjectConstants.MESSAGE_ROLE_NAME_DUPLICATED);
		}
		return ResultDto.successWithoutData();
	}
}
