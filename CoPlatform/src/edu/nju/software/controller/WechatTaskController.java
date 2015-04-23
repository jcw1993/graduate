package edu.nju.software.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class WechatTaskController {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory
			.getLogger(WorkController.class);
	
//	org.apache.log4j.Logger log=LoggerUtils.getLogger(WechatTaskController.class, "log1.txt", true);

	@SuppressWarnings("unused")
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	private MemberService memberService;
	@Autowired
	private WorkService workService;

	@RequestMapping(value = { "/wechat/mytasks" }, method = RequestMethod.GET)
	public ModelAndView wxMemberTaskList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String openId = request.getParameter("openid");
//		log.info("task:openid= "+openId);
		HttpSession session = request.getSession(true);

		// 无效openid则转向错误界面，错误信息存入session
		if (null == openId || StringUtils.isBlank(openId)) {
			session.setAttribute("errorMsg", "wxLogin: invalid openId.");
			return new ModelAndView("wechat/error", null);
		}
		Member member = (Member) session.getAttribute("wechatMember");

		// 如果session中的用户不是当前用户
		if (null == member || null == member.getOpenId() || !member.getOpenId().equals(openId)) {
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

	// TODO
	@RequestMapping(value = { "/wechat/taskinfo" }, method = RequestMethod.GET)
	public ModelAndView wxTaskInfo(HttpServletRequest request,
			HttpServletResponse response) {
		int taskId = CoUtils.getRequestIntValue(request, "taskid", true);

		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<Task> taskResult = null;
		taskResult = workService.getTaskById(taskId);

		if (taskResult.getResultCode() == ResultCode.NORMAL) {
			model.put("wxtask", taskResult.getData());
		}
		return new ModelAndView("wechat/taskInfo", "model", model);
	}

	// TODO
	@RequestMapping(value = { "/wechat/updatetask" }, method = RequestMethod.POST)
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
