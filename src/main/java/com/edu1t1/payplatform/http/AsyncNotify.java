package com.edu1t1.payplatform.http;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 商户异步通知操作
 * @author zengxt
 * @date 2018-03-02
 */
public class AsyncNotify {
	
	private final static Logger logger = LogManager.getLogger(AsyncNotify.class.getName());
	
	/**
	 * 发送异步通知
	 * @param url 目标地址 
	 * @param data 数据
	 * @param encoding 编码方式
	 * @param connectionTimeout 连接建立超时时间 毫秒
	 * @param readTimeOut 读取数据超时时间 毫秒
	 * @return
	 */
	public String send(String url, Map<String, String> data, String encoding, int connectionTimeout, int readTimeOut) {
		try {
			if (StringUtils.isNotBlank(url)) {
				HttpClient httpClient = new HttpClient(url, connectionTimeout, readTimeOut);
				int status = httpClient.send(data, encoding);
				if (status == 200) {
					if (logger.isInfoEnabled()) {
						logger.info("异步通知成功，backUrl=" + url);
					}
					return "异步通知成功，backUrl=" + url;
				} else {
					if (logger.isWarnEnabled()) {
						logger.warn("异步通知失败，httpStatus=" + status + "，backUrl=" + url);
					}
					return "异步通知失败，httpStatus=" + status + "，backUrl=" + url;
				}
			} else {
				if (logger.isWarnEnabled()) {
					logger.warn("异步通知失败, url为空");
				}
				return "异步通知失败, url为空";
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("异步通知失败，backUrl=" + url + "异常：" + e.getMessage());
			}
			return "异步通知失败，backUrl=" + url + "异常：" + e.getMessage();
		}
	}
}
