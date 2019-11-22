package com.shiyifan.hystrix.dubbo.filter;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface FallBack {

     Object	invoke();
}
