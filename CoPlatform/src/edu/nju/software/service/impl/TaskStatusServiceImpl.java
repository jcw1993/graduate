package edu.nju.software.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import edu.nju.software.dao.TaskStatusDao;
import edu.nju.software.pojo.TaskStatus;
import edu.nju.software.service.TaskStatusService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

public class TaskStatusServiceImpl implements TaskStatusService {
	private static Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	private static final String ALL_TASK_STATUS_CACHE_KEY = "all_task_status_cache";
	
	@Autowired
	private TaskStatusDao taskStatusDao;
	
	@Override
	public GeneralResult<List<TaskStatus>> getAll() {
		GeneralResult<List<TaskStatus>> result = new GeneralResult<List<TaskStatus>>();
		@SuppressWarnings("unchecked")
		List<TaskStatus> taskStatusList = (List<TaskStatus>) CoCacheManager.get(ALL_TASK_STATUS_CACHE_KEY);
		if(null != taskStatusList && !taskStatusList.isEmpty()) {
			result.setData(taskStatusList);
		}else {
			try {
				taskStatusList = taskStatusDao.getAll();
				if(null != taskStatusList && !taskStatusList.isEmpty()) {
					result.setData(taskStatusList);
					CoCacheManager.put(ALL_TASK_STATUS_CACHE_KEY, taskStatusList);
				}else {
					result.setResultCode(ResultCode.E_NO_DATA);
				}
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setMessage(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			}
		}
		return result;
	}

}
