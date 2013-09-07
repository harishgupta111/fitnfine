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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fitnfine.dao.IJobApplicantDao;
import com.fitnfine.dao.hibernate.BaseDaoHibernateSupport;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.JobApplicant;

@Transactional(readOnly = true)
@Repository("iJobApplicantDao")
public class JobApplicantDaoHibernateImpl  extends BaseDaoHibernateSupport<JobApplicant, String> implements IJobApplicantDao {
	
	private static Logger logger = Logger
			.getLogger(JobApplicantDaoHibernateImpl.class);

	@Override
	@CacheEvict(value = { "entity.FF_JobApplicant", "entity.FF_JobApplicant" }, allEntries = true, beforeInvocation = false)
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = FFSystemException.class, isolation = Isolation.DEFAULT)
	public JobApplicant createEntity(JobApplicant t) throws FFSystemException {
		JobApplicant created = null;
		try {
			t.setJobApplicantID(UUID.randomUUID().toString());
			created = this.insert(t, true);
			logger.debug("created entity with ID"
					+ t.getJobApplicantID());
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return created;

	}

	@Override
	public Set<JobApplicant> findAll() throws FFSystemException {
		String strSQL = "Select c from JobApplicant c";
		Set<JobApplicant> set = null;
		try {

			@SuppressWarnings("unchecked")
			List<JobApplicant> list = (List<JobApplicant>) this
					.executeQuery(strSQL);
			set = new LinkedHashSet<JobApplicant>(
					new LinkedList<JobApplicant>(list));
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return set;
	}

	@Override
	public JobApplicant findById(String id) throws FFSystemException {
		String strSQL = "Select c from JobApplicant c where c.jobApplicantID = :id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		try {
			@SuppressWarnings("unchecked")
			List<JobApplicant> list = (List<JobApplicant>) this.executeQuery(
					strSQL, map);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return null;

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = FFSystemException.class, isolation = Isolation.DEFAULT)
	@CacheEvict(value = "entity.FF_JobApplicant", allEntries = true, beforeInvocation = false)
	public JobApplicant updateEntity(JobApplicant t) throws FFSystemException {
		JobApplicant vm = null;
		try {
			vm = super.update(t, true);
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return vm;
	}

	@Override
	public Set<JobApplicant> searchByNamePart(String nameString) {
		Criteria query = super.getSessionFactory().getCurrentSession().createCriteria(JobApplicant.class);
		query.add(Restrictions.like("applicantName", nameString.toUpperCase(), MatchMode.ANYWHERE));
		Set<JobApplicant> set = null;
		try {
			@SuppressWarnings("unchecked")
			List<JobApplicant> list = (List<JobApplicant>) query.list();
			set = new LinkedHashSet<JobApplicant>(
					new LinkedList<JobApplicant>(list));
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return set;
	}

}
