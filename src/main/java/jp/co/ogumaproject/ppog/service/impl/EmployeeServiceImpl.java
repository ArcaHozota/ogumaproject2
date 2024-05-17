package jp.co.ogumaproject.ppog.service.impl;

import org.springframework.stereotype.Service;

import jp.co.ogumaproject.ppog.dto.EmployeeDto;
import jp.co.ogumaproject.ppog.service.IEmployeeService;
import jp.co.ogumaproject.ppog.utils.Pagination;
import jp.co.ogumaproject.ppog.utils.ResultDto;
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
	@Override
	public ResultDto<String> checkDuplicated(final String loginAccount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeDto getEmployeeById(final Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pagination<EmployeeDto> getEmployeesByKeyword(final Integer pageNum, final String keyword, final Long userId,
			final String authChkFlag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean register(final EmployeeDto employeeDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(final Long userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean resetPassword(final EmployeeDto employeeDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(final EmployeeDto employeeDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public ResultDto<String> update(final EmployeeDto employeeDto) {
		// TODO Auto-generated method stub
		return null;
	}
}
