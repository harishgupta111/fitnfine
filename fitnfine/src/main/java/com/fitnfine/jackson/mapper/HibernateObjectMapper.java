package com.fitnfine.jackson.mapper;

import java.io.Serializable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnfine.exception.FFSystemException;

public interface HibernateObjectMapper<T extends Serializable> {

	public ObjectMapper fetchEagerly(boolean forceLazyLoading);
	
	public String prepareJSON(ObjectMapper objectMapper, Object data) throws FFSystemException;
	
	public T readValue(ObjectMapper mapper, String jsonRequest, Class<T> t);
}
