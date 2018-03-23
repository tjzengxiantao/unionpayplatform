package com.edu1t1.payplatform.unionpay.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu1t1.payplatform.config.PayPlatformConfig;
import com.edu1t1.payplatform.unionpay.UnionpayConfig;
import com.edu1t1.payplatform.unionpay.enums.TxnType;
import com.edu1t1.payplatform.unionpay.sdk.AcpService;
import com.edu1t1.payplatform.unionpay.sdk.SDKConstants;
import com.edu1t1.payplatform.unionpay.sdk.SDKUtil;
import com.edu1t1.payplatform.unionpay.service.UnionpayService;
import com.edu1t1.payplatform.util.SerialNumberGenerator;

@Service
public class UnionpayServiceImpl implements UnionpayService {
	
	private final static Logger logger = LogManager.getLogger(UnionpayServiceImpl.class.getName());
	
	@Autowired
	private UnionpayConfig unionpayConfig;
	@Autowired
	private PayPlatformConfig  payPlatformConfig;

	@Override
	public Map<String, String> pay(Map<String, String> reqData, String encoding) {
		
		Map<String, String> respData = new HashMap<String, String>();
		try {
			
			respData.putAll(this.initMessage(reqData));
			
			if (unionpayConfig.isValidateSign() && !AcpService.validate(reqData, encoding)) {
				logger.error("银联在线--支付--请求报文，签名验证失败：" + SDKUtil.printMap(reqData));
				respData.put(SDKConstants.param_respCode, "99"); //应答码
				respData.put(SDKConstants.param_respMsg, "交易失败，数据签名验证失败"); //应答码信息
			} else {
				String txnType = reqData.get(SDKConstants.param_txnType);
				if (TxnType.PAY.getType().equals(txnType)) { //消费
					//支付随机结果
					boolean paySuccess = this.paySuccess();
					respData.put(SDKConstants.param_respCode, paySuccess ? "00" : "99"); //应答码
					respData.put(SDKConstants.param_respMsg, paySuccess ? "交易成功" : "交易失败"); //应答码信息
				} else {
					respData.put(SDKConstants.param_respCode, "99"); //应答码
					respData.put(SDKConstants.param_respMsg, "交易失败, 交易类型错误, txnType=" + txnType); //应答码信息
				}
			}
		} catch (Exception e) {
			respData.put(SDKConstants.param_respCode, "99"); //应答码
			respData.put(SDKConstants.param_respMsg, "交易失败，交易异常：" + e.getMessage()); //应答码信息
		}
		
		respData = AcpService.sign(respData, encoding);
		
		return respData;
	}
	
	@Override
	public Map<String, String> refund(Map<String, String> reqData, String encoding) {
		Map<String, String> respData = new HashMap<String, String>();
		try {
			
			respData.putAll(this.initMessage(reqData));
			
			if (unionpayConfig.isValidateSign() && !AcpService.validate(reqData, encoding)) {
				logger.error("银联在线--支付--请求报文，签名验证失败：" + SDKUtil.printMap(reqData));
				respData.put(SDKConstants.param_respCode, "99"); //应答码
				respData.put(SDKConstants.param_respMsg, "交易失败，数据签名验证失败"); //应答码信息
			} else {
				String txnType = reqData.get(SDKConstants.param_txnType);
				if (TxnType.REFUND.getType().equals(txnType)) { //退货
					respData.put(SDKConstants.param_origQryId, reqData.get(SDKConstants.param_origQryId)); //原始交易流水号
					//支付随机结果
					boolean paySuccess = this.paySuccess();
					respData.put(SDKConstants.param_respCode, paySuccess ? "00" : "99"); //应答码
					respData.put(SDKConstants.param_respMsg, paySuccess ? "交易成功" : "交易失败"); //应答码信息
				} else {
					respData.put(SDKConstants.param_respCode, "99"); //应答码
					respData.put(SDKConstants.param_respMsg, "交易失败, 交易类型错误, txnType=" + txnType); //应答码信息
				}
			}
		} catch (Exception e) {
			respData.put(SDKConstants.param_respCode, "99"); //应答码
			respData.put(SDKConstants.param_respMsg, "交易失败，交易异常：" + e.getMessage()); //应答码信息
		}
		
		respData = AcpService.sign(respData, encoding);
		
		return respData;
	}

	@Override
	public Map<String, String> query(Map<String, String> reqData, String encoding) {
		Map<String, String> respData = new HashMap<String, String>();
		try {
			
			respData.putAll(this.initMessage(reqData));
			
			if (unionpayConfig.isValidateSign() && !AcpService.validate(reqData, encoding)) {
				logger.error("银联在线--支付--请求报文，签名验证失败：" + SDKUtil.printMap(reqData));
				respData.put(SDKConstants.param_respCode, "99"); //应答码
				respData.put(SDKConstants.param_respMsg, "交易失败，数据签名验证失败"); //应答码信息
			} else {
				String txnType = reqData.get(SDKConstants.param_txnType);
				if (TxnType.QUERY.getType().equals(txnType)) { //查询
					respData.put(SDKConstants.param_origRespCode, "00"); //原交易应答码
					respData.put(SDKConstants.param_origRespMsg, "交易成功"); //原交易应答信息
					//支付随机结果
					boolean paySuccess = this.paySuccess();
					respData.put(SDKConstants.param_respCode, paySuccess ? "00" : "99"); //应答码
					respData.put(SDKConstants.param_respMsg, paySuccess ? "交易成功" : "交易失败"); //应答码信息
				} else {
					respData.put(SDKConstants.param_respCode, "99"); //应答码
					respData.put(SDKConstants.param_respMsg, "交易失败, 交易类型错误, txnType=" + txnType); //应答码信息
				}
			}
		} catch (Exception e) {
			respData.put(SDKConstants.param_respCode, "99"); //应答码
			respData.put(SDKConstants.param_respMsg, "交易失败，交易异常：" + e.getMessage()); //应答码信息
		}
		
		respData = AcpService.sign(respData, encoding);
		
		return respData;
	}
	
	/**
	 * 初始化银联报文
	 * @param reqData
	 * @return
	 */
	private Map<String, String> initMessage(Map<String, String> reqData) {
		
		Map<String, String> respData = new HashMap<String, String>();
		
		respData.put(SDKConstants.param_version, reqData.get(SDKConstants.param_version)); //版本号
		respData.put(SDKConstants.param_encoding, reqData.get(SDKConstants.param_encoding)); //编码方式
		respData.put(SDKConstants.param_certId, reqData.get(SDKConstants.param_certId)); //证书ID
		respData.put(SDKConstants.param_signMethod, reqData.get(SDKConstants.param_signMethod)); //签名方法
		respData.put(SDKConstants.param_txnType, reqData.get(SDKConstants.param_txnType)); //交易类型
		respData.put(SDKConstants.param_txnSubType, reqData.get(SDKConstants.param_txnSubType)); //交易子类
		respData.put(SDKConstants.param_bizType, reqData.get(SDKConstants.param_bizType)); //业务类型
		respData.put(SDKConstants.param_accessType, reqData.get(SDKConstants.param_accessType)); //接入类型
		respData.put(SDKConstants.param_merId, reqData.get(SDKConstants.param_merId)); //商户代码
		respData.put(SDKConstants.param_orderId, reqData.get(SDKConstants.param_orderId)); //商户订单号
		respData.put(SDKConstants.param_txnTime, reqData.get(SDKConstants.param_txnTime)); //交易时间
		respData.put(SDKConstants.param_txnAmt, reqData.get(SDKConstants.param_txnAmt)); //交易金额
		respData.put(SDKConstants.param_currencyCode, reqData.get(SDKConstants.param_currencyCode)); //币种
		respData.put(SDKConstants.param_reqReserved, reqData.get(SDKConstants.param_reqReserved)); //请求方保留域
		
		if (TxnType.PAY.equals(reqData.get(SDKConstants.param_txnType))
				|| TxnType.QUERY.equals(reqData.get(SDKConstants.param_txnType))) {
			respData.put(SDKConstants.param_accNo, unionpayConfig.getAccNo()); //交易账号
			respData.put(SDKConstants.param_payCardType, unionpayConfig.getPayCardType()); //支付卡类型
			respData.put(SDKConstants.param_payType, unionpayConfig.getPayType()); //支付方式
		}
		
		if (StringUtils.isBlank(reqData.get(SDKConstants.param_queryId))) {
			respData.put(SDKConstants.param_queryId, SerialNumberGenerator.sn24("YPP", payPlatformConfig.getAppId())); //平台流水号
		} else {
			respData.put(SDKConstants.param_queryId, reqData.get(SDKConstants.param_queryId)); //平台流水号
		}
		
		return respData;
	}
	
	/**
	 * 是否付款成功
	 * @return
	 */
	private boolean paySuccess() {
		if (unionpayConfig.getSuccessRate() == 100) {
			return true;
		} else if (unionpayConfig.getSuccessRate() == 0) {
			return false;
		}
		Random random = new Random();
		int value = random.nextInt(100) + 1;
		
		return value <= unionpayConfig.getSuccessRate();
	}
}
