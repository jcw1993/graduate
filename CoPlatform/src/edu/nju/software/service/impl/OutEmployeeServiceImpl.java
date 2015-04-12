package edu.nju.software.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import edu.nju.software.dao.OutEmployeeDao;
import edu.nju.software.pojo.Company;
import edu.nju.software.pojo.OutEmployee;
import edu.nju.software.service.OutEmployeeService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Service
public class OutEmployeeServiceImpl implements OutEmployeeService {
	private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	private static final String COMPANY_OUT_EMPLOYEE_CACHE_KEY = "company_out_employee_cache_key_%d";
	
	private static final String OUT_EMPLOYEE_RELATED_COMPANY_CACHE_KEY = "out_employee_related_company_cache_key_%d";
	
	@Autowired
	private OutEmployeeDao outEmployeeDao;
	
	@Override
	public GeneralResult<List<OutEmployee>> getByCompany(int companyId) {
		GeneralResult<List<OutEmployee>> result = new GeneralResult<List<OutEmployee>>();
		@SuppressWarnings("unchecked")
		List<OutEmployee> outEmployeeList = (List<OutEmployee>) CoCacheManager.get(String.format(COMPANY_OUT_EMPLOYEE_CACHE_KEY, companyId));
		if(null != outEmployeeList && !outEmployeeList.isEmpty()) {
			result.setData(outEmployeeList);
		}else {
			try {
				outEmployeeList = outEmployeeDao.getByCompany(companyId);
				if(null != outEmployeeList && !outEmployeeList.isEmpty()) {
					result.setData(outEmployeeList);
					CoCacheManager.put(String.format(COMPANY_OUT_EMPLOYEE_CACHE_KEY, companyId), outEmployeeList);
				}else {
					result.setResultCode(ResultCode.E_NO_DATA);
					result.setMessage("no out employee data in company, companyId = " + companyId);
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
	public GeneralResult<List<Company>> getRelatedCompanies(int outEmployeeId) {
		GeneralResult<List<Company>> result = new GeneralResult<List<Company>>();
		@SuppressWarnings("unchecked")
		List<Company> companyList = (List<Company>) CoCacheManager.get(String.format(OUT_EMPLOYEE_RELATED_COMPANY_CACHE_KEY, outEmployeeId));
		if(null != companyList && !companyList.isEmpty()) {
			result.setData(companyList);
		}else {
			try {
				companyList = outEmployeeDao.getRelatedCompanies(outEmployeeId);
				if(null != companyList && !companyList.isEmpty()) {
					result.setData(companyList);
					CoCacheManager.put(String.format(OUT_EMPLOYEE_RELATED_COMPANY_CACHE_KEY, outEmployeeId), companyList);
				}else {
					result.setResultCode(ResultCode.E_NO_DATA);
					result.setMessage("no related company data, outEmployeeId = " + outEmployeeId);
				}
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

}