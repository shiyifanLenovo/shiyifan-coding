package com.shiyifan.protal.controller;

import com.shiyifan.protal.support.ResponseData;
import com.shiyifan.user.IUserCoreService;
import com.shiyifan.user.IUserQueryService;
import com.shiyifan.user.dto.UserLoginRequest;
import com.shiyifan.user.dto.UserLoginResponse;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IndexController {


	@Autowired
	IUserCoreService coreService;


	@RequestMapping("/index")
	private String index() {
		return "login";
	}

	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	@PostMapping("/login")
	public UserLoginResponse doLogin(String textUser, String userPwd, HttpResponse response){
		UserLoginRequest userLoginRequest = new UserLoginRequest();
		userLoginRequest.setPassword(userPwd);
		userLoginRequest.setUsername(textUser);
		UserLoginResponse login = coreService.login(userLoginRequest);
		if("000000".equals(login.getCode())){
			response.setHeader("Set-Cookie","access-token="+login.getToken()+":Path=/:HttpOnly");
		}
		return  login;
	}



}
