package com.watsoo.database_utility.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.watsoo.database_utility.entity.Configuration;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE configuration c SET c.last_fetched_date = :newLastFetchedDate, c.largest_id = :newLargestId WHERE c.id = :id", nativeQuery = true)
	void updateLastFetchedDateAndLargestId(Long id, Date newLastFetchedDate, Long newLargestId);

	@Query(value = "SELECT * FROM configuration WHERE is_active = 1", nativeQuery = true)
	List<Configuration> findAllActiveInfo();

}
