package edu.nju.software.dao;

import java.util.List;

import edu.nju.software.pojo.Member;
import edu.nju.software.pojo.Task;
import edu.nju.software.pojo.TaskAssign;

public interface TaskDao {
	public int create(Task task);
	
	public void update(Task task);
	
	public void delete(int taskId);
	
	public void deleteAllByProject(int projectId);
	
	public List<Task> getByProject(int projectId);
	
	public Task getById(int id);
	
	public void assignTask(TaskAssign taskAssign);
	
	public List<Task> getTasksByMember(int memberId);
	
	public List<Task> getTasksByOutEmployee(int companyId, int outEmployeeId);
	
	public void deleteTaskAssignByTask(int taskId);
	
	public void deleteTaskAssign(int taskId, Integer memberId, Integer outEmployeeId);
	
	public List<Task> getTasksWithChildrenByProject(int projectId);
	
	public List<Member> getRelatedMembers(int taskId);
	
}
