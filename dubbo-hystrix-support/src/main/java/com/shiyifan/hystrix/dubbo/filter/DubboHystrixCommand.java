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

/**
 * @Description: 服务降级核心类
 * @Author: shiyf
 * @CreateDate: 2019/11/25 15:50
 * @UpdateUser: shiyf
 * @UpdateDate: 2019/11/25 15:50
 * @UpdateRemark: 修改内容
 * @Version: cmp2.0
 */
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
		Result result = invoker.invoke(invocation);
		if(result.hasException()){
			//针对异常做处理
			throw new  NullPointerException("空指针异常");
			//throw  new HystrixRuntimeException(HystrixRuntimeException.FailureType.COMMAND_EXCEPTION,DubboHystrixCommand.class,result.toString(),result.getException(),result.getException() );
		}
		return result;
	}

	/**
	 * 方法降级
	 * @return
	 */
	@Override
	public Result getFallback() {
        if(StringUtils.isEmpty(fallback)){
        	super.getFallback();
        }
		ExtensionLoader<FallBack> extensionLoader = ExtensionLoader.getExtensionLoader(FallBack.class);
		FallBack fallBack = extensionLoader.getExtension(fallback);
		String methodName = invocation.getMethodName();
		Class<?> anInterface = invoker.getInterface();
		LOGGER.info("%s,%s"+anInterface,methodName);
		Object invoke = fallBack.invoke();
		return new RpcResult(invoke);
	}
}
