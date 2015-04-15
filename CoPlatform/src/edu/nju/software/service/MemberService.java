package edu.nju.software.service;

import java.util.List;

import edu.nju.software.pojo.Member;
import edu.nju.software.pojo.Task;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;

public interface MemberService {
	public GeneralResult<List<Member>> getAllByCompany(int companyId);
	
	public GeneralResult<Member> getById(int id);
	
	public GeneralResult<Member> getByCompanyAndId(int companyId, int memberId);
	
	public GeneralResult<Member> getByCompanyAndWorkId(int companyId, String workId);
	
	public GeneralResult<Integer> create(Member member);
	
	public NoDataResult update(Member member);
	
	public NoDataResult delete(Member member);
	
	public GeneralResult<List<Task>> getTasks(int memberId);
}
