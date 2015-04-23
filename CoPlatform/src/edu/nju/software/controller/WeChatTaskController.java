package edu.nju.software.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import edu.nju.software.pojo.Member;
import edu.nju.software.pojo.Task;
import edu.nju.software.pojo.TaskStatus;
import edu.nju.software.service.MemberService;
import edu.nju.software.service.WorkService;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataJsonResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Controller
public class WeChatTaskController {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(WorkController.class);
	
	@SuppressWarnings("unused")
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	private MemberService memberService;
	@Autowired
	private WorkService workService;

	@RequestMapping(value = { "/wechat/MyTasks" }, method = RequestMethod.GET)
	public ModelAndView wxMemberTaskList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String memberCookieValue = CoUtils.getCookie(request, "currentMember");
		if(null != memberCookieValue) {
			@SuppressWarnings("deprecation")
			Member member = new Gson().fromJson(URLDecoder.decode(memberCookieValue), Member.class);
			if (null != member) {
				Map<String, Object> model = new HashMap<String, Object>();
				GeneralResult<List<Task>> taskResult = memberService.getTasks(member.getId());
				if (taskResult.getResultCode() == ResultCode.NORMAL) {
					model.put("wxtasks", taskResult.getData());
				}
				return new ModelAndView("wechat/taskList", "model", model);
			}else {
				response.sendRedirect(request.getContextPath() + "/wechat");
				return null;
			}
		}else {
			response.sendRedirect(request.getContextPath() + "/wechat");
			return null;
		}
	}

	// TODO
	@RequestMapping(value = { "/wechat/TaskInfo" }, method = RequestMethod.GET)
	public ModelAndView wxTaskInfo(HttpServletRequest request,
			HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);

		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<Task> taskResult = null;
		taskResult = workService.getTaskById(taskId);

		if (taskResult.getResultCode() == ResultCode.NORMAL) {
			model.put("wxtask", taskResult.getData());
		}
		return new ModelAndView("wechat/taskInfo", "model", model);
	}

	// TODO
	@RequestMapping(value = { "/wechat/UpdateTask" }, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult wxUpdateTask(HttpServletRequest request,
			HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		int status = CoUtils.getRequestIntValue(request, "status", true);

		Task task = workService.getTaskById(taskId).getData();
		task.setStatus(new TaskStatus(status));

		NoDataResult result = workService.updateTask(task);
		return new NoDataJsonResult(result);
	}

}
