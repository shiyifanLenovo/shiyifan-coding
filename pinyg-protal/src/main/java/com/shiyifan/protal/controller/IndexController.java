package com.pinyg.protal.controller;

import com.pinyg.protal.support.ResponseData;
import com.pinyg.user.IUserCoreService;
import com.pinyg.user.IUserQueryService;
import com.pinyg.user.dto.UserLoginRequest;
import com.pinyg.user.dto.UserLoginResponse;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IndexController extends BaseController {


	@Autowired
	IUserCoreService userCoreServices;


	@RequestMapping("/index")
	private String index() {
		return "login";
	}

	@RequestMapping("/login")
	public String login(){
		System.out.println("denglu");
		return "login";
	}
	@RequestMapping("/doLogin")
	public UserLoginResponse doLogin(String textUser, String userPwd, HttpResponse response){
		UserLoginRequest userLoginRequest = new UserLoginRequest();
		userLoginRequest.setPassword(userPwd);
		userLoginRequest.setUsername(textUser);
		UserLoginResponse login = userCoreServices.login(userLoginRequest);
		if("000000".equals(login.getCode())){
			response.setHeader("Set-Cookie","access-token="+login.getToken()+":Path=/:HttpOnly");
		}
		return  login;
	}



}
