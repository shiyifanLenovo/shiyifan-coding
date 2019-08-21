package com.pinyg.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * @Description: 生成token的工具类
 * @Author: shiyf
 * @CreateDate: 2019/8/12 22:22
 * @UpdateUser: shiyf
 * @UpdateDate: 2019/8/12 22:22
 * @UpdateRemark: 修改内容
 * @Version: cmp2.0
 */
public class JwtTokenUtil {


	/**
	 * 生成token
	 * @param jwtInfo
	 * @param expire
	 * @return
	 */
	public static String generateToken(JwtInfo jwtInfo, int  expire){

		return Jwts.builder().claim(JwtConstant.JWT_KEY_USER_ID,jwtInfo.getUid()).
				setExpiration(DateTime.now().plusSeconds(expire).toDate()).signWith(SignatureAlgorithm.HS256,getKeyInstance()).compact();
	}

	/**
	 * 获取token中的信息
	 * @param token
	 * @return
	 */
	public static JwtInfo getTokenInfo(String token){
		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
		Claims claims = claimsJws.getBody();
		return new JwtInfo( claims.get(JwtConstant.JWT_KEY_USER_ID).toString());
	}

	private static Key getKeyInstance(){
		SignatureAlgorithm signatureAlgorithm= SignatureAlgorithm.HS256;
		byte[] dc = DatatypeConverter.parseBase64Binary("shiyifan-user");
		return new SecretKeySpec(dc,signatureAlgorithm.getJcaName());
	}



}
