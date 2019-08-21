package com.pinyg.services;

import com.pinyg.utils.JwtInfo;
import com.pinyg.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenService {

	@Value("${jwt.expire}")
	private int expire;

	public String generatorToken(JwtInfo jwtInfo){
		return JwtTokenUtil.generateToken(jwtInfo,expire);
	}

	public JwtInfo getInfoFromToken(String token){
		return JwtTokenUtil.getTokenInfo(token);
	}


}
