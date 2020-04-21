package com.shiyifan.filter;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.Properties;


public class ProducerService {
	private static final Logger LOGGER=LoggerFactory.getLogger(ProducerService.class);

	private  static  DefaultMQProducer producer;
	private static final String DUBBO_INTERFACES_TOPIC="dubboInterfaceTopic";

	private  static Properties props;

	static {
		try {
			InputStream in = ClassLoader.getSystemResourceAsStream("consumer.properties");
			props = new Properties();
			props.load(in);
			String producerGroup = props.getProperty("producerGroup").trim();
			producerGroup=producerGroup.equals("")?null:producerGroup;
			String nameServerUrl = props.getProperty("rocketmq.nameserver").trim();
			nameServerUrl=nameServerUrl.equals("")?null:nameServerUrl;
			Assert.notNull(producerGroup,"the rocketmq.nameserver in the consumer.properties  is null");
			Assert.notNull(producerGroup,"the producerGroup in the consumer.properties  is null");
			producer = new DefaultMQProducer(producerGroup);
			producer.setNamesrvAddr(nameServerUrl);
			producer.setInstanceName(String.valueOf(System.currentTimeMillis()));
			//TODO 设置异步发送重试几次
			producer.setRetryTimesWhenSendAsyncFailed(0);
			//TODO 自动规避故障得broker一段时间 假设broker master A 发送消息延迟到了500ms，就回在一段时间内屏蔽broker master A
			//TODO 会选择其他得broker master 发送。
			producer.setSendLatencyFaultEnable(true);
			producer.start();
		}catch (Exception e){
			LOGGER.error (" the consumer.properties file could not be found ",e);
		}

	}

	public void sendMessage(Object msgContent) {
		try {
			LOGGER.info("dubbo interface  info "+msgContent.toString());
			Message message = new Message(DUBBO_INTERFACES_TOPIC,null,null,JSON.toJSONString(msgContent).getBytes());
			producer.send(message);

			//producer 发送消息以后，可以走其他代码了，不需要阻塞在这里，当Mq返回结果时，producer 回回调callback函数，成功回调
			//onSuccess ，失败回调 onException
			producer.send(message, new SendCallback() {
				@Override
				public void onSuccess(SendResult sendResult) {

				}

				@Override
				public void onException(Throwable throwable) {

				}
			});
			//单向发送，不回等待服务器返回结果，不关注发送得成功与失败
			producer.sendOneway(message);
			producer.setDefaultTopicQueueNums(6);
		}catch (Exception e){
			LOGGER.error("send message exception "+e);
		}

	}

	/**
	 * 发送延迟消息
	 */
	public void sendDelayMessage(RequestMessagePo<String> data) throws Exception {
		Message message = new Message(data.getTopicName(), data.getMessageTag(), data.getMessageKey(), JSON.toJSONBytes(data));
		//延迟登记 1s 5s 10s ..........2H
		message.setDelayTimeLevel(5);
		SendResult send = producer.send(message);
		System.out.println("发送延迟消息结果=="+send.getSendStatus());
	}

	public static void main(String[] args) throws Exception {
		ProducerService producerService = new ProducerService();
		RequestMessagePo<String> stringRequestMessagePo = new RequestMessagePo<>();
		stringRequestMessagePo.setTopicName("OrderPaySuccessTopic");
		stringRequestMessagePo.setData("测试延迟消息");
		producerService.sendDelayMessage(stringRequestMessagePo);
	}


}
