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
package com.appspot.bitlyminous.handler.twitter;

import java.util.ArrayList;
import java.util.List;

import com.appspot.bitlyminous.entity.Url;
import com.appspot.bitlyminous.entity.User;
import com.appspot.bitlyminous.entity.Version;

/**
 * The Class TwitterContext.
 */
public class TwitterContext {
	
	/** The user. */
	private User user;
	
	/** The version. */
	private Version version;
	
	/** The urls. */
	private List<Url> urls = new ArrayList<Url>();
	
	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
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
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public Version getVersion() {
		return version;
	}
	
	/**
	 * Sets the version.
	 * 
	 * @param version the new version
	 */
	public void setVersion(Version version) {
		this.version = version;
	}
	
	/**
	 * Gets the urls.
	 * 
	 * @return the urls
	 */
	public List<Url> getUrls() {
		return urls;
	}
	
	/**
	 * Sets the urls.
	 * 
	 * @param urls the new urls
	 */
	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}
}
