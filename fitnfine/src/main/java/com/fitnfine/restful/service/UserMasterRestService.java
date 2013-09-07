package com.fitnfine.restful.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnfine.dao.service.IUserMasterDaoService;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.jackson.mapper.HibernateObjectMapper;
import com.fitnfine.model.UserMaster;
import com.fitnfine.restful.response.dto.RestResponseConstraintVoilationWrapper;
import com.fitnfine.restful.response.dto.RestResponseWrapper;
import com.fitnfine.restful.validation.IUserInputValidationService;

@Controller
@Path("/user")
public class UserMasterRestService implements InitializingBean {

	@Autowired
	private IUserMasterDaoService iUserMasterDaoService;

	@Autowired
	private HibernateObjectMapper<UserMaster> hibernateObjectMapper;

	@Autowired
	private IUserInputValidationService<UserMaster> iUserInputValidationService;

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody
	Response createUser(String jsonRequest) throws FFSystemException {

		ObjectMapper mapper = this.hibernateObjectMapper.fetchEagerly(false);
		UserMaster user = hibernateObjectMapper.readValue(mapper, jsonRequest, UserMaster.class);

		RestResponseConstraintVoilationWrapper<UserMaster> constraintVoilationWrapper = this.iUserInputValidationService
				.validateInput(user);

		if (constraintVoilationWrapper != null) {
			return iUserInputValidationService.sendValidationErrorsInResponse(
					constraintVoilationWrapper, mapper);
		}

		UserMaster created = this.iUserMasterDaoService.create(user);

		RestResponseWrapper<UserMaster> restResponseWrapper = new RestResponseWrapper.Builder<UserMaster>()
				.data(created).status(Status.CREATED).build();

		String json = this.hibernateObjectMapper.prepareJSON(mapper,
				restResponseWrapper);

		return Response.status(restResponseWrapper.getStatus())
				.header("Content-Type", "application/json").entity(json)
				.build();

	}

	@GET
	@Path("/login/{userName}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody
	Response login(@PathParam("userName") String userName,
			@PathParam("password") String password) throws FFSystemException {
		ObjectMapper mapper = this.hibernateObjectMapper.fetchEagerly(false);
		UserMaster um = this.iUserMasterDaoService.login(userName,password);

		RestResponseWrapper<UserMaster> restResponseWrapper = new RestResponseWrapper.Builder<UserMaster>()
				.data(um).status(Status.CREATED).build();

		String json = this.hibernateObjectMapper.prepareJSON(mapper,
				restResponseWrapper);

		return Response.status(restResponseWrapper.getStatus())
				.header("Content-Type", "application/json").entity(json)
				.build();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.iUserMasterDaoService);
		Assert.notNull(this.hibernateObjectMapper);
	}

}
