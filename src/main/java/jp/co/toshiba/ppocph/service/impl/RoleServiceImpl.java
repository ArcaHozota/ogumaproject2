package jp.co.toshiba.ppocph.service.impl;

import java.util.List;
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
import jp.co.toshiba.ppocph.entity.Role;
import jp.co.toshiba.ppocph.exception.PgCrowdException;
import jp.co.toshiba.ppocph.repository.EmployeeExRepository;
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
public class RoleServiceImpl implements IRoleService {

	/**
	 * 役割管理リポジトリ
	 */
	private final RoleRepository roleRepository;

	/**
	 * 社員役割連携リポジトリ
	 */
	private final EmployeeExRepository employeeExRepository;

	@Override
	public boolean check(final String name) {
		final Specification<Role> where1 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("deleteFlg"), PgCrowdConstants.LOGIC_DELETE_INITIAL);
		final Specification<Role> where2 = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"),
				name);
		final Specification<Role> specification = Specification.where(where1).and(where2);
		return this.roleRepository.findOne(specification).isPresent();
	}

	@Override
	public Pagination<Role> getRolesByKeyword(final Integer pageNum, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, PgCrowdConstants.DEFAULT_PAGE_SIZE,
				Sort.by(Direction.ASC, "id"));
		final String searchStr = "%" + keyword + "%";
		final Specification<Role> where1 = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("deleteFlg"), PgCrowdConstants.LOGIC_DELETE_INITIAL);
		final Specification<Role> where2 = StringUtils.isEmpty(keyword) ? null
				: (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), searchStr);
		final Specification<Role> specification = Specification.where(where1).and(where2);
		final Page<Role> pages = this.roleRepository.findAll(specification, pageRequest);
		return Pagination.of(pages.getContent(), pages.getTotalElements(), pageNum, PgCrowdConstants.DEFAULT_PAGE_SIZE);
	}

	@Override
	public ResultDto<String> removeById(final Long roleId) {
		final Specification<EmployeeEx> where = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("roleId"), roleId);
		final Specification<EmployeeEx> specification = Specification.where(where);
		final List<EmployeeEx> list = this.employeeExRepository.findAll(specification);
		if (!list.isEmpty()) {
			return ResultDto.failed(PgCrowdConstants.MESSAGE_STRING_FORBIDDEN);
		}
		final Optional<Role> findById = this.roleRepository.findById(roleId);
		if (findById.isEmpty()) {
			return ResultDto.failed(PgCrowdConstants.MESSAGE_STRING_NOTEXISTS);
		}
		final Role role = findById.get();
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
	public void update(final RoleDto roleDto) throws DataIntegrityViolationException {
		final Role role = this.roleRepository.findById(roleDto.getId()).orElseThrow(() -> {
			throw new PgCrowdException(PgCrowdConstants.MESSAGE_STRING_NOTEXISTS);
		});
		SecondBeanUtils.copyNullableProperties(roleDto, role);
		this.roleRepository.saveAndFlush(role);
	}
}
