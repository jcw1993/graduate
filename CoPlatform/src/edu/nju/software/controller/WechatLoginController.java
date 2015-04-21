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

import edu.nju.software.pojo.Member;
import edu.nju.software.service.MemberService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Controller
public class WechatLoginController {

	@Autowired
	private MemberService memberService;

	@RequestMapping(value = { "/wechat" }, method = RequestMethod.GET)
	public ModelAndView wxLoginView(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String openId = request.getParameter("openid");
		HttpSession session = request.getSession(true);

		// 无效openid则转向错误界面，错误信息存入session
		if (null == openId || StringUtils.isBlank(openId)) {
			session.setAttribute("errorMsg", "wxLogin: invalid openId.");
			return new ModelAndView("wechat/error", null);
		}

		session.setAttribute("openid", openId);
		return new ModelAndView("wechat/login", null);
	}

	@RequestMapping(value = { "/wechat" }, method = RequestMethod.POST)
	public void wxLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");

		HttpSession session = request.getSession(true);
		String openId = (String) session.getAttribute("openid");

		String redirectPath = "mytasks";
		// redirectPath = (null == redirectPath) ? "mytasks" : redirectPath;

		if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
			response.sendRedirect(request.getContextPath() + "/wechat");
			return;
		}

		// 其他页面中，openid在数据库找不到，则转向wechat的login界面
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

	@RequestMapping(value = { "/wechat/error" }, method = RequestMethod.GET)
	public ModelAndView error(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/wechat/error", null);
	}

}
