package edu.nju.software.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import edu.nju.software.dao.LogDao;
import edu.nju.software.pojo.Log;
import edu.nju.software.pojo.Project;
import edu.nju.software.pojo.Task;

@Repository
public class LogDaoImpl extends HibernateDaoBase implements LogDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Log> search(int projectId, int taskId, Date startTime,
			Date endTime) throws DataAccessException {
		return getHibernateTemplate().find("from log where (project = ? or project is null) " + 
			"and (task = ? or task is null) and createdTime >= startTime and createdTime <= endTime",
				new Project(projectId), new Task(taskId), startTime, endTime);
	}

	@Override
	public int create(Log log) {
		return (Integer) getHibernateTemplate().save(log);
	}

}
