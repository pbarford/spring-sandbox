package com.pjb.sandbox.persistence.dao;

import java.io.Serializable;
import java.util.Collection;

public interface Dao<T extends Serializable> {

	T persist(T entity);
	T update(T entity);
	void delete(Long id);
	
	T getById(Long id);
	Collection<T> getAll();
}
