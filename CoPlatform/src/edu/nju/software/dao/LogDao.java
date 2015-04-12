package edu.nju.software.dao;

import java.util.Date;
import java.util.List;

import edu.nju.software.pojo.Log;

public interface LogDao {
	public List<Log> search(int projectId, int taskId, Date satrtTime, Date endTime);
	
	public int create(Log log);
}
