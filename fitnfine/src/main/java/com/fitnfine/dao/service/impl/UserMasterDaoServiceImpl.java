package com.fitnfine.dao.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnfine.auth.manager.IAuthenticationManagerService;
import com.fitnfine.auth.manager.IUserAuthenticationDTOService;
import com.fitnfine.dao.IUserMasterDao;
import com.fitnfine.dao.hibernate.BaseDaoHibernateSupport;
import com.fitnfine.dao.service.IUserMasterDaoService;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.jackson.mapper.HibernateObjectMapper;
import com.fitnfine.model.UserMaster;
import com.fitnfine.model.enums.UserRole;

@Transactional(readOnly=true)
@Component("iUserMasterDaoService")
public class UserMasterDaoServiceImpl extends BaseDaoHibernateSupport<UserMaster, Long> implements IUserMasterDaoService {
	
	private static Logger logger = Logger.getLogger(UserMasterDaoServiceImpl.class);

	@Autowired
	@Qualifier("iUserMasterDao")
	private IUserMasterDao iUserMasterDao;

	@Autowired
	private HibernateObjectMapper<UserMaster> hibernateObjectMapper;
	
	@Autowired
	private IAuthenticationManagerService iAuthenticationManagerService;
	
	@Autowired
	private IUserAuthenticationDTOService iUserAuthenticationDTOService;
	
	/**
	 * 
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=FFSystemException.class)
	public UserMaster create(UserMaster user) throws FFSystemException {
		logger.debug("Before persisting object");
		return this.iUserMasterDao.createEntity(user);
	}

	/**
	 * @throws TASystemException 
	 * 
	 */
	@Override
	public Set<UserMaster> getAll() throws FFSystemException {
		return this.iUserMasterDao.findAll();
	}

	/**
	 * 
	 */
	@Override
	public UserMaster getById(String id) throws FFSystemException {
		return this.iUserMasterDao.findById(id);
	}


	@Override
	public String getAllJSon() throws JsonParseException, JsonMappingException,
			IOException, FFSystemException {
		Set<UserMaster> set = this.getAll();
		ObjectMapper mapper = this.hibernateObjectMapper.fetchEagerly(false);
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
					set);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		@SuppressWarnings("unchecked")
		LinkedHashSet<UserMaster> result = mapper
				.readValue(json, LinkedHashSet.class);
		System.out.println(result.size());
		return json;
	}

	@Override
	public Set<UserMaster> getAllUsersByRole(UserRole userRole) throws FFSystemException {
		return iUserMasterDao.getAllUsersByRole(userRole);
	}

	@Override
	public UserMaster login(String userName, String password) throws FFSystemException {
		boolean boo = false;
		UserMaster userMaster= null; 
	       UsernamePasswordAuthenticationToken token =
			new UsernamePasswordAuthenticationToken(userName, password);

			Authentication auth = this.iAuthenticationManagerService.authenticateToken(token);
			SecurityContext securityContext = new SecurityContextImpl();

			//Places in ThredLocal for future retrieval
			SecurityContextHolder.setContext(securityContext);
			SecurityContextHolder.getContext().setAuthentication(auth);
			boo =  true;
			if(boo){
				logger.info("User Authenticated " + auth.isAuthenticated());
				this.iUserAuthenticationDTOService.setAuthenticate(auth);
				userMaster = loadUserByName(userName);
			}				

		return userMaster;
	}


	@Override
	public UserMaster updateEntity(UserMaster t) throws FFSystemException {
		return this.iUserMasterDao.updateEntity(t);
	}
	
	@SuppressWarnings("unchecked")
	public UserMaster loadUserByName(String username) throws FFSystemException {
		String strSQL = "Select c from UserMaster c where c.username = :username";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		List<UserMaster> list = null;

		try {
			list = (List<UserMaster>) executeQuery(strSQL, map);
		} catch (HibernateException e) {
			throw new FFSystemException(e);
		}
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
