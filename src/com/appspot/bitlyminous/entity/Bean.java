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
package com.appspot.bitlyminous.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.appengine.api.datastore.Key;

/**
 * The Class Bean.
 */
@javax.persistence.Entity
@MappedSuperclass
public abstract class Bean implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -841557860141558703L;
	
	/** The id. */
	private Key id;

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	@Id
	public Key getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the new id
	 */
	public void setId(Key id) {
		this.id = id;
	}
}
