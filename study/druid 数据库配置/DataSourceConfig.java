package com.shixun.config;

import static org.hamcrest.CoreMatchers.nullValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;

/**
 * 数据库连接池配置 druid （德鲁伊）
 * @author Administrator
 *
 */
@Configuration
public class DataSourceConfig {

	private String driverClassName = "com.mysql.cj.jdbc.Driver";// 驱动
	private String url = "jdbc:mysql://127.0.0.1:3306/tianyang?characterEncoding=utf-8&serverTimezone=GMT%2B8&allowMultiQueries=true";// 连接地址
	private String username = "root";// 账号
	private String password = "root";// 密码
	private String dataSourceType = "com.alibaba.druid.pool.DruidDataSource";

	private Integer init = 5;// 初始化连接数
	private Integer min = 5;// 最小连接数
	private Integer max = 20;// 最大活跃
	private Long maxWait = 6000L;// 最大等待
	private boolean testWhileIdle = true;// #申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
	private Long timeBetweenEvictionRunSmillis = 6000L;
	private Long minEvictableIdleTimeMillis = 30000L;
	private String validationQuery = "select 'x'";
	private Boolean testOnBorrow = true;
	private Boolean testOnReturn = false;
	private Boolean poolPreparedStatements = true;
	private Boolean useGlobalDataSourceStat = true;
	private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500";
	private String filters = "stat";

	@Bean(destroyMethod = "close", initMethod = "init") // 声明其为Bean实例
	protected DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		// 连接配置
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		// 基础配置
		dataSource.setInitialSize(init);
		dataSource.setMinIdle(min);
		dataSource.setMaxActive(max);
		dataSource.setMaxWait(maxWait);
		dataSource.setTestWhileIdle(testWhileIdle);
		dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunSmillis);
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		dataSource.setValidationQuery(validationQuery);
		dataSource.setTestOnBorrow(testOnBorrow);
		dataSource.setTestOnReturn(testOnReturn);
		dataSource.setPoolPreparedStatements(poolPreparedStatements);
		dataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
		dataSource.setConnectionProperties(connectionProperties);
		try {
			dataSource.setFilters(filters);
			dataSource.setProxyFilters(getFilter());
		} catch (SQLException e) {
			System.out.println("druid 配置有问题");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataSource;
	}
	/**
	 * 配置druid 监控页面
	 * @return
	 */
	@Bean
	public ServletRegistrationBean statViewServlet() { // 创建servlet注册实体
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/druid/*");
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1"); // 设置 druid监控页面 ip白名单
		// 设置控制台管理用户__登录用户名和密码
		servletRegistrationBean.addInitParameter("loginUsername", "1");
		servletRegistrationBean.addInitParameter("loginPassword", "1");
		// 充值数据
		servletRegistrationBean.addInitParameter("resetEnable", "true");
		// 是否可以重置数据 servletRegistrationBean.addInitParameter("resetEnable","false");
		return servletRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean statFilter() { // 创建过滤器
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter()); // 设置过滤器过滤路径
		filterRegistrationBean.addUrlPatterns("/*"); // 忽略过滤的形式
		String antMatchers = "/404/**" + "/js/**" + "/login/css/**" + "/login/images/**" + "/logo/**" + "/tags/**"
				+ "/xml/**" + "/css/**" + "/layui/**" + "/layer/**" + "/images/**";
		filterRegistrationBean.addInitParameter("exclusions", antMatchers);
		return filterRegistrationBean;
	}

	/**
	 * 配置druid （德鲁伊） 拦截器
	 * @return
	 */
	public List<Filter> getFilter(){
		List<Filter> list = new ArrayList<Filter>();
		//sql 防火墙
		WallFilter wallFilter = new WallFilter();
		//sql 防火墙配置
		WallConfig wallConfig = new WallConfig();
		wallConfig.setMultiStatementAllow(true);//允许一次执行多条sql
		wallConfig.setNoneBaseStatementAllow(true);
		
		wallFilter.setConfig(wallConfig);
		list.add(wallFilter);
		return list;
	}
	
	
	

}
