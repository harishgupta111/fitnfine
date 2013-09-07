package com.fitnfine.dao;

import java.util.Set;

import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.UserMaster;
import com.fitnfine.model.enums.UserRole;

public interface IUserMasterDao extends IBaseDao<UserMaster, String> {
	
	public UserMaster loadUserByName(String name) throws FFSystemException;

	public Set<UserMaster> getAllUsersByRole(UserRole userRole) throws FFSystemException;
	
}
