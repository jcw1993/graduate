package edu.nju.software.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Query;
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
		Query query = getSession().createQuery("from Task where projectId = " + projectId + " order by id asc");
		return query.list();
	}

	@Override
	public Task getById(int id) {
		return getHibernateTemplate().get(Task.class, id);
	}

	@Override
	public void deleteAllByProject(int projectId) {
		Query query = getSession().createQuery("delete from Task where projectId = " + projectId);
		query.executeUpdate();
	}

	@Override
	public void assignTask(TaskAssign taskAssign) {
		getHibernateTemplate().save(taskAssign);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTasksByMember(int memberId) {
		Query query = getSession().createQuery("select t from TaskAssign as ta, Task as t, Member as m where ta.taskId = t.id and "
				+ "ta.memberId = m.id and m.id = " + memberId + " order by t.id asc");
		return query.list();
	}

	// bug, outEmployee and company
	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTasksByOutEmployee(int companyId, int outEmployeeId) {
//		Query query = getSession().createQuery("select t from TaskAssign as ta, Task as t, OutEmployee as oe where  ta.taskId = t.id and "
//				+ "ta.outEmployeeId = oe.id and t.project.company.id = " + companyId + " and oe.id = " + outEmployeeId + " order by t.id asc");
		Query query = getSession().createQuery("select t from TaskAssign as ta, Task as t, OutEmployee as oe where  ta.taskId = t.id and "
				+ "ta.outEmployeeId = oe.id and oe.id = " + outEmployeeId + " order by t.id asc");
		return query.list();
	}

	@Override
	public void deleteTaskAssign(int taskId) {
		Query query = getSession().createQuery("delete from TaskAssign where taskId = " + taskId);
		query.executeUpdate();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public LinkedHashMap getTasksWithChildrenByProject(int projectId) {
		Query query = getSession().createQuery("select t from Task as t where projectId = " + projectId + " order by t.depth asc, id asc");
		List<Task> taskList = query.list();
		LinkedHashMap<Integer, Task> taskTree = new LinkedHashMap<Integer,Task>();
		for(Task task : taskList) {
			taskTree.put(task.getId(), task);
		}
		
		return taskTree;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> getRelatedMembers(int taskId) {
		Query query = getSession().createQuery("select m from TaskAssign as ts, Member as m where ts.memberId = m.id and ts.taskId = " + taskId);
		return query.list();
	}

}
