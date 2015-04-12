package edu.nju.software.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import edu.nju.software.dao.LogDao;
import edu.nju.software.pojo.Log;
import edu.nju.software.service.LogService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Service
public class LogServiceImpl implements LogService {
	private static Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
	
	@Autowired
	private LogDao logDao;

	@Override
	public GeneralResult<List<Log>> getByProject(int projectId, Date startTime,
			Date endTime) {
		GeneralResult<List<Log>> result = new GeneralResult<List<Log>>();
		try {
			List<Log> logList = logDao.search(projectId, 0, startTime, endTime);
			if(null != logList && !logList.isEmpty()) {
				result.setData(logList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
			}
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<List<Log>> getByTask(int taskId, Date startTime,
			Date endTime) {
		GeneralResult<List<Log>> result = new GeneralResult<List<Log>>();
		try {
			List<Log> logList = logDao.search(0, taskId, startTime, endTime);
			if(null != logList && !logList.isEmpty()) {
				result.setData(logList);
			}else {
				result.setResultCode(ResultCode.E_NO_DATA);
			}
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<Integer> create(Log log) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = logDao.create(log);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
}
