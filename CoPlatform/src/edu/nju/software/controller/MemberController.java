package edu.nju.software.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.nju.software.pojo.Member;
import edu.nju.software.service.MemberService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.ResultCode;

@Controller
public class MemberController {
	private static Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = {"/MemberList"})
	public ModelAndView getMemberList(HttpServletRequest request, HttpServletResponse response) {
		String companyIdStr = request.getParameter("companyId");
		if(StringUtils.isBlank(companyIdStr)) {
			throw new IllegalArgumentException();
		}
		
		int companyId = 0;
		try {
			companyId = Integer.parseInt(companyIdStr);
		}catch(NumberFormatException e) {
			logger.error(e.getMessage());
			throw e;
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<List<Member>> memberResult = memberService.getAllByCompany(companyId);
		if(memberResult.getResultCode() == ResultCode.NORMAL) {
			model.put("members", memberResult.getData());
		}
		
		return new ModelAndView("memberList", "model", model);
	}
}
