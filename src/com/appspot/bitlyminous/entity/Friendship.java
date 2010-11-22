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

import javax.persistence.Basic;
import javax.persistence.ManyToOne;


/**
 * The Class Friendship.
 */
@javax.persistence.Entity
public class Friendship extends Bean {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8804819770056590028L;
	
	/**
	 * The Interface Status.
	 */
	public static interface Status {
		
		/** The Constant FRIEND. */
		public static final Long FRIEND = 1L;
		
		/** The Constant FOLLOWER. */
		public static final Long FOLLOWER = 2L;
		
		/** The Constant BI_DIRECTIONAL. */
		public static final Long BI_DIRECTIONAL = 3L;
		
		/** The Constant RECOMMENDED. */
		public static final Long RECOMMENDED = 4L;
	}
	
	/** The user. */
	private User user;
	
	/** The friend. */
	private User friend;
	
	/** The status. */
	private Long status;
	
	/** The similarity. */
	private Double similarity;
	
	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	@ManyToOne
	public User getUser() {
		return user;
	}
	
	/**
	 * Sets the user.
	 * 
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Gets the friend.
	 * 
	 * @return the friend
	 */
	public User getFriend() {
		return friend;
	}
	
	/**
	 * Sets the friend.
	 * 
	 * @param friend the new friend
	 */
	public void setFriend(User friend) {
		this.friend = friend;
	}
	
	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	@Basic
	public Long getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 * 
	 * @param status the new status
	 */
	public void setStatus(Long status) {
		this.status = status;
	}
	
	/**
	 * Gets the similarity.
	 * 
	 * @return the similarity
	 */
	@Basic
	public Double getSimilarity() {
		return similarity;
	}
	
	/**
	 * Sets the similarity.
	 * 
	 * @param similarity the new similarity
	 */
	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}
}
