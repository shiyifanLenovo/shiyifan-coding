package com.shiyifan.conf;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinyg.common.utils.RedisUtils;
import com.shiyifan.consumer.DubboInterFaceInfoListener;
import com.shiyifan.consumer.OrderPaySuccessListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RocketMqConsumerConfiguration{




	@Bean
	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		// value序列化方式采用jackson
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		// hash的value序列化方式采用jackson
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}


	@Bean
	public DubboInterFaceInfoListener dubboInterFaceInfoListener(){
		return new DubboInterFaceInfoListener();
	}


	@Bean
	public OrderPaySuccessListener orderPaySuccessListener(){
		return new OrderPaySuccessListener();
	}

	@Bean
	public RedisUtils  redisUtils(RedisTemplate<String,Object> redisTemplate){
		 RedisUtils redisUtils = new RedisUtils();
		 redisUtils.setRedisTemplate(redisTemplate);
		return redisUtils;
	}
}
