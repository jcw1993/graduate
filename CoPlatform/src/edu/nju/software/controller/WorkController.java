package edu.nju.software.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.nju.software.pojo.Company;
import edu.nju.software.pojo.Member;
import edu.nju.software.pojo.OutEmployee;
import edu.nju.software.pojo.Project;
import edu.nju.software.pojo.Task;
import edu.nju.software.pojo.TaskStatus;
import edu.nju.software.service.MemberService;
import edu.nju.software.service.OutEmployeeService;
import edu.nju.software.service.WorkService;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataJsonResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Controller
public class WorkController {
	
	@Autowired
	private WorkService workService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private OutEmployeeService outEmployeeService;
	
	@RequestMapping(value = {"/GetProjectInfo"}, method = RequestMethod.GET)
	public ModelAndView getProjectInfo(HttpServletRequest request, HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		int companyId = CoUtils.getRequestIntValue(request, "companyId", false);
		
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<Project> projectResult = null;
		if(companyId == 0) {
			projectResult = workService.getProjectById(projectId);
		}else {
			projectResult = workService.getProjectByCompanyAndId(companyId, projectId);
		}
	
		if(projectResult.getResultCode() == ResultCode.NORMAL) {
			model.put("project", projectResult.getData());
		}
		return new ModelAndView("projectInfo", "model", model);
	}
	
	@RequestMapping(value = {"/WorkList"}, method = RequestMethod.GET)
	public ModelAndView getWorkList(HttpServletRequest request, HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<List<Project>> projectResult = workService.getProjectsByCompany(companyId);
		if(projectResult.getResultCode() == ResultCode.NORMAL) {
			Map<Project, List<Task>> workMap = new HashMap<Project, List<Task>>();
			for(Project project : projectResult.getData()) {
				if(null == project) {
					continue;
				} 
				GeneralResult<List<Task>> taskResult = workService.getTasksByProject(project.getId());
				if(taskResult.getResultCode() == ResultCode.NORMAL) {
					workMap.put(project, taskResult.getData());
				}else {
					workMap.put(project, null);
				}
			}
			model.put("works", workMap);
		}
		
		GeneralResult<List<Member>> memberResult = memberService.getAllByCompany(companyId);
		if(memberResult.getResultCode() == ResultCode.NORMAL) {
			model.put("members", memberResult.getData());
		}
		
		GeneralResult<List<OutEmployee>> outEmployeeResult = outEmployeeService.getByCompany(companyId);
		if(outEmployeeResult.getResultCode() == ResultCode.NORMAL) {
			model.put("outEmployees", outEmployeeResult.getData());
		}
		
		return new ModelAndView("workList", "model", model);
	}
	
	@RequestMapping(value = {"/ProjectList"}, method = RequestMethod.GET)
	public ModelAndView getProjectList(HttpServletRequest request, HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<List<Project>> projectResult = workService.getProjectsByCompany(companyId);
		if(projectResult.getResultCode() == ResultCode.NORMAL) {
			model.put("projects", projectResult.getData());
		}
		
		return new ModelAndView("projectList", "model", model);
	}
	
	@RequestMapping(value = {"/UpdateProject"}, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult updateProject(HttpServletRequest request, HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		double progress = CoUtils.getRequestDoubleValue(request, "progress", true);
		if(null != name) {
			name = name.trim();
		}
		if(null != description) {
			description = description.trim();
		}
		if(null != startTime) {
			startTime = startTime.trim();
		}
		if(null != endTime) {
			endTime = endTime.trim();
		}
		
		Project project = new Project(projectId, new Company(companyId), name, description, new Date(), new Date(), progress);
		
		NoDataResult result = workService.updateProject(project);
		return new NoDataJsonResult(result);
	}
	
	@RequestMapping(value = {"/DeleteProject"}, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult deleteProject(HttpServletRequest request, HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		int companyId = CoUtils.getRequestIntValue(request, "companyId", false);
		
		Company company = null;
		if(companyId == 0) {
			GeneralResult<Project> projectResult = workService.getProjectById(projectId);
			if(projectResult.getResultCode() == ResultCode.NORMAL) {
				company = projectResult.getData().getCompany();
			}else {
				throw new IllegalArgumentException();
			}
		}else {
			company = new Company(companyId);
		}
		
		
		Project project = new Project(projectId);
		project.setCompany(company);
		
		NoDataResult result = workService.deleteProject(project);
		return new NoDataJsonResult(result);
	}
	
	
	@RequestMapping(value = {"/TaskList"}, method = RequestMethod.GET)
	public ModelAndView getTaskList(HttpServletRequest request, HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<List<Task>> taskResult = workService.getTasksByProject(projectId);
		if(taskResult.getResultCode() == ResultCode.NORMAL) {
			model.put("tasks", taskResult.getData());
		}
		
		return new ModelAndView("taskList", "model", model);
	}
	
	@RequestMapping(value = {"/GetTaskInfo"}, method = RequestMethod.GET)
	public ModelAndView getTaskInfo(HttpServletRequest request, HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		int projectId = CoUtils.getRequestIntValue(request, "projectId", false);
		
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<Task> taskResult = null;
		if(projectId == 0) {
			taskResult = workService.getTaskById(taskId);
		}else {
			taskResult = workService.getTaskByProjectAndId(projectId, taskId);
		}
	
		if(taskResult.getResultCode() == ResultCode.NORMAL) {
			model.put("task", taskResult.getData());
		}
		return new ModelAndView("taskInfo", "model", model);
	}
	
	@RequestMapping(value = {"/UpdateTask"}, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult updateTask(HttpServletRequest request, HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		int status = CoUtils.getRequestIntValue(request, "status", true);
		
		if(null != name) {
			name = name.trim();
		}
		if(null != description) {
			description = description.trim();
		}
		if(null != startTime) {
			startTime = startTime.trim();
		}
		if(null != endTime) {
			endTime = endTime.trim();
		}
		
		Task task = new Task(taskId, new Project(projectId), name, description, null ,new TaskStatus(status), new Date(), new Date());
		
		NoDataResult result = workService.updateTask(task);
		return new NoDataJsonResult(result);
	}
	
	@RequestMapping(value = {"/DeleteTask"}, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult deleteTask(HttpServletRequest request, HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		int projectId = CoUtils.getRequestIntValue(request, "projectId", false);
		
		Project project = null;
		if(projectId == 0) {
			GeneralResult<Task> taskResult = workService.getTaskById(taskId);
			if(taskResult.getResultCode() == ResultCode.NORMAL) {
				project = taskResult.getData().getProject();
			}else {
				throw new IllegalArgumentException();
			}
		}else {
			project = new Project(projectId);
		}
		
		Task task = new Task(taskId);
		task.setProject(project);
		NoDataResult result = workService.deleteTask(task);
		return new NoDataJsonResult(result);
	}
	
	@RequestMapping(value = {"/MemberTaskAssign"}, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult memberTaskAssign(HttpServletRequest request, HttpServletResponse response) {
		int memberId = CoUtils.getRequestIntValue(request, "memberId", true);
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		
		NoDataResult taskAssignResult = workService.assignTaskToMember(taskId, memberId);
		return new NoDataJsonResult(taskAssignResult);
	}
	
	@RequestMapping(value = {"/OutEmployeeTaskAssign"}, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult outEmployeeTaskAssign(HttpServletRequest request, HttpServletResponse response) {
		int outEmployeeId = CoUtils.getRequestIntValue(request, "outEmployeeId", true);
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		
		NoDataResult taskAssignResult = workService.assignTaskToOutEmployee(taskId, outEmployeeId);
		return new NoDataJsonResult(taskAssignResult);
	}
}
