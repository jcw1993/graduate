package edu.nju.software.pojo;

import java.util.Date;

public class Task {
	private int id;
	private Project project;
	private String name;
	private String description;
	private Task parent;
	private TaskStatus status;
	private Date startTime;
	private Date endTime;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Task getParent() {
		return parent;
	}
	public void setParent(Task parent) {
		this.parent = parent;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Task() {}
	
	public Task(int id) {
		this.id = id;
	}
	
	public Task(int id, Project project, String name, String description, Task parent, TaskStatus status, Date startTime, Date endTime) {
		this.id = id;
		this.project = project;
		this.name = name;
		this.description = description;
		this.parent = parent;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public boolean equals(Object obj) {
		if(null == obj) {
			return false;
		}
		
		if(obj instanceof Task) {
			Task task = (Task) obj;
			return this.id == task.getId();
		}
		return false;
	}
}
