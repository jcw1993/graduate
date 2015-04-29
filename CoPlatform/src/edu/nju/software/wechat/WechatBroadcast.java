package edu.nju.software.wechat;

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
		UserManager userManager = new UserManager();
		List<Object> openIDList = userManager.allSubscriber();

		for (Object openId : openIDList) {
			String openIdString = String.valueOf(openId);
			CustomerMsg msg = new CustomerMsg(openIdString);

			ArticleResponse newsRsp = new ArticleResponse();
			newsRsp.setTitle(news.getTitle());
			newsRsp.setDescription(news.getContent());
			newsRsp.setPicUrl("http://njucowork-pic.stor.sinaapp.com/info.jpg");

			msg.sendNews(newsRsp);
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
