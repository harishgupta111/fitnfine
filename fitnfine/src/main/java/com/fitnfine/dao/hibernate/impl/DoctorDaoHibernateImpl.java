package com.fitnfine.dao.hibernate.impl;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fitnfine.dao.IDoctorDao;
import com.fitnfine.dao.hibernate.BaseDaoHibernateSupport;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.Doctor;

@Transactional(readOnly = true)
@Repository("iDoctorDao")
public class DoctorDaoHibernateImpl extends BaseDaoHibernateSupport<Doctor, String> implements IDoctorDao {
	
	private static Logger logger = Logger
			.getLogger(DoctorDaoHibernateImpl.class);

	@CacheEvict(value = { "entity.FF_Doctor", "entity.FF_Doctor" }, allEntries = true, beforeInvocation = false)
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = FFSystemException.class, isolation = Isolation.DEFAULT)
	public Doctor createEntity(Doctor t) throws FFSystemException {
		Doctor created = null;
		try {
			t.setDoctorID(UUID.randomUUID().toString());
			created = this.insert(t, true);
			logger.debug("created entity with ID"
					+ t.getDoctorID());
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return created;

	}

	@Cacheable(value = "entity.FF_Doctor")
	public Set<Doctor> findAll() throws FFSystemException {
		String strSQL = "Select c from Doctor c";
		Set<Doctor> set = null;
		try {

			@SuppressWarnings("unchecked")
			List<Doctor> list = (List<Doctor>) this
					.executeQuery(strSQL);
			set = new LinkedHashSet<Doctor>(
					new LinkedList<Doctor>(list));
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return set;
	}

	@Cacheable(value = "entity.FF_Doctor", key="{#root.methodName,#id}")
	public Doctor findById(String id) throws FFSystemException {
		String strSQL = "Select c from Doctor c where c.doctorID = :id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		try {
			@SuppressWarnings("unchecked")
			List<Doctor> list = (List<Doctor>) this.executeQuery(
					strSQL, map);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = FFSystemException.class, isolation = Isolation.DEFAULT)
	@CacheEvict(value = "entity.FF_Doctor", allEntries = true, beforeInvocation = false)
	public Doctor updateEntity(Doctor t) throws FFSystemException {
		Doctor vm = null;
		try {
			vm = super.update(t, true);
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return vm;
	}

	@Override
	@Cacheable(value = "entity.FF_Doctor")
	public Set<Doctor> searchByNamePart(String nameString) {
		Criteria query = super.getSessionFactory().getCurrentSession().createCriteria(Doctor.class);
		query.add(Restrictions.like("doctorName", nameString.toUpperCase(), MatchMode.ANYWHERE));
		Set<Doctor> set = null;
		try {
			@SuppressWarnings("unchecked")
			List<Doctor> list = (List<Doctor>) query.list();
			set = new LinkedHashSet<Doctor>(
					new LinkedList<Doctor>(list));
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return set;

	}


}
