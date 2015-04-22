package edu.nju.software.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import edu.nju.software.dao.ProjectDao;
import edu.nju.software.pojo.Company;
import edu.nju.software.pojo.Project;

@Repository
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
	public void delete(Project project)  throws DataAccessException {
		getHibernateTemplate().delete(project);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getByCompany(int companyId) {
		Company company = new Company(companyId);
		return getHibernateTemplate().find("from Project where company = ? order by id asc", company);
	}

	@Override
	public void deleteTaskAssign(int projectId) {
		Query query = getSession().createQuery("delete from TaskAssign where id > 0 and task.id in (select id from Task where project.id = "
				+ projectId + ")");
		query.executeUpdate();
	}

}
