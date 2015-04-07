package edu.nju.software.dao;

import java.util.List;
import edu.nju.software.pojo.TaskStatus;

public interface TaskStatusDao {
	public List<TaskStatus> getAll();
}
