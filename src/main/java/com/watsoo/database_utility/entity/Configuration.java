package com.watsoo.database_utility.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "configuration")
public class Configuration {

	@JsonIgnore
	@Transient
	private JdbcTemplate JdbcTemplate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "port")
	private String port;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "database_name")
	private String databaseName;

	@Column(name = "table_name")
	private String tableName;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "no_of_days")
	private Integer noOfDays;

	@Column(name = "column_name")
	private String columnName;

	@Column(name = "batch_size")
	private Integer batchSize;

	@Column(name = "interval")
	private Integer interval;

	@Column(name = "last_fetched_date")
	private Date lastFetchedDate;

	@Column(name = "largest_id")
	private Long largestId;

	public Configuration() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JdbcTemplate getJdbcTemplate() {
		return JdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		JdbcTemplate = jdbcTemplate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Integer getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public Date getLastFetchedDate() {
		return lastFetchedDate;
	}

	public void setLastFetchedDate(Date lastFetchedDate) {
		this.lastFetchedDate = lastFetchedDate;
	}

	public Long getLargestId() {
		return largestId;
	}

	public void setLargestId(Long largestId) {
		this.largestId = largestId;
	}

}
