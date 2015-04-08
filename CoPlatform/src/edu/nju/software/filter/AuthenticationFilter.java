package edu.nju.software.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

	@Override
	public void destroy() {
		logger.info("AuthencationFilter destroy");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		// TODO user authenticate 
		
		chain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("AuthencationFilter init");
	}

}
