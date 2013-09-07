package com.fitnfine.dao.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fitnfine.dao.IJobApplicantDao;
import com.fitnfine.dao.service.IJobApplicantService;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.JobApplicant;

@Service("iJobApplicantService")
@Transactional(readOnly=true)
public class JobApplicantServiceImpl implements IJobApplicantService {

	@Autowired
	private IJobApplicantDao iJobApplicantDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=FFSystemException.class, isolation=Isolation.DEFAULT)
	public JobApplicant create(JobApplicant t) throws FFSystemException {
		return this.iJobApplicantDao.createEntity(t);
	}

	@Override
	public Set<JobApplicant> getAll() throws FFSystemException {
		return this.iJobApplicantDao.findAll();
	}

	@Override
	public JobApplicant getById(String id) throws FFSystemException {
		return this.iJobApplicantDao.findById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=FFSystemException.class, isolation=Isolation.DEFAULT)
	public JobApplicant updateEntity(JobApplicant t) throws FFSystemException {
		return this.iJobApplicantDao.updateEntity(t);
	}

	@Override
	public Set<JobApplicant> searchByNamePart(String nameString) {
		return this.iJobApplicantDao.searchByNamePart(nameString);
	}

}
