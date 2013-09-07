package com.fitnfine.auth.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.fitnfine.auth.UserAuthenticationDTO;
import com.fitnfine.auth.manager.IUserAuthenticationDTOService;

@Component("iUserAuthenticationDTOService")
public class UserAuthenticationDTOServiceImpl implements IUserAuthenticationDTOService {

	@Autowired
	private UserAuthenticationDTO userAuthenticationDTO;
	
	@Override
	public void setAuthenticate(Authentication authentication) {
		this.userAuthenticationDTO.setAuthentication(authentication);
	}

	@Override
	public UserAuthenticationDTO getAuthenticationDTO() {
		return this.userAuthenticationDTO;
	}



}
