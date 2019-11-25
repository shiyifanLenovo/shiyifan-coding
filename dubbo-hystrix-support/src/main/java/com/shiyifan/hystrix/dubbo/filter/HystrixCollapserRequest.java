package com.shiyifan.hystrix.dubbo.filter;

import java.io.Serializable;

public class HystrixCollapserRequest implements Serializable {


	private static final long serialVersionUID = -6586248515052089918L;
	private String requestKey;

	public HystrixCollapserRequest(String requestKey) {
		this.requestKey = requestKey;
	}

	public String getRequestKey() {
		return requestKey;
	}


}
