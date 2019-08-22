package com.pinyg.sellergoods.entity;

import java.io.Serializable;

public class Result implements Serializable {


	private static final long serialVersionUID = 6382204570038394743L;
	private  boolean success;

	private String  message;

	public Result(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
