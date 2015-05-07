package edu.nju.software.wechat;

import java.util.ArrayList;
import java.util.List;

import org.sword.wechat4j.message.CustomerMsg;
import org.sword.wechat4j.response.ArticleResponse;
import org.sword.wechat4j.user.UserManager;

import edu.nju.software.pojo.Log;
import edu.nju.software.pojo.News;

/**
 * 
 * 微信群发类
 *
 */
public class WechatBroadcast {
	// 群发资讯
	public static void broadcastNews(News news) {
		ArticleResponse newsRsp = WeChatRsp.getNewsRsp(news, null);
		
		/*ArticleResponse newsRsp = new ArticleResponse();
		String parameter = "?newsId=" + news.getId();

		newsRsp.setTitle(news.getTitle());
		newsRsp.setDescription(news.getContent());
		newsRsp.setPicUrl("http://njucowork-pic.stor.sinaapp.com/info.jpg");
		newsRsp.setUrl(WeChatInstruct.DIMAIN + "/wechat/NewsDetail"
				+ parameter);*/
		
		UserManager userManager = new UserManager();
		List<Object> openIDList = userManager.allSubscriber();

		for (Object openId : openIDList) {
			String openIdString = String.valueOf(openId);
			CustomerMsg msg = new CustomerMsg(openIdString);		

			msg.sendNews(newsRsp);
		}
	}

	// 群发资讯列表
	public static void broadcastNewsList(List<News> newsList) {
		UserManager userManager = new UserManager();
		List<Object> openIDList = userManager.allSubscriber();
		
		List<ArticleResponse> newsRsps = new ArrayList<ArticleResponse>();

		int index = 1;
		for (News news : newsList) {
			String picURL = "http://njucowork-pic.stor.sinaapp.com/number"
					+ index + ".png";
			ArticleResponse newsRsp = WeChatRsp.getNewsRsp(news, picURL);
			if(null == newsRsp){
				continue;
			}
			newsRsps.add(newsRsp);
			index++;
		}
		
		for (Object openId : openIDList) {
			String openIdString = String.valueOf(openId);
			CustomerMsg msg = new CustomerMsg(openIdString);		

			msg.sendNews(newsRsps);
		}
	}

	// 任务状态修改推送
	public static void broadcastChanges(List<String> openIDList, Log changes) {
		for (Object openId : openIDList) {
			String openIdString = String.valueOf(openId);
			CustomerMsg msg = new CustomerMsg(openIdString);

			msg.sendText("任务状态更改:" + changes);// TODO
		}
	}
}
