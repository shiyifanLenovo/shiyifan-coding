package com.shiyifan.hystrix.dubbo.filter;

import java.io.Serializable;

/**
 * @compang 联想懂的通信
 * @Description: 请求合并响应类
 * @Author: shiyf
 * @CreateDate: 2019/11/22 15:42
 * @UpdateUser: shiyf
 * @UpdateDate: 2019/11/22 15:42
 * @UpdateRemark: 修改内容
 * @Version: cmp2.0
 */
public class HystrixCollapserResponse<T> implements Serializable {


	private static final long serialVersionUID = -3246672404555962200L;
	private T  responseData;

	public T getResponseData() {
		return responseData;
	}

	public void setResponseData(T responseData) {
		this.responseData = responseData;
	}
}
