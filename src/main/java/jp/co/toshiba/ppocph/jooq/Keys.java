/*
 * This file is generated by jOOQ.
 */
package jp.co.toshiba.ppocph.jooq;


import jp.co.toshiba.ppocph.jooq.tables.Authorities;
import jp.co.toshiba.ppocph.jooq.tables.Cities;
import jp.co.toshiba.ppocph.jooq.tables.Districts;
import jp.co.toshiba.ppocph.jooq.tables.EmployeeRole;
import jp.co.toshiba.ppocph.jooq.tables.Employees;
import jp.co.toshiba.ppocph.jooq.tables.RoleAuth;
import jp.co.toshiba.ppocph.jooq.tables.Roles;
import jp.co.toshiba.ppocph.jooq.tables.records.AuthoritiesRecord;
import jp.co.toshiba.ppocph.jooq.tables.records.CitiesRecord;
import jp.co.toshiba.ppocph.jooq.tables.records.DistrictsRecord;
import jp.co.toshiba.ppocph.jooq.tables.records.EmployeeRoleRecord;
import jp.co.toshiba.ppocph.jooq.tables.records.EmployeesRecord;
import jp.co.toshiba.ppocph.jooq.tables.records.RoleAuthRecord;
import jp.co.toshiba.ppocph.jooq.tables.records.RolesRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AuthoritiesRecord> AUTH_NAME_UNIQUE = Internal.createUniqueKey(Authorities.AUTHORITIES, DSL.name("auth_name_unique"), new TableField[] { Authorities.AUTHORITIES.NAME }, true);
    public static final UniqueKey<AuthoritiesRecord> AUTH_PKEY = Internal.createUniqueKey(Authorities.AUTHORITIES, DSL.name("auth_pkey"), new TableField[] { Authorities.AUTHORITIES.ID }, true);
    public static final UniqueKey<AuthoritiesRecord> AUTH_TITLE_UNIQUE = Internal.createUniqueKey(Authorities.AUTHORITIES, DSL.name("auth_title_unique"), new TableField[] { Authorities.AUTHORITIES.TITLE }, true);
    public static final UniqueKey<CitiesRecord> CITIES_PKEY = Internal.createUniqueKey(Cities.CITIES, DSL.name("cities_pkey"), new TableField[] { Cities.CITIES.ID }, true);
    public static final UniqueKey<DistrictsRecord> DISTRICTS_PKEY = Internal.createUniqueKey(Districts.DISTRICTS, DSL.name("districts_pkey"), new TableField[] { Districts.DISTRICTS.ID }, true);
    public static final UniqueKey<EmployeeRoleRecord> EMPLOYEE_ROLE_PKEY = Internal.createUniqueKey(EmployeeRole.EMPLOYEE_ROLE, DSL.name("employee_role_pkey"), new TableField[] { EmployeeRole.EMPLOYEE_ROLE.EMPLOYEE_ID }, true);
    public static final UniqueKey<EmployeesRecord> EMAIL_UNIQUE = Internal.createUniqueKey(Employees.EMPLOYEES, DSL.name("email_unique"), new TableField[] { Employees.EMPLOYEES.EMAIL }, true);
    public static final UniqueKey<EmployeesRecord> EMPLOYEE_PKEY = Internal.createUniqueKey(Employees.EMPLOYEES, DSL.name("employee_pkey"), new TableField[] { Employees.EMPLOYEES.ID }, true);
    public static final UniqueKey<EmployeesRecord> LOGIN_ACCOUNT_UNIQUE = Internal.createUniqueKey(Employees.EMPLOYEES, DSL.name("login_account_unique"), new TableField[] { Employees.EMPLOYEES.LOGIN_ACCOUNT }, true);
    public static final UniqueKey<RoleAuthRecord> ROLE_AUTH_PKEY = Internal.createUniqueKey(RoleAuth.ROLE_AUTH, DSL.name("role_auth_pkey"), new TableField[] { RoleAuth.ROLE_AUTH.ROLE_ID, RoleAuth.ROLE_AUTH.AUTH_ID }, true);
    public static final UniqueKey<RolesRecord> ROLE_NAME_UNIQUE = Internal.createUniqueKey(Roles.ROLES, DSL.name("role_name_unique"), new TableField[] { Roles.ROLES.NAME }, true);
    public static final UniqueKey<RolesRecord> ROLE_PKEY = Internal.createUniqueKey(Roles.ROLES, DSL.name("role_pkey"), new TableField[] { Roles.ROLES.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<CitiesRecord, DistrictsRecord> CITIES__FK_CITIES_DISTRICTS = Internal.createForeignKey(Cities.CITIES, DSL.name("fk_cities_districts"), new TableField[] { Cities.CITIES.DISTRICT_ID }, Keys.DISTRICTS_PKEY, new TableField[] { Districts.DISTRICTS.ID }, true);
    public static final ForeignKey<EmployeeRoleRecord, EmployeesRecord> EMPLOYEE_ROLE__FK_ER_EMPLOYEES = Internal.createForeignKey(EmployeeRole.EMPLOYEE_ROLE, DSL.name("fk_er_employees"), new TableField[] { EmployeeRole.EMPLOYEE_ROLE.EMPLOYEE_ID }, Keys.EMPLOYEE_PKEY, new TableField[] { Employees.EMPLOYEES.ID }, true);
    public static final ForeignKey<EmployeeRoleRecord, RolesRecord> EMPLOYEE_ROLE__FK_ER_ROLES = Internal.createForeignKey(EmployeeRole.EMPLOYEE_ROLE, DSL.name("fk_er_roles"), new TableField[] { EmployeeRole.EMPLOYEE_ROLE.ROLE_ID }, Keys.ROLE_PKEY, new TableField[] { Roles.ROLES.ID }, true);
    public static final ForeignKey<RoleAuthRecord, RolesRecord> ROLE_AUTH__FK_RA_AUTHORITIES = Internal.createForeignKey(RoleAuth.ROLE_AUTH, DSL.name("fk_ra_authorities"), new TableField[] { RoleAuth.ROLE_AUTH.ROLE_ID }, Keys.ROLE_PKEY, new TableField[] { Roles.ROLES.ID }, true);
    public static final ForeignKey<RoleAuthRecord, AuthoritiesRecord> ROLE_AUTH__FK_RA_ROLES = Internal.createForeignKey(RoleAuth.ROLE_AUTH, DSL.name("fk_ra_roles"), new TableField[] { RoleAuth.ROLE_AUTH.AUTH_ID }, Keys.AUTH_PKEY, new TableField[] { Authorities.AUTHORITIES.ID }, true);
}
