package com.spring.main.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataBaseConfig {
	
	@Value("${spring.datasource.mariadb.url}")
    String mariaUrl;
	
	@Value("${spring.datasource.mariadb.usr}")
	String mariaUsr;
	
	@Value("${spring.datasource.mariadb.pwd}")
	String mariaPwd;
	
	@Value("${spring.datasource.mariadb.driver-class-name}")
	String mariaDrivarName;
	
	@Value("${spring.datasource.mysql.url}")
    String mysqlUrl;
	
	@Value("${spring.datasource.mysql.usr}")
	String mysqlUsr;
	
	@Value("${spring.datasource.mysql.pwd}")
	String mysqlPwd;
	
	@Value("${spring.datasource.mysql.driver-class-name}")
	String mysqlDrivarName;

	@Value("${app.datasource.active}")
	String activeDatabase;
	
	@Value("${jpa.database-platform.mariadb}")
    String jpaPlatformMariaDb;
    
	@Value("${jpa.database-platform.mysql}")
    String jpaPlatformMySql;
    
	@Bean
    DataSource dataSource() throws IllegalArgumentException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        if("MARIADB".equals(activeDatabase)) {
        	dataSource.setUrl(mariaUrl);
        	dataSource.setUsername(mariaUsr);
        	dataSource.setPassword(mariaPwd);
        	dataSource.setDriverClassName(mariaDrivarName);
        	System.setProperty("spring.jpa.database-platform", jpaPlatformMariaDb);
        }else if("MYSQL".equals(activeDatabase)) {
        	dataSource.setUrl(mysqlUrl);
        	dataSource.setUsername(mysqlUsr);
        	dataSource.setPassword(mysqlPwd);
        	dataSource.setDriverClassName(mysqlDrivarName);
        	System.setProperty("spring.jpa.database-platform", jpaPlatformMySql);
        }else {
        	throw new IllegalArgumentException("Unsupported Database :!" + activeDatabase);
        }
        return dataSource;
    }
}
