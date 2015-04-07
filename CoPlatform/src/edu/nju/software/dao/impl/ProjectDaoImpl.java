package edu.nju.software.dao.impl;

import org.springframework.dao.DataAccessException;

import edu.nju.software.dao.ProjectDao;
import edu.nju.software.pojo.Project;

public class ProjectDaoImpl extends HibernateDaoBase implements ProjectDao {

	@Override
	public Project getById(int id) throws DataAccessException {
		return getHibernateTemplate().get(Project.class, id);
	}

	@Override
	public int create(Project project) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(project);
	}

	@Override
	public void update(Project project)  throws DataAccessException {
		getHibernateTemplate().update(project);
	}

	@Override
	public void delete(int id)  throws DataAccessException {
		Project project = new Project();
		project.setId(id);
		getHibernateTemplate().delete(project);
	}

}
