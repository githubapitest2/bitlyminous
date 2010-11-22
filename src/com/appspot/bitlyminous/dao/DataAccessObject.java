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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The Interface DataAccessObject.
 */
public interface DataAccessObject {
    
    /**
     * Load all.
     * 
     * @param clazz the clazz
     * 
     * @return the list< t>
     */
    public <T> List<T> loadAll(Class<T> clazz);

    /**
     * Load top.
     * 
     * @param clazz the clazz
     * @param iNumRows the i num rows
     * @param sPropertyName the s property name
     * @param bWithMaxValues the b with max values
     * 
     * @return the list< t>
     */
    public <T> List<T> loadTop(Class<T> clazz, int iNumRows, String sPropertyName, boolean bWithMaxValues);

    /**
     * Load.
     * 
     * @param clazz the clazz
     * @param id the id
     * 
     * @return the t
     */
    public <T> T load(Class<T> clazz, Serializable id);

    /**
     * Creates the reference.
     * 
     * @param clazz the clazz
     * @param id the id
     * 
     * @return the t
     */
    public <T> T createReference(Class<T> clazz, Serializable id);

    /**
     * Save or update.
     * 
     * @param entity the entity
     */
    public <T> void saveOrUpdate(T entity);

    /**
     * Save or update all.
     * 
     * @param entities the entities
     */
    public void saveOrUpdateAll(Collection<?> entities);

    /**
     * Merge.
     * 
     * @param entity the entity
     * 
     * @return the t
     */
    public <T> T merge(T entity);

    /**
     * Delete.
     * 
     * @param entity the entity
     */
    public <T> void delete(T entity);

    /**
     * Delete all.
     * 
     * @param collection the collection
     */
    public void deleteAll(Collection<?> collection);

    /**
     * Run as connected.
     * 
     * @param callback the callback
     * 
     * @return the t
     */
    public <T> T runAsConnected(ConnectedModeCallback<T> callback);

    /**
     * Lookup.
     * 
     * @param clazz the clazz
     * @param id the id
     * 
     * @return the t
     */
    public <T> T lookup(Class<T> clazz, Serializable id);

    /**
     * Refresh.
     * 
     * @param entity the entity
     */
    public <T> void refresh(T entity);

    /**
     * Attach.
     * 
     * @param entity the entity
     */
    public <T> void attach(T entity);

    /**
     * Flush.
     */
    public void flush();
    
    /**
     * Execute update.
     * 
     * @param queryName the query name
     * @param parameters the parameters
     * 
     * @return the int
     */
    public int executeUpdate(String queryName, Map<String, Object> parameters);
    
    /**
     * Gets the single result.
     * 
     * @param clazz the clazz
     * @param queryName the query name
     * 
     * @return the single result
     */
    public <T> T getSingleResult(Class<T> clazz, String queryName);
    
    /**
     * Gets the result list.
     * 
     * @param clazz the clazz
     * @param queryName the query name
     * 
     * @return the result list
     */
    public <T> List<T> getResultList(Class<T> clazz, String queryName);
    
    /**
     * Gets the result list.
     * 
     * @param clazz the clazz
     * @param queryName the query name
     * @param maxRows the max rows
     * 
     * @return the result list
     */
    public <T> List<T> getResultList(Class<T> clazz, String queryName, int maxRows);
    
    /**
     * Gets the single result.
     * 
     * @param clazz the clazz
     * @param queryName the query name
     * @param parameters the parameters
     * 
     * @return the single result
     */
    public <T> T getSingleResult(Class<T> clazz, String queryName, Map<String, Object> parameters);
    
    /**
     * Gets the result list.
     * 
     * @param clazz the clazz
     * @param queryName the query name
     * @param parameters the parameters
     * 
     * @return the result list
     */
    public <T> List<T> getResultList(Class<T> clazz, String queryName, Map<String, Object> parameters);

    /**
     * Gets the result list.
     * 
     * @param clazz the clazz
     * @param queryName the query name
     * @param parameters the parameters
     * @param maxRows the max rows
     * 
     * @return the result list
     */
    public <T> List<T> getResultList(Class<T> clazz, String queryName, Map<String, Object> parameters, int maxRows);
}
