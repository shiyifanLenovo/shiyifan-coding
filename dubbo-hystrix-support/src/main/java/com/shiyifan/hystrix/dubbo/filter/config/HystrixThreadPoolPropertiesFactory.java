package com.shiyifan.hystrix.dubbo.filter.config;

import com.alibaba.dubbo.common.URL;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class HystrixThreadPoolPropertiesFactory {


	 public static HystrixThreadPoolProperties.Setter create(URL url){

	 	return HystrixThreadPoolProperties.Setter().withCoreSize(url.getParameter("coreSize",10))
			    .withKeepAliveTimeMinutes(url.getParameter("keepAliveTimeMinutes",1))
			    .withMaxQueueSize(-1)
			    .withMaximumSize(url.getParameter("maximumSize",10))
			    .withAllowMaximumSizeToDivergeFromCoreSize(true)
			    ;

	 }


}
