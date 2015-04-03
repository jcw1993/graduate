package edu.nju.software.service;

import edu.nju.software.service.impl.AdminServiceImpl;
import edu.nju.software.service.impl.CompanyServiceImpl;

public class ServiceFactory {
	private static AdminService adminService;
	private static CompanyService companyService;
	
	public static AdminService getAdminService() {
		if(null == adminService) {
			adminService = new AdminServiceImpl();
		}
		return adminService;
	}
	
	public static CompanyService getCompanyService() {
		if(null == companyService) {
			companyService = new CompanyServiceImpl();
		}
		return companyService;
	}
}
