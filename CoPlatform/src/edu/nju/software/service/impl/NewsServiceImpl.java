package edu.nju.software.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.nju.software.pojo.News;
import edu.nju.software.service.NewsService;
import edu.nju.software.util.GeneralResult;

@Service
public class NewsServiceImpl implements NewsService{

	@Override
	public GeneralResult<List<News>> getLatestFewNews() {
		// TODO Auto-generated method stub
		return null;
	}

}
