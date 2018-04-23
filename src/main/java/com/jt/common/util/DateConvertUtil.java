package com.jt.common.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/*自定义json序列化对象
 * 1 此类 自定义转换对象
 * 2 如何使用:
 * 		在对应实体bean对象的get方法上使用@@JsonSerialize 注解转换日期*/
public class DateConvertUtil extends JsonSerializer<Date> {
	
	/**
	 * @param value 表示要转换的对象
	 * @param gen 表示 json串生成器
	 * @param serializers json串的序列化提供者
	 * 每次使用都会创建此对象
	 */
	
	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		// 由于每次访问均需要创建一个对象,如何保障如下对象每个线程有一份:课后了解ThreadLocal
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String data = sdf.format(value);
		//将此字符串写入到json串中
		gen.writeString(data);
	}

	public DateConvertUtil() {
		System.out.println("DateConvertUtil()");
	}

	
	
}
