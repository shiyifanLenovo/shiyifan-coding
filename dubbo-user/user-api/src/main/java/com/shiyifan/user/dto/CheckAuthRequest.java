package com.shiyifan.user.dto;

import com.shiyifan.user.abs.AbstractRequest;

import java.io.Serializable;

public class CheckAuthRequest extends AbstractRequest implements Serializable {


	private static final long serialVersionUID = -760681731308212788L;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "CheckAuthRequest{" +
				"token='" + token + '\'' +
				'}';
	}
}
