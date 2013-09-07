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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fitnfine.dao.IUserMasterDao;
import com.fitnfine.dao.hibernate.BaseDaoHibernateSupport;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.UserMaster;
import com.fitnfine.model.enums.UserRole;

@Transactional(readOnly=true)
@Repository("iUserMasterDao")
public class UserMasterDaoHibernateImpl extends BaseDaoHibernateSupport<UserMaster, Long>
		implements IUserMasterDao, UserDetailsService {

	Logger logger = Logger.getLogger(UserMasterDaoHibernateImpl.class);
	
	@Autowired 
	private ShaPasswordEncoder shaPasswordEncoder;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor=FFSystemException.class, isolation=Isolation.DEFAULT)
	@CacheEvict(value = "entity.ta_UserMaster", allEntries = true, beforeInvocation = false)
	public UserMaster createEntity(UserMaster user) throws FFSystemException {
		UserMaster user1 = null;
		try {
			user.setUserId(UUID.randomUUID().toString());
			String passwordEncoded = shaPasswordEncoder.encodePassword(user.getPassword(), user.getUsername());
			logger.info(passwordEncoded);
			user.setPassword(passwordEncoded);
			user1 = this.insert(user, true);
			logger.debug("created entity with ID" + user.getUserId());
		} catch (HibernateException e) {
			throw new FFSystemException(e.getMessage());
		}
		return user1;
	}

	@Override
	@Cacheable(value = "entity.ta_User", key = "#root.methodName")
	public Set<UserMaster> findAll() {
		String strSQL = "Select c from UserMaster c";
		@SuppressWarnings("unchecked")
		Set<UserMaster> set = new LinkedHashSet<UserMaster>(new LinkedList<UserMaster>(
				(List<UserMaster>) (this.executeQuery(strSQL))));
		return set;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Cacheable(value = "entity.ta_UserMaster")
	public UserMaster findById(String id) throws FFSystemException {
		String strSQL = "Select c from UserMaster c where c.userId = :id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		List<UserMaster> list = null;

		try {
			list = (List<UserMaster>) this.executeQuery(strSQL, map);
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "entity.ta_UserMaster", key="{#root.methodName,#username}")
	public UserMaster loadUserByName(String username) throws FFSystemException {
		String strSQL = "Select c from UserMaster c where c.username = :username";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		List<UserMaster> list = null;

		try {
			list = (List<UserMaster>) this.executeQuery(strSQL, map);
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@Cacheable(value = "entity.ta_UserMaster")
	public Set<UserMaster> getAllUsersByRole(UserRole userRole) throws FFSystemException {
		String strSQL = "Select c from UserMaster c where c.role = :userRole";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userRole", userRole);
		Set<UserMaster> set = null;
		try {
			@SuppressWarnings("unchecked")
			List<UserMaster> list = (List<UserMaster>) this.executeQuery(strSQL, map);
			set = new LinkedHashSet<UserMaster>(new LinkedList<UserMaster>(list));
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return set;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor=FFSystemException.class, isolation=Isolation.DEFAULT)
	@CacheEvict(value = "entity.ta_User", allEntries = true, beforeInvocation = false)
	public UserMaster updateEntity(UserMaster t) throws FFSystemException {
		UserMaster user = null;
		try {
			user = super.update(t, true);
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		return user;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		if (username != null && !username.equals("")) {
			UserMaster user = null;

				try {
					user = this.loadUserByName(username);
				} catch (FFSystemException e) {
					logger.error(e.getCause());
					throw new UsernameNotFoundException("User with username " + username + " not found!", e);
				}
			
			if (user == null) {
				return null;
			}

			return user;
		} else {
			return null;
		}
	}

}