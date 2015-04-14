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
import edu.nju.software.pojo.Project;
import edu.nju.software.pojo.Task;
import edu.nju.software.pojo.TaskStatus;
import edu.nju.software.service.ProjectService;
import edu.nju.software.service.TaskService;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataJsonResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Controller
public class WorkController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value = {"/GetProjectInfo"}, method = RequestMethod.GET)
	public ModelAndView getProjectInfo(HttpServletRequest request, HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		int companyId = CoUtils.getRequestIntValue(request, "companyId", false);
		
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<Project> projectResult = null;
		if(companyId == 0) {
			projectResult = projectService.getById(projectId);
		}else {
			projectResult = projectService.getByCompanyAndId(companyId, projectId);
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
		GeneralResult<List<Project>> projectResult = projectService.getByCompany(companyId);
		if(projectResult.getResultCode() == ResultCode.NORMAL) {
			Map<Project, List<Task>> workMap = new HashMap<Project, List<Task>>();
			for(Project project : projectResult.getData()) {
				if(null == project) {
					continue;
				} 
				GeneralResult<List<Task>> taskResult = taskService.getByProject(project.getId());
				if(taskResult.getResultCode() == ResultCode.NORMAL) {
					workMap.put(project, taskResult.getData());
				}else {
					workMap.put(project, null);
				}
			}
			model.put("works", workMap);
		}
		
		return new ModelAndView("workList", "model", model);
	}
	
	@RequestMapping(value = {"/ProjectList"}, method = RequestMethod.GET)
	public ModelAndView getProjectList(HttpServletRequest request, HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<List<Project>> projectResult = projectService.getByCompany(companyId);
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
		
		NoDataResult result = projectService.update(project);
		return new NoDataJsonResult(result);
	}
	
	@RequestMapping(value = {"/DeleteProject"}, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult deleteProject(HttpServletRequest request, HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		NoDataResult result = projectService.delete(new Project(projectId));
		return new NoDataJsonResult(result);
	}
	
	
	@RequestMapping(value = {"/TaskList"}, method = RequestMethod.GET)
	public ModelAndView getTaskList(HttpServletRequest request, HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<List<Task>> taskResult = taskService.getByProject(projectId);
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
			taskResult = taskService.getById(taskId);
		}else {
			taskResult = taskService.getByProjectAndId(projectId, taskId);
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
		
		NoDataResult result = taskService.update(task);
		return new NoDataJsonResult(result);
	}
	
	@RequestMapping(value = {"/DeleteTask"}, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult deleteTask(HttpServletRequest request, HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		NoDataResult result = taskService.delete(new Task(taskId));
		return new NoDataJsonResult(result);
	}
}
