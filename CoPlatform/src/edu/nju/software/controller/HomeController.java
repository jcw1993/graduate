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
import org.springframework.web.servlet.ModelAndView;

import edu.nju.software.pojo.Admin;
import edu.nju.software.service.AdminService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Controller
public class HomeController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value = { "/Home", "/home" }, method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<List<Admin>> adminResult = adminService.getAll();
		if(adminResult.getResultCode() == ResultCode.NORMAL) {
			model.put("admins", adminResult.getData());
		}
		return new ModelAndView("index", "model", model);
	}
}
