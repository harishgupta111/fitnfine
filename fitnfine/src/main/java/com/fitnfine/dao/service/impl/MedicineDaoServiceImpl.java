package com.fitnfine.dao.service.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fitnfine.dao.IMedicineDao;
import com.fitnfine.dao.service.IMedicineDaoService;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.Medicine;

@Service("iMedicineDaoService")
@Transactional(readOnly=true)
public class MedicineDaoServiceImpl implements IMedicineDaoService {
	
	private static Logger logger = Logger.getLogger(MedicineDaoServiceImpl.class);

	@Autowired
	private IMedicineDao iMedicineDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=FFSystemException.class, isolation=Isolation.DEFAULT)
	public Medicine create(Medicine t) throws FFSystemException {
		logger.debug("Before creating object");
		return iMedicineDao.createEntity(t);
	}

	public Set<Medicine> getAll() throws FFSystemException {
		return iMedicineDao.findAll();
	}

	public Medicine getById(String id) throws FFSystemException {
		return iMedicineDao.findById(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=FFSystemException.class, isolation=Isolation.DEFAULT)
	public Medicine updateEntity(Medicine t) throws FFSystemException {
		return iMedicineDao.updateEntity(t);
	}

	@Override
	public Set<Medicine> searchByNamePart(String nameString) {
		return iMedicineDao.searchByNamePart(nameString);
	}

}
