package com.shiyifan.user.abs;

import java.io.Serializable;

public abstract class AbstractResponse  implements Serializable {

	/**
	 * 返回码（请求）
	 */
	public String code;

	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "AbstractResponse{" +
				"code='" + code + '\'' +
				", msg='" + msg + '\'' +
				'}';
	}
}
