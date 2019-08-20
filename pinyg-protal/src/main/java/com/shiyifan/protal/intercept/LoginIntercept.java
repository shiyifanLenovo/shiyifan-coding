package com.shiyifan.protal.intercept;

import com.shiyifan.protal.support.Anonymous;
import com.shiyifan.user.IUserCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class LoginIntercept extends HandlerInterceptorAdapter {


	@Autowired
	private IUserCoreService userCoreService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod=(HandlerMethod)handler;

		if(isAnonymous(handlerMethod)){
             return  true;
		}
		//从cookis中取出token
		//如果未null，未登陆，


		return super.preHandle(request, response, handler);
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


}
