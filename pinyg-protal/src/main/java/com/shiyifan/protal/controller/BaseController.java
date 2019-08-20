package com.shiyifan.protal.controller;

public class BaseController {

	private ThreadLocal<String> threadLocal=new ThreadLocal<>();

	public String getUid(){
	  return 	threadLocal.get();
	}

	public void setUid(String uid){
		threadLocal.set(uid);
	}
}
