package edu.nju.software.dao;

import java.util.List;

import edu.nju.software.pojo.Task;

public interface TaskDao {
	public int create(Task task);
	
	public void update(Task task);
	
	public void delete(int id);
	
	public List<Task> getByProject(int projectId);
	
	public Task getParent(int parentId);
}
