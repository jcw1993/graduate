package edu.nju.software.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.software.dao.AdminDao;
import edu.nju.software.pojo.Admin;
import edu.nju.software.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDao adminDao;
	
	public AdminServiceImpl() {}

	@Override
	public Admin getById(int id) {
		return adminDao.getById(id);
	}
}
