package com.pjb.sandbox.persistence.dao;

import java.io.Serializable;
import java.util.Collection;

public interface Dao<E extends Serializable, K extends Serializable> {

	E persist(E entity);
	E update(E entity);
	void delete(Long id);
	
	E getById(K key);
	Collection<E> getAll();
}
