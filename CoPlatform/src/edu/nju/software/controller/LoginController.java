package edu.nju.software.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;

import edu.nju.software.pojo.Admin;
import edu.nju.software.service.AdminService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Controller
public class LoginController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value = {"/Login", "/login"}, method = RequestMethod.GET)
	public ModelAndView loginView(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return new ModelAndView("login", null);
	}
	
	@RequestMapping(value = {"/Login", "/login"}, method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		if(StringUtils.isBlank(mail) || StringUtils.isBlank(password)) {
			response.sendRedirect(request.getContextPath() + "/" + "Login");
			return;
		}
		
		mail = mail.trim();
		password = password.trim();
		GeneralResult<Admin> adminResult = adminService.getByMailAndPassword(mail, password);
		if(adminResult.getResultCode() == ResultCode.NORMAL) {
			response.sendRedirect(request.getContextPath() + "/" + "Home");
		}else {
			response.sendRedirect(request.getContextPath() + "/" + "Login");
		}
		
	}

	@RequestMapping(value = {"/Logout", "/logout"})
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		response.addHeader("Cache-Control", "no-cache");
		return;
	}
}
