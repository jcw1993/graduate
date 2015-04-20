package edu.nju.software.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.nju.software.pojo.Admin;
import edu.nju.software.pojo.Member;
import edu.nju.software.pojo.Project;
import edu.nju.software.pojo.Task;
import edu.nju.software.pojo.TaskStatus;
import edu.nju.software.service.AdminService;
import edu.nju.software.service.MemberService;
import edu.nju.software.service.WorkService;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataJsonResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Controller
public class WechatTaskController {
	private static Logger logger = LoggerFactory
			.getLogger(WorkController.class);

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	private MemberService memberService;
	@Autowired
	private WorkService workService;

	@RequestMapping(value = { "/wechat/mytasks" }, method = RequestMethod.GET)
	public ModelAndView wxMemberTaskList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String openId = request.getParameter("openid");
		// TODO 验证openId，然后？

		HttpSession session = request.getSession(true);
		Member member = (Member) session.getAttribute("wechatMember");
		String currentOpenId = member.getOpenId();

		// 如果session中的用户不是当前用户
		if (null == currentOpenId || StringUtils.isBlank(currentOpenId)
				|| !currentOpenId.equals(openId)) {
			GeneralResult<Member> memberResult = memberService
					.getByOpenId(openId);

			// 如果用户记录过openid，则放入session；否则转到微信登陆界面，并附带重定向路径
			if (memberResult.getResultCode() == ResultCode.NORMAL) {
				member = memberResult.getData();
				session.setAttribute("wechatMember", member);
			} else {
				String redirectPath = "mytasks";
				// TODO 是返回null还是什么？？
				response.sendRedirect(request.getContextPath()
						+ "/wechat?openid=" + openId + "&redirectpath="
						+ redirectPath);
				return null;
			}
		}

		int memberId = member.getId();
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<List<Task>> taskResult = memberService.getTasks(memberId);
		if (taskResult.getResultCode() == ResultCode.NORMAL) {
			model.put("wxtasks", taskResult.getData());
		}
		return new ModelAndView("wechat/taskList", "model", model);
	}

	//TODO
	@RequestMapping(value = { "/wechat/taskInfo" }, method = RequestMethod.GET)
	public ModelAndView wxTaskInfo(HttpServletRequest request,
			HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		int projectId = CoUtils.getRequestIntValue(request, "projectId", false);

		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<Task> taskResult = null;
		if (projectId == 0) {
			taskResult = workService.getTaskById(taskId);
		} else {
			taskResult = workService.getTaskByProjectAndId(projectId, taskId);
		}

		if (taskResult.getResultCode() == ResultCode.NORMAL) {
			model.put("task", taskResult.getData());
		}
		return new ModelAndView("taskInfo", "model", model);
	}

	//TODO
	@RequestMapping(value = { "/wechat/updatetask" }, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult wxUpdateTask(HttpServletRequest request,
			HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskId", true);
		int projectId = CoUtils.getRequestIntValue(request, "projectId", true);

		String name = request.getParameter("name");
		String description = request.getParameter("description");

		String startDateStr = request.getParameter("startDate").trim();
		String startTimeStr = request.getParameter("startTime").trim();
		String endDateStr = request.getParameter("endDate").trim();
		String endTimeStr = request.getParameter("endTime").trim();
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = CoUtils.parseDate(startDateStr + " " + startTimeStr,
					DATE_FORMAT);
			endDate = CoUtils.parseDate(endDateStr + " " + endTimeStr,
					DATE_FORMAT);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return new NoDataJsonResult(ResultCode.E_OTHER_ERROR, null);
		}

		int status = CoUtils.getRequestIntValue(request, "status", true);

		if (null != name) {
			name = name.trim();
		}
		if (null != description) {
			description = description.trim();
		}

		Task task = new Task(taskId, new Project(projectId), name, description,
				null, new TaskStatus(status), startDate, endDate);

		NoDataResult result = workService.updateTask(task);
		return new NoDataJsonResult(result);
	}

}
