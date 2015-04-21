package edu.nju.software.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.nju.software.dao.NewsDao;
import edu.nju.software.pojo.News;

@Repository
public class NewsDaoImpl extends HibernateDaoBase implements NewsDao{
	//最大图文链接条数
	@SuppressWarnings("unused")
	private static final int MAX_PIECES = 10;
	
	@Override
	public List<News> getLatestFewNews() {
		// TODO Auto-generated method stub
		return null;
	}

}
