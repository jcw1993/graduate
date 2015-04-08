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
	public List<Member> getByCompany(int companyId)  throws DataAccessException {
		Company company = new Company();
		company.setId(companyId);
		return getHibernateTemplate().find("from Member where company = ?", company);
	}

	@Override
	public int create(Member member)  throws DataAccessException {
		return 0;
	}

	@Override
	public void update(Member member)  throws DataAccessException {
		getHibernateTemplate().update(member);
		
	}

	@Override
	public void delete(int id)  throws DataAccessException {
		Company company = new Company();
		company.setId(id);
		getHibernateTemplate().delete(company);
	}

	@Override
	public Member getById(int id) {
		return getHibernateTemplate().get(Member.class, id);
	}
	
}
