package com.shiyifan.user.dto;

import com.shiyifan.user.abs.AbstractResponse;

import java.io.Serializable;

public class CheckAuthResponse extends AbstractResponse implements Serializable {

	private static final long serialVersionUID = 9191669162864278095L;

	private String uid;


	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "CheckAuthResponse{" +
				"uid='" + uid + '\'' +
				'}';
	}
}
