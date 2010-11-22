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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

/**
 * The Class User.
 */
@javax.persistence.Entity
public class User implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8640005165274811415L;
	
	/**
	 * The Interface Level.
	 */
	public static interface Level {
		
		/** The Constant DIRECT_USER. */
		public static final Long DIRECT_USER = 1L;
		
		/** The Constant USER_FRIEND. */
		public static final Long USER_FRIEND = 2L;
		
		/** The Constant STRANGER. */
		public static final Long STRANGER = 3L;
	}
	
	/** The twitter id. */
	@Basic
	private String twitterId;
	
	/** The screen name. */
	@Basic
	private String screenName;
	
	/** The twitter token. */
	@Basic
	private Text twitterToken;
	
	/** The twitter secret. */
	@Basic
	private Text twitterSecret;
	
	/** The delicious token. */
	@Basic
	private Text deliciousToken;
	
	/** The delicious secret. */
	@Basic
	private Text deliciousSecret;
	
	/** The delicious session handle. */
	@Basic
	private Text deliciousSessionHandle;
	
	/** The digg token. */
	@Basic
	private Text diggToken;
	
	/** The digg secret. */
	@Basic
	private Text diggSecret;
	
	/** The level. */
	@Basic
	private Long level;
	
	/** The search engine id. */
	@Basic
	private String searchEngineId;
	
	/** The tags. */
	@Lob
	private Tags tags;
	
	/** The urls. */
	@javax.persistence.OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	private List<Url> urls = new ArrayList<Url>();
	
	/** The friendships. */
	@javax.persistence.OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	private List<Friendship> friendships = new ArrayList<Friendship>();
	
	@Basic
	private Long lastFriendFeedId = 1L;
	
	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Key id;

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
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
	
	/**
	 * Gets the twitter id.
	 * 
	 * @return the twitter id
	 */
	
	public String getTwitterId() {
		return twitterId;
	}
	
	/**
	 * Sets the twitter id.
	 * 
	 * @param twitterId the new twitter id
	 */
	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}
	
	/**
	 * Gets the screen name.
	 * 
	 * @return the screen name
	 */
	
	public String getScreenName() {
		return screenName;
	}
	
	/**
	 * Sets the screen name.
	 * 
	 * @param screenName the new screen name
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	/**
	 * Gets the twitter token.
	 * 
	 * @return the twitter token
	 */
	
	public Text getTwitterToken() {
		return twitterToken;
	}
	
	/**
	 * Sets the twitter token.
	 * 
	 * @param twitterToken the new twitter token
	 */
	public void setTwitterToken(Text twitterToken) {
		this.twitterToken = twitterToken;
	}
	
	/**
	 * Gets the twitter secret.
	 * 
	 * @return the twitter secret
	 */
	
	public Text getTwitterSecret() {
		return twitterSecret;
	}
	
	/**
	 * Sets the twitter secret.
	 * 
	 * @param twitterSecret the new twitter secret
	 */
	public void setTwitterSecret(Text twitterSecret) {
		this.twitterSecret = twitterSecret;
	}
	
	/**
	 * Gets the delicious token.
	 * 
	 * @return the delicious token
	 */
	
	public Text getDeliciousToken() {
		return deliciousToken;
	}
	
	/**
	 * Sets the delicious token.
	 * 
	 * @param deliciousToken the new delicious token
	 */
	public void setDeliciousToken(Text deliciousToken) {
		this.deliciousToken = deliciousToken;
	}
	
	/**
	 * Gets the delicious secret.
	 * 
	 * @return the delicious secret
	 */
	
	public Text getDeliciousSecret() {
		return deliciousSecret;
	}
	
	/**
	 * Sets the delicious secret.
	 * 
	 * @param deliciousSecret the new delicious secret
	 */
	public void setDeliciousSecret(Text deliciousSecret) {
		this.deliciousSecret = deliciousSecret;
	}
	
	/**
	 * Gets the level.
	 * 
	 * @return the level
	 */
	
	public Long getLevel() {
		return level;
	}
	
	/**
	 * Sets the level.
	 * 
	 * @param level the new level
	 */
	public void setLevel(Long level) {
		this.level = level;
	}
	
	/**
	 * Gets the search engine id.
	 * 
	 * @return the search engine id
	 */
	public String getSearchEngineId() {
		return searchEngineId;
	}
	
	/**
	 * Sets the search engine id.
	 * 
	 * @param searchEngineId the new search engine id
	 */
	public void setSearchEngineId(String searchEngineId) {
		this.searchEngineId = searchEngineId;
	}
	
	/**
	 * Gets the tags.
	 * 
	 * @return the tags
	 */
	public Tags getTags() {
		return tags;
	}
	
	/**
	 * Sets the tags.
	 * 
	 * @param tags the new tags
	 */
	public void setTags(Tags tags) {
		this.tags = tags;
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
	
	/**
	 * Gets the friendships.
	 * 
	 * @return the friendships
	 */
	
	public List<Friendship> getFriendships() {
		return friendships;
	}
	
	/**
	 * Sets the friendships.
	 * 
	 * @param friendships the new friendships
	 */
	public void setFriendships(List<Friendship> friendships) {
		this.friendships = friendships;
	}
	
	/**
	 * Gets the delicious session handle.
	 * 
	 * @return the delicious session handle
	 */
	public Text getDeliciousSessionHandle() {
		return deliciousSessionHandle;
	}
	
	/**
	 * Sets the delicious session handle.
	 * 
	 * @param deliciousSessionHandle the new delicious session handle
	 */
	public void setDeliciousSessionHandle(Text deliciousSessionHandle) {
		this.deliciousSessionHandle = deliciousSessionHandle;
	}
	
	/**
	 * Gets the digg token.
	 * 
	 * @return the digg token
	 */
	public Text getDiggToken() {
		return diggToken;
	}
	
	/**
	 * Sets the digg token.
	 * 
	 * @param diggToken the new digg token
	 */
	public void setDiggToken(Text diggToken) {
		this.diggToken = diggToken;
	}
	
	/**
	 * Gets the digg secret.
	 * 
	 * @return the digg secret
	 */
	public Text getDiggSecret() {
		return diggSecret;
	}
	
	/**
	 * Sets the digg secret.
	 * 
	 * @param diggSecret the new digg secret
	 */
	public void setDiggSecret(Text diggSecret) {
		this.diggSecret = diggSecret;
	}

	/**
	 * @return the lastFriendFeedId
	 */
	public Long getLastFriendFeedId() {
		return lastFriendFeedId;
	}

	/**
	 * @param lastFriendFeedId the lastFriendFeedId to set
	 */
	public void setLastFriendFeedId(Long lastFriendFeedId) {
		this.lastFriendFeedId = lastFriendFeedId;
	}
}
