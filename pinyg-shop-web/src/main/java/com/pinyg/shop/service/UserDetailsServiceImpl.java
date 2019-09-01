package com.pinyg.shop.service;

import com.pinyg.sellergoods.pojo.TbSeller;
import com.pinyg.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

	private SellerService sellerService;

	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		System.out.println("进入了 == UserDetailsServiceImpl");

		List<GrantedAuthority>  grantedAuthorityList=new ArrayList<>();
		grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_SELLER"));

		TbSeller one = sellerService.findOne(userName);
		if(one!=null && "1".equals(one.getStatus())){
			return new User(userName,one.getPassword(),grantedAuthorityList );
		}
       return null;
	}
}
