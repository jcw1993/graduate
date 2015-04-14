package edu.nju.software.pojo;

import java.util.Date;

public class Project {
	private int id;
	private Company company;
	private String name;
	private String desc;
	private Date startTime;
	private Date endTime;
	private double progress;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
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
	public double getProgress() {
		return progress;
	}
	public void setProgress(double progress) {
		this.progress = progress;
	}
	
	public Project() {}
	
	public Project(int id) {
		this.id = id;
	}
	
	public Project(int id, Company company, String name, String desc, Date startTime, Date endTime, double progress) {
		this.id = id;
		this.company = company;
		this.name = name;
		this.desc = desc;
		this.startTime = startTime;
		this.endTime = endTime;
		this.progress = progress;
	}

	@Override
	public boolean equals(Object obj) {
		if(null == obj) {
			return false;
		}
		
		if(obj instanceof Project) {
			Project project = (Project) obj;
			return this.id == project.getId();
		}
		return false;
	}
}
