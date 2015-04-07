package edu.nju.software.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import edu.nju.software.dao.LogDao;
import edu.nju.software.pojo.Log;

public class LogDaoImpl extends HibernateDaoBase implements LogDao {

	@Override
	public List<Log> search(Integer projectId, Integer taskId, Date startTime,
			Date endTime) throws DataAccessException {
		if(null == projectId && null == taskId) {
			return null;
		}
		
		return null;
	}

}
