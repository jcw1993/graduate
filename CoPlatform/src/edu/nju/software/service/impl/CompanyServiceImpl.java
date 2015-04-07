package edu.nju.software.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.nju.software.pojo.Company;
import edu.nju.software.service.CompanyService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	public CompanyServiceImpl() {}

	@Override
	public GeneralResult<List<Company>> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralResult<Company> getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralResult<Integer> create(Company company) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoDataResult update(Company company) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoDataResult delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
