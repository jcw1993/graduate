package edu.nju.software.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sina.sae.storage.SaeStorage;
import com.sina.sae.util.SaeUserInfo;

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
	
	public static void addCookie(HttpServletRequest request,HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
//		cookie.setPath("/");
//		cookie.setPath("/1/njucowork");
//		cookie.setPath("/njucowork");
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("gbk");
        PrintWriter out = response.getWriter();
        // 使用SaeUserInfo拿到改请求可写的路径
        String realPath = SaeUserInfo.getSaeTmpPath() + "/";
        try {
            // 使用common-upload上传文件至这个路径中
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart)
                return;
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(1024 * 1024);
            List<FileItem> items = null;
            items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File fullFile = new File(item.getName());
                    File uploadFile = new File(realPath, fullFile.getName());
                    item.write(uploadFile);
                    // 上传完毕后 使用SaeStorage往storage里面写
                    SaeStorage ss = new SaeStorage();
                    // 使用upload方法上传到域为image下
                    ss.upload("image", realPath + fullFile.getName(),
                            fullFile.getName());

                    out.print("upload file:" + realPath + fullFile.getName()
                            + "</br>");
                }
            }
            out.print("upload end...");
        } catch (Exception e) {
            out.print("ERROR:" + e.getMessage() + "</br>");
        } finally {
            out.flush();
            out.close();
        }
    }
}
