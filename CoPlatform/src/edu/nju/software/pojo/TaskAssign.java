package edu.nju.software.pojo;


public class TaskAssign {
	private int id;
	private Task task;
	private Member member;
	private OutEmployee outEmployee;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public OutEmployee getOutEmployee() {
		return outEmployee;
	}
	public void setOutEmployee(OutEmployee outEmployee) {
		this.outEmployee = outEmployee;
	}
	
	public TaskAssign() {}
	
	public TaskAssign(int id) {
		this.id = id;
	}
	
	public TaskAssign(Task task, Member member, OutEmployee outEmployee) {
		this.task = task;
		this.member = member;
		this.outEmployee = outEmployee;
	}
}
