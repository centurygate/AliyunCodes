package com.xigeng.waterfactory.controller;


import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by free on 16-11-14.
 */
@Configuration
//@ComponentScan(basePackages = "com.xigeng.waterfactory.controller")
@MapperScan("com.xigeng.waterfactory.controller")
public class DataSourceConfig
{

	//spring-mybatis.xml 中原来的内容
//	<beans:bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource"
//	destroy-method="close">
//        <beans:property name="driverClassName" value="com.mysql.jdbc.Driver" />
//        <beans:property name="url" value="jdbc:mysql://192.168.1.105:3306/waterfactory?characterEncoding=utf8&amp;useSSL=true" />
//        <beans:property name="username" value="root" />
//        <beans:property name="password" value="singapore" />
//        <!-- 初始化连接大小 -->
//        <beans:property name="initialSize" value="20"></beans:property>
//        <!-- 连接池最大数量 -->
//        <beans:property name="maxActive" value="150"></beans:property>
//        <!-- 连接池最大空闲 -->
//        <beans:property name="maxIdle" value="20"></beans:property>
//        <!-- 连接池最小空闲 -->
//        <beans:property name="minIdle" value="5"></beans:property>
//        <!-- 获取连接最大等待时间 -->
//        <beans:property name="maxWait" value="60000"></beans:property>
//    </beans:bean>

	@Bean
	public DataSource dataSourceforauth() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("singapore");
		dataSource.setUrl("jdbc:mysql://192.168.1.105:3306/waterfactory?characterEncoding=utf8&amp;useSSL=true");
		dataSource.setInitialSize(20);
		dataSource.setMaxActive(150);
		dataSource.setMaxIdle(20);
		dataSource.setMinIdle(5);
		dataSource.setMaxWait(60000);
		return dataSource;
	}
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.apache.commons.dbcp.BasicDataSource");
		dataSource.setUsername("root");
		dataSource.setPassword("singapore");
		dataSource.setUrl("jdbc:mysql://192.168.1.105:3306/waterfactory?characterEncoding=utf8&amp;useSSL=true");
		dataSource.setInitialSize(20);
		dataSource.setMaxActive(150);
		dataSource.setMaxIdle(20);
		dataSource.setMinIdle(5);
		dataSource.setMaxWait(60000);
		return dataSource;
	}
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		return sessionFactory;
	}
	@Bean
	public CustomSuccessHandler customSuccessHandler()
	{
		return new CustomSuccessHandler();
	}
}
