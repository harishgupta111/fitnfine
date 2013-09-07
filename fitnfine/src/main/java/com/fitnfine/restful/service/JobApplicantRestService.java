package com.fitnfine.restful.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnfine.dao.service.IJobApplicantService;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.jackson.mapper.HibernateObjectMapper;
import com.fitnfine.model.JobApplicant;
import com.fitnfine.model.enums.RecordCreatorType;
import com.fitnfine.restful.response.dto.RestResponseConstraintVoilationWrapper;
import com.fitnfine.restful.response.dto.RestResponseWrapper;
import com.fitnfine.restful.validation.IUserInputValidationService;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Controller
@Path("/jobApplication")
public class JobApplicantRestService {
	
	@Autowired
	private HibernateObjectMapper<JobApplicant> hibernateObjectMapper;

	@Autowired
	private IJobApplicantService iJobApplicantService;

	@Autowired
	private IUserInputValidationService<JobApplicant> iUserInputValidationService;

	@POST
	@Path("/submit")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public @ResponseBody
	Response submit(@FormDataParam("file") InputStream uploadedInputStream,
	        @FormDataParam("file") FormDataContentDisposition fileInfo,
	        @FormDataParam("applicantName") String applicantName,
	        @FormDataParam("applicantAge") Integer applicantAge,
	        @FormDataParam("experienceInYears") Integer experienceInYears,
	        @FormDataParam("experienceComment") String experienceComment,
	        @FormDataParam("contactAddress") String contactAddress,
	        @FormDataParam("contactNumber") String contactNumber,
	        @FormDataParam("otherComment") String otherComment) throws FFSystemException, IOException {
		
		ObjectMapper mapper = this.hibernateObjectMapper.fetchEagerly(false);
		JobApplicant ja = new JobApplicant();
		ja.setApplicantAge(applicantAge);
		ja.setApplicantName(applicantName);
		ja.setContactAddress(contactAddress);
		ja.setContactNumber(contactNumber);
		ja.setExperienceComment(experienceComment);
		ja.setExperienceInYears(experienceInYears);
		ja.setOtherComment(otherComment);
		ja.setResumeFile(IOUtils.toByteArray(uploadedInputStream));
		ja.setCreateDate(new Date());
		ja.setUpdateDate(new Date());
		ja.setUpdatedBy(RecordCreatorType.SYSTEM);
		ja.setCreatedBy(RecordCreatorType.SYSTEM);
		
		RestResponseConstraintVoilationWrapper<JobApplicant> constraintVoilationWrapper = iUserInputValidationService
				.validateInput(ja);

		if (constraintVoilationWrapper != null) {
			return iUserInputValidationService.sendValidationErrorsInResponse(
					constraintVoilationWrapper, mapper);
		}
		
		JobApplicant created = this.iJobApplicantService.create(ja);

		RestResponseWrapper<JobApplicant> restResponseWrapper = new RestResponseWrapper.Builder<JobApplicant>()
				.data(created).status(Status.CREATED).build();

		String json = this.hibernateObjectMapper.prepareJSON(mapper,
				restResponseWrapper);

		return Response.status(restResponseWrapper.getStatus())
				.header("Content-Type", "multipart/form-data").entity(restResponseWrapper)
				.build();

	}


}
