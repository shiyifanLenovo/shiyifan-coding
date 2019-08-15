package com.shiyifan.user;

import com.shiyifan.user.dto.UserQueryRequest;
import com.shiyifan.user.dto.UserQueryResponse;

public interface IUserQueryService {


	/**
	 * 根据用户id来查询用户信息
	 * @param request
	 * @return
	 */
	UserQueryResponse getUserById(UserQueryRequest request);

	/**
	 * 根据用户id来查询用户信息,分页查询
	 * @param request
	 * @return
	 */
	UserQueryResponse getUserByIdWithLimiter(UserQueryRequest request);
}
