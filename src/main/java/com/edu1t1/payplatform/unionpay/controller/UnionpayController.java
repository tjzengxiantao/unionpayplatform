package com.edu1t1.payplatform.unionpay.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.edu1t1.payplatform.config.PayPlatformConfig;
import com.edu1t1.payplatform.http.AsyncNotify;
import com.edu1t1.payplatform.unionpay.sdk.AcpService;
import com.edu1t1.payplatform.unionpay.sdk.SDKConstants;
import com.edu1t1.payplatform.unionpay.sdk.SDKUtil;
import com.edu1t1.payplatform.unionpay.service.UnionpayService;
import com.edu1t1.payplatform.util.PaymentType;

/**
 * 银联支付接口
 * @author zengxt
 * @date 2018-02-28
 */
@RestController
@RequestMapping("/unionpay/gateway/api")
public class UnionpayController {
	
	private final static Logger logger = LogManager.getLogger(UnionpayController.class.getName());
	
	@Autowired
	private PayPlatformConfig  payPlatformConfig;
	@Autowired
	private UnionpayService unionpayService;
	
	/**
	 * 前台请求
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/frontTransReq")
	public ModelAndView frontTransReq(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> message = new HashMap<String, Object>();
		
		//UI样式
		Map<String, Object> uiStatus = new HashMap<String, Object>();
		uiStatus.put("ui", "danger");
		uiStatus.put("color", "red");
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", message);
		model.put("uiStatus", uiStatus);
		model.put("paymentType", PaymentType.UNIONPAY);
		
		ModelAndView modelAndView = new ModelAndView("pay_result");
		
		//获取商户请求报文
		Map<String, String> requestMessage = null;
		//同步通知报文数据
		Map<String, String> syncNotifyMessage = new HashMap<String, String>(); 
		String encoding = null;
		try {
			request.setCharacterEncoding(SDKConstants.ISO_8859_1_ENCODING);
			encoding = request.getParameter(SDKConstants.param_encoding);
			requestMessage = SDKUtil.getAllRequestParam(request, encoding);
			if (logger.isInfoEnabled()) {
				logger.info("银联在线--支付--请求报文：" + SDKUtil.printMap(requestMessage));
			}
			message.put("requestMessage", requestMessage);
		} catch (Exception e) {
			logger.error("银联在线--支付--请求报文，报文解析异常：" + e.getMessage());
			
			syncNotifyMessage.put(SDKConstants.param_respCode, "99"); //应答码
			syncNotifyMessage.put(SDKConstants.param_respMsg, "银联在线--支付--请求报文，报文解析异常：" + e.getMessage()); //应答码信息
			syncNotifyMessage = AcpService.sign(syncNotifyMessage, encoding); //数据签名
			
			message.put("requestMessage", requestMessage);
			message.put("syncNotifyMessage", syncNotifyMessage); //同步通知报文数据
			message.put("asyncNotifyMessage", null); //异步通知报文数据
			
			model.put("asyncNotifyMsg", "交易失败，无需异步通知");
			
			modelAndView.addAllObjects(model);
			return modelAndView;
		}
		
		Map<String, String> asyncNotifyMessage = unionpayService.pay(requestMessage, encoding);
		syncNotifyMessage.putAll(asyncNotifyMessage);
		
		if (logger.isInfoEnabled()) {
			logger.info("银联在线--支付--同步通知报文：" + SDKUtil.printMap(syncNotifyMessage));
		}
		message.put("syncNotifyMessage", syncNotifyMessage); //同步通知报文数据
		
		if (logger.isInfoEnabled()) {
			logger.info("银联在线--支付--异步通知报文：" + SDKUtil.printMap(asyncNotifyMessage));
		}
		message.put("asyncNotifyMessage", asyncNotifyMessage); //异步通知报文数据
		
		//同步通知
		String frontUrl = requestMessage.get(SDKConstants.param_frontUrl);
		if (StringUtils.isNotBlank(frontUrl)) {
			model.put("notifyUrl", frontUrl);
		}
		
		if ("00".equals(asyncNotifyMessage.get(SDKConstants.param_respCode))) {
			//修改UI样式为成功
			uiStatus.put("ui", "success");
			uiStatus.put("color", "green");
			
			//异步通知
			String backUrl = requestMessage.get(SDKConstants.param_backUrl);
			AsyncNotify asyncNotify = new AsyncNotify();
			String asyncNotifyInfo =  asyncNotify.send(backUrl, asyncNotifyMessage, encoding, 
					payPlatformConfig.getAsyncNotifyConnectionTimeout(), payPlatformConfig.getAsyncNotifyReadTimeout());
			model.put("asyncNotifyInfo", asyncNotifyInfo);
			
		} else {
			model.put("asyncNotifyInfo", "交易失败，无需异步通知");
		}
		
		modelAndView.addAllObjects(model);
		
		return modelAndView;
	}
	
	/**
	 * 后台请求，目前仅支持 txnType=04 退货类交易
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/backTransReq")
	public String backTransReq(HttpServletRequest request, HttpServletResponse response) {	
		
		Map<String, String> respData = new HashMap<String, String>();
		
		//获取商户请求报文
		Map<String, String> requestMessage = null;
		String encoding = null;
		try {
			request.setCharacterEncoding(SDKConstants.ISO_8859_1_ENCODING);
			encoding = request.getParameter(SDKConstants.param_encoding);
			requestMessage = SDKUtil.getAllRequestParam(request, encoding);
			if (logger.isInfoEnabled()) {
				logger.info("银联在线--退货--请求报文：" + SDKUtil.printMap(requestMessage));
			}
		} catch (Exception e) {
			logger.error("银联在线--退货--请求报文，报文解析异常：" + e.getMessage());
			respData.put(SDKConstants.param_respCode, "99"); //应答码
			respData.put(SDKConstants.param_respMsg, "银联在线--请求报文，报文解析异常：" + e.getMessage()); //应答码信息
			respData = AcpService.sign(respData, encoding); //数据签名
			return SDKUtil.map2StringAll(respData);
		}
		
//		String txnType = reqData.get(SDKConstants.param_txnType);
//		if ("04".equals(txnType)) { //退货
//			respData = unionpayService.refund(reqData, encoding);
//		}
		respData = unionpayService.refund(requestMessage, encoding);
		
		if (logger.isInfoEnabled()) {
			logger.info("银联在线--退货--通知报文：" + SDKUtil.printMap(respData));
		}
		
		if ("00".equals(respData.get(SDKConstants.param_respCode))
				&& !respData.isEmpty()) {
			//异步通知
			String backUrl = requestMessage.get(SDKConstants.param_backUrl);
			AsyncNotify asyncNotify = new AsyncNotify();
			asyncNotify.send(backUrl, respData, encoding, 
					payPlatformConfig.getAsyncNotifyConnectionTimeout(), payPlatformConfig.getAsyncNotifyReadTimeout());
		}
		return SDKUtil.map2StringAll(respData);
	}
	
	/**
	 * 单笔查询请求
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryTrans")
	public String queryTrans(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> respData = new HashMap<String, String>();
		
		//获取商户请求报文
		Map<String, String> requestMessage = null;
		String encoding = null;
		try {
			request.setCharacterEncoding(SDKConstants.ISO_8859_1_ENCODING);
			encoding = request.getParameter(SDKConstants.param_encoding);
			requestMessage = SDKUtil.getAllRequestParam(request, encoding);
			if (logger.isInfoEnabled()) {
				logger.info("银联在线--单笔查询--请求报文：" + SDKUtil.printMap(requestMessage));
			}
		} catch (Exception e) {
			logger.error("银联在线--单笔查询--请求报文，报文解析异常：" + e.getMessage());
			respData.put(SDKConstants.param_respCode, "99"); //应答码
			respData.put(SDKConstants.param_respMsg, "银联在线--请求报文，报文解析异常：" + e.getMessage()); //应答码信息
			respData = AcpService.sign(respData, encoding); //数据签名
			return SDKUtil.map2StringAll(respData);
		}
		
		respData = unionpayService.query(requestMessage, encoding);
		
		if (logger.isInfoEnabled()) {
			logger.info("银联在线--单笔查询--响应报文：" + SDKUtil.printMap(respData));
		}
		
		return SDKUtil.map2StringAll(respData);
	}
}
