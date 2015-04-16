package edu.nju.software.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import edu.nju.software.dao.ProjectDao;
import edu.nju.software.dao.TaskDao;
import edu.nju.software.pojo.Member;
import edu.nju.software.pojo.OutEmployee;
import edu.nju.software.pojo.Project;
import edu.nju.software.pojo.Task;
import edu.nju.software.pojo.TaskAssign;
import edu.nju.software.service.WorkService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Service
public class WorkServiceImpl implements WorkService {
	private static final String COMPANY_PROJECT_CACHE_KEY_FORMAT = "company_project_cache_key_%d";
	
	private static final String PROJECT_CACHE_KEY_FOMAT = "project_%d";
	
	private static final String PROJECT_TASK_CACHE_KEY_FOMAT = "projct_task_%d";
	
	private static final String TASK_CACHE_KEY_FORMAT = "task_%d";
	
	private static Logger logger = LoggerFactory.getLogger(WorkServiceImpl.class);
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private TaskDao taskDao;
	
	@Override
	public GeneralResult<Project> getProjectById(int id) {
		GeneralResult<Project> result = new GeneralResult<Project>();
		Project project = (Project) CoCacheManager.get(String.format(PROJECT_CACHE_KEY_FOMAT, id));
		if(null != project) {
			result.setData(project);
		}else {
			try {
				project = projectDao.getById(id);
				result.setData(project);
				CoCacheManager.put(String.format(PROJECT_CACHE_KEY_FOMAT, id), project);
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<Integer> createProject(Project project) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = projectDao.create(project);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}
		
		CoCacheManager.remove(String.format(COMPANY_PROJECT_CACHE_KEY_FORMAT, project.getCompany().getId()));
		return result;
	}

	@Override
	public NoDataResult updateProject(Project project) {
		NoDataResult result = new NoDataResult();
		try {
			projectDao.update(project);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}
		
		CoCacheManager.remove(String.format(COMPANY_PROJECT_CACHE_KEY_FORMAT, project.getCompany().getId()));
		CoCacheManager.remove(String.format(PROJECT_CACHE_KEY_FOMAT, project.getId()));
		return result;
	}

	@Transactional
	@Override
	public NoDataResult deleteProject(Project project) {
		NoDataResult result = new NoDataResult();
		try {
			projectDao.delete(project);
			taskDao.deleteAllByProject(project.getId());
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}		
		CoCacheManager.remove(String.format(COMPANY_PROJECT_CACHE_KEY_FORMAT, project.getCompany().getId()));
		CoCacheManager.remove(String.format(PROJECT_CACHE_KEY_FOMAT, project.getId()));
		
		CoCacheManager.remove(String.format(PROJECT_TASK_CACHE_KEY_FOMAT, project.getId()));
		return result;
	}

	@Override
	public GeneralResult<List<Project>> getProjectsByCompany(int companyId) {
		GeneralResult<List<Project>> result = new GeneralResult<List<Project>>();
		@SuppressWarnings("unchecked")
		List<Project> projectList = (List<Project>) CoCacheManager.get(String.format(COMPANY_PROJECT_CACHE_KEY_FORMAT, companyId));
		if(null != projectList && !projectList.isEmpty()) {
			result.setData(projectList);
		}else {
			try {
				projectList = projectDao.getByCompany(companyId);
				if(null != projectList && !projectList.isEmpty()) {
					result.setData(projectList);
					CoCacheManager.put(String.format(COMPANY_PROJECT_CACHE_KEY_FORMAT, companyId), projectList);
				}else {
					result.setResultCode(ResultCode.E_NO_DATA);
					result.setMessage("no project data in company, companyId = " + companyId);
				}
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<Project> getProjectByCompanyAndId(int companyId, int projectId) {
		GeneralResult<Project> result = new GeneralResult<Project>();
		GeneralResult<List<Project>> companyProjctResult = getProjectsByCompany(companyId);
		if(companyProjctResult.getResultCode() == ResultCode.NORMAL) {
			for(Project project : companyProjctResult.getData()) {
				if(project.getId() == projectId) {
					result.setData(project);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
			}
		}else {
			result.setResultCode(companyProjctResult.getResultCode());
			result.setMessage(companyProjctResult.getMessage());
		}
		return result;
	}
	
	@Override
	public GeneralResult<Integer> createTask(Task task) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = taskDao.create(task);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}
		
		CoCacheManager.remove(String.format(PROJECT_TASK_CACHE_KEY_FOMAT, task.getProject().getId()));
		return result;
	}

	@Override
	public NoDataResult updateTask(Task task) {
		NoDataResult result = new NoDataResult();
		try {
			taskDao.update(task);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}
		
		CoCacheManager.remove(String.format(PROJECT_TASK_CACHE_KEY_FOMAT, task.getProject().getId()));
		CoCacheManager.remove(String.format(TASK_CACHE_KEY_FORMAT, task.getId()));
		return result;
	}

	@Override
	public NoDataResult deleteTask(Task task) {
		NoDataResult result = new NoDataResult();
		try {
			taskDao.delete(task);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}
		
		CoCacheManager.remove(String.format(PROJECT_TASK_CACHE_KEY_FOMAT, task.getProject().getId()));
		CoCacheManager.remove(String.format(TASK_CACHE_KEY_FORMAT, task.getId()));
		return result;
	}

	@Override
	public GeneralResult<List<Task>> getTasksByProject(int projectId) {
		GeneralResult<List<Task>> result = new GeneralResult<List<Task>>();
		@SuppressWarnings("unchecked")
		List<Task> taskList = (List<Task>) CoCacheManager.get(String.format(PROJECT_TASK_CACHE_KEY_FOMAT, projectId));
		if(null != taskList && !taskList.isEmpty()) {
			result.setData(taskList);
		}else {
			try {
				taskList = taskDao.getByProject(projectId);
				if(null != taskList && !taskList.isEmpty()) {
					result.setData(taskList);
					CoCacheManager.put(String.format(PROJECT_TASK_CACHE_KEY_FOMAT, projectId), taskList);
				}else {
					result.setResultCode(ResultCode.E_NO_DATA);
					result.setMessage("no task data in project, projectId = " + projectId);
				}
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<Task> getTaskByProjectAndId(int projectId, int taskId) {
		GeneralResult<Task> result = new GeneralResult<Task>();
		GeneralResult<List<Task>> projectTaskResult = getTasksByProject(projectId);
		if(projectTaskResult.getResultCode() == ResultCode.NORMAL) {
			for(Task task : projectTaskResult.getData()) {
				if(task.getId() == taskId) {
					result.setData(task);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
			}
		}else {
			result.setResultCode(projectTaskResult.getResultCode());
			result.setMessage(projectTaskResult.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<Task> getTaskById(int id) {
		GeneralResult<Task> result = new GeneralResult<Task>();
		Task task = (Task) CoCacheManager.get(String.format(TASK_CACHE_KEY_FORMAT, id));
		if(null != task) {
			result.setData(task);
		}else {
			try {
				task = taskDao.getById(id);
				result.setData(task);
				CoCacheManager.put(String.format(TASK_CACHE_KEY_FORMAT, id), task);
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<Task> getParentTask(int parentId) {
		GeneralResult<Task> result = new GeneralResult<Task>();
		try {
			Task task = taskDao.getParent(parentId);
			result.setData(task);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoDataResult assignTaskToMember(int taskId, int memberId) {
		NoDataResult result = new NoDataResult();
		if(!checkTaskAssigned(taskId, memberId, 0, 0)) {
			Task task = new Task(taskId);
			Member member = new Member(memberId);
			try {
				taskDao.assignTask(new TaskAssign(task, member, null));
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
				result.setMessage(e.getMessage());
			}
		}else {
			result.setResultCode(ResultCode.E_TASK_ASSIGNED);
			result.setMessage("task is assigned");
		}
		return result;
	}

	@Override
	public NoDataResult assignTaskToOutEmployee(int taskId, int outEmployeeId, int companyId) {
		NoDataResult result = new NoDataResult();
		if(!checkTaskAssigned(taskId, 0, outEmployeeId, companyId)) {
			Task task = new Task(taskId);
			OutEmployee outEmployee = new OutEmployee(outEmployeeId);
			try {
				taskDao.assignTask(new TaskAssign(task, null, outEmployee));
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
				result.setMessage(e.getMessage());
			}
		}else {
			result.setResultCode(ResultCode.E_TASK_ASSIGNED);
			result.setMessage("task is assigned");
		}
		return result;
	}
	
	private boolean checkTaskAssigned(int taskId, int memberId, int outEmployeeId, int companyId) {
		if((taskId <= 0) || (memberId <= 0 && outEmployeeId <= 0)) {
			throw new IllegalArgumentException();
		}

		Task task = taskDao.getById(taskId);
		if(null == task) {
			throw new IllegalArgumentException();
		}
		
		List<Task> taskList = null;
		if(memberId > 0) {
			taskList = taskDao.getTasksByMember(memberId);
		}else {
			taskList = taskDao.getTasksByOutEmployee(companyId, outEmployeeId);
		}
		
		for(Task taskItem : taskList) {
			if(taskItem.getId() == taskId) {
				return true;
			}
		}
		return false;
	}
}
