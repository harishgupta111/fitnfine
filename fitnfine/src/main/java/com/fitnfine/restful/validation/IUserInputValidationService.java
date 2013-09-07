package com.fitnfine.restful.validation;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnfine.model.BaseEntity;
import com.fitnfine.restful.response.dto.RestResponseConstraintVoilationWrapper;

public interface IUserInputValidationService<T extends BaseEntity> {
	
	public RestResponseConstraintVoilationWrapper<T> validateInput(T t);
	
	public Response sendValidationErrorsInResponse(RestResponseConstraintVoilationWrapper<T> constraintVoilationWrapper, ObjectMapper mapper);

}