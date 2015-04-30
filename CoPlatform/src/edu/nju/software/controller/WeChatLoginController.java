package edu.nju.software.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import edu.nju.software.pojo.Member;
import edu.nju.software.service.MemberService;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Controller
public class WeChatLoginController {

	@Autowired
	private MemberService memberService;

	@RequestMapping(value = { "/wechat" }, method = RequestMethod.GET)
	public ModelAndView wxLoginView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String openId = request.getParameter("openId");
		if (StringUtils.isBlank(openId)) {
			response.sendRedirect(request.getContextPath() + "/Error");
			return null;
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("openId", openId.trim());

		return new ModelAndView("wechat/login", "model", model);
	}

	@RequestMapping(value = { "/wechat" }, method = RequestMethod.POST)
	public void wxLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String openId = request.getParameter("openId");

		if (StringUtils.isBlank(openId)) {
			response.sendRedirect(request.getContextPath() + "/wechat?openId="
					+ openId);
			return;
		}

		if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
			response.sendRedirect(request.getContextPath() + "/wechat/Error");
			return;
		}

		phone = phone.trim();
		password = password.trim();
		openId = openId.trim();

		GeneralResult<Member> memberResult = memberService
				.getByPhoneAndPassword(phone, password);
		if (memberResult.getResultCode() == ResultCode.NORMAL) {
			Member member = memberResult.getData();
			member.setOpenId(openId);
			memberService.update(member);
			Gson gson = new Gson();
			CoUtils.addCookie(response, "currentMember",
					URLEncoder.encode(gson.toJson(member), "UTF-8"), 3600);

			response.sendRedirect(request.getContextPath()
					+ "/wechat/MyTasks?openId=" + openId);
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/wechat?openId="
					+ openId);
			return;
		}

	}

	@RequestMapping(value = { "/wechat/Error" }, method = RequestMethod.GET)
	public ModelAndView error(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/wechat/error", null);
	}

}
