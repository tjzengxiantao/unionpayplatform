package com.edu1t1.payplatform.unionpay.enums;

/**
 * 交易类型
 * @author zengxt
 * @date 2018-03-20
 */
public enum TxnType {
	
	QUERY("00", "交易状态查询交易"),
	PAY("01", "消费类交易"),
	REFUND("04", "退货类交易");
	
	private String type;
	private String desc;
	
	private TxnType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public boolean equals(String type) {
		if (type == null || type.length() == 0) {
			return false;
		}
		return this.type.equals(type);
	}

}
