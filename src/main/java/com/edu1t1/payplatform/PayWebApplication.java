package com.edu1t1.payplatform;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 启动类
 * @author zengxt
 * @date 2018-02-27
 */
@SpringBootApplication
public class PayWebApplication {
	
	protected PayWebApplication() { }
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(PayWebApplication.class)
		.web(true)
		.bannerMode(Banner.Mode.LOG)
		.run(args);
	}

}
