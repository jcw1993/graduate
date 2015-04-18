package edu.nju.software.dao;

import java.util.List;

import edu.nju.software.pojo.Member;


public interface MemberDao {
	public List<Member> getByCompany(int companyId);
	
	public Member getById(int id);
	
	public Member getByOpenId(String openId);
	
	public int create(Member member);
	
	public void update(Member member);
	
	public void delete(Member member);
}
