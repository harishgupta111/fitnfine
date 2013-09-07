package com.fitnfine.dao;

import java.io.Serializable;
import java.util.Set;

import com.fitnfine.exception.FFSystemException;
import com.fitnfine.model.BaseEntity;

interface IBaseDao<T extends BaseEntity, P extends Serializable> {

	public T createEntity(T t) throws FFSystemException;

	public Set<T> findAll() throws FFSystemException;

	public T findById(P id) throws FFSystemException;

	public T updateEntity(T t) throws FFSystemException;
}
