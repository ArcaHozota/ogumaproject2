package jp.co.ogumaproject.ppok.config;

import javax.sql.DataSource;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.ogumaproject.ppok.entity.Authority;
import jp.co.ogumaproject.ppok.entity.Chiho;
import jp.co.ogumaproject.ppok.entity.City;
import jp.co.ogumaproject.ppok.entity.District;
import jp.co.ogumaproject.ppok.entity.Employee;
import jp.co.ogumaproject.ppok.entity.EmployeeRole;
import jp.co.ogumaproject.ppok.entity.Role;
import jp.co.ogumaproject.ppok.entity.RoleAuth;
import lombok.extern.slf4j.Slf4j;

/**
 * JDBI設定クラス
 *
 * @author ArkamaHozota
 * @version
 */
@Slf4j
@Configuration
public class JDBIConfiguration {

	@Bean
	protected Jdbi jdbi(final DataSource dataSource) {
		final Jdbi jdbi = Jdbi.create(dataSource); // 使用 Spring 提供的 DataSource
		jdbi.installPlugin(new SqlObjectPlugin()); // 安装插件，支持 DAO 接口
		jdbi.registerRowMapper(BeanMapper.factory(Authority.class)); // 注册驼峰映射策略
		jdbi.registerRowMapper(BeanMapper.factory(Chiho.class));
		jdbi.registerRowMapper(BeanMapper.factory(City.class));
		jdbi.registerRowMapper(BeanMapper.factory(District.class));
		jdbi.registerRowMapper(BeanMapper.factory(Employee.class));
		jdbi.registerRowMapper(BeanMapper.factory(EmployeeRole.class));
		jdbi.registerRowMapper(BeanMapper.factory(Role.class));
		jdbi.registerRowMapper(BeanMapper.factory(RoleAuth.class));
		log.info("JDBIフレームワーク配置成功！");
		return jdbi;
	}

}
