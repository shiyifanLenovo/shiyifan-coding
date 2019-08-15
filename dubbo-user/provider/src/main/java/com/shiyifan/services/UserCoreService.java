package com.shiyifan.services;

import com.shiyifan.user.IUserCoreService;
import com.shiyifan.user.dto.UserLoginRequest;
import com.shiyifan.user.dto.UserLoginResponse;
import com.shiyifan.user.dto.UserRegisterRequest;
import com.shiyifan.user.dto.UserRegisterResponse;
import org.springframework.stereotype.Service;

@Service("UserCoreService")
public class UserCoreService implements IUserCoreService {


	@Override
	public UserLoginResponse login(UserLoginRequest userLoginRequest) {
		return null;
	}

	@Override
	public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
		return null;
	}
}
