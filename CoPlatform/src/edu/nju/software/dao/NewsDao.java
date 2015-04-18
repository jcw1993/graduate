package edu.nju.software.dao;

import java.util.List;

import edu.nju.software.pojo.News;

public interface NewsDao {
	//获取最新的十条资讯（图文链接最大条数为10）
	public List<News> getLatestFewNews();
}
