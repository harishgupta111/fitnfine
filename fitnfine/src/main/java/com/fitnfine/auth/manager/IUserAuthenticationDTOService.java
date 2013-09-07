package com.fitnfine.auth.manager;

import org.springframework.security.core.Authentication;

import com.fitnfine.auth.UserAuthenticationDTO;

public interface IUserAuthenticationDTOService {
	
	public void setAuthenticate(Authentication authentication);
	
	public UserAuthenticationDTO getAuthenticationDTO();
	
}
