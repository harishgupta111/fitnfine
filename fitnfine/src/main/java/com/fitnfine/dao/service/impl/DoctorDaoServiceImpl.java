package com.fitnfine.dao.service.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fitnfine.dao.IDoctorDao;
import com.fitnfine.dao.service.IDoctorDaoService;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.Doctor;

@Service("iDoctorDaoService")
@Transactional(readOnly=true)
public class DoctorDaoServiceImpl implements IDoctorDaoService {
	
	private static Logger logger = Logger.getLogger(DoctorDaoServiceImpl.class);

	@Autowired
	private IDoctorDao iDoctorDao;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=FFSystemException.class, isolation=Isolation.DEFAULT)
	public Doctor create(Doctor t) throws FFSystemException {
		logger.debug("Before creating object");
		return iDoctorDao.createEntity(t);
	}

	public Set<Doctor> getAll() throws FFSystemException {
		return iDoctorDao.findAll();
	}

	public Doctor getById(String id) throws FFSystemException {
		return iDoctorDao.findById(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=FFSystemException.class, isolation=Isolation.DEFAULT)
	public Doctor updateEntity(Doctor t) throws FFSystemException {
		return iDoctorDao.updateEntity(t);
	}

	@Override
	public Set<Doctor> searchByNamePart(String nameString) {
		return iDoctorDao.searchByNamePart(nameString);
	}

}
