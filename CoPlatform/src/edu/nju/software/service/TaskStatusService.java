package edu.nju.software.service;

import java.util.List;

import edu.nju.software.pojo.TaskStatus;
import edu.nju.software.util.GeneralResult;

public interface TaskStatusService {
	public GeneralResult<List<TaskStatus>> getAll();
}
