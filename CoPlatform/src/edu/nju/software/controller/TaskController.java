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
import org.springframework.web.portlet.ModelAndView;

import edu.nju.software.pojo.Task;
import edu.nju.software.service.TaskService;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataJsonResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Controller
public class TaskController {
	
	@Autowired
	private TaskService taskService;
		
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
	
	@RequestMapping(value = {"/EditTask"}, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult updateTask(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		return null;
	}
	
	@RequestMapping(value = {"/DeleteTask"}, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult deleteTask(HttpServletRequest request, HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		NoDataResult result = taskService.delete(new Task(taskId));
		return new NoDataJsonResult(result);
	}
}
