<!DOCTYPE html>
<html>
	<head>
		<title>在线支付平台仿真端--${status!}</title>
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
					<div class="box box-warning">
						<div class="box-body">
							<div class="row">
								<div class="col-sm-5  text-center">
									<img src="/ynlx/img/error/xiaoqi.jpg" alt="errorLogo" />
								</div>
								<div class="col-sm-7">
									<#-- 详见 org.springframework.boot.autoconfigure.web.DefaultErrorAttributes -->
									<div class="row">
										<div class="col-sm-12 text-center">
											<h1 class="error-header text-yellow">${status!}</h1>
											<h4>${error!}</h4>
										</div>
										<div class="col-sm-12">&nbsp;</div>
										<div class="col-sm-12">
											<div class="callout callout-warning">
												<h6>${exception!}</h6>
												<p>${message!}</p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-2 col-md-2"></div>
			</div>
		</div>
	</body>
</html>