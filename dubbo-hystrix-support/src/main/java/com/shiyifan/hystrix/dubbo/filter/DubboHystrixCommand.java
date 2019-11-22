package com.shiyifan.hystrix.dubbo.filter;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcResult;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class DubboHystrixCommand extends HystrixCommand<Result> {
	private static final Logger LOGGER=LoggerFactory.getLogger(DubboHystrixCommand.class);

	private  Invoker<?> invoker;
	private  Invocation invocation;
    private  String fallback;


	public DubboHystrixCommand(Setter setter, Invoker<?> invoker, Invocation invocation, String fallback) {
		super(setter);
		this.fallback=fallback;
		this.invoker=invoker;
		this.invocation=invocation;
	}

	@Override
	protected Result run() throws Exception {
		LOGGER.info("===============run=============");
		Result result = invoker.invoke(invocation);
		if(result.hasException()){
			throw  new HystrixRuntimeException(HystrixRuntimeException.FailureType.COMMAND_EXCEPTION,DubboHystrixCommand.class,result.toString(),result.getException(),result.getException() );
		}
		return result;
	}

	@Override
	public Result getFallback() {
		LOGGER.info("getFallback===================================");
        if(StringUtils.isEmpty(fallback)){
        	super.getFallback();
        }
		ExtensionLoader<FallBack> extensionLoader = ExtensionLoader.getExtensionLoader(FallBack.class);
		FallBack fallBack = extensionLoader.getExtension(fallback);
		Object invoke = fallBack.invoke();
		return new RpcResult(invoke);
	}
}
