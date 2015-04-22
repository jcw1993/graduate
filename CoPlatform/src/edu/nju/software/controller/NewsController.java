package edu.nju.software.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.nju.software.util.CoHashMap;

@Controller
public class NewsController {

	@RequestMapping(value = {"/NewsList"}, method = RequestMethod.GET)
	public ModelAndView newsList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new CoHashMap(request);
		return new ModelAndView("newsList", "model", model);
	}
}
