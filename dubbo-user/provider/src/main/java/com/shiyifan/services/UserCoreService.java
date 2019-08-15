package com.shiyifan.services;

import com.shiyifan.dao.mapper.UserMapper;
import com.shiyifan.dao.po.User;
import com.shiyifan.user.IUserCoreService;
import com.shiyifan.user.constants.ResponseCodeEnum;
import com.shiyifan.user.dto.UserLoginRequest;
import com.shiyifan.user.dto.UserLoginResponse;
import com.shiyifan.user.dto.UserRegisterRequest;
import com.shiyifan.user.dto.UserRegisterResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserCoreService")
public class UserCoreService implements IUserCoreService {

	private static final Logger LOGGER=LoggerFactory.getLogger(UserCoreService.class);

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserLoginResponse login(UserLoginRequest userLoginRequest) {
		LOGGER.info("begin login request "+userLoginRequest.toString());
		UserLoginResponse userLoginResponse = new UserLoginResponse();
		User user = userMapper.getUserByUserName(userLoginRequest.getUsername());
		if(user==null || !userLoginRequest.getPassword().equals(user.getPassword())){
			userLoginResponse.setCode(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getCode());
			userLoginResponse.setMsg(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getMsg());
			return userLoginResponse;
		}
		//设置用户状态
		userLoginResponse.setAvatar(user.getAvatar());
		userLoginResponse.setMobile(user.getMobile());
		userLoginResponse.setSex(user.getSex());
		userLoginResponse.setRealName(user.getRealname());
		userLoginResponse.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
		userLoginResponse.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
		return userLoginResponse;
	}

	@Override
	public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
		return null;
	}
}
