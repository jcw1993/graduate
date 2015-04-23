package edu.nju.software.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import edu.nju.software.dao.NewsDao;
import edu.nju.software.pojo.News;
import edu.nju.software.service.NewsService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;
import edu.nju.software.util.ResultCode;

@Service
public class NewsServiceImpl implements NewsService{

	private static Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);
	
	@Autowired
	private NewsDao newsDao;
	
	@Override
	public GeneralResult<List<News>> getLatestFewNews() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralResult<Integer> create(News news) {
		GeneralResult<Integer> result = new GeneralResult<Integer>();
		try {
			int outId = newsDao.create(news);
			result.setData(outId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		
		return result;
	}

	@Override
	public NoDataResult update(News news) {
		NoDataResult result = new NoDataResult();
		try {
			newsDao.update(news);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_UPDATE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}
		
		return result;
	}

	@Override
	public NoDataResult delete(int newsId) {
		NoDataResult result = new NoDataResult();
		try {
			newsDao.delete(newsId);
		}catch(DataAccessException e) {
			logger.error(e.getMessage());
			result.setResultCode(ResultCode.E_DATABASE_DELETE_ERROR);
			result.setMessage(e.getMessage());
			return result;
		}
		
		return result;
	}

}
