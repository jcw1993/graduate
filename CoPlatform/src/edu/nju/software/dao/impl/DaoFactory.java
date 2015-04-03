package edu.nju.software.dao.impl;

import edu.nju.software.dao.AdminDao;
import edu.nju.software.dao.CompanyDao;

public class DaoFactory {
	private static AdminDao adminDao = null;
	private static CompanyDao companyDao = null;
	
	public static AdminDao getAdminDao() {
		if(null == adminDao) {
			adminDao = new AdminDaoImpl();
		}
		return adminDao;
	}
	
	public static CompanyDao getCompanyDao() {
		if(null == companyDao) {
			companyDao = new CompanyDaoImpl();
		}
		return companyDao;
	}
}
