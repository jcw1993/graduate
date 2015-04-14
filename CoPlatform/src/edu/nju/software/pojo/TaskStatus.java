package edu.nju.software.pojo;

public class TaskStatus {
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public TaskStatus() {}
	
	public TaskStatus(int id) {
		this.id = id;
	}
	
	public TaskStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
}
