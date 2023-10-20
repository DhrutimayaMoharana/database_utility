package com.watsoo.database_utility.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.watsoo.database_utility.service.ConfigurationService;

@Component
public class Scheduler {
	
	@Autowired
	private ConfigurationService configurationService;
	
//	@Scheduled(cron = "* */3 * * * *")
	@Scheduled(fixedRate = 10000,initialDelay = 5000)
	public void updateApplicationStatus() {
		configurationService.fetchAndDeleteDataFromTable();
	}
	
}
