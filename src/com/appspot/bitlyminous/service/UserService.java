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
package com.appspot.bitlyminous.service;

import java.util.List;

import com.appspot.bitlyminous.entity.User;

/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * Gets the user by screen name.
	 * 
	 * @param screenName the screen name
	 * 
	 * @return the user by screen name
	 */
	public User getUserByScreenName(String screenName);

	/**
	 * Gets the similar users.
	 * 
	 * @param user the user
	 * @param count the count
	 * 
	 * @return the similar users
	 */
	public List<User> getSimilarUsers(User user, int count);

	/**
	 * Update user.
	 * 
	 * @param user the user
	 */
	public void updateUser(User user);
	
	/**
	 * Gets the all authenticated users.
	 * 
	 * @return the all authenticated users
	 */
	public List<User> getAllAuthenticatedUsers();

}
