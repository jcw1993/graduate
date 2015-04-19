package edu.nju.software.wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.sword.wechat4j.WechatSupport;
import org.sword.wechat4j.response.ArticleResponse;

import edu.nju.software.pojo.News;
import edu.nju.software.service.NewsService;
import edu.nju.software.util.WeChatInstruct;

public class WeChatProcessor extends WechatSupport {

	private static Logger logger = Logger.getLogger(WeChatProcessor.class);

	@Autowired
	private NewsService newsService;

	public WeChatProcessor(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 文本消息
	 */
	@Override
	protected void onText() {
		String content = wechatRequest.getContent().trim();
		String openID = wechatRequest.getFromUserName();
		String parameter = "?openID=" + openID;

		logger.info(content);

		if (content.equals("1")) {
			responseText("openId=" + openID);
		}

		// 回复任务相关图文链接
		if ((content.toUpperCase()).equals(WeChatInstruct.TASKS)) {
			List<ArticleResponse> tasksRsp = new ArrayList<ArticleResponse>();

			ArticleResponse viewTasksRsp = new ArticleResponse();
			viewTasksRsp.setTitle("查看任务");
			viewTasksRsp.setDescription("查看任务");
			viewTasksRsp.setUrl("" + parameter); // TODO
			viewTasksRsp
					.setPicUrl("http://njucowork-pic.stor.sinaapp.com/01.jpg");
			tasksRsp.add(viewTasksRsp);

			ArticleResponse modifyTasksRsp = new ArticleResponse();
			modifyTasksRsp.setTitle("修改任务状态");
			modifyTasksRsp.setDescription("修改任务状态");
			modifyTasksRsp.setUrl("" + parameter); // TODO
			modifyTasksRsp
					.setPicUrl("http://njucowork-pic.stor.sinaapp.com/5.jpg");
			tasksRsp.add(modifyTasksRsp);

			responseNews(tasksRsp);
		}
		// 回复资讯
		else if ((content.toUpperCase()).equals(WeChatInstruct.NEWS)) {
			List<News> newsList = newsService.getLatestFewNews().getData();

			if (newsList == null || newsList.isEmpty()) {
				responseText("抱歉，没有最新资讯咯╮(╯▽╰)╭");
			} else {
				List<ArticleResponse> newsRsps = new ArrayList<ArticleResponse>();

				for (News news : newsList) {
					ArticleResponse newsRsp = new ArticleResponse();
					newsRsp.setTitle(news.getTitle());
					newsRsp.setDescription(news.getContent());
					newsRsp.setPicUrl("http://njucowork-pic.stor.sinaapp.com/01.jpg");
					newsRsps.add(newsRsp);
				}

				responseNews(newsRsps);
			}
		}
		// 回复帮助信息
		else if ((content.toUpperCase()).equals(WeChatInstruct.HELP)) {
			String result = "您好，若需查询任务或修改任务状态请发送" + WeChatInstruct.TASKS
					+ ",若需获取最新资讯请发送" + WeChatInstruct.NEWS + ",若需要帮助请发送"
					+ WeChatInstruct.HELP + "。祝您工作愉快↖(^ω^)↗";

			responseText(result);
		} else {
			// 聊天机器人
			String requesturl = "http://www.tuling123.com/openapi/api?key=525dc3676cf81a5e8def59891d1ef813&info="
					+ content;
			try {
				requesturl = URLDecoder.decode(requesturl, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			HttpGet request = new HttpGet(requesturl);
			HttpResponse response;

			try {
				DefaultHttpClient client = new DefaultHttpClient();
				response = client.execute(request);

				// 200即正确的返回码
				if (response.getStatusLine().getStatusCode() == 200) {
					String result = EntityUtils.toString(response.getEntity());
					String[] splitString = result.split("\"");
					if (splitString.length >= 6)
						result = splitString[5];
					else {
						result = "好像有哪里不对...";
					}
					responseText(result);
				} else {
					responseText("什么鬼？？？");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 图片消息
	 */
	@Override
	protected void onImage() {
		String picUrl = wechatRequest.getPicUrl();
		String MediaId = wechatRequest.getMediaId();
		String MsgId = wechatRequest.getMsgId();

		String result = "图片消息picUrl:" + picUrl + ", MediaId:" + MediaId
				+ ", MsgId:" + MsgId;
		logger.info(result);
		responseText(result);
		// responseImage(mediaId);
	}

	/**
	 * 语音消息
	 */
	@Override
	protected void onVoice() {
		String Format = wechatRequest.getFormat();
		String MediaId = wechatRequest.getMediaId();// 视频消息媒体id，可以调用多媒体文件下载接口拉取数据
		String MsgId = wechatRequest.getMsgId();

		String result = "语音消息Format:" + Format + ", MediaId:" + MediaId
				+ ", MsgId:" + MsgId;
		logger.info(result);
		responseText(result);
		// responseVoice(mediaId);

		// 回复音乐消息
		// MusicResponse music = new MusicResponse();
		// music.setTitle(title);
		// music.setDescription(description);
		// music.setMusicURL(musicURL);
		// music.setHQMusicUrl(hQMusicUrl);
		// music.setThumbMediaId(thumbMediaId);
		// responseMusic(music);
		//
		// responseMusic(title, description, musicURL, hQMusicUrl,
		// thumbMediaId);
	}

	/**
	 * 视频消息
	 */
	@Override
	protected void onVideo() {
		String ThumbMediaId = wechatRequest.getThumbMediaId();
		String MediaId = wechatRequest.getMediaId();// 语音消息媒体id，可以调用多媒体文件下载接口拉取数据
		String MsgId = wechatRequest.getMsgId();

		String result = "视频消息ThumbMediaId:" + ThumbMediaId + ", MediaId:"
				+ MediaId + ", MsgId:" + MsgId;
		logger.info(result);
		responseText(result);

		// 回复视频消息
		// VideoResponse video = new VideoResponse();
		// video.setTitle(title);
		// video.setDescription(description);
		// video.setMediaId(mediaId);
		// responseVideo(video);
		//
		// responseVideo(mediaId, title, description);
	}

	/**
	 * 地理位置消息
	 */
	@Override
	protected void onLocation() {
		String Location_X = wechatRequest.getLocation_X();
		String Location_Y = wechatRequest.getLocation_Y();
		String Scale = wechatRequest.getScale();
		String Label = wechatRequest.getLabel();
		String MsgId = wechatRequest.getMsgId();

		String result = "地理位置消息Location_X:" + Location_X + ", Location_Y:"
				+ Location_Y + ", Scale:" + Scale + ", Label:" + Label
				+ ", MsgId:" + MsgId;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 链接消息
	 */
	@Override
	protected void onLink() {
		String Title = wechatRequest.getTitle();
		String Description = wechatRequest.getDescription();
		String Url = wechatRequest.getUrl();
		String MsgId = wechatRequest.getMsgId();

		String result = "链接消息Title:" + Title + ", Description:" + Description
				+ ", Url:" + Url + ", MsgId:" + MsgId;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 未知消息类型，错误处�?
	 */
	@Override
	protected void onUnknown() {
		String msgType = wechatRequest.getMsgType();

		String result = "未知消息msgType:" + msgType;
		logger.info(result);
		responseText(result);

	}

	/**
	 * 扫描二维码事�?
	 */
	@Override
	protected void scan() {
		String FromUserName = wechatRequest.getFromUserName();
		String Ticket = wechatRequest.getTicket();

		String result = "扫描二维码事件FromUserName:" + FromUserName + ", Ticket:"
				+ Ticket;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 订阅事件
	 */
	@Override
	protected void subscribe() {
		String result = "感谢您关注南大任务协同平台，若需查询任务或修改任务状态请发送" + WeChatInstruct.TASKS
				+ ",若需获取最新资讯请发送" + WeChatInstruct.NEWS + ",若需要帮助请发送"
				+ WeChatInstruct.HELP + "。祝您工作愉快↖(^ω^)↗";

		responseText(result);
	}

	/**
	 * 取消订阅事件
	 */
	@Override
	protected void unSubscribe() {
		String FromUserName = wechatRequest.getFromUserName();
		String result = "取消订阅事件FromUserName:" + FromUserName;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 点击菜单跳转链接时的事件推�?
	 */
	@Override
	protected void view() {
		String link = super.wechatRequest.getEventKey();
		logger.info("点击菜单跳转链接时的事件推�?link:" + link);
		responseText("点击菜单跳转链接时的事件推�?link:" + link);
	}

	/**
	 * 自定义菜单事�?
	 */
	@Override
	protected void click() {
		String key = super.wechatRequest.getEventKey();
		logger.info("自定义菜单事件eventKey:" + key);
		responseText("自定义菜单事件eventKey:" + key);
	}

	/**
	 * 上报地理位置事件
	 */
	@Override
	protected void location() {
		String Latitude = wechatRequest.getLatitude();
		String Longitude = wechatRequest.getLongitude();
		String Precision = wechatRequest.getPrecision();
		String result = "上报地理位置事件Latitude:" + Latitude + ", Longitude:"
				+ Longitude + ", Precision:" + Precision;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 模板消息发�?成功推�?事件
	 */
	@Override
	protected void templateMsgCallback() {
		String MsgID = wechatRequest.getMsgId();
		String Status = wechatRequest.getStatus();
		String result = "模板消息发�?成功推�?事件MsgID:" + MsgID + ", Status:" + Status;
		logger.info(result);
	}

	/**
	 * 弹出地理位置选择器的事件
	 */
	@Override
	protected void locationSelect() {
		String Location_X = wechatRequest.getSendLocationInfo().getLocation_X();
		String Location_Y = wechatRequest.getSendLocationInfo().getLocation_Y();
		String Scale = wechatRequest.getSendLocationInfo().getScale();
		String Label = wechatRequest.getSendLocationInfo().getLabel();
		String Poiname = wechatRequest.getSendLocationInfo().getPoiname();
		String result = "弹出地理位置选择器的事件Location_X:" + Location_X
				+ ", Location_Y:" + Location_Y + ", Scale:" + Scale
				+ ", Label:" + Label + ", Poiname:" + Poiname;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 弹出拍照或�?相册发图的事�?
	 */
	@Override
	protected void picPhotoOrAlbum() {
		String Count = wechatRequest.getSendPicsInfo().getCount();
		String PicMd5Sum = "";
		if (StringUtils.isNotBlank(Count) && !Count.equals("0")) {
			PicMd5Sum = wechatRequest.getSendPicsInfo().getItem().get(0)
					.getPicMd5Sum();
		}
		String result = "弹出系统拍照发图的事件Count:" + Count + ", PicMd5Sum:"
				+ PicMd5Sum;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 弹出系统拍照发图的事�?
	 */
	@Override
	protected void picSysPhoto() {
		String Count = wechatRequest.getSendPicsInfo().getCount();
		String result = "弹出系统拍照发图的事件Count:" + Count;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 弹出微信相册发图器的事件推�?
	 */
	@Override
	protected void picWeixin() {
		String Count = wechatRequest.getSendPicsInfo().getCount();
		String result = "弹出系统拍照发图的事件Count:" + Count;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 扫码推事�? *
	 */
	@Override
	protected void scanCodePush() {
		String ScanType = wechatRequest.getScanCodeInfo().getScanType();
		String ScanResult = wechatRequest.getScanCodeInfo().getScanResult();
		String result = "扫码推事件ScanType:" + ScanType + ", ScanResult:"
				+ ScanResult;
		logger.info(result);
		responseText(result);
	}

	/**
	 * 扫码推事件且弹出“消息接收中”提示框的事�?
	 */
	@Override
	protected void scanCodeWaitMsg() {
		String ScanType = wechatRequest.getScanCodeInfo().getScanType();
		String ScanResult = wechatRequest.getScanCodeInfo().getScanResult();
		String result = "扫码推事件ScanType:" + ScanType + ", ScanResult:"
				+ ScanResult;
		logger.info(result);
		responseText(result);
	}

}
