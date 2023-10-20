package com.watsoo.database_utility.serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.watsoo.database_utility.entity.Configuration;
import com.watsoo.database_utility.service.CustomService;
import com.watsoo.database_utility.util.DateUtil;

@Service
@Transactional
public class CustomServiceImpl implements CustomService {

	@Override
	public DataSource mysqlDataSource(Configuration configuration) {
		String url = "jdbc:mysql://" + configuration.getIpAddress() + ":" + configuration.getPort() + "/"
				+ configuration.getDatabaseName();
		String username = configuration.getUsername();
		String password = configuration.getPassword();

		return DataSourceBuilder.create().url(url).username(username).password(password)
				.driverClassName("com.mysql.cj.jdbc.Driver").build();
	}

	@Override
	@Transactional
	public Map<String, Object> fetchDataFromTable(Configuration configuration) {
		Connection connection = null;
		try {

			JdbcTemplate jdbcTemplate = configuration.getJdbcTemplate();

			connection = jdbcTemplate.getDataSource().getConnection();

			String date = DateUtil.getDateAfterSomeDay(new Date(), configuration.getNoOfDays());
			System.out.println(date);

			String sql = "SELECT * FROM " + configuration.getTableName() + " WHERE DATE("
					+ configuration.getColumnName() + ")<='" + date + "' ORDER BY id DESC LIMIT 1";

			return jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		} finally {
			// Close the resources in the reverse order of opening

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	@Transactional
	public Integer deleteDataFromTable(Configuration configuration) {
		Connection connection = null;
		try {
			JdbcTemplate jdbcTemplate = configuration.getJdbcTemplate();

			connection = jdbcTemplate.getDataSource().getConnection();

			String sql = "DELETE FROM " + configuration.getTableName() + " WHERE id <=" + configuration.getLargestId()
					+ " LIMIT " + configuration.getBatchSize();

			int rowsAffected = jdbcTemplate.update(sql);
			System.out.println(rowsAffected);
			return rowsAffected;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		} finally {
			// Close the resources in the reverse order of opening

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
