package edu.nju.software.dao.impl;

import java.util.List;

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
		return getHibernateTemplate().find("from Member where company = ?",
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
		return (Member) getHibernateTemplate().find(
				"from Member where open_id = ?", openId).get(0);
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<Member> getAll() { return
	 * getHibernateTemplate().find("from Member"); }
	 */

	@Override
	public Member getByPhone(String phone) {
		return (Member) getHibernateTemplate().find(
				"from Member where phone = ?", phone).get(0);
	}

}
