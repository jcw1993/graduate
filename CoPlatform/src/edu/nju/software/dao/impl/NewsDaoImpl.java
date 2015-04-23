package edu.nju.software.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.nju.software.dao.NewsDao;
import edu.nju.software.pojo.News;

@Repository
public class NewsDaoImpl extends HibernateDaoBase implements NewsDao{
	//最大图文链接条数
	@SuppressWarnings("unused")
	private static final int MAX_PIECES = 10;
	
	@Override
	public List<News> getLatestFewNews(int companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(News news) {
		return (Integer) getHibernateTemplate().save(news);
	}

	@Override
	public void update(News news) {
		getHibernateTemplate().update(news);
		
	}

	@Override
	public void delete(int newsId) {
		Query query = getSession().createQuery("delete from News where id = " + newsId);
		query.executeUpdate();
	}

}
