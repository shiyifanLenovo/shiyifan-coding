package com.shiyifan.hystrix.dubbo.filter.config;

import com.alibaba.dubbo.common.URL;
import com.netflix.hystrix.HystrixCommandProperties;

public class HystrixCommandPropertiesFactory {

	public static HystrixCommandProperties.Setter create(URL url,String method){

         return HystrixCommandProperties.Setter().withCircuitBreakerSleepWindowInMilliseconds
		         (url.getMethodParameter(method,"sleepWindowInMilliseconds",500))
		         .withCircuitBreakerErrorThresholdPercentage(url.getMethodParameter(method,"errorThresholdPercentage",20))
		         .withExecutionIsolationThreadInterruptOnTimeout(true)
		         .withCircuitBreakerRequestVolumeThreshold(url.getMethodParameter(method,"requestVolumeThreshold",20))
		         .withExecutionTimeoutInMilliseconds(url.getMethodParameter(method,"timeoutInMilliseconds",30000))
		         .withFallbackIsolationSemaphoreMaxConcurrentRequests(url.getMethodParameter(method,"fallbackMaxConcurrentRequest",50))
		         .withExecutionIsolationSemaphoreMaxConcurrentRequests(url.getMethodParameter(method,"maxConcurrentRequests",10))
		         ;
	}
}
