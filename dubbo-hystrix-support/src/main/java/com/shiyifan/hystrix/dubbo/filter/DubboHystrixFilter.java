package com.shiyifan.hystrix.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.netflix.hystrix.HystrixCommand;
import com.shiyifan.hystrix.dubbo.filter.config.SetterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

@Activate(group = Constants.PROVIDER)
public class DubboHystrixFilter implements Filter {

	private static final Logger LOGGER=LoggerFactory.getLogger(DubboHystrixFilter.class);


	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		URL url = invoker.getUrl();
		String methodName = invocation.getMethodName();
		String interfaceName = invoker.getInterface().getName();
		LOGGER.info(" url method  interfaceName{}"+url+","+methodName+","+interfaceName);
		//获取降级方法
		String fallback = url.getMethodParameter(methodName, "fallback");
        if(StringUtils.isEmpty(fallback)){
        	return  invoker.invoke(invocation);
        }
		HystrixCommand.Setter setter = SetterFactory.create(interfaceName, methodName, url);
		DubboHystrixCommand dubboHystrixCommand = new DubboHystrixCommand(setter, invoker, invocation, fallback);
		Result execute = dubboHystrixCommand.execute();
		return execute;
	}
}
