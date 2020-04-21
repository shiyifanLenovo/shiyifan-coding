package com.shiyifan.filter;

import com.alibaba.fastjson.JSON;
import com.sun.istack.internal.NotNull;

public class RequestMessagePo<T> {


	@NotNull
	private String topicName;

	private String messageKey;

	private String messageTag;

	@NotNull
	private T  data;

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessageTag() {
		return messageTag;
	}

	public void setMessageTag(String messageTag) {
		this.messageTag = messageTag;
	}

	public byte[] getData() {
		return JSON.toJSONBytes(data) ;
	}

	public void setData(T data) {
		this.data = data;
	}
}
