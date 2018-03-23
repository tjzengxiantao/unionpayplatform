package com.edu1t1.payplatform.util;

/**
 * 支付类型
 * @author zengxt
 * @date 2018-02-28
 */
public enum PaymentType {
	
	UNIONPAY("unionpay", "银联支付"),
	TRCPAY("trcpay", "吉祥钱包");
	
	private String type;
	private String desc;
	
	private PaymentType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getDesc() {
		return this.desc;
	}

}
