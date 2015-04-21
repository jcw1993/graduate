package edu.nju.software.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.nju.software.dao.OutEmployeeDao;
import edu.nju.software.pojo.Company;
import edu.nju.software.pojo.OutEmployee;

@Repository
public class OutEmployeeDaoImpl extends HibernateDaoBase implements OutEmployeeDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<OutEmployee> getByCompany(int companyId) {
		Query query = getSession().createQuery("select oe from OutEmployee oe join oe.companies c where c.id = " + companyId + " order by oe.id asc");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getRelatedCompanies(int outEmployeeId) {
		Query query = getSession().createQuery("select c from Company c join c.outEmployees oe where oe.id = " + outEmployeeId + " order by c.id asc");
		return query.list();
	}

	@Override
	public OutEmployee getById(int outEmployeeId) {
		return getHibernateTemplate().get(OutEmployee.class, outEmployeeId);
	}


}
