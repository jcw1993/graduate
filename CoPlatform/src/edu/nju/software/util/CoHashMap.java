package edu.nju.software.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import edu.nju.software.pojo.Admin;

public class CoHashMap extends HashMap<String, Object>{

	private static Logger logger = LoggerFactory.getLogger(CoHashMap.class);
	
	private static final long serialVersionUID = -2882821927335974480L;
	
	private static Gson gson = null;
	
	public CoHashMap() {
		super();
		if(null == gson) {
			gson = new Gson();
		}
	}
	
	public CoHashMap(HttpServletRequest request) {
		this();
		if(null == gson) {
			gson = new Gson();
		}
		Admin admin = null;
		try {
			admin = gson.fromJson(URLDecoder.decode(CoUtils.getCookie(request, "currentAdmin"), "UTF-8"), Admin.class);
			super.put("admin", admin);
		} catch (JsonSyntaxException e) {
			logger.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		
	}
}

	
