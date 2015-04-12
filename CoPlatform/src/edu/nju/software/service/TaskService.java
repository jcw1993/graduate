package edu.nju.software.service;

import java.util.List;

import edu.nju.software.pojo.Task;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;

public interface TaskService {
	public GeneralResult<Integer> create(Task task);
	
	public NoDataResult update(Task task);
	
	public NoDataResult delete(Task task);
	
	public GeneralResult<List<Task>> getByProject(int projectId);
	
	public GeneralResult<Task> getByProjectAndId(int projectId, int taskId);
	
	public GeneralResult<Task> getById(int id);
	
	public GeneralResult<Task> getParent(int parentId);
}
