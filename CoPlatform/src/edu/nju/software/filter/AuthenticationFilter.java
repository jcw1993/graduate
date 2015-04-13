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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.nju.software.pojo.Admin;

public class AuthenticationFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

	@Override
	public void destroy() {
		logger.info("AuthencationFilter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO user authenticate 
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String requestURI = httpServletRequest.getRequestURI();
		if(!requestURI.endsWith("/Login")) {
			Admin admin = (Admin) httpServletRequest.getSession().getAttribute("currentAdmin");
			if(null == admin) {
				((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/Login");
				return;
			}
		}


		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("AuthencationFilter init");
	}

}
