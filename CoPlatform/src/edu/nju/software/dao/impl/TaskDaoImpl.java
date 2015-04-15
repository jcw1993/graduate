package edu.nju.software.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import edu.nju.software.dao.TaskDao;
import edu.nju.software.pojo.Project;
import edu.nju.software.pojo.Task;

@Repository
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
	public void delete(Task task) throws DataAccessException {
		getHibernateTemplate().delete(task);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getByProject(int projectId) throws DataAccessException {
		Project project = new Project(projectId);
		return getHibernateTemplate().find("from Task where project = ?", project);
	}

	@Override
	public Task getParent(int parentId) throws DataAccessException {
		Task task = new Task(parentId);
		return (Task) getHibernateTemplate().find("from Task where task = ?", task);
	}

	@Override
	public Task getById(int id) {
		return getHibernateTemplate().get(Task.class, id);
	}

	@Override
	public void deleteAllByProject(int projectId) {
		Query query = getSession().createQuery("delete from Task where project.id = " + projectId);
		query.executeUpdate();
	}

}
