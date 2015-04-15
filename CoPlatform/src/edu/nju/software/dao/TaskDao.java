package edu.nju.software.dao;

import java.util.List;

import edu.nju.software.pojo.Task;

public interface TaskDao {
	public int create(Task task);
	
	public void update(Task task);
	
	public void delete(Task task);
	
	public void deleteAllByProject(int projectId);
	
	public List<Task> getByProject(int projectId);
	
	public Task getParent(int parentId);
	
	public Task getById(int id);
}
