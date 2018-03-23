package com.edu1t1.payplatform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 平台配置
 * @author zengxt
 * @date 2018-02-28
 */
@Component
@ConfigurationProperties(prefix = "payplatform")
public class PayPlatformConfig {
	
	/**
	 * 应用标识
	 */
	private String appId;
	/**
	 * 异步通知 连接建立超时时间 毫秒
	 */
	private int asyncNotifyConnectionTimeout = 30000;
	/**
	 * 异步通知 读取数据超时时间 毫秒
	 */
	private int asyncNotifyReadTimeout = 30000;

	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public int getAsyncNotifyConnectionTimeout() {
		return asyncNotifyConnectionTimeout;
	}
	public void setAsyncNotifyConnectionTimeout(int asyncNotifyConnectionTimeout) {
		this.asyncNotifyConnectionTimeout = asyncNotifyConnectionTimeout;
	}
	public int getAsyncNotifyReadTimeout() {
		return asyncNotifyReadTimeout;
	}
	public void setAsyncNotifyReadTimeout(int asyncNotifyReadTimeout) {
		this.asyncNotifyReadTimeout = asyncNotifyReadTimeout;
	}
}
