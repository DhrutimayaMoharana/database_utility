package com.watsoo.database_utility.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.watsoo.database_utility.dto.ConditionCheckDTO;
import com.watsoo.database_utility.entity.Configuration;
import com.watsoo.database_utility.repository.ConfigurationRepository;
import com.watsoo.database_utility.service.ConfigurationService;
import com.watsoo.database_utility.service.CustomService;
import com.watsoo.database_utility.util.DateUtil;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

	@Autowired
	private ConfigurationRepository configurationRepository;

	@Autowired
	private CustomService customService;

	public static Map<Long, ConditionCheckDTO> conditionCheckMap = new HashMap<>();

	public List<Configuration> configurationList = new ArrayList<>();

	@PostConstruct
	@Override
	public void fetchForFirstTimeFromDB() {
		Map<String, JdbcTemplate> databaseWiseJdbcTemplate = new HashMap<>();
		List<Configuration> dbConfigurations = configurationRepository.findAllActiveInfo();

		for (Configuration configuration : dbConfigurations) {
			String keyForDatabaseWiseJdbcTemplate = configuration.getIpAddress() + configuration.getPort()
					+ configuration.getUsername() + configuration.getDatabaseName();

			if (databaseWiseJdbcTemplate.size() > 0
					&& databaseWiseJdbcTemplate.containsKey(keyForDatabaseWiseJdbcTemplate)) {
				configuration.setJdbcTemplate(databaseWiseJdbcTemplate.get(keyForDatabaseWiseJdbcTemplate));
			} else {
				configuration.setJdbcTemplate(new JdbcTemplate(customService.mysqlDataSource(configuration)));
				databaseWiseJdbcTemplate.put(keyForDatabaseWiseJdbcTemplate, configuration.getJdbcTemplate());
			}
		}

		// Clearing and replacing the contents of configurationList
		configurationList.clear();
		configurationList.addAll(dbConfigurations);
	}

	@Override
	public Object fetchAndDeleteDataFromTable() {
		try {
			Map<String, Object> response = new HashMap<>();
			List<Configuration> configurationUpdatedList = new ArrayList<>();
			for (Configuration configuration : configurationList) {
				ConditionCheckDTO conditionCheckDTO = conditionCheckMap.get(configuration.getId()) != null
						? conditionCheckMap.get(configuration.getId())
						: new ConditionCheckDTO(null, null, null);
				conditionCheckDTO.setId(configuration.getId());
				conditionCheckMap.put(configuration.getId(), conditionCheckDTO);

				if (!conditionCheckDTO.getInProgress()) {
					conditionCheckDTO.setInProgress(true);

					if (new Date().after(conditionCheckMap.get(configuration.getId()).getNextDateToProcess())) {

						conditionCheckDTO.setNextDateToProcess(
								DateUtil.addSeconds(conditionCheckDTO.getNextDateToProcess().getTime(),
										configuration != null && configuration.getInterval() != null
												? configuration.getInterval().longValue()
												: 10L));

						Date todaysDate = DateUtil.getUTCDateStartTimeINString(new Date());

						if ((configuration.getLargestId() == null || configuration.getLastFetchedDate() == null
								|| (configuration.getLastFetchedDate() != null
										&& configuration.getLastFetchedDate().before(todaysDate)))) {
							System.out.println("-----------Fetch Data-------------" + configuration.getIpAddress()
									+ " :::: " + configuration.getDatabaseName() + " :::: "
									+ configuration.getTableName());
							response = customService.fetchDataFromTable(configuration);
						}

						if (response != null && response.size() > 0) {
							System.out.println("------------Update Data---------------" + configuration.getIpAddress()
									+ " :::: " + configuration.getDatabaseName() + " :::: "
									+ configuration.getTableName());
							configuration.setLastFetchedDate(todaysDate);
							configuration.setLargestId(Long.parseLong(response.get("id").toString()));
							configurationRepository.updateLastFetchedDateAndLargestId(configuration.getId(),
									configuration.getLastFetchedDate(), configuration.getLargestId());

							conditionCheckDTO.setIsAllDataDeleted(false);
						} else if (response == null) {
							System.out.println("------------Update Data---------------" + configuration.getIpAddress()
									+ " :::: " + configuration.getDatabaseName() + " :::: "
									+ configuration.getTableName());
							configuration.setLastFetchedDate(todaysDate);
							configuration.setLargestId(0L);
							configurationRepository.updateLastFetchedDateAndLargestId(configuration.getId(),
									configuration.getLastFetchedDate(), configuration.getLargestId());
						}

						if (configuration.getLargestId() != null && (conditionCheckDTO.getIsAllDataDeleted() == null
								|| !conditionCheckDTO.getIsAllDataDeleted())) {
							System.out.println("****************Delete Data***************"
									+ configuration.getIpAddress() + " :::: " + configuration.getDatabaseName()
									+ " :::: " + configuration.getTableName());
							Integer noOfRowsAfftected = customService.deleteDataFromTable(configuration);
							if (noOfRowsAfftected != null && noOfRowsAfftected == 0) {
								conditionCheckDTO.setIsAllDataDeleted(true);
							}
						}

					}
				}
				conditionCheckDTO.setInProgress(false);
				conditionCheckMap.put(configuration.getId(), conditionCheckDTO);

				configurationUpdatedList.add(configuration);
			}

			configurationList.clear();
			configurationList = configurationUpdatedList;

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
