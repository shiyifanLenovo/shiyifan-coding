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

/**
 * @compang 联想懂的通信
 * @Description: 服务降级拦截器
 * @Author: shiyf
 * @CreateDate: 2019/11/22 16:07
 * @UpdateUser: shiyf
 * @UpdateDate: 2019/11/22 16:07
 * @UpdateRemark: 修改内容
 * @Version: cmp2.0
 */
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
		Object[] arguments = invocation.getArguments();
        LOGGER.info("method request param "+arguments);
		HystrixCommand.Setter setter = SetterFactory.create(interfaceName, methodName, url);
		DubboHystrixCommand dubboHystrixCommand = new DubboHystrixCommand(setter, invoker, invocation, fallback);
		Result execute = dubboHystrixCommand.execute();
		return execute;
	}
}
