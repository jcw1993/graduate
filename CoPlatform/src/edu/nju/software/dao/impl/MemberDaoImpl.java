package edu.nju.software.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import edu.nju.software.dao.MemberDao;
import edu.nju.software.pojo.Company;
import edu.nju.software.pojo.Member;

@Repository
public class MemberDaoImpl extends HibernateDaoBase implements MemberDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> getByCompany(int companyId) throws DataAccessException {
		Company company = new Company(companyId);
		return getHibernateTemplate().find("from Member where company = ? order by id asc",
				company);
	}

	@Override
	public int create(Member member) throws DataAccessException {
		return (Integer) getHibernateTemplate().save(member);
	}

	@Override
	public void update(Member member) throws DataAccessException {
		getHibernateTemplate().update(member);

	}

	@Override
	public void delete(Member member) throws DataAccessException {
		getHibernateTemplate().delete(member);
	}

	@Override
	public Member getById(int id) {
		return getHibernateTemplate().get(Member.class, id);
	}

	@Override
	public Member getByOpenId(String openId) {
		@SuppressWarnings("unchecked")
		List<Member> memberList = getHibernateTemplate().find(
				"from Member where openId = ?", openId);
		if(memberList.isEmpty()){
			return null;
		}else {
			return memberList.get(0);
		}
		
	}

	@Override
	public Member getByPhoneAndPassword(String phone, String password) {
		Query query = getSession().createQuery("from Member where phone = " + phone + " and password = " + password);
		@SuppressWarnings("unchecked")
		List<Member> memberList = query.list();
		if(null != memberList && !memberList.isEmpty()) {
			return memberList.get(0);
		}else {
			return null;
		}
	}

}
