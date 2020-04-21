package com.shiyifan.producer;

import com.shiyifan.filter.RequestMessagePo;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

public class TransactionProducerService {


	private TransactionMQProducer producer;

	public TransactionProducerService(TransactionListener transactionListener) throws MQClientException {
		producer=new TransactionMQProducer();
		producer.setNamesrvAddr("172.16.4.126:9876");
		producer.setTransactionListener(transactionListener);
		producer.start();
	}

	public void sendTransactionMessage(RequestMessagePo<Object> messagePo) throws MQClientException {
		Message message = new Message(messagePo.getTopicName(), messagePo.getMessageTag(), messagePo.getMessageKey(), messagePo.getData());
		producer.sendMessageInTransaction(message,messagePo.getMessageTag());
	}

}
