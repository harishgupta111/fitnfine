package com.fitnfine.dao.service.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fitnfine.dao.IUserAuthorityMasterDao;
import com.fitnfine.dao.service.IUserAuthorityMasterDaoService;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.UserAuthorityMaster;
import com.fitnfine.model.UserMaster;

@Transactional(readOnly=true)
@Component("iUserAuthorityMasterDaoService")
public class UserAuthorityMasterDaoServiceImpl  implements IUserAuthorityMasterDaoService {
	
	private static Logger logger = Logger.getLogger(UserAuthorityMasterDaoServiceImpl.class);

	@Autowired
	private IUserAuthorityMasterDao iUserAuthorityMasterDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=FFSystemException.class, isolation=Isolation.DEFAULT)
	public UserAuthorityMaster create(UserAuthorityMaster t)
			throws FFSystemException {
		logger.debug("Before persisting object");
		return this.iUserAuthorityMasterDao.createEntity(t);
	}

	@Override
	public Set<UserAuthorityMaster> getAll() throws FFSystemException {
		return this.iUserAuthorityMasterDao.findAll();
	}

	@Override
	public UserAuthorityMaster getById(String id) throws FFSystemException {
		return this.iUserAuthorityMasterDao.findById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=FFSystemException.class, isolation=Isolation.DEFAULT)
	public UserAuthorityMaster updateEntity(UserAuthorityMaster t)
			throws FFSystemException {
		return this.iUserAuthorityMasterDao.updateEntity(t);
	}

	@Override
	public Set<UserAuthorityMaster> findByUserMaster(UserMaster userMaster)
			throws FFSystemException {
		return this.iUserAuthorityMasterDao.findByUserMaster(userMaster);
	}

}
