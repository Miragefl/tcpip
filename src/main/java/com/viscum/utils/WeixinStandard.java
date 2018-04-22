package com.viscum.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiangl
 */
public class WeixinStandard {


	public static final String MESSAGE_TYPE_TEXT = "text";//请求消息类型：文本
	public static final String MESSAGE_TYPE_IMAGE = "image";//请求消息类型：图片
	public static final String MESSAGE_TYPE_LINK = "link";//请求消息类型：链接
	public static final String MESSAGE_TYPE_LOCATION = "location";//请求消息类型：地理位置
	public static final String MESSAGE_TYPE_VOICE = "voice";//请求消息类型：音频
	public static final String MESSAGE_TYPE_EVENT = "event";//请求消息类型：推送

	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";//事件类型：subscribe(订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";//事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_CLICK = "CLICK";//事件类型：CLICK(自定义菜单点击事件)

	public static final String FLOW_RESULT_MAPID = "wxCallOpResponseMap";//flow执行结果保存到CONTEXT的MAP名称
	public static final String FLOW_RESULT_MULTI_POJOID = "wxMultiItemListPojo";//flow执行结果保存到CONTEXT的图文消息对象名称

	//flow执行结果字符串保存到CONTEXT的字段名,答复内容里要使用时需要用该字段名占位
	//在设置生成后op执行map时需要此字段时也必须设置名称与之一致
	public static final String FLOW_RESULT_STRING = "wxLocalOpResult";

	/**
	 * 成功
	 */
	public static final String RET_SUCC = "000000";
	/**
	 * 失败
	 */
	public static final String RET_FAIL = "999999";
	/**
	 * 查询数据不存在
	 */
	public static final String NOT_FIND_MSG = "查询数据不存在";
	/**
	 * 字段-errCode
	 */
	public static final String FEILD_ERRCODE = "errCode";
	/**
	 * 字段-errMsg
	 */
	public static final String FEILD_ERRMSG = "errMsg";



}

