package com.fitnfine.jackson.mapper.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fitnfine.exception.FFSystemException;
import com.fitnfine.jackson.mapper.HibernateObjectMapper;
import com.fitnfine.model.BaseEntity;

@Component("hibernateObjectMapper")
public class HibernateObjectMapperImpl<T extends BaseEntity> implements HibernateObjectMapper<T> {

	public ObjectMapper fetchEagerly(boolean forceLazyLoading) {
		ObjectMapper mapper = new ObjectMapper();
		Hibernate4Module mod = new Hibernate4Module();
		mod.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING,
				forceLazyLoading);
		mapper.registerModule(mod);
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		mapper.setDateFormat(df);
		return mapper;
	}

	@Override
	public String prepareJSON(ObjectMapper objectMapper, Object data)
			throws FFSystemException {
		String json = null;
		try {
			json = objectMapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(data);
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			throw new FFSystemException(jpe.getCause());
		}

		return json;
	}

	@Override
	public T readValue(ObjectMapper mapper, String jsonRequest, Class<T> t) {
		try {
			return (T) mapper.readValue(jsonRequest, t);
		} catch (IOException e) {
			throw new FFSystemException(e);
		}
	}
}
