package edu.nju.software.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import edu.nju.software.dao.TaskDao;
import edu.nju.software.pojo.Member;
import edu.nju.software.pojo.Task;
import edu.nju.software.pojo.TaskAssign;

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
	public void delete(int taskId) throws DataAccessException {
		getHibernateTemplate().delete(new Task(taskId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getByProject(int projectId) throws DataAccessException {
		Session session = super.getSession(true);
<<<<<<< HEAD
		try {
			Query query = session.createQuery("from Task where projectId = " + projectId + " order by id asc");
=======
		Query query = null;
		try {
			query = session.createQuery("from Task where projectId = " + projectId + " order by id asc");
>>>>>>> a0fec286f646e1f62e98ea82f01fdc0ee78e890a
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

	@Override
	public Task getById(int id) {
		return getHibernateTemplate().get(Task.class, id);
	}

	@Override
	public void deleteAllByProject(int projectId) {
		Session session = super.getSession(true);
<<<<<<< HEAD
		try {
			Query query = getSession().createQuery("delete from Task where projectId = " + projectId);
=======
		Query query = null;
		try {
			query = session.createQuery("delete from Task where projectId = " + projectId);
>>>>>>> a0fec286f646e1f62e98ea82f01fdc0ee78e890a
			query.executeUpdate();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
<<<<<<< HEAD

=======
		
>>>>>>> a0fec286f646e1f62e98ea82f01fdc0ee78e890a
	}

	@Override
	public void assignTask(TaskAssign taskAssign) {
		getHibernateTemplate().save(taskAssign);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTasksByMember(int memberId) {
		Session session = super.getSession(true);
<<<<<<< HEAD
		try {
			Query query = getSession().createQuery("select t from TaskAssign as ta, Task as t, Member as m where ta.taskId = t.id and "
=======
		Query query = null;
		try {
			query = session.createQuery("select t from TaskAssign as ta, Task as t, Member as m where ta.taskId = t.id and "
>>>>>>> a0fec286f646e1f62e98ea82f01fdc0ee78e890a
					+ "ta.memberId = m.id and m.id = " + memberId + " order by t.id asc");
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

	// bug, outEmployee and company
	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTasksByOutEmployee(int companyId, int outEmployeeId) {
		Session session = super.getSession(true);
<<<<<<< HEAD
		try {
			Query query = getSession().createQuery("select t from TaskAssign as ta, Task as t, OutEmployee as oe where  ta.taskId = t.id and "
=======
		Query query = null;
		try {
			query = session.createQuery("select t from TaskAssign as ta, Task as t, OutEmployee as oe where  ta.taskId = t.id and "
>>>>>>> a0fec286f646e1f62e98ea82f01fdc0ee78e890a
					+ "ta.outEmployeeId = oe.id and oe.id = " + outEmployeeId + " order by t.id asc");
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

	@Override
	public void deleteTaskAssign(int taskId) {
		Session session = super.getSession(true);
<<<<<<< HEAD
		try {
			Query query = getSession().createQuery("delete from TaskAssign where taskId = " + taskId);
=======
		Query query = null;
		try {
			query = session.createQuery("delete from TaskAssign where taskId = " + taskId);
>>>>>>> a0fec286f646e1f62e98ea82f01fdc0ee78e890a
			query.executeUpdate();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
<<<<<<< HEAD
=======
		
>>>>>>> a0fec286f646e1f62e98ea82f01fdc0ee78e890a
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTasksWithChildrenByProject(int projectId) {
		Session session = super.getSession(true);
<<<<<<< HEAD
		try {
			Query query = getSession().createQuery("select t from Task as t where projectId = " + projectId + " order by t.depth asc, id asc");
=======
		Query query = null;
		try {
			query = session.createQuery("select t from Task as t where projectId = " + projectId + " order by t.depth asc, id asc");
>>>>>>> a0fec286f646e1f62e98ea82f01fdc0ee78e890a
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> getRelatedMembers(int taskId) {
		Session session = super.getSession(true);
<<<<<<< HEAD
		try {
			Query query = getSession().createQuery("select m from TaskAssign as ts, Member as m where ts.memberId = m.id and ts.taskId = " + taskId);
=======
		Query query = null;
		try {
			query = getSession().createQuery("select m from TaskAssign as ts, Member as m where ts.memberId = m.id and ts.taskId = " + taskId);
>>>>>>> a0fec286f646e1f62e98ea82f01fdc0ee78e890a
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

}
