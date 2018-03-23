package com.edu1t1.payplatform.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 序列号生成器
 * @author zengxt
 * @date 2018-02-28
 */
public final class SerialNumberGenerator {
	
	/**
	 * 序号
	 */
	private static int orderNumber = 0;
	
	/**
	 * 生成序号
	 * @param length 需要生成序号的长度
	 * @return
	 */
	public static final String generatorOrderNumber(int length) {
		orderNumber ++;
		if (orderNumber >= Math.pow(10, length)) {
			orderNumber = 1;
		}
		return String.format("%0" + length + "d", orderNumber);
	}
	
	/**
	 * ID共计24位长,例如：000yyMMddHHmmssSSS001001 <br>
	 * 1~3位，表下标 3位  当数据量大时，会把数据做横向拆表，此处表示数据拆分后的表的下标<br>
	 * 4~18位，时间戳 15位 到毫秒   yyMMddHHmmssSSS<br>
	 * 19~20位，应用id 2位  每个应用指定一个id  默认是00<br>
	 * 21~24位，唯一下标 4位 每个应用内为每一个表做一个序列号生成器，该生成器在一毫秒内唯一，产生定长4位字符串
	 * @return 24位长ID
	 */
	public static final String sn24() {
		return sn24("000", "00");
	}

	/**
	 * ID共计24位长,例如：000yyMMddHHmmssSSS001001 <br>
	 * 1~3位，表下标 3位  当数据量大时，会把数据做横向拆表，此处表示数据拆分后的表的下标<br>
	 * 4~18位，时间戳 15位 到毫秒   yyMMddHHmmssSSS<br>
	 * 19~20位，应用id 2位  每个应用指定一个id  默认是00<br>
	 * 21~24位，唯一下标 4位 每个应用内为每一个表做一个序列号生成器，该生成器在一毫秒内唯一，产生定长4位字符串
	 * @param subscript 下标 3个字符
	 * @return 24位长ID
	 */
	public static final String sn24(String subscript) {
		return sn24(subscript, "00");
	}
	
	/**
	 * ID共计24位长,例如：000yyMMddHHmmssSSS001001 <br>
	 * 1~3位，表下标 3位  当数据量大时，会把数据做横向拆表，此处表示数据拆分后的表的下标<br>
	 * 4~18位，时间戳 15位 到毫秒   yyMMddHHmmssSSS<br>
	 * 19~20位，应用id 2位  每个应用指定一个id  默认是00<br>
	 * 21~24位，唯一下标 4位 每个应用内为每一个表做一个序列号生成器，该生成器在一毫秒内唯一，产生定长4位字符串
	 * @param subscript 下标 3个字符
	 * @param appId 应用ID 2个字符
	 * @return 24位长ID
	 */
	public static final String sn24(String subscript, String appId) {
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
		return subscript + format.format(new Date()) + appId + generatorOrderNumber(4);
	}
}
