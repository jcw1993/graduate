package edu.nju.software.pojo;

import java.util.Date;

public class Log {
	private int id;
	private String title;
	private String desc;
	private Project project;
	private Task task;
	private TaskStatus originStatus;
	private TaskStatus currentStatus;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
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
	public TaskStatus getOriginStatus() {
		return originStatus;
	}
	public void setOriginStatus(TaskStatus originStatus) {
		this.originStatus = originStatus;
	}
	public TaskStatus getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(TaskStatus currentStatus) {
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
	
}
