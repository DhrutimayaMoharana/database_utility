package com.watsoo.database_utility.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;

import com.watsoo.database_utility.entity.Configuration;


public class DatabaseConfiguration {

//	public DataSource mysqlDataSource(Configuration configuration) {
//
//		String url = "jdbc:mysql://" + configuration.getIpAddress() + ":" + configuration.getPort() + "/"
//				+ configuration.getDatabaseName();
//		String username = configuration.getUsername();
//		String password = configuration.getPassword();
//
//		return DataSourceBuilder
//				.create()
//				.url(url)
//				.username(username)
//				.password(password)
//				.driverClassName("com.mysql.cj.jdbc.Driver")
//				.build();
//	}

}
