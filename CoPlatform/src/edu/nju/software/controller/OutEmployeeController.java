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

import edu.nju.software.pojo.OutEmployee;
import edu.nju.software.service.OutEmployeeService;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Controller
public class OutEmployeeController {
	
	@Autowired
	private OutEmployeeService outEmployeeService;
	
	@RequestMapping(value = {"/OutEmployeeList"}, method = RequestMethod.GET)
	public ModelAndView getOutEmployeeList(HttpServletRequest request, HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<List<OutEmployee>> outEmployeeResult = outEmployeeService.getByCompany(companyId);
		if(outEmployeeResult.getResultCode() == ResultCode.NORMAL) {
			model.put("outEmployees",outEmployeeResult.getData());
		}
		
		return new ModelAndView("outEmployeeList", "model", model);
	}
}