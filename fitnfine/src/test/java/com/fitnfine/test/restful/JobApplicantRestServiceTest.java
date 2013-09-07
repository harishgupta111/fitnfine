package com.fitnfine.test.restful;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.fitnfine.exception.FFSystemException;
import com.fitnfine.jackson.mapper.HibernateObjectMapper;
import com.fitnfine.model.JobApplicant;
import com.fitnfine.model.enums.RecordCreatorType;
import com.fitnfine.model.enums.UserRole;
import com.fitnfine.restful.response.dto.RestResponseWrapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = { "/jerseyApplicationContext-test.xml" })
@Transactional
public class JobApplicantRestServiceTest extends SpringAwareJerseyTests {
	
	@Autowired
	private HibernateObjectMapper<UserRole> hibernateObjectMapper;

	String filePath="E:/Workspace/fitnfine/src/test/resources/TestData/Vivekanandha_Java Architect.docx";
	public JobApplicantRestServiceTest() {
		super();
	}
	
	@Test
	public void shouldCreate() throws FFSystemException, IOException {
		
		JobApplicant ja = new JobApplicant();
		ja.setApplicantAge(25);
		ja.setApplicantName("John Brown");
		ja.setContactAddress("123 Broad St");
		ja.setContactNumber("446-897-6735");
		ja.setExperienceComment("Fresher");
		ja.setExperienceInYears(0);
		InputStream is = new FileInputStream(new File(filePath));
		ja.setResumeFile(IOUtils.toByteArray(is));
		ja.setCreateDate(new Date());
		ja.setUpdateDate(new Date());
		ja.setUpdatedBy(RecordCreatorType.SYSTEM);
		ja.setCreatedBy(RecordCreatorType.SYSTEM);
		ja.setOtherComment("Other Comment");

		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(MultiPartWriter.class);
		String requestUrl="http://localhost:9998/fitnfine/rest/jobApplication/submit";
		Client client = client().create(cc);

		WebResource service = client.resource(requestUrl);//http://alt-plutonium:8080/altcso/service/request

		FormDataMultiPart formData = new FormDataMultiPart() ;
		formData.field("applicantAge",ja.getApplicantAge().toString()) ; 
		formData.field("applicantName",ja.getApplicantName()) ;
		formData.field("contactAddress",ja.getContactAddress()) ;
		formData.field("contactNumber",ja.getContactNumber()); 
		formData.field("experienceComment",ja.getExperienceComment()); 
		formData.field("experienceInYears",ja.getExperienceInYears().toString()); 
		formData.field("file", is, MediaType.MULTIPART_FORM_DATA_TYPE);
		formData.field("createDate", new Date().toString());
		formData.field("updateDate", new Date().toString());
		formData.field("updatedBy", RecordCreatorType.SYSTEM.toString());
		formData.field("createdBy", RecordCreatorType.SYSTEM.toString());
		formData.field("otherComment", "Other Comment");

		
		ClientResponse clientResponse = service.type(MediaType.MULTIPART_FORM_DATA).header("appkey","546869734973434e53").post(ClientResponse.class,formData);

//		ClientResponse clientResponse = webResource.path("/jobApplication/submit")
//				.entity(ja, MediaType.MULTIPART_FORM_DATA)
//				.accept(MediaType.MULTIPART_FORM_DATA)
//				.type(MediaType.MULTIPART_FORM_DATA)
//				.post(ClientResponse.class, ja);

		Assert.assertNotNull(clientResponse.getEntity(RestResponseWrapper.class));

		System.out.println("*******************************************");
		System.out.println(clientResponse.getEntity(String.class));
		System.out.println("*******************************************");
		Assert.assertTrue(
				"clientResponse found as " + clientResponse.getStatus(),
				clientResponse.getStatus() == 201);

	}

	

}
