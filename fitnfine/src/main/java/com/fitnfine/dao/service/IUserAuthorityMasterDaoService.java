package com.fitnfine.dao.service;

import java.util.Set;

import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.UserAuthorityMaster;
import com.fitnfine.model.UserMaster;

public interface IUserAuthorityMasterDaoService extends
		IBaseDaoService<UserAuthorityMaster, String> {
	
	public Set<UserAuthorityMaster> findByUserMaster(UserMaster userMaster) throws FFSystemException;

}
