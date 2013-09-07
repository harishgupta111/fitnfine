package com.fitnfine.restful.messagebody.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.JobApplicant;

@Produces(MediaType.MULTIPART_FORM_DATA)
@Provider
@Component
public class JobApplicantMessageBodyWriter implements MessageBodyWriter<JobApplicant> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return type == JobApplicant.class;
	}

	@Override
	public long getSize(JobApplicant t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(JobApplicant t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(JobApplicant.class);
 
            // serialize the entity myBean to the entity output stream
            jaxbContext.createMarshaller().marshal(t, entityStream);
        } catch (JAXBException jaxbException) {
            throw new FFSystemException(
                "Error serializing a MyBean to the output stream", jaxbException);
        }
		
	}

}
