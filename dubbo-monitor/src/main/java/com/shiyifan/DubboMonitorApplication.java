package com.shiyifan;

import com.shiyifan.annotation.EnableRocketMqConsumer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @compang 联想懂的通信
 * @Description: dubbo接口调用信心监控
 * @Author: shiyf
 * @CreateDate: 2020/3/27 10:44
 * @UpdateUser: shiyf
 * @UpdateDate: 2020/3/27 10:44
 * @Version: cmp2.0
 */
@SpringBootApplication
@EnableRocketMqConsumer
@MapperScan("com.lenovoconnect.dao")
public class DubboMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboMonitorApplication.class,args);
	}
}
