package edu.nju.software.service;

import java.util.List;

import edu.nju.software.pojo.News;
import edu.nju.software.util.GeneralResult;

public interface NewsService {
	//获取最新的十条资讯（图文链接最大条数为10）
	public GeneralResult<List<News>> getLatestFewNews();
}
