package com.shiyifan.user;

import com.shiyifan.user.dto.UserLoginRequest;
import com.shiyifan.user.dto.UserLoginResponse;
import com.shiyifan.user.dto.UserRegisterRequest;
import com.shiyifan.user.dto.UserRegisterResponse;

public interface IUserCoreService {

	/**
	 * 用户登录操作
	 * @param userLoginRequest
	 * @return
	 */
	UserLoginResponse login(UserLoginRequest userLoginRequest);


	/**
	 * 注册
	 * @param userRegisterRequest
	 * @return
	 */
	UserRegisterResponse register(UserRegisterRequest userRegisterRequest);
}
