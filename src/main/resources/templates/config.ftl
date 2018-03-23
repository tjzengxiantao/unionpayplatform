<!DOCTYPE html>
<html>
	<head>
		<title>在线支付平台仿真端--配置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	    <meta content="telephone=no" name="format-detection"/>
	    <meta name="apple-mobile-web-app-capable" content="yes">
	    <meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="/adminlte/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
		<link href="/platform/css/payplatform.css" rel="stylesheet" type="text/css" />
	</head>
	<body class="bg-gray">
		<#-- 银联支付-支付卡类型 -->
		<#assign payCardTypeMap={"00":"未知", "01":"借记账户", "02":"贷记账户", "03":"准贷记账户", "04":"借贷合一账户", "05":"预付费账户", "06":"半开放预付费账户"} />
		<#-- 银联支付-支付方式 -->
		<#assign payTypeMap={"0001":"认证支付", "0002":"快捷支付", "0004":"储值卡支付", "0005":"IC卡支付", "0201":"网银支付", "1001":"牡丹畅通卡支付", "1002":"中铁银通卡支付",
												"0401":"信用卡支付-暂定", "0402":"小额临时支付", "0403":"认证支付2.0", "0404":"互联网订单手机支付", "9000":"其他无卡支付（如手机客户端支付）"} />
		<div class="container-fluid bg-light-blue">
			<div class="row">
				<div class="col-sm-8 col-md-offset-2 col-md-8 col-md-offset-2"><h2>银联在线支付&nbsp;&nbsp;<small>平台仿真端<small></h2></div>
			</div>
		</div>
		<div class="container">
			<div id="configAlert" class="alert alert-dismissible" style="display:none; z-index:10000;"></div>
			<div class="row">
				<div class="col-sm-12 col-md-12">&nbsp;</div>
				<div class="col-sm-12 col-md-12">&nbsp;</div>
				<div class="col-sm-12 col-md-12">
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_base" data-toggle="tab">平台配置</a></li>
							<li><a href="#tab_unionpay" data-toggle="tab">银联在线支付配置</a></li>
						</ul>
						<div class ="tab-content">
							<div class="tab-pane active" id="tab_base">
				            	<#-- 平台配置用表单 begin -->
				            	<form id="baseConfigForm" class="form-horizontal">
								<div class="row">
									<div class="col-sm-6 col-sm-offset-3 col-md-6 col-md-offset-3">
										<div class="panel panel-default">
											<div class="panel-heading">基本配置</div>
											<div class="panel-body">
												<div class="form-group">
								                	<label for="appId" class="col-sm-3 control-label">应用标识</label>
								                	<div class="col-sm-9">
								                		<input type="text" class="form-control" id="appId" name="appId" placeholder="应用标识" maxlength="2" value="${base.appId!""}">
							                		</div>
							                		<div class="col-sm-9 col-sm-offset-3 small">分布式部署时使用（例如：保障平台流水号的唯一性）</div>
								                </div>
								                <div class="form-group">
								                	<label for="connectionTimeout" class="col-sm-3 control-label">连接建立超时时间</label>
								                	<div class="col-sm-9">
								                		<input type="number" min="100" class="form-control" id="connectionTimeout" name="connectionTimeout" placeholder="连接建立超时时间" value="#{base.connectionTimeout}">
							                		</div>
							                		<div class="col-sm-9 col-sm-offset-3 small">异步通知 连接建立超时时间 毫秒</div>
								                </div>
								                <div class="form-group">
								                	<label for="readTimeout" class="col-sm-3 control-label">读取数据超时时间</label>
								                	<div class="col-sm-9">
								                		<input type="number" min="100" class="form-control" id="readTimeout" name="readTimeout" placeholder="读取数据超时时间" value="#{base.readTimeout}">
							                		</div>
							                		<div class="col-sm-9 col-sm-offset-3 small">异步通知 读取数据超时时间 毫秒</div>
								                </div>
											</div>
										</div>
									</div>
									<div class="col-sm-6 col-sm-offset-3 col-md-6 col-md-offset-3">
										<div class="form-group">
						                	<div class="col-sm-4"></div>
						                	<div class="col-sm-4">
						                		<button type="button" class="btn btn-block btn-primary" onclick="saveConfig('baseConfigForm', '/config/base')">保存配置</button>
						                	</div>
						                </div>
									</div>
								</div>
								</form>
								<#-- 平台配置用表单 end -->
				            </div>
							<div class="tab-pane" id="tab_unionpay">
								<#-- 银联支付配置用表单 begin -->
								<form id="unionpayConfigForm" class="form-horizontal">
								<div class="row">
									<div class="col-sm-6 col-sm-offset-3 col-md-6 col-md-offset-3">
										<div class="panel panel-default">
											<div class="panel-heading">基本配置</div>
											<div class="panel-body">
												<div class="form-group">
								                	<label for="successRate" class="col-sm-3 control-label">交易成功率</label>
								                	<div class="col-sm-9">
								                		<input type="number" min="0" max="100" step="5" class="form-control" id="successRate" name="successRate" placeholder="交易成功率" value="#{unionpay.successRate}">
							                		</div>
							                		<div class="col-sm-9 col-sm-offset-3 small">交易成功率 0-100 100为全部成功</div>
								                </div>
								                <div class="form-group">
								                	<label for="validateSign" class="col-sm-3 control-label">数字签名验证</label>
								                	<div class="col-sm-9">
								                		<select class="form-control" id="validateSign" name="validateSign" placeholder="数字签名验证" autocomplete="off">
								                			<option value="true" ${(true==unionpay.validateSign) ? string('selected="selected"', '')}>true - 启用</option>
								                			<option value="false" ${(false==unionpay.validateSign) ? string('selected="selected"', '')}>false - 禁用</option>
								                		</select>
								                	</div>
								                	<div class="col-sm-9 col-sm-offset-3 small">请求报文是否启用数字签名验证</div>
								                </div>
								                <div class="col-sm-12">
								                	银联证书：
								                	<ul>
								                		<li>签名证书密码 000000</li>
								                		<li>签名证书类型 PKCS12</li>
								                		<li>私钥/签名证书<a href="/config/unionpay/download/edu1t1_sign/pfx">下载</a></li>
								                		<li>公钥/验签证书<a href="/config/unionpay/download/edu1t1_validate/cer">下载</a></li>
								                	</ul>
								                </div>
											</div>
										</div>
										<div class="panel panel-default">
											<div class="panel-heading">交易配置-通知报文数据</div>
											<div class="panel-body">
												<div class="form-group">
								                	<label for="accNo" class="col-sm-3 control-label">交易账号/accNo</label>
								                	<div class="col-sm-9">
								                		<input type="text" class="form-control" id="accNo" name="accNo" placeholder="交易账号/accNo" onkeyup="formatBankNo(this)" maxlength="23" value="${unionpay.accNo!""}">
							                		</div>
								                </div>
								                <div class="form-group">
								                	<label for="payCardType" class="col-sm-3 control-label">支付卡类型/payCardType</label>
								                	<div class="col-sm-9">
								                		<select class="form-control" id="payCardType" name="payCardType" placeholder="支付卡类型" autocomplete="off">
								                			<#list payCardTypeMap?keys as key>
								                			<option value="${key}" ${(key==unionpay.payCardType) ? string('selected="selected"', '')}>${key} - ${payCardTypeMap[key]}</option>
								                			</#list>
								                		</select>
								                	</div>
								                </div>
								                <div class="form-group">
								                	<label for="payType" class="col-sm-3 control-label">支付方式/payType</label>
								                	<div class="col-sm-9">
								                		<select class="form-control" id="payType" name="payType" placeholder="支付方式" autocomplete="off">
								                			<#list payTypeMap?keys as key>
								                			<option value="${key}" ${(key==unionpay.payType) ? string('selected="selected"', '')}>${key} - ${payTypeMap[key]}</option>							                			
								                			</#list>
								                		</select>
								                	</div>
								                </div>
											</div>
										</div>
									</div>
									<div class="col-sm-6 col-sm-offset-3 col-md-6 col-md-offset-3">
										<div class="form-group">
						                	<div class="col-sm-4"></div>
						                	<div class="col-sm-4">
						                		<button type="button" class="btn btn-block btn-primary" onclick="saveConfig('unionpayConfigForm', '/config/unionpay')">保存配置</button>
						                	</div>
						                </div>
									</div>
								</div>
								</form>
								<#-- 银联支付配置用表单 end -->
				            </div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script src="/plugins/jquery/jquery.min.js" type="text/javascript"></script>
		<script src="/plugins/jquery/jquery.form.js" type="text/javascript"></script>
		<script src="/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			function saveConfig(configFormId, actionUrl) {
				var options = {
					url:actionUrl,
					type:'POST',
					dataType:'json',
					beforeSubmit: function () {},
					success:function (data) {
						$("#configAlert").removeClass("alert-success");
						$("#configAlert").removeClass("alert-error");
						if(data.success) {
							$("#configAlert").addClass("alert-success");
						} else {
							$("#configAlert").addClass("alert-error");
						}
						$("#configAlert").html(
							"<a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a>" +
							data.message
						);
						$("#configAlert").fadeIn(100).delay(2000).fadeOut(500);
					}
				};
				$('#' + configFormId).ajaxSubmit(options);
			}
			
			//$("#accNo").on("keyup", formatBC);
			
			function formatBC(e){
				$(this).attr("data-oral", $(this).val().replace(/\ +/g,""));
				//$("#bankCard").attr("data-oral")获取未格式化的卡号
				  
				var self = $.trim(e.target.value);
				var temp = this.value.replace(/\D/g, '').replace(/(....)(?=.)/g, '$1 ');
				if(self.length > 22){
					this.value = self.substr(0, 22);
					return this.value;
				}
				if(temp != this.value){
					this.value = temp;
				}
			}
			
			function formatBankNo (BankNo){
			    if (BankNo.value == "") return;
			    var account = new String (BankNo.value);
			    account = account.substring(0,23); /*帐号的总数, 包括空格在内 */
			    if (account.match (".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null){
			        /* 对照格式 */
			        if (account.match (".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" +
			        ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|" + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null){
			            var accountNumeric = accountChar = "", i;
			            for (i=0;i<account.length;i++){
			                accountChar = account.substr (i,1);
			                if (!isNaN (accountChar) && (accountChar != " ")) accountNumeric = accountNumeric + accountChar;
			            }
			            account = "";
			            for (i=0;i<accountNumeric.length;i++){    /* 可将以下空格改为-,效果也不错 */
			                if (i == 4) account = account + " "; /* 帐号第四位数后加空格 */
			                if (i == 8) account = account + " "; /* 帐号第八位数后加空格 */
			                if (i == 12) account = account + " ";/* 帐号第十二位后数后加空格 */
			                if (i == 16) account = account + " ";/* 帐号第十六位后数后加空格 */
			                account = account + accountNumeric.substr (i,1)
			            }
			        }
			    }
			    else
			    {
			        account = " " + account.substring (1,5) + " " + account.substring (6,10) + " " + account.substring (14,18) + "-" + account.substring(18,25);
			    }
			    if (account != BankNo.value) BankNo.value = account;
			}
			formatBankNo(document.getElementById("accNo"));
		</script>
	</body>
</html>