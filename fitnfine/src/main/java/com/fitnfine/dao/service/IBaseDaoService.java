package com.fitnfine.dao.service;

import java.io.Serializable;
import java.util.Set;

import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.BaseEntity;

interface IBaseDaoService<T extends BaseEntity, P extends Serializable> {

	public T create(T t) throws FFSystemException;

	public Set<T> getAll() throws FFSystemException;

	public T getById(P id) throws FFSystemException;

	public T updateEntity(T t) throws FFSystemException;

}
