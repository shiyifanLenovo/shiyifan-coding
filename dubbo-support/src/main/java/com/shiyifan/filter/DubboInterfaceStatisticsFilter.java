package com.shiyifan.filter;


import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Activate(group = {Constants.PROVIDER})
public class DubboInterfaceStatisticsFilter implements Filter {

	private static final Logger LOGGER=LoggerFactory.getLogger(DubboInterfaceStatisticsFilter.class);

	private ProducerService producerService;

	public DubboInterfaceStatisticsFilter(){
		producerService=new ProducerService();
	}

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

		URL url = invoker.getUrl();
		String methodName = invocation.getMethodName();
		String interfaceName = invoker.getInterface().getName();
		LOGGER.info(" url method  interfaceName{}"+url+","+methodName+","+interfaceName);
		String host = url.getHost();
		String ip = url.getIp();
		String protocol = url.getProtocol();
		Date startDate = new Date();
		Result invoke = invoker.invoke(invocation);
		Date endDate = new Date();
		Map<String,Object> dubboInterfaceInfo=new HashMap<>(8);
		dubboInterfaceInfo.put("host",host);
		dubboInterfaceInfo.put("port",url.getPort());
		dubboInterfaceInfo.put("ip",ip);
		dubboInterfaceInfo.put("protocol",protocol);
		dubboInterfaceInfo.put("startDate",startDate);
		dubboInterfaceInfo.put("endDate",endDate);
		dubboInterfaceInfo.put("interfaceName",interfaceName);
		dubboInterfaceInfo.put("methodName",methodName);
		producerService.sendMessage(dubboInterfaceInfo);
		return invoke;
	}

}
