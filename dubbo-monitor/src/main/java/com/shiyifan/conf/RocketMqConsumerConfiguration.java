package com.shiyifan.conf;


import com.shiyifan.consumer.DubboInterFaceInfoListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMqConsumerConfiguration {



	@Bean
	public DubboInterFaceInfoListener dubboInterFaceInfoListener(){
		return new DubboInterFaceInfoListener();
	}









}
