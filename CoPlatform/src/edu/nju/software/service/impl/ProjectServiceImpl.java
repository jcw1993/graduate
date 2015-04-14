package edu.nju.software.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import edu.nju.software.dao.ProjectDao;
import edu.nju.software.pojo.Project;
import edu.nju.software.service.ProjectService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	private static final String COMPANY_PROJECT_CACHE_KEY = "company_project_cache_key_%d";
	
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
			return result;
		}
		
		CoCacheManager.remove(String.format(COMPANY_PROJECT_CACHE_KEY, project.getCompany().getId()));
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
			return result;
		}
		
		CoCacheManager.remove(String.format(COMPANY_PROJECT_CACHE_KEY, project.getCompany().getId()));
		CoCacheManager.remove(String.format(PROJECT_CACHE_KEY_FOMAT, project.getId()));
		return result;
	}

	@Override
	public NoDataResult delete(Project project) {
		NoDataResult result = new NoDataResult();
		try {
			projectDao.delete(project);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}
		
		CoCacheManager.remove(String.format(COMPANY_PROJECT_CACHE_KEY, project.getCompany().getId()));
		CoCacheManager.remove(String.format(PROJECT_CACHE_KEY_FOMAT, project.getId()));
		return result;
	}

	@Override
	public GeneralResult<List<Project>> getByCompany(int companyId) {
		GeneralResult<List<Project>> result = new GeneralResult<List<Project>>();
		@SuppressWarnings("unchecked")
		List<Project> projectList = (List<Project>) CoCacheManager.get(String.format(COMPANY_PROJECT_CACHE_KEY, companyId));
		if(null != projectList && !projectList.isEmpty()) {
			result.setData(projectList);
		}else {
			try {
				projectList = projectDao.getByCompany(companyId);
				if(null != projectList && !projectList.isEmpty()) {
					result.setData(projectList);
					CoCacheManager.put(String.format(COMPANY_PROJECT_CACHE_KEY, companyId), projectList);
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
	public GeneralResult<Project> getByCompanyAndId(int companyId, int projectId) {
		GeneralResult<Project> result = new GeneralResult<Project>();
		GeneralResult<List<Project>> companyProjctResult = getByCompany(companyId);
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

}
