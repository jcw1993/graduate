package edu.nju.software.service;

import java.util.List;

import edu.nju.software.pojo.News;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataResult;

public interface NewsService {
	//获取最新的十条资讯（图文链接最大条数为10）
	public GeneralResult<List<News>> getLatestFewNews();
	
	public GeneralResult<Integer> create(News news);

	public NoDataResult update(News news);
	
	public NoDataResult delete(int newsId);
	
}
