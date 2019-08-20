package com.shiyifan.user;

import com.shiyifan.user.dto.*;

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


	/**
	 * 授权
	 * @param checkAuthRequest
	 * @return
	 */
	CheckAuthResponse checkAuth(CheckAuthRequest checkAuthRequest);

}
