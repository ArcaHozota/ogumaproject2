package jp.co.toshiba.ppocph.service.impl;

import jp.co.toshiba.ppocph.entity.Employee;
import jp.co.toshiba.ppocph.service.IEmployeeService;

/**
 * 社員サービス実装クラス
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
public class EmployeeServiceImpl implements IEmployeeService {

	@Override
	public Employee getAdminByLoginAccount(final String account, final String password) {
		// do nothing
		return null;
	}
}
