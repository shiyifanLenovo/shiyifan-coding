package com.pinyg.services;
import com.pinyg.dao.mapper.UserMapper;
import com.pinyg.dao.po.User;
import com.pinyg.user.IUserQueryService;
import com.pinyg.user.dto.UserQueryRequest;
import com.pinyg.user.dto.UserQueryResponse;
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
