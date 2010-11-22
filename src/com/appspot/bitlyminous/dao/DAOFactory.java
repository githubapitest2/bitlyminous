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
package com.appspot.bitlyminous.dao;

import javax.persistence.EntityManager;

import com.appspot.bitlyminous.dao.impl.DataAccessObjectImpl;



/**
 * A factory for creating DAO objects.
 */
public class DAOFactory {
	
    /**
     * Instantiates a new dAO factory.
     */
    private DAOFactory() {}

    /**
     * New instance.
     * 
     * @return the dAO factory
     */
    public static DAOFactory newInstance() {
        return new DAOFactory();
    }
    
    /**
     * Creates a new DAO object.
     * 
     * @param entityManager the entity manager
     * 
     * @return the data access object
     */
    public DataAccessObject createDataAccessObject(EntityManager entityManager) {
    	return new DataAccessObjectImpl(entityManager);
    }
}
