package com.shiyifan.hystrix.dubbo.filter.config;

import com.alibaba.dubbo.common.URL;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * @Description: 降级,熔断 配置
 * @Author: shiyf
 * @CreateDate: 2019/11/25 15:38
 * @UpdateUser: shiyf
 * @UpdateDate: 2019/11/25 15:38
 * @UpdateRemark: 修改内容
 */
public class HystrixThreadPoolPropertiesFactory {


	 public static HystrixThreadPoolProperties.Setter create(URL url){

	 	return HystrixThreadPoolProperties.Setter().withCoreSize(url.getParameter("coreSize",Runtime.getRuntime().availableProcessors()+1))
			    //keepAliveTimeMinutes： 如果corePoolSize和maxPoolSize设成一样（默认实现）该设置无效。如果coreSize小于maximumSize，那么该属性控制一个线程从实用完成到被释放的时间。默认1（分钟）。
			    .withKeepAliveTimeMinutes(url.getParameter("keepAliveTimeMinutes",1))
			    //maxQueueSize BlockingQueue的最大队列数，当设为－1，会使用SynchronousQueue，值为正时使用LinkedBlcokingQueue。该设置只会在初始化时有效，之后不能修改threadpool的queue size，除非reinitialising thread executor。默认－1。
			    .withMaxQueueSize(-1)
			    .withMaximumSize(url.getParameter("maximumSize",Runtime.getRuntime().availableProcessors()+1))
			    .withAllowMaximumSizeToDivergeFromCoreSize(true)
			    ;

	 }


}
