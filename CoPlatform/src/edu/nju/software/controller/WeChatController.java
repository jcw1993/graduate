package edu.nju.software.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.nju.software.wechat.WeChatProcessor;

@Controller
public class WeChatController {
	
	@RequestMapping(value="/weixin")
	public void wechat(HttpServletRequest request, HttpServletResponse response) throws IOException {
		WeChatProcessor myWechat = new WeChatProcessor(request);
		String result = myWechat.execute();
		response.getOutputStream().write(result.getBytes());
	}
	
}
