package edu.nju.software.controller;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.nju.software.pojo.Company;
import edu.nju.software.pojo.Member;
import edu.nju.software.service.MemberService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataJsonResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Controller
public class MemberController {
	private static Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = {"/MemberList"}, method = RequestMethod.GET)
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
	
	@RequestMapping(value = {"/GetMemberInfo"}, method = RequestMethod.GET)
	public ModelAndView getMemberInfo(HttpServletRequest request, HttpServletResponse response) {
		String memberIdStr = request.getParameter("memberId");
		if(StringUtils.isBlank(memberIdStr)) {
			throw new IllegalArgumentException();
		}
		
		int memberId = 0;
		try {
			memberId = Integer.parseInt(memberIdStr);
		}catch(NumberFormatException e) {
			logger.error(e.getMessage());
			throw e;
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<Member> memberResult = memberService.getById(memberId);
		if(memberResult.getResultCode() == ResultCode.NORMAL) {
			model.put("member", memberResult.getData());
		}
		return new ModelAndView("memberInfo", "model", model);
	}
	
	@RequestMapping(value = {"/EditMemberInfo"}, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult editMemberInfo(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String idStr = request.getParameter("id");
		String companyIdStr = request.getParameter("companyId");
		if(StringUtils.isBlank(idStr) || StringUtils.isBlank(companyIdStr)) {
			throw new IllegalArgumentException();
		}
		
		int id = 0;
		int companyId = 0;
		try {
			id = Integer.parseInt(idStr);
			companyId = Integer.parseInt(companyIdStr);
		}catch(NumberFormatException e) {
			logger.error(e.getMessage());
			throw e;
		}
		
		String name = request.getParameter("name");
		String workId = request.getParameter("workId");
		String qqNumber = request.getParameter("qqNumber");
		String wxNumber = request.getParameter("wxNumber");
		String phone = request.getParameter("phone");
		if(null != name) {
			name = name.trim();
		}
		if(null != workId) {
			workId = workId.trim();
		}
		if(null != qqNumber) {
			qqNumber = qqNumber.trim();
		}
		if(null != wxNumber) {
			wxNumber = wxNumber.trim();
		}
		if(null != phone) {
			phone = phone.trim();
		}
		
		System.out.print("name: " + name);
		Member member = new Member();
		member.setId(id);
		member.setName(name);
		member.setWorkId(workId);
		member.setQqNumber(qqNumber);
		member.setWxNumber(wxNumber);
		member.setPhone(phone);
		member.setCompany(new Company(companyId));
		
		NoDataResult result = memberService.update(member);
		return new NoDataJsonResult(result);
	}
}
