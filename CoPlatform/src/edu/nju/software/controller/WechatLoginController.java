package edu.nju.software.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//import test.LoggerUtils;
import edu.nju.software.pojo.Member;
import edu.nju.software.service.MemberService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Controller
public class WechatLoginController {
//	org.apache.log4j.Logger log=LoggerUtils.getLogger(WechatLoginController.class, "log1.txt", true);

	@Autowired
	private MemberService memberService;

	@RequestMapping(value = { "/wechat" }, method = RequestMethod.GET)
	public ModelAndView wxLoginView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//url所带参数放入session
		HttpSession session = request.getSession(true);
		String query[]=request.getQueryString().split("=");
		session.setAttribute(query[0], query[1]);
		return new ModelAndView("wechat/login", null);
	}

	@RequestMapping(value = { "/wechat" }, method = RequestMethod.POST)
	public void wxLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession(true);
		String openId = (String) session.getAttribute("openid");
		String redirectPath = null;

		redirectPath = (null == redirectPath) ? "mytasks" : redirectPath;

		
//		log.info("phone" + phone + ", openid=" + openId);

		if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
			response.sendRedirect(request.getContextPath() + "/wechat");
			return;
		}

		// TODO 验证openId，redirectPath，然后？

		// 其他页面中，openid在数据库找不到，则转向wechat页面，即微信的login界面
		// 验证成功后，在session中存入member，将openid插入数据库，然后跳转
		phone = phone.trim();
		password = password.trim();
		GeneralResult<Member> memberResult = memberService
				.getByPhoneAndPassword(phone, password);
		if (memberResult.getResultCode() == ResultCode.NORMAL) {
			Member member = memberResult.getData();
			member.setOpenId(openId);
			session.setAttribute("wechatMember", member);
			memberService.update(member);
			response.sendRedirect(request.getContextPath() + "/wechat/"
					+ redirectPath);
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/wechat");
			return;
		}

	}

}
