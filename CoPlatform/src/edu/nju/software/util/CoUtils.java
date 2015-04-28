package edu.nju.software.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CoUtils.class);
	
	public static int getRequestIntValue(HttpServletRequest request, String parameterName, boolean throwException) {
		int result = 0;
		String parameterValue = request.getParameter(parameterName);
		if(StringUtils.isBlank(parameterValue)) {
			if(throwException) {
				throw new IllegalArgumentException();
			}else {
				return result;
			}
		}
		
		try {
			result = Integer.parseInt(parameterValue);
		}catch(NumberFormatException e) {
			logger.error(e.getMessage());
			if(throwException) {
				throw e;
			}
		}
		
		return result;
	}

	public static double getRequestDoubleValue(HttpServletRequest request, String parameterName, boolean throwException) {
		double result = 0.0;
		String parameterValue = request.getParameter(parameterName);
		if(StringUtils.isBlank(parameterValue)) {
			if(throwException) {
				throw new IllegalArgumentException();
			}else {
				return result;
			}
		}
		
		try {
			result = Double.parseDouble(parameterValue);
		}catch(NumberFormatException e) {
			logger.error(e.getMessage());
			if(throwException) {
				throw e;
			}
		}
		
		return result;
	}
	
	public static Date parseDate(String dateStr, String format) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(dateStr);
	}
	
	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(null == cookies || cookies.length == 0) {
			return null;
		}
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
//		cookie.setPath("/1/njucowork");
		response.addCookie(cookie);
	}
}
