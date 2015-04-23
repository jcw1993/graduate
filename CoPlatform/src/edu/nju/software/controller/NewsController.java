package edu.nju.software.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.nju.software.pojo.Company;
import edu.nju.software.pojo.News;
import edu.nju.software.service.NewsService;
import edu.nju.software.util.CoHashMap;
import edu.nju.software.util.CoUtils;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.NoDataJsonResult;

@Controller
public class NewsController {
	
	@Autowired
	private NewsService newsService;

	@RequestMapping(value = {"/NewsList"}, method = RequestMethod.GET)
	public ModelAndView newsList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new CoHashMap(request);
		return new ModelAndView("newsList", "model", model);
	}
	
	@RequestMapping(value = {"/CreateNews"}, method = RequestMethod.POST)
	@ResponseBody
	public NoDataJsonResult createNews(HttpServletRequest request, HttpServletResponse response) {
		int companyId = CoUtils.getRequestIntValue(request, "companyId", true);
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		News news = new News(new Company(companyId), title, content);
		GeneralResult<Integer> createResult = newsService.create(news);
		return new NoDataJsonResult(createResult);
	}
}
