package edu.nju.software.dao.impl;

import org.springframework.stereotype.Repository;

import edu.nju.software.dao.AdminDao;
import edu.nju.software.pojo.Admin;

@Repository
public class AdminDaoImpl extends HibernateDaoBase implements AdminDao {
	
	public AdminDaoImpl() {}

	@Override
	public Admin getById(int id) {
		System.out.println("id: " + id);
		return getHibernateTemplate().get(Admin.class, 1);
	}
	
}
