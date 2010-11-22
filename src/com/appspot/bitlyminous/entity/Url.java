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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.ManyToOne;

import com.google.gson.annotations.SerializedName;

/**
 * The Class Url.
 */
@javax.persistence.Entity
public class Url extends Bean {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8977184509144514674L;
	
	/** The user. */
	private User user;
	
	/** The url. */
	@SerializedName("u")
	private String url;
	
	/** The description. */
	@SerializedName("d")
	private String description;
	
	/** The tags. */
	@SerializedName("t")
	private List<String> tags = new ArrayList<String>();
	
	/** The short url. */
	private String shortUrl;
	
	/** The date submitted. */
	@SerializedName("dt")
	private Date dateSubmitted;
	
	/** The category. */
	private String category;
	
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
	 * Gets the url.
	 * 
	 * @return the url
	 */
	@Basic
	public String getUrl() {
		return url;
	}
	
	/**
	 * Sets the url.
	 * 
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	@Basic
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 * 
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the tags.
	 * 
	 * @return the tags
	 */
	@Basic
	public List<String> getTags() {
		return tags;
	}
	
	/**
	 * Sets the tags.
	 * 
	 * @param tags the new tags
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	/**
	 * Gets the short url.
	 * 
	 * @return the short url
	 */
	@Basic
	public String getShortUrl() {
		return shortUrl;
	}
	
	/**
	 * Sets the short url.
	 * 
	 * @param shortUrl the new short url
	 */
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	
	@Basic
	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	/**
	 * @return the category
	 */
	@Basic
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Url [description=" + description + ", shortUrl=" + shortUrl
				+ ", tags=" + tags + ", url=" + url + ", user=" + user + "]";
	}
}
