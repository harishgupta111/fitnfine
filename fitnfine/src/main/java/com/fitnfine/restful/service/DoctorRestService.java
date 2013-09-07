package com.fitnfine.restful.service;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnfine.dao.service.IDoctorDaoService;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.jackson.mapper.HibernateObjectMapper;
import com.fitnfine.model.Doctor;
import com.fitnfine.restful.response.dto.RestResponseCollectionWrapper;
import com.fitnfine.restful.response.dto.RestResponseConstraintVoilationWrapper;
import com.fitnfine.restful.response.dto.RestResponseWrapper;
import com.fitnfine.restful.validation.IUserInputValidationService;

@Controller
@Path("/doctor")
public class DoctorRestService {

	@Autowired
	private HibernateObjectMapper<Doctor> hibernateObjectMapper;

	@Autowired
	private IDoctorDaoService iDoctorDaoService;

	@Autowired
	private IUserInputValidationService<Doctor> iUserInputValidationService;

	@POST
	@Path("/submit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody
	Response submit(String jsonRequest) throws FFSystemException {
		ObjectMapper mapper = this.hibernateObjectMapper.fetchEagerly(false);
		Doctor doctor = hibernateObjectMapper.readValue(mapper, jsonRequest, Doctor.class);

		RestResponseConstraintVoilationWrapper<Doctor> constraintVoilationWrapper = iUserInputValidationService
				.validateInput(doctor);

		if (constraintVoilationWrapper != null) {
			return iUserInputValidationService.sendValidationErrorsInResponse(
					constraintVoilationWrapper, mapper);
		}

		Doctor created = this.iDoctorDaoService.create(doctor);

		RestResponseWrapper<Doctor> restResponseWrapper = new RestResponseWrapper.Builder<Doctor>()
				.data(created).status(Status.CREATED).build();

		String json = this.hibernateObjectMapper.prepareJSON(mapper,
				restResponseWrapper);

		return Response.status(restResponseWrapper.getStatus())
				.header("Content-Type", "application/json").entity(json)
				.build();

	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody
	Response getById(@PathParam("id") String id) throws FFSystemException {
		Doctor doctor = iDoctorDaoService.getById(id);
		ObjectMapper mapper = this.hibernateObjectMapper.fetchEagerly(false);

		RestResponseWrapper<Doctor> restResponseWrapper = new RestResponseWrapper.Builder<Doctor>()
				.data(doctor).status(Status.CREATED).build();

		String json = this.hibernateObjectMapper.prepareJSON(mapper,
				restResponseWrapper);

		return Response.status(restResponseWrapper.getStatus())
				.header("Content-Type", "application/json").entity(json)
				.build();

	}

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody
	Response getAll() throws FFSystemException {
		Set<Doctor> set = iDoctorDaoService.getAll();
		ObjectMapper mapper = this.hibernateObjectMapper.fetchEagerly(false);

		RestResponseCollectionWrapper<Doctor> restResponseWrapper = new RestResponseCollectionWrapper.Builder<Doctor>()
				.collection(set).status(Status.CREATED).build();

		String json = this.hibernateObjectMapper.prepareJSON(mapper,
				restResponseWrapper);

		return Response.status(restResponseWrapper.getStatus())
				.header("Content-Type", "application/json").entity(json)
				.build();
	}
	
	@GET
	@Path("/search/{namePart}")
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody
	Response searchByNamePart(@PathParam("namePart") String namePart) throws FFSystemException {
		Set<Doctor> set = iDoctorDaoService.searchByNamePart(namePart);
		ObjectMapper mapper = this.hibernateObjectMapper.fetchEagerly(false);

		RestResponseCollectionWrapper<Doctor> restResponseWrapper = new RestResponseCollectionWrapper.Builder<Doctor>()
				.collection(set).status(Status.CREATED).build();

		String json = this.hibernateObjectMapper.prepareJSON(mapper,
				restResponseWrapper);

		return Response.status(restResponseWrapper.getStatus())
				.header("Content-Type", "application/json").entity(json)
				.build();
	}

}
