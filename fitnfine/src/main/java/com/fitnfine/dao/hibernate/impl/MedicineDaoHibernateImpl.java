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

import com.fitnfine.dao.IMedicineDao;
import com.fitnfine.dao.hibernate.BaseDaoHibernateSupport;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.Medicine;

@Transactional(readOnly = true)
@Repository("iMedicineDao")
public class MedicineDaoHibernateImpl extends BaseDaoHibernateSupport<Medicine, String> implements
		IMedicineDao {

	private static Logger logger = Logger
			.getLogger(MedicineDaoHibernateImpl.class);

	@CacheEvict(value = { "entity.FF_Medicine", "entity.FF_Medicine" }, allEntries = true, beforeInvocation = false)
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = FFSystemException.class, isolation = Isolation.DEFAULT)
	public Medicine createEntity(Medicine t) throws FFSystemException {
		Medicine created = null;
		try {
			t.setMedicineID(UUID.randomUUID().toString());
			created = this.insert(t, true);
			logger.debug("created entity with ID"
					+ t.getMedicineID());
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return created;
	}

	@Cacheable(value = "entity.FF_Medicine")
	public Set<Medicine> findAll() throws FFSystemException {
		String strSQL = "Select c from Medicine c";
		Set<Medicine> set = null;
		try {

			@SuppressWarnings("unchecked")
			List<Medicine> list = (List<Medicine>) this
					.executeQuery(strSQL);
			set = new LinkedHashSet<Medicine>(
					new LinkedList<Medicine>(list));
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return set;
	}

	@Cacheable(value = "entity.FF_Medicine", key="{#root.methodName,#id}")
	public Medicine findById(String id) throws FFSystemException {
		String strSQL = "Select c from Medicine c where c.medicineID = :id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		try {
			@SuppressWarnings("unchecked")
			List<Medicine> list = (List<Medicine>) this.executeQuery(
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
	@CacheEvict(value = "entity.FF_Medicine", allEntries = true, beforeInvocation = false)
	public Medicine updateEntity(Medicine t) throws FFSystemException {
		Medicine vm = null;
		try {
			vm = super.update(t, true);
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return vm;
	}

	@Override
	@Cacheable(value = "entity.FF_Doctor")
	public Set<Medicine> searchByNamePart(String nameString) {
		Criteria query = super.getSessionFactory().getCurrentSession().createCriteria(Medicine.class);
		query.add(Restrictions.like("medicineName", nameString.toUpperCase(), MatchMode.ANYWHERE));
		Set<Medicine> set = null;
		try {
			@SuppressWarnings("unchecked")
			List<Medicine> list = (List<Medicine>) query.list();
			set = new LinkedHashSet<Medicine>(
					new LinkedList<Medicine>(list));
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return set;
	}

}
