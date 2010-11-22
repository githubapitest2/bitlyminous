/*
 * Copyright 2010 Nabeel Mukhtar 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * 
 */
package com.appspot.bitlyminous.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.appspot.bitlyminous.dao.ConnectedModeCallback;
import com.appspot.bitlyminous.dao.DataAccessObject;

/**
 * The Class DataAccessObjectImpl.
 */
public class DataAccessObjectImpl implements DataAccessObject {
	
	/** The logger. */
	protected final Logger logger = Logger.getLogger(getClass().getCanonicalName());	
	
	/** The entity manager. */
	protected EntityManager entityManager;
	
	/**
	 * Instantiates a new data access object impl.
	 * 
	 * @param entityManager the entity manager
	 */
	public DataAccessObjectImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#attach(java.lang.Object)
	 */
	@Override
	public <T> void attach(T entity) {
		entityManager.merge(entity);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#createReference(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <T> T createReference(Class<T> clazz, Serializable id) {
		return entityManager.getReference(clazz, id);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#delete(java.lang.Object)
	 */
	@Override
	public <T> void delete(T entity) {
		entityManager.remove(entity);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#deleteAll(java.util.Collection)
	 */
	@Override
	public void deleteAll(Collection<?> collection) {
		for (Object entity : collection) {
			entityManager.remove(entity);
		}
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#flush()
	 */
	@Override
	public void flush() {
		entityManager.flush();
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#load(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <T> T load(Class<T> clazz, Serializable id) {
		return entityManager.find(clazz, id);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#loadAll(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> loadAll(Class<T> clazz) {
		try {
			Query query = entityManager.createQuery("select d from " + clazz.getSimpleName() + " d");
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#loadTop(java.lang.Class, int, java.lang.String, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> loadTop(Class<T> clazz, int numRows,
			String propertyName, boolean withMaxValues) {
		try {
			Query query = entityManager.createQuery("select d from " + clazz.getSimpleName() + " d order by " + propertyName + ((withMaxValues)? " desc" : "asc"));
			query.setMaxResults(numRows);
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#lookup(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <T> T lookup(Class<T> clazz, Serializable id) {
		try {
			return entityManager.find(clazz, id);
		} catch (NoResultException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#merge(java.lang.Object)
	 */
	@Override
	public <T> T merge(T entity) {
		return entityManager.merge(entity);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#refresh(java.lang.Object)
	 */
	@Override
	public <T> void refresh(T entity) {
		entityManager.refresh(entity);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#runAsConnected(com.appspot.bitlyminous.dao.ConnectedModeCallback)
	 */
	@Override
	public <T> T runAsConnected(ConnectedModeCallback<T> callback) {
		return callback.run();
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public <T> void saveOrUpdate(T entity) {
		entityManager.persist(entity);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#saveOrUpdateAll(java.util.Collection)
	 */
	@Override
	public void saveOrUpdateAll(Collection<?> entities) {
		for (Object entity : entities) {
			entityManager.persist(entity);
		}
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#executeUpdate(java.lang.String, java.util.Map)
	 */
	@Override
	public int executeUpdate(String queryName, Map<String, Object> parameters) {
		Query query = entityManager.createNamedQuery(queryName);
		for (String name : parameters.keySet()) {
			query.setParameter(name, parameters.get(name));
		}
		return query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#getResultList(java.lang.Class, java.lang.String, java.util.Map)
	 */
	@Override
	public <T> List<T> getResultList(Class<T> clazz,
			String queryName, Map<String, Object> parameters) {
		return getResultList(clazz, queryName, parameters, -1);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.dao.DataAccessObject#getSingleResult(java.lang.Class, java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSingleResult(Class<T> clazz,
			String queryName, Map<String, Object> parameters) {
		try {
			Query query = entityManager.createNamedQuery(queryName);
			for (String name : parameters.keySet()) {
				query.setParameter(name, parameters.get(name));
			}
			return (T) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
    /* (non-Javadoc)
     * @see com.appspot.bitlyminous.dao.DataAccessObject#getSingleResult(java.lang.Class, java.lang.String)
     */
    public <T> T getSingleResult(Class<T> clazz, String queryName) {
    	Map<String, Object> parameters = Collections.emptyMap();
    	return getSingleResult(clazz, queryName, parameters);
    }
    
    /* (non-Javadoc)
     * @see com.appspot.bitlyminous.dao.DataAccessObject#getResultList(java.lang.Class, java.lang.String)
     */
    public <T> List<T> getResultList(Class<T> clazz, String queryName) {
    	Map<String, Object> parameters = Collections.emptyMap();
    	return getResultList(clazz, queryName, parameters);
    }
    
    /* (non-Javadoc)
     * @see com.appspot.bitlyminous.dao.DataAccessObject#getResultList(java.lang.Class, java.lang.String, int)
     */
    public <T> List<T> getResultList(Class<T> clazz, String queryName, int maxRows) {    
    	Map<String, Object> parameters = Collections.emptyMap();
    	return getResultList(clazz, queryName, parameters, maxRows);
    }
	
    /* (non-Javadoc)
     * @see com.appspot.bitlyminous.dao.DataAccessObject#getResultList(java.lang.Class, java.lang.String, java.util.Map, int)
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> getResultList(Class<T> clazz, String queryName, Map<String, Object> parameters, int maxRows) {
		try {
			Query query = entityManager.createNamedQuery(queryName);
			for (String name : parameters.keySet()) {
				query.setParameter(name, parameters.get(name));
			}
			if (maxRows > -1) {
				query.setMaxResults(maxRows);
			}
			return query.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
    }
}
