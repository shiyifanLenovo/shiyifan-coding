package com.shiyifan.services;
import com.shiyifan.dao.mapper.UserMapper;
import com.shiyifan.dao.po.User;
import com.shiyifan.user.IUserQueryService;
import com.shiyifan.user.dto.UserQueryRequest;
import com.shiyifan.user.dto.UserQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userQueryService")
public class UserQueryService implements IUserQueryService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserQueryResponse getUserById(UserQueryRequest request) {
		User userByUid = userMapper.getUserByUid(request.getUid());
		return null;
	}

	@Override
	public UserQueryResponse getUserByIdWithLimiter(UserQueryRequest request) {
		return null;
	}
}
