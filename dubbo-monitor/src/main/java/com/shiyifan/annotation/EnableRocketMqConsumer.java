package com.shiyifan.annotation;

import com.shiyifan.conf.RocketMqConsumerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RocketMqConsumerConfiguration.class)
public @interface EnableRocketMqConsumer {
}
