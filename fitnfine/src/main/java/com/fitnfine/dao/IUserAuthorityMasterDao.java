package com.fitnfine.dao;

import java.util.Set;

import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.UserAuthorityMaster;
import com.fitnfine.model.UserMaster;

public interface IUserAuthorityMasterDao extends IBaseDao<UserAuthorityMaster, String> {
	
	public Set<UserAuthorityMaster> findByUserMaster(UserMaster userMaster) throws FFSystemException;

}
