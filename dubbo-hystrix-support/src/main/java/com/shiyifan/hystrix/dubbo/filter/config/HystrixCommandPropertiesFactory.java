package com.shiyifan.hystrix.dubbo.filter.config;

import com.alibaba.dubbo.common.URL;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * @Description: 降级，熔断配置
 * @Author: shiyf
 * @CreateDate: 2019/11/25 15:37
 * @UpdateUser: shiyf
 * @UpdateDate: 2019/11/25 15:37
 * @UpdateRemark: 修改内容
 */
public class HystrixCommandPropertiesFactory {

	public static HystrixCommandProperties.Setter create(URL url,String method){

         return HystrixCommandProperties.Setter()
		          //触发短路的时间值，当该值设为5000时，则当触发circuit break后的5000毫秒内都会拒绝request，也就是5000毫秒后才会关闭circuit。默认5000
		         .withCircuitBreakerSleepWindowInMilliseconds(url.getMethodParameter(method,"sleepWindowInMilliseconds",5000))
		         //错误比率阀值，如果错误率>=该值，circuit会被打开，并短路所有请求触发fallback。默认50
		         .withCircuitBreakerErrorThresholdPercentage(url.getMethodParameter(method,"errorThresholdPercentage",50))
		         .withExecutionIsolationThreadInterruptOnTimeout(true)
		         //withCircuitBreakerRequestVolumeThreshold： 一个rolling window内最小的请求数。如果设为20，那么当一个rolling window的时间内（比如说1个rolling window是10秒）收到19个请求，即使19个请求都失败，也不会触发circuit break。默认20
		         .withCircuitBreakerRequestVolumeThreshold(url.getMethodParameter(method,"requestVolumeThreshold",20))
		         //命令执行超时时间，超过此时间，HystrixCommand被标记为TIMEOUT，并执行回退逻辑，默认1000ms
		         .withExecutionTimeoutInMilliseconds(url.getMethodParameter(method,"timeoutInMilliseconds",3000))
		         //如果并发数达到该设置值，请求会被拒绝和抛出异常并且fallback不会被调用。默认10
		         .withFallbackIsolationSemaphoreMaxConcurrentRequests(url.getMethodParameter(method,"fallbackMaxConcurrentRequest",50))

		         //最大并发请求数，默认10，该参数当使用ExecutionIsolationStrategy.SEMAPHORE策略时才有效。如果达到最大并发请求数，请求会被拒绝。理论上选择semaphore size的原则和选择thread size一致，但选用semaphore时每次执行的单元要比较小且执行速度快（ms级别），否则的话应该用thread。
		         //semaphore应该占整个容器（tomcat）的线程池的一小部分。
		         .withExecutionIsolationSemaphoreMaxConcurrentRequests(url.getMethodParameter(method,"maxConcurrentRequests",10))
		         //配置熔断  默认是true  10s内请求数大于3个时就启动熔断器，请求错误率大于80%时就熔断
		         //.withCircuitBreakerEnabled(true)
		         // .withCircuitBreakerRequestVolumeThreshold(url.getMethodParameter(method,"circuitBreakerRequestVolumeThreshold",3))
		         //.withCircuitBreakerErrorThresholdPercentage(url.getMethodParameter(method,"circuitBreakerErrorThresholdPercentage",80))
		         // 置为true时，所有请求都将被拒绝，直接到fallback
		         //.withCircuitBreakerForceOpen(true)
		         // 置为true时，将忽略错误
                 //.withCircuitBreakerForceClosed(true)
		         // 信号量隔离
                 //.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)
                 //.withExecutionTimeoutInMilliseconds(5000)
		         ;
	}
}
