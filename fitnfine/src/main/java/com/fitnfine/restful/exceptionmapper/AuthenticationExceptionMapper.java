package com.fitnfine.restful.exceptionmapper;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.jackson.mapper.HibernateObjectMapper;
import com.fitnfine.restful.response.dto.RestResponseExceptionWrapper;

@Provider
@Component
public class AuthenticationExceptionMapper implements
		ExceptionMapper<AuthenticationException> {

	@Autowired
	private HibernateObjectMapper<AuthenticationException> hibernateObjectMapper;

	private static Logger logger = Logger
			.getLogger(AuthenticationExceptionMapper.class);

	@Override
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody
	Response toResponse(AuthenticationException exception) {

		logger.error(exception.getMessage());
		RestResponseExceptionWrapper<AuthenticationException> restResponseExceptionWrapper = new RestResponseExceptionWrapper.Builder<AuthenticationException>()
				.status(Status.UNAUTHORIZED).exception(null)
				.errorMessage(exception.getMessage()).build();
		ObjectMapper mapper = this.hibernateObjectMapper.fetchEagerly(false);
		String json = null;
		try {
			json = this.hibernateObjectMapper.prepareJSON(mapper,
					restResponseExceptionWrapper);
		} catch (FFSystemException e) {
			e.printStackTrace();
		}
		logger.error(exception.getCause());
		return Response.status(restResponseExceptionWrapper.getStatus())
				.header("Content-Type", "application/json").entity(json)
				.build();
	}

}
