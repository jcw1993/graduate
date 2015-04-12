package edu.nju.software.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.nju.software.pojo.Company;
import edu.nju.software.pojo.Member;
import edu.nju.software.service.MemberService;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataJsonResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = {"/MemberList"}, method = RequestMethod.GET)
	public ModelAndView getMemberList(HttpServletRequest request, HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<List<Member>> memberResult = memberService.getAllByCompany(companyId);
		if(memberResult.getResultCode() == ResultCode.NORMAL) {
			model.put("members", memberResult.getData());
		}
		
		return new ModelAndView("memberList", "model", model);
	}
	
	@RequestMapping(value = {"/GetMemberInfo"}, method = RequestMethod.GET)
	public ModelAndView getMemberInfo(HttpServletRequest request, HttpServletResponse response) {
		int memberId = CoUtils.getRequestIntValue(request, "memberId", true);
		
		Map<String, Object> model = new HashMap<String, Object>();
		GeneralResult<Member> memberResult = memberService.getById(memberId);
		if(memberResult.getResultCode() == ResultCode.NORMAL) {
			model.put("member", memberResult.getData());
		}
		return new ModelAndView("memberInfo", "model", model);
	}
	
	@RequestMapping(value = {"/UpdateMember"}, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult updateMember(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		int id = CoUtils.getRequestIntValue(request, "id", true);
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		
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
		
		Member member = new Member(id, name, new Company(companyId), workId,
				qqNumber, wxNumber, phone);
		
		NoDataResult result = memberService.update(member);
		return new NoDataJsonResult(result);
	}
	
	@RequestMapping(value = {"/DeleteMember"}, method = RequestMethod.GET)
	@ResponseBody
	public NoDataJsonResult deleteMember(HttpServletRequest request, HttpServletResponse response) {
		int memberId = CoUtils.getRequestIntValue(request, "memberId", true);
		NoDataResult result = memberService.delete(new Member(memberId));
		return new NoDataJsonResult(result);
	}
}
