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
package com.appspot.bitlyminous.service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import com.appspot.bitlyminous.dao.DAOFactory;
import com.appspot.bitlyminous.dao.DataAccessObject;
import com.appspot.bitlyminous.dao.EMF;
import com.appspot.bitlyminous.dao.NamedQueries;
import com.appspot.bitlyminous.entity.Version;

/**
 * The Class BaseService.
 */
public abstract class BaseService {
	
	/** The Constant DAO_FACTORY. */
	protected static final DAOFactory DAO_FACTORY = DAOFactory.newInstance();
	
	/** The logger. */
	protected final Logger logger = Logger.getLogger(getClass().getCanonicalName());	

	/**
	 * Creates the entity manager.
	 * 
	 * @return the entity manager
	 */
	protected EntityManager createEntityManager() {
		return EMF.get().createEntityManager();		
	}
	
	/**
	 * Close entity manager.
	 * 
	 * @param entityManager the entity manager
	 */
	protected void closeEntityManager(EntityManager entityManager) {
		if (entityManager != null) {
			try {
				entityManager.close();
			} catch (Exception e) {
				logger.log(Level.WARNING, "Error while closing entity manager.", e);
			}
		}
	}
	
	/**
	 * Gets the dao.
	 * 
	 * @param entityManager the entity manager
	 * 
	 * @return the dao
	 */
	protected DataAccessObject getDao(EntityManager entityManager) {
		return DAO_FACTORY.createDataAccessObject(entityManager);
	}
	
	/**
	 * Gets the current version.
	 * 
	 * @return the current version
	 */
	protected Version getCurrentVersion() {
		EntityManager entityManager = createEntityManager();
		try {
			Version version = getDao(entityManager).getSingleResult(Version.class, NamedQueries.FIND_CURRENT_VERSION);
			if (version == null) {
				version = new Version();			
			}
			return version;
		} finally {
			closeEntityManager(entityManager);
		}
	}
}
