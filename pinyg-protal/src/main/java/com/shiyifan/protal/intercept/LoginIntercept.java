package com.pinyg.protal.intercept;

import com.alibaba.fastjson.JSONObject;
import com.pinyg.protal.controller.BaseController;
import com.pinyg.protal.support.Anonymous;
import com.pinyg.user.IUserCoreService;
import com.pinyg.user.dto.CheckAuthRequest;
import com.pinyg.user.dto.CheckAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class LoginIntercept extends HandlerInterceptorAdapter {

	private static final String ACCESS_TOKEN="access_token";

	@Autowired
	private IUserCoreService userCoreService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod=(HandlerMethod)handler;
		Object bean = handlerMethod.getBean();
		if(! (bean instanceof BaseController)){
           throw  new Exception("登陆异常");
		}
		if(isAnonymous(handlerMethod)){
             return  true;
		}
		String token = CookieUtils.getToken(request);
		if(StringUtils.isEmpty(token)){
			//TODO 未登陆
			if(CookieUtils.isAjax(request)){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("code",-1);
				jsonObject.put("msg","请登陆");
				response.getWriter().write(jsonObject.toString());
				return false;
			}
			response.sendRedirect("/login.shtml");
			return false;
		}
		CheckAuthRequest checkAuthRequest = new CheckAuthRequest();
		checkAuthRequest.setToken(token);
		CheckAuthResponse checkAuthResponse = userCoreService.checkAuth(checkAuthRequest);
		if("000000".equals(checkAuthResponse.getCode())){
			((BaseController) bean).setUid(checkAuthResponse.getUid());
			return true;
		}
		return false;
	}

	/**
	 * 判断当前会话是不是一个要拦截的操作
	 * @param handlerMethod
	 * @return
	 */
	private boolean isAnonymous(HandlerMethod handlerMethod){
		Anonymous methodAnnotation = handlerMethod.getMethodAnnotation(Anonymous.class);
		if(methodAnnotation!=null){
			return true;
		}
		Method method = handlerMethod.getMethod();
		return method.getAnnotation(Anonymous.class)!=null;
	}

	private static class CookieUtils{
		public static String getToken(HttpServletRequest request){
			//从cookis中取出token
			Cookie[] cookies = request.getCookies();
			if (null != cookies && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (ACCESS_TOKEN.equals(cookie.getName())) {
						return cookie.getValue();
					}
				}
			}
			return null;
		}
		public static boolean isAjax(HttpServletRequest request){
			boolean isAjaxRequest = false;
			if(!StringUtils.isEmpty(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")){
				isAjaxRequest = true;
			}
			return isAjaxRequest;
		}

	}

}
