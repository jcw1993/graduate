package edu.nju.software.service;

import java.util.List;

import edu.nju.software.pojo.Project;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;

public interface ProjectService {
	public GeneralResult<List<Project>> getByCompany(int companyId);
	
	public GeneralResult<Project>  getByCompanyAndId(int companyId, int projectId);
	
	public GeneralResult<Project> getById(int id);
	
	public GeneralResult<Integer> create(Project project);
	
	public NoDataResult update(Project project);
	
	public NoDataResult delete(Project project);
}
