package com.pjb.sandbox.persistence.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public abstract class AbstractDao<E extends Serializable, K extends Serializable> implements Dao<E, K> {

	private Class<E> genericType;

	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
		this.genericType = ((Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected Class<E> getGenericType() {
		return genericType;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public E persist(E entity) {	
		getEntityManager().persist(entity);
		return entity;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public E update(E entity) {	
		return getEntityManager().merge(entity);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void delete(K id) {
		E entity = getById(id);		
		getEntityManager().remove(entity);
	}
	
	@SuppressWarnings("unchecked")
	public E querySingle(String sql, Object ...params) {
		
		Query q = getEntityManager().createQuery(sql);
		//TypedQuery<E> q = getEntityManager().createQuery(sql, getGenericType());
		for(int i=0; i < params.length; i++) {
			q.setParameter(i+1, params[i]);
		}
		try {
			return (E) q.getSingleResult();
		}
		catch(NoResultException nrex) {
			return null;
		}		
	}

	@SuppressWarnings("unchecked")
	public Collection<E> query(String sql, Object ...params) {
		
		Query q = getEntityManager().createQuery(sql);
		
		//TypedQuery<E> q = getEntityManager().createQuery(sql, getGenericType());
		
		for(int i=0; i < params.length; i++) {
			q.setParameter(i+1, params[i]);
		}
		return q.getResultList();		
	}
	
	@SuppressWarnings("unchecked")
	public Collection<E> query(String sql) {
		
		Query q = getEntityManager().createQuery(sql);		
		return q.getResultList();		
	}
	
	public E getById(K id) {
		return getEntityManager().find(getGenericType(), id);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<E> getAll() {
		return getEntityManager().createQuery("from " + getGenericType().getSimpleName()).getResultList();
	}

}
