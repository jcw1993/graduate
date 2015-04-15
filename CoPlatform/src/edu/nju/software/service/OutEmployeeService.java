package edu.nju.software.service;

import java.util.List;

import edu.nju.software.pojo.Company;
import edu.nju.software.pojo.OutEmployee;
import edu.nju.software.pojo.Task;
import edu.nju.software.util.GeneralResult;

public interface OutEmployeeService {
	
	public GeneralResult<List<OutEmployee>> getByCompany(int companyId);
	
	public GeneralResult<List<Company>> getRelatedCompanies(int outEmployeeId);
	
	public GeneralResult<List<Task>> getTasks(int companyId, int outEmployeeId);
	
	public GeneralResult<OutEmployee> getById(int outEmployeeId);
}
