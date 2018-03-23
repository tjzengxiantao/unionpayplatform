
银联在线支付仿真平台
================

#### 背景
银联在线支付本身为开发人员提供了测试环境，但是在某些项目组中，无法连接外部网络，这样就给需要开发银联在线支付的人员造成了一个比较头疼的问题。<br/>
通过对银联在线支付SDK研究，银联支付5.0.0版（即报文中version=5.0.0）采用RSA非对称数字证书签名的方式进行报文合法性校验，故决定实现银联在线支付平台仿真端，为不能够连接外网，而又需要调试银联在线支付的开发人员，提供另外一种途径。


#### 工程说明
在工程的 application.properties 文件中有详细的启动配置 <br/>
工程启动后，访问 http://[ip]:[port]/config.do 可在线配置平台部分参数

该仿真平台实现：消费类交易（支付/付款），退货类交易（退款），交易状态查询交易
+ 消费类交易地址：http://[ip]:[port]//unionpay/gateway/api/frontTransReq.do
+ 退货类交易地址：http://[ip]:[port]//unionpay/gateway/api/backTransReq.do
+ 交易状态查询交易地址：http://[ip]:[port]//unionpay/gateway/api/queryTrans.do

[银联在线支付5.0.0版-证书生成](https://blog.csdn.net/tjzengxiantao/article/details/79664722)