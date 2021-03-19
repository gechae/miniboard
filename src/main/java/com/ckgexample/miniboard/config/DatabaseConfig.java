package com.ckgexample.miniboard.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//@Bean(name = "dataSource")
@Primary
@ConfigurationProperties("spring.datasource.hikari")
public class DatabaseConfig {
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Bean(destroyMethod = "close")
	public HikariDataSource dataSource(DataSourceProperties dataSourceProperties, ApplicationPreparedEvent applicationProperties) {
		log.debug("========================================= Configuring DataSoucre");
		
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(dataSourceProperties.getDriverClassName());
		
		config.addDataSourceProperty("url", dataSourceProperties.getUrl());
		
		if (dataSourceProperties.getUsername() != null ) {
			log.debug("======================================= DataSoucre user", dataSourceProperties.getUsername());
			config.addDataSourceProperty("user", dataSourceProperties.getUsername());
		} else {
			config.addDataSourceProperty("user", "");
		}
		
		if (dataSourceProperties.getPassword() != null) {
			log.debug("========================================= DataSoucre password", dataSourceProperties.getPassword());
			config.addDataSourceProperty("password", dataSourceProperties.getPassword());
		} else {
			config.addDataSourceProperty("password", "");
		}
		
		return new HikariDataSource(config);
	}
}
