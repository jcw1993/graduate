package edu.nju.software.wechat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.sword.wechat4j.message.CustomerMsg;
import org.sword.wechat4j.response.ArticleResponse;
import org.sword.wechat4j.user.UserManager;

import edu.nju.software.common.EmployeeType;
import edu.nju.software.common.TaskStatus;
import edu.nju.software.pojo.Log;
import edu.nju.software.pojo.Member;
import edu.nju.software.pojo.News;
import edu.nju.software.pojo.OutEmployee;
import edu.nju.software.service.MemberService;
import edu.nju.software.service.OutEmployeeService;
import edu.nju.software.util.GeneralResult;

/**
 * 
 * 微信群发类
 *
 */
public class WechatBroadcast {
	@Autowired
	private static MemberService memberService;

	@Autowired
	private static OutEmployeeService outEmployeeService;

	// 群发资讯
	public static void broadcastNews(News news) {
		ArticleResponse newsRsp = WeChatRsp.getNewsRsp(news, null);

		/*
		 * ArticleResponse newsRsp = new ArticleResponse(); String parameter =
		 * "?newsId=" + news.getId();
		 * 
		 * newsRsp.setTitle(news.getTitle());
		 * newsRsp.setDescription(news.getContent());
		 * newsRsp.setPicUrl("http://njucowork-pic.stor.sinaapp.com/info.jpg");
		 * newsRsp.setUrl(WeChatInstruct.DIMAIN + "/wechat/NewsDetail" +
		 * parameter);
		 */

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
			if (null == newsRsp) {
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
	public static void broadcastChanges(List<String> openIDList, Log change) {
		for (Object openId : openIDList) {
			String openIdString = String.valueOf(openId);
			CustomerMsg customerMsg = new CustomerMsg(openIdString);
			String name = "";
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");

			if (change.getCreatorType() == EmployeeType.MEMBER) {
				GeneralResult<Member> memberResult = memberService
						.getById(change.getCreatorId());
				Member member = memberResult.getData();

				if (null != member) {
					name = name + "员工" + member.getName();
				} else {
					name += "某员工";
				}

			} else if (change.getCreatorType() == EmployeeType.OUT_EMPLOYEE) {
				name += "外聘人员";
				GeneralResult<OutEmployee> outEmployeeResult = outEmployeeService
						.getById(change.getCreatorId());
				OutEmployee outEmployee = outEmployeeResult.getData();

				if (null != outEmployee) {
					name = name + "外聘人员" + outEmployee.getName();
				} else {
					name += "某外聘人员";
				}
			}

			String message = "任务状态修改提示：您好，您所参与的"
					+ change.getProject().getName() + "-"
					+ change.getTask().getName() + "任务状态已经于"
					+ dateFormat.format(change.getCreatedTime()) + "被" + name
					+ "由“" + TaskStatus.getStatusStr(change.getOriginStatus())
					+ "”改为“" + TaskStatus.getStatusStr(change.getCurrentStatus())
					+ "”，敬请关注。";

			customerMsg.sendText(message);
		}
	}
}
