<!DOCTYPE html>
<html>
	<head>
		<title>${paymentType.desc!}--支付结果</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	    <meta content="telephone=no" name="format-detection"/>
	    <meta name="apple-mobile-web-app-capable" content="yes">
	    <meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="/adminlte/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
	</head>
	<body class="bg-gray">
		<div class="container-fluid bg-light-blue">
			<div class="row">
				<div class="col-sm-8 col-md-offset-2 col-md-8 col-md-offset-2"><h2>银联在线支付&nbsp;&nbsp;<small>平台仿真端<small></h2></div>
			</div>
		</div>
		<div class="container">
			<div class="row">
				<div class="col-sm-12 col-md-12">&nbsp;</div>
				<div class="col-sm-12 col-md-12">&nbsp;</div>
				<div class="col-sm-2 col-md-2"></div>
				<div class="col-sm-8 col-md-8">
					<div class="box box-${uiStatus.ui}">
						<div class="box-header with-border">
							<div class="row">
								<div class="col-sm-6 col-md-6">
									<img src="/ynlx/img/pay_logo/${paymentType.type!"default"}.png" height="40px" alt="支付平台LOGO" />
								</div>
								<div class="col-sm-6 col-md-6 text-right">
									<h5>${paymentType.desc!}--支付结果</h5>
								</div>
							</div>
						</div>
						<div class="box-body">
							<div class="row">
								<div class="col-sm-3 text-center" style="font-size:20px;">
									<#if uiStatus.ui=="success">
									<i class="icon icon-ok-circle icon-4x text-${uiStatus.color}"></i>
									<#else>
									<i class="icon icon-remove-circle icon-4x text-${uiStatus.color}"></i>
									</#if>
								</div>
								<div class="col-sm-9">
										<#if message.syncNotifyMessage??>
										<#if paymentType.type=="unionpay">
										<div class="row">
											<div class="col-xs-4 col-sm-3 text-right">平台流水号</div>
											<div class="col-xs-8 col-sm-9">${message.syncNotifyMessage.queryId!"&nbsp;"}</div>
											<div class="col-xs-4 col-sm-3 text-right">订单号</div>
											<div class="col-xs-8 col-sm-9">${message.syncNotifyMessage.orderId!"&nbsp;"}</div>
											<div class="col-xs-4 col-sm-3 text-right">订单金额</div>
											<div class="col-xs-8 col-sm-9">${message.syncNotifyMessage.txnAmt!"&nbsp;"}</div>
											<div class="col-xs-4 col-sm-3 text-right">订单状态</div>
											<div class="col-xs-8 col-sm-9">${(message.syncNotifyMessage.respCode=='00') ? string('成功', '失败')}</div>
											<div class="col-xs-4 col-sm-3 text-right">描述信息</div>
											<div class="col-xs-8 col-sm-9">${message.syncNotifyMessage.respMsg!"&nbsp;"}</div>
										</div>
										</#if>
										<#else>
										<div class="row">
											<div class="col-xs-12 col-sm-12"><h4 class=" text-red">无响应报文</h4></div>
										</div>
										</#if>
								</div>
							</div>
						</div>
						<div class="box-footer">
							<div class="row">
								<div class="col-sm-4"></div>
								<div class="col-sm-4">
									<a class="btn btn-block btn-${uiStatus.ui}" href="javascript:void(0);" onclick="javascript:returnShop();">返回商户</a>
								</div>
								<div class="col-sm-4"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-2 col-md-2"></div>
				<div class="col-sm-12 col-md-12">&nbsp;</div>
				<div class="col-sm-2 col-md-2"></div>
				<div class="col-sm-8 col-md-8">
					<div class="row">
						<div class="col-sm-12"><h5>商家请求报文</h5></div>
						<div class="col-sm-12">
							<#if message.requestMessage??>
								<#list message.requestMessage?keys as key>
									${key!}=${message.requestMessage[key]!} <br />
								</#list>
							</#if>
						</div>
					</div>
				</div>
				<div class="col-sm-2 col-md-2"></div>
				<div class="col-sm-12 col-md-12">&nbsp;</div>
				<div class="col-sm-2 col-md-2"></div>
				<div class="col-sm-8 col-md-8">
					<div class="row">
						<div class="col-sm-12"><h5>异步通知报文&nbsp;&nbsp;<small class="text-red">${asyncNotifyInfo!}</small></h5></div>
						<div class="col-sm-12">
							<#if message.asyncNotifyMessage??>
								<#list message.asyncNotifyMessage?keys as key>
									${key!}=${message.asyncNotifyMessage[key]!} <br />
								</#list>
							</#if>
						</div>
					</div>
				</div>
				<div class="col-sm-2 col-md-2"></div>
				<div class="col-sm-12 col-md-12">&nbsp;</div>
			</div>
		</div>
		<#if notifyUrl??>
		<!-- 返回商户，提交表单 -->
		<form id="returnShopForm" method="post">
			<#if message.syncNotifyMessage??>
				<#list message.syncNotifyMessage?keys as key>
					<input type="hidden" name="${key!}" value="${message.syncNotifyMessage[key]!""}" />
				</#list>
			</#if>
		</form>
		</#if>
		<!-- 引入及定义脚本 -->
		<script src="/plugins/jquery/jquery.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			function returnShop() {
				<#if notifyUrl??>
				$("#returnShopForm").attr("action", "${notifyUrl!}");
				$("#returnShopForm").submit();
				<#else>
				alert("商家报文，返回商户地址参数为空");
				</#if>
			}
		</script>
	</body>
</html>