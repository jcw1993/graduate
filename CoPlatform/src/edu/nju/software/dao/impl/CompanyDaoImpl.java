package edu.nju.software.dao.impl;

import org.springframework.stereotype.Repository;

import edu.nju.software.dao.CompanyDao;
import edu.nju.software.pojo.Company;

@Repository
public class CompanyDaoImpl extends HibernateDaoBase implements CompanyDao {

	@Override
	public Company getById(int id) {
		return null;
	}
}
