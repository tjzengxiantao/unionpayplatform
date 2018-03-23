package com.edu1t1.payplatform.unionpay.service;

import java.util.Map;

/**
 * 银联交易处理
 * @author zengxt
 * @date 2018-03-02
 */
public interface UnionpayService {
	
	/**
	 * 支付
	 * @param reqData
	 * @param encoding
	 * @return
	 */
	public Map<String, String> pay(Map<String, String> reqData, String encoding);
	
	/**
	 * 退款
	 * @param reqData
	 * @param encoding
	 * @return
	 */
	public Map<String, String> refund(Map<String, String> reqData, String encoding);
	
	/**
	 * 查证
	 * @param reqData
	 * @param encoding
	 * @return
	 */
	public Map<String, String> query(Map<String, String> reqData, String encoding);

}
