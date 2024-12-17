package com.spring.main.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataBaseConfig {
	
	@Value("${spring.mariadb.datasource.url}")
    private String url;

    @Value("${spring.mariadb.datasource.driver-class-name}")
    private String driverClassName;

    @Autowired
    Environment env;
	
    @Bean
    DataSource dataSource() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(env.getProperty("spring.mariadb.datasource.usr"));
        dataSource.setPassword(env.getProperty("spring.mariadb.datasource.pwd"));
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }
}
