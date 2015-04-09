package edu.nju.software.dao.impl;

import java.util.List;

import edu.nju.software.dao.TaskStatusDao;
import edu.nju.software.pojo.TaskStatus;

public class TaskStatusDaoImpl extends HibernateDaoBase implements TaskStatusDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskStatus> getAll() {
		return getHibernateTemplate().find("from TaskStatus");
	}
}