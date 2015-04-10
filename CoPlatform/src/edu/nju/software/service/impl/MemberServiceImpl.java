package edu.nju.software.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import edu.nju.software.dao.MemberDao;
import edu.nju.software.pojo.Member;
import edu.nju.software.service.MemberService;
import edu.nju.software.util.CoCacheManager;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Service
public class MemberServiceImpl implements MemberService {
	
	private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	private static final String COMPANY_MEMBER_CACHE_KEY = "company_member_cache_key_%d";
	
	private static final String MEMBER_CACHE_KEY = "member_%d";
	
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public GeneralResult<List<Member>> getAllByCompany(int companyId) {
		GeneralResult<List<Member>> result = new GeneralResult<List<Member>>();
		@SuppressWarnings("unchecked")
		List<Member> memberList = (List<Member>) CoCacheManager.get(String.format(COMPANY_MEMBER_CACHE_KEY, companyId));
		if(null != memberList && !memberList.isEmpty()) {
			result.setData(memberList);
		}else {
			try {
				memberList = memberDao.getByCompany(companyId);
				if(null != memberList && !memberList.isEmpty()) {
					result.setData(memberList);
					CoCacheManager.put(String.format(COMPANY_MEMBER_CACHE_KEY, companyId), memberList);
				}else {
					result.setResultCode(ResultCode.E_NO_DATA);
					result.setMessage("no member data in company, companyId = " + companyId);
				}
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public GeneralResult<Member> getByCompanyAndId(int companyId, int memberId) {
		GeneralResult<Member> result = new GeneralResult<Member>();
		GeneralResult<List<Member>> companyMemberResult = getAllByCompany(companyId);
		if(companyMemberResult.getResultCode() == ResultCode.NORMAL) {
			for(Member member : companyMemberResult.getData()) {
				if(member.getId() == memberId) {
					result.setData(member);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
			}
		}else {
			result.setResultCode(companyMemberResult.getResultCode());
			result.setMessage(companyMemberResult.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<Member> getByCompanyAndWorkId(int companyId,
			String workId) {
		GeneralResult<Member> result = new GeneralResult<Member>();
		GeneralResult<List<Member>> companyMemberResult = getAllByCompany(companyId);
		if(companyMemberResult.getResultCode() == ResultCode.NORMAL) {
			for(Member member : companyMemberResult.getData()) {
				if(member.getWorkId().equals(workId)) {
					result.setData(member);
					break;
				}
			}
			if(null == result.getData()) {
				result.setResultCode(ResultCode.E_NO_DATA);
			}
		}else {
			result.setResultCode(companyMemberResult.getResultCode());
			result.setMessage(companyMemberResult.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<Integer> create(Member member) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = memberDao.create(member);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoDataResult update(Member member) {
		NoDataResult result = new NoDataResult();
		try {
			memberDao.update(member);
			CoCacheManager.remove(String.format(COMPANY_MEMBER_CACHE_KEY, member.getCompany().getId()));
			CoCacheManager.remove(String.format(MEMBER_CACHE_KEY, member.getId()));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public NoDataResult delete(Member member) {
		NoDataResult result = new NoDataResult();
		try {
			memberDao.delete(member);
			CoCacheManager.remove(String.format(COMPANY_MEMBER_CACHE_KEY, member.getCompany().getId()));
			CoCacheManager.remove(String.format(MEMBER_CACHE_KEY, member.getId()));
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public GeneralResult<Member> getById(int id) {
		GeneralResult<Member> result = new GeneralResult<Member>();
		Member member = (Member) CoCacheManager.get(String.format(MEMBER_CACHE_KEY, id));
		if(null != member) {
			result.setData(member);
		}else {
			try {
				member = memberDao.getById(id);
				result.setData(member);
			}catch(DataAccessException e) {
				logger.error(e.getMessage());
				result.setResultCode(ResultCode.E_DATABASE_GET_ERROR);
				result.setMessage(e.getMessage());
			}
		}
		return result;
	}

}
