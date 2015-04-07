package edu.nju.software.service;

import edu.nju.software.pojo.Project;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;

public interface ProjectService {
	public GeneralResult<Project> getById(int id);
	
	public GeneralResult<Integer> create(Project project);
	
	public NoDataResult update(Project project);
	
	public NoDataResult delete(int id);
}
