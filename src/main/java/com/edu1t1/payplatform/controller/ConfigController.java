package com.edu1t1.payplatform.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.edu1t1.payplatform.config.PayPlatformConfig;
import com.edu1t1.payplatform.unionpay.UnionpayConfig;
import com.edu1t1.payplatform.unionpay.sdk.SDKConstants;

/**
 * 平台配置
 * @author zengxt
 * @date 2018-03-20
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
	
	private final static Logger logger = LogManager.getLogger(ConfigController.class.getName());
	
	@Autowired
	private PayPlatformConfig payPlatformConfig;
	@Autowired
	private UnionpayConfig unionpayConfig;
	
	@RequestMapping("")
	public ModelAndView defaultPage(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		//平台配置
		Map<String, Object> base = new HashMap<String, Object>();
		base.put("appId", payPlatformConfig.getAppId()); //应用标识
		base.put("connectionTimeout", payPlatformConfig.getAsyncNotifyConnectionTimeout()); //异步通知 连接建立超时时间
		base.put("readTimeout", payPlatformConfig.getAsyncNotifyReadTimeout()); //异步通知 读取数据超时时间
		model.put("base", base);
		
		Map<String, Object> unionpay = new HashMap<String, Object>();
		//银联基本配置
		unionpay.put("successRate", unionpayConfig.getSuccessRate()); //交易成功率
		unionpay.put("validateSign", unionpayConfig.isValidateSign()); //数据签名验证
		//银联交易配置
		unionpay.put(SDKConstants.param_accNo, unionpayConfig.getAccNo()); //交易账号
		unionpay.put(SDKConstants.param_payCardType, unionpayConfig.getPayCardType()); //支付卡类型
		unionpay.put(SDKConstants.param_payType, unionpayConfig.getPayType()); //支付方式
		model.put("unionpay", unionpay);
		
		return new ModelAndView("config", model);
	}
	
	/**
	 * 平台配置
	 * @param appId 应用标识
	 * @param asyncNotifyConnectionTimeout 异步通知 连接建立超时时间
	 * @param asyncNotifyReadTimeout 异步通知 读取数据超时时间
	 * @return
	 */
	@RequestMapping(value="/base", method=RequestMethod.POST)
	public Map<String, Object> baseConfig(@RequestParam(name="appId", required=true) String appId,
			@RequestParam(name="connectionTimeout", required=true, defaultValue="8000") Integer asyncNotifyConnectionTimeout,
			@RequestParam(name="readTimeout", required=true, defaultValue="8000") Integer asyncNotifyReadTimeout) {	
		Map<String, Object> respData = new HashMap<String, Object>();
		
		if (StringUtils.isBlank(appId)) {
			respData.put("success", false);
			respData.put("message", "平台配置所有配置均为必输项");
			return respData;
		}
		
		payPlatformConfig.setAppId(appId);
		payPlatformConfig.setAsyncNotifyConnectionTimeout(asyncNotifyConnectionTimeout);
		payPlatformConfig.setAsyncNotifyReadTimeout(asyncNotifyReadTimeout);
		
		if (logger.isInfoEnabled()) {
			logger.info("平台配置更新为：[appId=" + appId +  ", asyncNotifyConnectionTimeout=" + asyncNotifyConnectionTimeout + ", asyncNotifyReadTimeout=" + asyncNotifyReadTimeout + "]");
		}
		
		respData.put("success", true);
		respData.put("message", "平台配置保存成功");
		return respData;
	}
	
	/**
	 * 银联配置
	 * @param successRate 交易成功率
	 * @param validateSign 数据签名验证
	 * @param accNo 交易账号
	 * @param payCardType 支付卡类型
	 * @param payType 支付方式
	 * @return
	 */
	@RequestMapping(value="/unionpay", method=RequestMethod.POST)
	public Map<String, Object> unionpayConfig(@RequestParam(name="successRate", required=true, defaultValue="100") Integer successRate,
			@RequestParam(name="validateSign", required=true, defaultValue="true") Boolean validateSign,
			@RequestParam(name="accNo", required=true) String accNo,
			@RequestParam(name="payCardType", required=true) String payCardType,
			@RequestParam(name="payType", required=true) String payType) {	
		Map<String, Object> respData = new HashMap<String, Object>();
		
		if (StringUtils.isBlank(accNo)
				|| StringUtils.isBlank(payCardType)
				|| StringUtils.isBlank(payCardType)) {
			respData.put("success", false);
			respData.put("message", "银联支付所有配置均为必输项");
			return respData;
		}
		
		accNo = accNo.replaceAll(" ", "");
		
		unionpayConfig.setSuccessRate(successRate);
		unionpayConfig.setValidateSign(validateSign);
		
		unionpayConfig.setAccNo(accNo);
		unionpayConfig.setPayCardType(payCardType);
		unionpayConfig.setPayType(payType);
		
		if (logger.isInfoEnabled()) {
			logger.info("银联支付配置更新为：[successRate=" + successRate +  ", validateSign=" + validateSign + ", accNo=" + accNo + ", payCardType=" + payCardType + ", payType=" + payType + "]");
		}
		
		respData.put("success", true);
		respData.put("message", "银联支付配置保存成功");
		return respData;
	}
	
	/**
	 * 银联文件下载
	 * @param filename 文件名
	 * @param extension 扩展名
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/unionpay/download/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> unionpayDownload(@PathVariable("filename") String filename,
			@PathVariable("extension") String extension) throws IOException {
		
		if (StringUtils.isBlank(filename)
				|| StringUtils.isBlank(extension)) {
			throw new IOException("文件名或者扩展名为空");
		}
		
		String filePath = String.format("certs/unionpay/%s.%s", filename, extension);
		
		ClassPathResource file = new ClassPathResource(filePath);
		
        HttpHeaders headers = new HttpHeaders(); //定义Http头
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename())); //避免出现文件随机产生名字，而不能识别
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
  
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream")) //文件格式
                .body(new InputStreamResource(file.getInputStream()));
	}
}
