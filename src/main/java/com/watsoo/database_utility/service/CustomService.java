package com.watsoo.database_utility.service;

import java.util.Map;

import javax.sql.DataSource;

import com.watsoo.database_utility.entity.Configuration;

public interface CustomService {

	Map<String, Object> fetchDataFromTable(Configuration configuration);

	DataSource mysqlDataSource(Configuration configuration);

	Integer deleteDataFromTable(Configuration configuration);

}
