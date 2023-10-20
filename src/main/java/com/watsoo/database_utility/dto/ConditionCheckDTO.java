package com.watsoo.database_utility.dto;

import java.util.Date;

public class ConditionCheckDTO {

	private Long id;

	private volatile Boolean inProgress;

	private Date nextDateToProcess;

	private Boolean isAllDataDeleted;

	public ConditionCheckDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConditionCheckDTO(Long id, Boolean inProgress, Date nextDateToProcess) {
		super();
		this.id = id;
		this.inProgress = false;
		this.nextDateToProcess = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getInProgress() {
		return inProgress;
	}

	public void setInProgress(Boolean inProgress) {
		this.inProgress = inProgress;
	}

	public Date getNextDateToProcess() {
		return nextDateToProcess;
	}

	public void setNextDateToProcess(Date nextDateToProcess) {
		this.nextDateToProcess = nextDateToProcess;
	}

	public Boolean getIsAllDataDeleted() {
		return isAllDataDeleted;
	}

	public void setIsAllDataDeleted(Boolean isAllDataDeleted) {
		this.isAllDataDeleted = isAllDataDeleted;
	}

}
