package com.shiyifan.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQConsumer;
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
import java.util.List;

public class OrderPaySuccessListener implements MessageListenerConcurrently {

	private static final String  ORDER_PAY_SUCCESS_OCUPON= "ORDER_PAY_SUECCES_OCUPON";
	private static final   String  TOPIC= "OrderPaySuccessTopic";


	@Autowired
	private Environment environment;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
		MessageExt messageExt = list.get(0);
		String string = new String(messageExt.getBody());
		System.out.println(String.format("消息Id=%s，写入时间=%s,消息延迟时间=%s",
				messageExt.getMsgId(),messageExt.getBornTimestamp(),(System.currentTimeMillis()-messageExt.getBornTimestamp())/1000));
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

	@PostConstruct
	public void  doOrderPaySuccessListener() throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(ORDER_PAY_SUCCESS_OCUPON);
		String nameServer = environment.getProperty("rocketmq.nameServer");
		Assert.notNull(nameServer,"rocketmq.nameServer is null");
		//设置消费者端消息拉取策略，表示从哪里开始消费
		consumer.setNamesrvAddr(nameServer);
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		consumer.subscribe(TOPIC,"*");
		consumer.registerMessageListener(this);
		consumer.start();
	}
}
