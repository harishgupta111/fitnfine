package com.fitnfine.restful.validation.impl;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnfine.jackson.mapper.HibernateObjectMapper;
import com.fitnfine.model.BaseEntity;
import com.fitnfine.restful.response.dto.RestResponseConstraintVoilationWrapper;
import com.fitnfine.restful.validation.IUserInputValidationService;

@Component("iUserInputValidationService")
public class UserInputValidationServiceImpl<T extends BaseEntity> implements
		IUserInputValidationService<T> {

	private static Validator validator = Validation
			.buildDefaultValidatorFactory().getValidator();

	@Autowired
	private HibernateObjectMapper<T> hibernateObjectMapper;
	
	@Override
	public RestResponseConstraintVoilationWrapper<T> validateInput(T t) {

		Set<ConstraintViolation<T>> constraintViolations = validator
				.validate(t);
		Set<String> voilations = new HashSet<String>();
		if (constraintViolations != null && constraintViolations.size() > 0) {

			for (ConstraintViolation<T> i : constraintViolations)
				voilations.add(i.getMessage());

			RestResponseConstraintVoilationWrapper<T> constraintVoilationWrapper = new RestResponseConstraintVoilationWrapper.Builder<T>()
					.voilationSet(voilations).status(Status.NOT_ACCEPTABLE)
					.build();

			return constraintVoilationWrapper;

		}
		else
			return null;

	}

	@Override
	public Response sendValidationErrorsInResponse(
			RestResponseConstraintVoilationWrapper<T> constraintVoilationWrapper, ObjectMapper mapper) {

			String json = this.hibernateObjectMapper.prepareJSON(mapper,
					constraintVoilationWrapper);

			return Response.status(constraintVoilationWrapper.getStatus())
					.header("Content-Type", "application/json").entity(json)
					.build();

	}
}
