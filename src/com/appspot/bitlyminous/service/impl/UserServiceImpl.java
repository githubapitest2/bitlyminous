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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.appspot.bitlyminous.dao.NamedQueries;
import com.appspot.bitlyminous.entity.Url;
import com.appspot.bitlyminous.entity.User;
import com.appspot.bitlyminous.service.UserService;

/**
 * The Class UserServiceImpl.
 */
public class UserServiceImpl extends BaseService implements UserService {
	
	/**
	 * Instantiates a new user service impl.
	 */
	public UserServiceImpl() {
		super("user-service");
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.UserService#getUserByScreenName(java.lang.String)
	 */
	public User getUserByScreenName(String screenName) {
		User user = (User) memcache.get(screenName);
		if (user != null) {
			return user;
		} else {
			EntityManager entityManager = createEntityManager();
			try {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("screenName", screenName);
				user = getDao(entityManager).getSingleResult(User.class, NamedQueries.FIND_USER_BY_SCREEN_NAME, parameters);
				if (user != null) {
					populateUser(user);
					memcache.put(user.getScreenName(), user);
				}
				return user;
			} finally {
				closeEntityManager(entityManager);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.UserService#getSimilarUsers(com.appspot.bitlyminous.entity.User, int)
	 */
	public List<User> getSimilarUsers(User user, int count) { 
		EntityManager entityManager = createEntityManager();
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("user", user);
			return getDao(entityManager).getResultList(User.class, NamedQueries.FIND_SIMILAR_USERS, parameters, count);
		} finally {
			closeEntityManager(entityManager);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.UserService#getSimilarUsers(com.appspot.bitlyminous.entity.User, int)
	 */
	public List<User> getAllAuthenticatedUsers() { 
		EntityManager entityManager = createEntityManager();
		try {
			return getDao(entityManager).getResultList(User.class, NamedQueries.FIND_AUTHENTICATED_USERS);
		} finally {
			closeEntityManager(entityManager);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.UserService#updateUser(com.appspot.bitlyminous.entity.User)
	 */
	public void updateUser(User user) {
		EntityManager entityManager = createEntityManager();
		try {
			if (user.getId() == null) {
				getDao(entityManager).saveOrUpdate(user);
			} else {
				getDao(entityManager).merge(user);
			}
			memcache.delete(user.getScreenName());
		} finally {
			closeEntityManager(entityManager);
		}
	}
	
	/**
	 * Populate user.
	 * 
	 * @param user the user
	 */
	private void populateUser(User user) {
		if (user.getTags() != null) {
			user.getTags().getTag().size();
		}
		if (user.getUrls() != null) {
			for (Url url : user.getUrls()) {
				url.getDescription();
			}
		}
	}
}
