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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.nju.software.pojo.Admin;

public class AuthenticationFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	private static final String LOGIN_URL = "Login";

	@Override
	public void destroy() {
		logger.info("AuthencationFilter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO user authenticate 
		logger.error("ssss");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		String requestURI = httpServletRequest.getRequestURI();
		if(!(requestURI.endsWith(LOGIN_URL) || requestURI.endsWith(".js") || requestURI.endsWith(".css"))) {
			HttpSession session = httpServletRequest.getSession(false);
			if(null != session) {
				System.out.println("session != null");
				Admin admin = (Admin) session.getAttribute("admin");
//				String adminName = (String) session.getAttribute("adminName");
				System.out.println("sessionId:" + session.getId());
				if(null == admin) {
					System.out.println("admin = null");
					httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/Login");
					return;
				}
			}else {
				System.out.println("session = null");
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/Login");
				return;
			}
		}
		chain.doFilter(request, response);
		return;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("AuthencationFilter init");
	}

}
