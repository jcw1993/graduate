package edu.nju.software.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import edu.nju.software.dao.TaskDao;
import edu.nju.software.pojo.Task;

public class TaskDaoImpl extends HibernateDaoBase implements TaskDao {

	@Override
	public int create(Task task) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(task);
	}

	@Override
	public void update(Task task) throws DataAccessException {
		getHibernateTemplate().update(task);
	}

	@Override
	public void delete(int id) throws DataAccessException {
		Task task = new Task();
		task.setId(id);
		getHibernateTemplate().delete(task);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getByProject(int projectId) throws DataAccessException {
		return getHibernateTemplate().find("from Task where projectId = :projectId", projectId);
	}

	@Override
	public Task getParent(int parentId) throws DataAccessException {
		return (Task) getHibernateTemplate().find("from Task where taskId = :parentId", parentId);
	}

	@Override
	public Task getById(int id) {
		return getHibernateTemplate().get(Task.class, id);
	}

}
