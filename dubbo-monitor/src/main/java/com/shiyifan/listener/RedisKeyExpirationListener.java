package com.shiyifan.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

	public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		//获取失效得key
		super.onMessage(message, pattern);
	}
}
