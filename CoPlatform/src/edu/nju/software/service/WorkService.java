package edu.nju.software.service;

import java.util.HashMap;
import java.util.List;

import edu.nju.software.pojo.Member;
import edu.nju.software.pojo.Project;
import edu.nju.software.pojo.Task;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;

public interface WorkService {
	public GeneralResult<List<Project>> getProjectsByCompany(int companyId);
	
	public GeneralResult<Project>  getProjectByCompanyAndId(int companyId, int projectId);
	
	public GeneralResult<Project> getProjectById(int id);
	
	public GeneralResult<Integer> createProject(Project project);
	
	public NoDataResult updateProject(Project project);
	
	public NoDataResult deleteProject(Project project);
	
	
	public GeneralResult<Integer> createTask(Task task);
	
	public NoDataResult updateTask(Task task);
	
	public NoDataResult deleteTask(Task task);
	
	public GeneralResult<List<Task>> getTasksByProject(int projectId);
	
	public GeneralResult<Task> getTaskByProjectAndId(int projectId, int taskId);
	
	public GeneralResult<Task> getTaskById(int id);
	
	public GeneralResult<Task> getParentTask(int parentId);
	
	public NoDataResult assignTaskToMember(int taskId, int memberId);
	
	public NoDataResult assignTaskToOutEmployee(int taskId, int outEmployeeId, int companyId);
	
	@SuppressWarnings("rawtypes")
	public GeneralResult<HashMap> getTasksWithChildrenByProject(int projectId);
	
	public GeneralResult<List<Member>> getRelatedMembers(int taskId);
}
