package edu.nju.software.controller;

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

import edu.nju.software.pojo.Project;
import edu.nju.software.service.ProjectService;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataJsonResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
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
	
	@RequestMapping(value = {"/EditProject"}, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult updateProject(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		return null;
	}
	
	@RequestMapping(value = {"/DeleteProject"}, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult deleteProject(HttpServletRequest request, HttpServletResponse response) {
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);
		NoDataResult result = projectService.delete(new Project(projectId));
		return new NoDataJsonResult(result);
	}
}
