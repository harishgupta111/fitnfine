package com.fitnfine.restful.exceptionmapper;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.jackson.mapper.HibernateObjectMapper;
import com.fitnfine.restful.response.dto.RestResponseExceptionWrapper;

@Provider
@Component
public class FFSystemExceptionMapper implements
		ExceptionMapper<FFSystemException> {

	@Autowired
	private HibernateObjectMapper<FFSystemException> hibernateObjectMapper;

	private static Logger logger = Logger
			.getLogger(FFSystemExceptionMapper.class);

	@Override
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody
	Response toResponse(FFSystemException exception) {

		logger.error(exception.getMessage());
		RestResponseExceptionWrapper<FFSystemException> restResponseExceptionWrapper = new RestResponseExceptionWrapper.Builder<FFSystemException>()
				.status(Status.INTERNAL_SERVER_ERROR).exception(null)
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
