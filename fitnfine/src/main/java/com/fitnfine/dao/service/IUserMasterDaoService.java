package com.fitnfine.dao.service;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.UserMaster;
import com.fitnfine.model.enums.UserRole;

public interface IUserMasterDaoService extends IBaseDaoService<UserMaster, String> {

	/**
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws TASystemException 
	 */
	public String getAllJSon() throws JsonParseException, JsonMappingException,
			IOException, FFSystemException;
	
	/**
	 * 
	 * @param userRole
	 * @return
	 */
	public Set<UserMaster> getAllUsersByRole(UserRole userRole) throws FFSystemException;
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws TASystemException 
	 */
	public UserMaster login(String userName, String password) throws FFSystemException;
	
}
