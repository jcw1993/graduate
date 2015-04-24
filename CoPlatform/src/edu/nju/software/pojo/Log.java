package edu.nju.software.pojo;

import java.util.Date;

public class Log {
	private int id;
	private String title;
	private String description;
	private Project project;
	private Task task;
	private int originStatus;
	private int currentStatus;
	private Date createdTime;
	private Company company;
	private int creatorId;
	private int creatorType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public int getOriginStatus() {
		return originStatus;
	}
	public void setOriginStatus(int originStatus) {
		this.originStatus = originStatus;
	}
	public int getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public int getCreatorType() {
		return creatorType;
	}
	public void setCreatorType(int creatorType) {
		this.creatorType = creatorType;
	}
	
	public Log() {}
	
	public Log(String title, String description, Project project, Task task, int originStatus, 
			int currentStatus, Date createdTime, Company company, int creatorId,int creatorType)
	{
		this.title = title;
		this.description = description;
		this.project = project;
		this.task = task;
		this.originStatus = originStatus;
		this.currentStatus = currentStatus;
		this.createdTime = createdTime;
		this.company = company;
		this.creatorId = creatorId;
		this.creatorType = creatorType;
	}
}
