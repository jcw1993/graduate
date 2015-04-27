package edu.nju.software.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import edu.nju.software.util.CoUtils;

public class AuthenticationFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	private static final String[] VALID_URL_SUFFIX = {"Login", ".js", ".css", ".jpg", ".png"};
	
	private static final String WECHAT_URL_PREFIX = "/wechat";
	
	private Gson gson = null;

	@Override
	public void destroy() {
		logger.info("AuthencationFilter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		String requestURI = httpServletRequest.getRequestURI();
		String contextPath = httpServletRequest.getContextPath();
//		if(!isValidURI(requestURI) && !requestURI.startsWith(contextPath + WECHAT_URL_PREFIX)) {
		/*if(!isValidURI(requestURI) && !requestURI.contains(WECHAT_URL_PREFIX)) {
			String adminCookieValue = CoUtils.getCookie(httpServletRequest, "currentAdmin");
			if(null == adminCookieValue) {
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/Login");
				return;
			}
		}*/
		
//		if(requestURI.startsWith(contextPath + WECHAT_URL_PREFIX) && !requestURI.equals(contextPath + WECHAT_URL_PREFIX)) {
//			String memberCookieValue = CoUtils.getCookie(httpServletRequest, "currentMember");
//			if(null == memberCookieValue) {
//				httpServletResponse.sendRedirect(httpServletRequest.nadeigetContextPath() + WECHAT_URL_PREFIX);
//				return;
//			}
//		}
		chain.doFilter(request, response);
		return;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("AuthencationFilter init");
		if(null == gson) {
			gson = new Gson();
		}
	}
	
	private boolean isValidURI(String uri) {
		if(StringUtils.isBlank(uri)) {
			return false;
		}
		
		for(String suffix : VALID_URL_SUFFIX) {
			if(uri.endsWith(suffix)) {
				return true;
			}
		}
		
		return false;
	}

}
