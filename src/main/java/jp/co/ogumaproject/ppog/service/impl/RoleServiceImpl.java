package jp.co.ogumaproject.ppog.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jp.co.ogumaproject.ppog.dto.AuthorityDto;
import jp.co.ogumaproject.ppog.dto.RoleDto;
import jp.co.ogumaproject.ppog.service.IRoleService;
import jp.co.ogumaproject.ppog.utils.Pagination;
import jp.co.ogumaproject.ppog.utils.ResultDto;
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
	@Override
	public ResultDto<String> checkDuplicated(final String name) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleDto> getRolesByEmployeeId(final Long employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pagination<RoleDto> getRolesByKeyword(final Integer pageNum, final String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultDto<String> remove(final Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(final RoleDto roleDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public ResultDto<String> update(final RoleDto roleDto) {
		// TODO Auto-generated method stub
		return null;
	}
}
