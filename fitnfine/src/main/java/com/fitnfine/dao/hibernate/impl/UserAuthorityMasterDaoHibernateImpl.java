package com.fitnfine.dao.hibernate.impl;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fitnfine.dao.IUserAuthorityMasterDao;
import com.fitnfine.dao.hibernate.BaseDaoHibernateSupport;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.UserAuthorityMaster;
import com.fitnfine.model.UserMaster;

@Transactional(readOnly = true)
@Repository("iUserAuthorityMasterDao")
public class UserAuthorityMasterDaoHibernateImpl extends
		BaseDaoHibernateSupport<UserAuthorityMaster, String> implements
		IUserAuthorityMasterDao {

	private static Logger logger = Logger
			.getLogger(UserAuthorityMasterDaoHibernateImpl.class);

	@Override
	@CacheEvict(value = { "entity.ta_UserAuthorityMaster",
			"entity.ta_UserAuthorityMaster" }, allEntries = true, beforeInvocation = false)
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = FFSystemException.class, isolation = Isolation.DEFAULT)
	public UserAuthorityMaster createEntity(UserAuthorityMaster t)
			throws FFSystemException {
		UserAuthorityMaster created = null;
		try {
			t.setUserAuthorityMasterId(UUID.randomUUID().toString());
			created = this.insert(t, true);
			logger.debug("created entity with ID"
					+ t.getUserAuthorityMasterId());
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return created;

	}

	@Override
	@Cacheable(value = "entity.ta_UserAuthorityMaster")
	public Set<UserAuthorityMaster> findAll() throws FFSystemException {
		String strSQL = "Select c from UserAuthorityMaster c";
		LinkedHashSet<UserAuthorityMaster> set = null;
		try {

			@SuppressWarnings("unchecked")
			List<UserAuthorityMaster> list = (List<UserAuthorityMaster>) this
					.executeQuery(strSQL);
			set = new LinkedHashSet<UserAuthorityMaster>(
					new LinkedList<UserAuthorityMaster>(list));
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return set;

	}

	@Override
	@Cacheable(value = "entity.ta_UserAuthorityMaster", key = "{#root.methodName,#id}")
	public UserAuthorityMaster findById(String id) throws FFSystemException {
		String strSQL = "Select c from UserAuthorityMaster c where c.userAuthorityMasterId = :id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		try {
			@SuppressWarnings("unchecked")
			List<UserAuthorityMaster> list = (List<UserAuthorityMaster>) this
					.executeQuery(strSQL, map);
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
	@CacheEvict(value = "entity.ta_UserAuthorityMaster", beforeInvocation = false, key = "{#root.methodName,#t.contactUsId}")
	public UserAuthorityMaster updateEntity(UserAuthorityMaster t)
			throws FFSystemException {
		UserAuthorityMaster obj = null;
		try {
			obj = super.update(t, true);
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return obj;

	}

	@Override
	@Cacheable(value = "entity.ta_UserAuthorityMaster")
	public Set<UserAuthorityMaster> findByUserMaster(UserMaster userMaster)
			throws FFSystemException {
		Set<UserAuthorityMaster> set = null;
		try {
			set = userMaster.getUserAuthorityMasterSet();
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return set;
	}

}
