package com.shiyifan.services;

import com.shiyifan.dao.mapper.UserMapper;
import com.shiyifan.dao.po.User;
import com.shiyifan.exception.ExceptionUtil;
import com.shiyifan.exception.ServiceException;
import com.shiyifan.exception.ValidateException;
import com.shiyifan.user.IUserCoreService;
import com.shiyifan.user.constants.ResponseCodeEnum;
import com.shiyifan.user.dto.*;
import com.shiyifan.utils.JwtInfo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("userCoreService")
public class UserCoreService implements IUserCoreService {

	private static final Logger LOGGER=LoggerFactory.getLogger(UserCoreService.class);

	@Autowired
	 UserMapper userMapper;

	@Autowired
	 JwtTokenService jwtTokenService;

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
		//设置token
		String token = jwtTokenService.generatorToken(new JwtInfo(user.getId().toString()));

		return userLoginResponse;
	}

	@Override
	public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
		return null;
	}

	@Override
	public CheckAuthResponse checkAuth(CheckAuthRequest request) {

		CheckAuthResponse response = new CheckAuthResponse();
		try {
			beforeCheckAuthValidate(request);
			JwtInfo infoFromToken = jwtTokenService.getInfoFromToken(request.getToken());
			response.setUid(infoFromToken.getUid());
			response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
			response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
		}catch (ExpiredJwtException e){
            //TODO token过期
		}catch (SignatureException e){
			//TODO 签名错误
		}catch (Exception e){
			ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
			response.setCode(serviceException.getErrorCode());
			response.setMsg(serviceException.getErrorMessage());
		}finally {
			LOGGER.info("checkAuth response 【"+response.toString()+"】");
		}
		return response;
	}

	private void beforeCheckAuthValidate(CheckAuthRequest request){
		if(null==request){
			throw new ValidateException("请求对象为空");
		}
		if(StringUtils.isEmpty(request.getToken())){
			throw new ValidateException("token为空");
		}

	}

	private void beforeRegisterValidate(UserRegisterRequest request){
		if(null==request){
			throw new ValidateException("请求对象为空");
		}
		if(StringUtils.isEmpty(request.getUsername())){
			throw new ValidateException("用户名为空");
		}
		if(StringUtils.isEmpty(request.getPassword())){
			throw new ValidateException("密码为空");
		}
		if(StringUtils.isEmpty(request.getMobile())){
			throw new ValidateException("手机号为空");
		}
	}

	private void beforeValidate(UserLoginRequest request){
		if(null==request){
			throw new ValidateException("请求对象为空");
		}
		if(StringUtils.isEmpty(request.getUsername())){
			throw new ValidateException("用户名为空");
		}
		if(StringUtils.isEmpty(request.getPassword())){
			throw new ValidateException("密码为空");
		}
	}
}
