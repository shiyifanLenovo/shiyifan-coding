package com.shiyifan.consumer;

import com.alibaba.fastjson.JSON;
import com.shiyifan.entity.DubboInterfaceInfo;
import com.shiyifan.service.DubboInterfaceInfoService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class DubboInterFaceInfoListener implements MessageListenerConcurrently {


	private static final String DUBBO_INTERFACES_TOPIC="dubboInterfaceTopic";
	private static final String CONSUMER_GROUP="dubbo-intface-group";

	@Autowired
	private DubboInterfaceInfoService dubboInterfaceInfoService;


	@Autowired
	private Environment environment;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
		try {
			MessageExt messageExt = list.get(0);
			String string = new String(messageExt.getBody());
			Map<String,Object> dubboInfoMap = JSON.parseObject(string, Map.class);
			DubboInterfaceInfo dubboInterfaceInfo = new DubboInterfaceInfo();
			dubboInterfaceInfo.setHost(String.valueOf(dubboInfoMap.get("host")));
			dubboInterfaceInfo.setPort(	Integer.valueOf((Integer) dubboInfoMap.get("port")));
			dubboInterfaceInfo.setIp(String.valueOf(dubboInfoMap.get("ip")));
			dubboInterfaceInfo.setProtocol(String.valueOf(dubboInfoMap.get("protocol")));
			dubboInterfaceInfo.setCreatetime(systemTimeToDate(dubboInfoMap.get("startDate")) );
			dubboInterfaceInfo.setEndtime(systemTimeToDate(dubboInfoMap.get("endDate")));
			dubboInterfaceInfo.setInterfacename(String.valueOf(dubboInfoMap.get("interfaceName")));
			dubboInterfaceInfo.setMethodname(String.valueOf(dubboInfoMap.get("methodName")));
			DubboInterfaceInfo insert = dubboInterfaceInfoService.insert(dubboInterfaceInfo);
		}catch (Exception e){
		    return  ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

	private Date systemTimeToDate(Object object){
		String startDate = String.valueOf(object);
		Date date = new Date();
		date.setTime(Long.valueOf(startDate));
		return date;
	}



	@PostConstruct
	public void dubboInterfaceInfoConsumer() throws MQClientException {
		//Broker主动push消息给Consumer
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
		//consumer 主动发送消息到broker中拉去
		DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
		//defaultMQPushConsumer.fetchSubscribeMessageQueues()
		String nameServer = environment.getProperty("rocketmq.nameServer");
		Assert.notNull(nameServer,"rocketmq.nameServer is null");
		//设置消费者端消息拉取策略，表示从哪里开始消费
		consumer.setNamesrvAddr(nameServer);
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		consumer.subscribe(DUBBO_INTERFACES_TOPIC,"*");
		consumer.registerMessageListener(this);
		consumer.start();
	}




}
