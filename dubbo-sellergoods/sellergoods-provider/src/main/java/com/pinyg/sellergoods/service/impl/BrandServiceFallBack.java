package com.pinyg.sellergoods.service.impl;

import com.shiyifan.hystrix.dubbo.filter.FallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrandServiceFallBack implements FallBack {

	private static final Logger LOG=LoggerFactory.getLogger(BrandServiceFallBack.class);

	@Override
	public Object invoke() {
		LOG.info("========服务降级方法回调=====");
		return "服务降级方法回调    response";
	}
}
