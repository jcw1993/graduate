package edu.nju.software.service;

import java.util.Date;
import java.util.List;

import edu.nju.software.pojo.Log;
import edu.nju.software.util.GeneralResult;

public interface LogService {
	
	public GeneralResult<List<Log>> getByProject(int projectId, Date startTime, Date endTime);
	
	public GeneralResult<List<Log>> getByTask(int taskId, Date startTime, Date endTime);
	
}
