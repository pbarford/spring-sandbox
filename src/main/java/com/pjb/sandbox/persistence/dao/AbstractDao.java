package com.pjb.sandbox.persistence.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public abstract class AbstractDao<T extends Serializable> implements Dao<T> {

	private Class<T> genericType;
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
		this.genericType = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected Class<T> getGenericType() {
		return genericType;
	}

	protected void setGenericType(Class<T> genericType) {
		this.genericType = genericType;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public T persist(T entity) {	
		getEntityManager().persist(entity);
		return entity;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public T update(T entity) {	
		return getEntityManager().merge(entity);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void delete(Long id) {
		T entity = getById(id);
		getEntityManager().remove(entity);
	}
	
	public T getById(Long id) {
		return getEntityManager().find(getGenericType(), id);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<T> getAll() {
		return getEntityManager().createQuery("from " + getGenericType().getSimpleName()).getResultList();
	}

}
