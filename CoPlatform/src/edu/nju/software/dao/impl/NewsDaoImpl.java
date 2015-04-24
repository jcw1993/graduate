package edu.nju.software.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.nju.software.dao.NewsDao;
import edu.nju.software.pojo.News;

@Repository
public class NewsDaoImpl extends HibernateDaoBase implements NewsDao{
	private static final int MAX_PIECES = 10;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<News> getLatestNews(int companyId) {
		Query query = getSession().createQuery("from News where companyId = " + companyId + " order by createdTime desc");
		query.setFirstResult(0);
		query.setMaxResults(MAX_PIECES);
		return query.list();
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
	public void delete(int id) {
		Query query = getSession().createQuery("delete from News where id = " + id);
		query.executeUpdate();
	}

	@Override
	public News getById(int id) {
		return getHibernateTemplate().get(News.class, id);
	}

}
