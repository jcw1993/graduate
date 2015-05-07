package edu.nju.software.wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sword.wechat4j.response.ArticleResponse;

import edu.nju.software.pojo.News;
import edu.nju.software.service.NewsService;
import edu.nju.software.util.GeneralResult;
import edu.nju.software.util.WeChatInstruct;

/**
 * 微信事件回复
 */
@Component
public class WeChatRsp {
	@Autowired
	private NewsService newsService;

	private String prefix = WeChatInstruct.DIMAIN;

	public WeChatRsp() {
	}

	// 返回任务相关图文链接
	public List<ArticleResponse> tasksRsp(String openId) {

		String parameter = "?openId=" + openId;

		List<ArticleResponse> tasksRsp = new ArrayList<ArticleResponse>();

		ArticleResponse viewTasksRsp = new ArticleResponse();
		viewTasksRsp.setTitle("查看任务");
		viewTasksRsp.setDescription("查看任务");
		viewTasksRsp.setUrl(prefix + "/wechat/MyTasks" + parameter);
		viewTasksRsp
				.setPicUrl("http://njucowork-pic.stor.sinaapp.com/assign.png");
		tasksRsp.add(viewTasksRsp);

		/*
		 * ArticleResponse modifyTasksRsp = new ArticleResponse();
		 * modifyTasksRsp.setTitle("修改任务状态");
		 * modifyTasksRsp.setDescription("修改任务状态"); modifyTasksRsp.setUrl("" +
		 * parameter); // TODO modifyTasksRsp
		 * .setPicUrl("http://njucowork-pic.stor.sinaapp.com/5.jpg");
		 * tasksRsp.add(modifyTasksRsp);
		 */
		return tasksRsp;
	}

	// 返回资讯
	public List<ArticleResponse> newsRsp() {
		GeneralResult<List<News>> result = newsService.getLatestNews(1);

		if (result == null || result.getData() == null
				|| result.getData().isEmpty()) {
			return null;
		} else {
			List<News> newsList = result.getData();
			List<ArticleResponse> newsRsps = new ArrayList<ArticleResponse>();

			int index = 1;
			for (News news : newsList) {

				ArticleResponse newsRsp = new ArticleResponse();
				newsRsp.setTitle(news.getTitle());
				newsRsp.setDescription(news.getContent());
				newsRsp.setPicUrl("http://njucowork-pic.stor.sinaapp.com/number"
						+ index + ".png");
				newsRsps.add(newsRsp);
				index++;
			}
			return newsRsps;
		}
	}

	// 返回帮助信息
	public String helpRsp() {
		String result = "您好，若需查询任务或修改任务状态请发送" + WeChatInstruct.TASKS
				+ ",若需获取最新资讯请发送" + WeChatInstruct.NEWS + ",若需要帮助请发送"
				+ WeChatInstruct.HELP + "。";

		return result;
	}

	// 返回订阅反馈
	public String subscribeRsp(String openId) {
		String parameter = "?openId=" + openId;
		String link = "<a href=\"http://" + prefix + "/wechat/MyTasks"
				+ parameter + "\">绑定南大协同工作平台PC端账号~点我！</a>";

		String result = "感谢您关注南大任务协同平台!" + "\n\n" + helpRsp() + "\n\n" + link;

		return result;
	}

	// 返回聊天机器人回复
	public String chattingRobotRsp(String content) {
		String info = "";

		try {
			info = URLEncoder.encode(content.replaceAll(" ", "%20"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String requesturl = "http://www.tuling123.com/openapi/api?key=525dc3676cf81a5e8def59891d1ef813&info="
				+ info;

		HttpGet request = new HttpGet(requesturl);
		HttpResponse response;

		try {
			DefaultHttpClient client = new DefaultHttpClient();
			response = client.execute(request);

			// 200即正确的返回码
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity());
				String[] splitString = result.split("\"");
				if (splitString.length >= 6) {
					result = splitString[5];
					result.replaceAll("<br>", " ");
				} else {
					result = "好像有哪里不对...";
				}
				return result;
			} else {
				return "什么鬼？？？";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "什么鬼？？？";
	}
}
