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
package com.appspot.bitlyminous.command.scheduled;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.appspot.bitlyminous.dao.EMF;
import com.appspot.bitlyminous.entity.Version;
import com.appspot.bitlyminous.service.ServiceFactory;





/**
 * The Class AbstractScheduledCommand.
 */
public abstract class AbstractScheduledCommand implements ScheduledCommand {
	
	/** The logger. */
	protected final Logger logger = Logger.getLogger(getClass().getCanonicalName());
	
	/** The SERVIC e_ factory. */
	protected final ServiceFactory SERVICE_FACTORY = ServiceFactory.newInstance(); 
	
	/**
	 * Creates the entity manager.
	 * 
	 * @return the entity manager
	 */
	protected EntityManager createEntityManager() {
		return EMF.get().createEntityManager();		
	}
	
	/**
	 * Gets the current version.
	 * 
	 * @return the current version
	 */
	protected Version getCurrentVersion() {
		EntityManager entityManager = createEntityManager();
		Version version = new Version();
		try {
			Query query = entityManager.createQuery("select v from Version v");
			version = (Version) query.getSingleResult();
			if (version == null) {
				version = new Version();			
			}
		} catch (NoResultException e) {}
		
		entityManager.close();
		return version;
	}
}
