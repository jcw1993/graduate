package edu.nju.software.dao;

import java.util.List;

import edu.nju.software.pojo.Company;
import edu.nju.software.pojo.OutEmployee;

public interface OutEmployeeDao {
	
	public List<OutEmployee> getByCompany(int companyId);
	
	public List<Company> getRelatedCompanies(int outEmployeeId);
	
	public List<OutEmployee> getAll();

}
