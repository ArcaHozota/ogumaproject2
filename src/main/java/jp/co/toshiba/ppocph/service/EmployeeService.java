package jp.co.toshiba.ppocph.service;

import jp.co.toshiba.ppocph.entity.Employee;

public interface EmployeeService {

	Employee getAdminByLoginAccount(String account, String password);
}
