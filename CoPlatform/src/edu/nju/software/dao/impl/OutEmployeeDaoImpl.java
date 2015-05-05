package edu.nju.software.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import edu.nju.software.dao.OutEmployeeDao;
import edu.nju.software.pojo.Company;
import edu.nju.software.pojo.OutEmployee;

@Repository
public class OutEmployeeDaoImpl extends HibernateDaoBase implements OutEmployeeDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<OutEmployee> getByCompany(int companyId) {
		Session session = super.getSession(true);
<<<<<<< HEAD
		try {
			Query query = getSession().createQuery("select oe from OutEmployee oe, Company c, CompanyOutEmployee coe where coe.companyId = c.id and coe.outEmployeeId = oe.id and c.id = " + companyId + " order by oe.id asc");
=======
		Query query = null;
		try {
			query = session.createQuery("select oe from OutEmployee oe, Company c, CompanyOutEmployee coe where coe.companyId = c.id and coe.outEmployeeId = oe.id and c.id = " + companyId + " order by oe.id asc");
>>>>>>> a0fec286f646e1f62e98ea82f01fdc0ee78e890a
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getRelatedCompanies(int outEmployeeId) {
		Session session = super.getSession(true);
<<<<<<< HEAD
		try {
			Query query = getSession().createQuery("select c from Company c, OutEmployee oe, CompanyOutEmployee coe where coe.companyId = c.id and coe.outEmployeeId = oe.id and eoe.id = " + outEmployeeId + " order by c.id asc");
=======
		Query query = null;
		try {
			query = session.createQuery("select c from Company c, OutEmployee oe, CompanyOutEmployee coe where coe.companyId = c.id and coe.outEmployeeId = oe.id and eoe.id = " + outEmployeeId + " order by c.id asc");
>>>>>>> a0fec286f646e1f62e98ea82f01fdc0ee78e890a
			return query.list();
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			session.close();
		}
		return null;
<<<<<<< HEAD
=======
		
>>>>>>> a0fec286f646e1f62e98ea82f01fdc0ee78e890a
	}

	@Override
	public OutEmployee getById(int outEmployeeId) {
		return getHibernateTemplate().get(OutEmployee.class, outEmployeeId);
	}


}
