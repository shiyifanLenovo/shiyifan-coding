package com.shiyifan.hystrix.dubbo.filter.config;

import com.alibaba.dubbo.common.URL;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

import java.util.concurrent.ConcurrentHashMap;

public class SetterFactory {


	private static   ConcurrentHashMap<String,HystrixCommand.Setter> setterConcurrentHashMap=  new ConcurrentHashMap<String,HystrixCommand.Setter>();


	public static HystrixCommand.Setter create(String interfaceName, String methodName, URL url){
		String key = String.format("%s.%s", interfaceName, methodName);
		if(setterConcurrentHashMap.contains(key)){
			return  setterConcurrentHashMap.get(key);
		}else {
		  	setterConcurrentHashMap.putIfAbsent(key,doCreate(interfaceName,methodName,url));
		  	return setterConcurrentHashMap.get(key);
		}
	}

	private static HystrixCommand.Setter doCreate(String interfaceName, String methodName, URL url) {

       return   HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(interfaceName))
		         .andCommandKey(HystrixCommandKey.Factory.asKey(methodName))
		         .andCommandPropertiesDefaults(HystrixCommandPropertiesFactory.create(url,methodName))
		         .andThreadPoolPropertiesDefaults(HystrixThreadPoolPropertiesFactory.create(url))
		        ;

	}

}
