package edu.nju.software.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import edu.nju.software.dao.TaskDao;
import edu.nju.software.pojo.Task;
import edu.nju.software.service.TaskService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

public class TaskServiceImpl implements TaskService {
	private static final String PROJECT_TASK_CACHE_KEY = "PROJECT_TASK_%d";
	private static final String TASK_CACHE_KEY = "TASK_%d";
	
	private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
	
	@Autowired
	private TaskDao taskDao;
	
	@Override
	public GeneralResult<Integer> create(Task task) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = taskDao.create(task);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoDataResult update(Task task) {
		NoDataResult result = new NoDataResult();
		try {
			taskDao.update(task);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoDataResult delete(int id) {
		NoDataResult result = new NoDataResult();
		try {
			taskDao.delete(id);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<List<Task>> getByProject(int projectId) {
		GeneralResult<List<Task>> result = new GeneralResult<List<Task>>();
		@SuppressWarnings("unchecked")
		List<Task> taskList = (List<Task>) CoCacheManager.get(String.format(PROJECT_TASK_CACHE_KEY, projectId));
		if(null != taskList && !taskList.isEmpty()) {
			result.setData(taskList);
		}else {
			try {
				taskList = taskDao.getByProject(projectId);
				if(null != taskList && !taskList.isEmpty()) {
					result.setData(taskList);
					CoCacheManager.put(String.format(PROJECT_TASK_CACHE_KEY, projectId), taskList);
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
	public GeneralResult<Task> getByProjectAndId(int projectId, int taskId) {
		GeneralResult<Task> result = new GeneralResult<Task>();
		GeneralResult<List<Task>> projectTaskResult = getByProject(projectId);
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
	public GeneralResult<Task> getById(int id) {
		GeneralResult<Task> result = new GeneralResult<Task>();
		Task task = (Task) CoCacheManager.get(String.format(TASK_CACHE_KEY, id));
		if(null != task) {
			result.setData(task);
		}else {
			try {
				task = taskDao.getById(id);
				result.setData(task);
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<Task> getParent(int parentId) {
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

}
