package edu.nju.software.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import edu.nju.software.dao.ProjectDao;
import edu.nju.software.pojo.Project;
import edu.nju.software.service.ProjectService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

public class ProjectServiceImpl implements ProjectService {
	
	private static final String PROJECT_CACHE_KEY_FOMAT = "project_%d";
	
	private static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
	
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public GeneralResult<Project> getById(int id) {
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
	public GeneralResult<Integer> create(Project project) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = projectDao.create(project);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoDataResult update(Project project) {
		NoDataResult result = new NoDataResult();
		try {
			projectDao.update(project);
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
			projectDao.delete(id);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

}
