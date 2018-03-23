package com.edu1t1.payplatform.unionpay;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.edu1t1.payplatform.unionpay.sdk.SDKConfig;

/**
 * 银联支付配置
 * @author zengxt
 * @date 2018-02-27
 */
@Component
@ConfigurationProperties(prefix = "payplatform.unionpay")
public class UnionpayConfig {
	
	//初始化银联sdk配置
	private SDKConfig sdkConfig = SDKConfig.getConfig();
	
	/**
	 * 交易成功率 0-100
	 */
	private int successRate;
	/**
	 * 是否启用数据签名验证
	 */
	private boolean validateSign;
	/**
	 * 签名证书路径
	 */
	private String signCertPath;
	/**
	 * 签名证书密码
	 */
	private String signCertPwd;
	/**
	 * 签名证书类型
	 */
	private String signCertType;
	/**
	 * 验证签名证书目录
	 */
	private String validateCertDir;
	
	/**
	 * 交易账号
	 */
	private String accNo;
	/**
	 * 支付方式
	 */
	private String payType;
	/**
	 * 支付卡类型
	 */
	private String payCardType;

	public int getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(int successRate) {
		this.successRate = successRate;
	}
	
	public boolean isValidateSign() {
		return validateSign;
	}
	public void setValidateSign(boolean validateSign) {
		this.validateSign = validateSign;
	}
	
	public String getSignCertPath() {
		return signCertPath;
	}
	public void setSignCertPath(String signCertPath) {
		Assert.notNull(signCertPath, "sign-cert-path must not be null");
		this.signCertPath = signCertPath;
		this.sdkConfig.setSignCertPath(this.signCertPath);
	}
	
	public String getSignCertPwd() {
		return signCertPwd;
	}
	public void setSignCertPwd(String signCertPwd) {
		this.signCertPwd = signCertPwd;
		this.sdkConfig.setSignCertPwd(signCertPwd);
	}
	
	public String getSignCertType() {
		return signCertType;
	}
	public void setSignCertType(String signCertType) {
		this.signCertType = signCertType;
		this.sdkConfig.setSignCertType(signCertType);
	}
	
	public String getValidateCertDir() {
		return validateCertDir;
	}
	public void setValidateCertDir(String validateCertDir) {
		Assert.notNull(validateCertDir, "validate-cert-dir must not be null");
		this.validateCertDir = validateCertDir;
		this.sdkConfig.setValidateCertDir(this.validateCertDir);
	}
	
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayCardType() {
		return payCardType;
	}
	public void setPayCardType(String payCardType) {
		this.payCardType = payCardType;
	}
}
