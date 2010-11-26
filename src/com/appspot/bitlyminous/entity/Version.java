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

import com.google.appengine.api.datastore.Text;

/**
 * The Class Version.
 */
@javax.persistence.Entity
public class Version extends Entity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1212357931466224449L;
	
	/** The last search tweet id. */
	private Long lastSearchTweetId = 1L;
	
	/** The last home tweet id. */
	private Long lastHomeTweetId = 1L;
	
	/** The last mention id. */
	private Long lastMentionId = 1L;
	
	/** The blacklist major version. */
	private Long blacklistMajorVersion = 1L;
	
	/** The blacklist minor version. */
	private Long blacklistMinorVersion = -1L;
	
	/** The malware major version. */
	private Long malwareMajorVersion = 1L;
	
	/** The malware minor version. */
	private Long malwareMinorVersion = -1L;
	
	/** The delicious token. */
	private Text deliciousToken;
	
	/** The delicious secret. */
	private Text deliciousSecret;
	
	/** The delicious session handle. */
	private Text deliciousSessionHandle;
	
	/**
	 * Gets the last search tweet id.
	 * 
	 * @return the last search tweet id
	 */
	@Basic
	public Long getLastSearchTweetId() {
		return lastSearchTweetId;
	}
	
	/**
	 * Sets the last search tweet id.
	 * 
	 * @param lastSearchTweetId the new last search tweet id
	 */
	public void setLastSearchTweetId(Long lastSearchTweetId) {
		this.lastSearchTweetId = lastSearchTweetId;
	}
	
	/**
	 * Gets the last home tweet id.
	 * 
	 * @return the last home tweet id
	 */
	@Basic
	public Long getLastHomeTweetId() {
		return lastHomeTweetId;
	}
	
	/**
	 * Sets the last home tweet id.
	 * 
	 * @param lastHomeTweetId the new last home tweet id
	 */
	public void setLastHomeTweetId(Long lastHomeTweetId) {
		this.lastHomeTweetId = lastHomeTweetId;
	}
	
	/**
	 * Gets the blacklist major version.
	 * 
	 * @return the blacklist major version
	 */
	@Basic
	public Long getBlacklistMajorVersion() {
		return blacklistMajorVersion;
	}
	
	/**
	 * Sets the blacklist major version.
	 * 
	 * @param blacklistMajorVersion the new blacklist major version
	 */
	public void setBlacklistMajorVersion(Long blacklistMajorVersion) {
		this.blacklistMajorVersion = blacklistMajorVersion;
	}
	
	/**
	 * Gets the blacklist minor version.
	 * 
	 * @return the blacklist minor version
	 */
	@Basic
	public Long getBlacklistMinorVersion() {
		return blacklistMinorVersion;
	}
	
	/**
	 * Sets the blacklist minor version.
	 * 
	 * @param blacklistMinorVersion the new blacklist minor version
	 */
	public void setBlacklistMinorVersion(Long blacklistMinorVersion) {
		this.blacklistMinorVersion = blacklistMinorVersion;
	}
	
	/**
	 * Gets the malware major version.
	 * 
	 * @return the malware major version
	 */
	@Basic
	public Long getMalwareMajorVersion() {
		return malwareMajorVersion;
	}
	
	/**
	 * Sets the malware major version.
	 * 
	 * @param malwareMajorVersion the new malware major version
	 */
	public void setMalwareMajorVersion(Long malwareMajorVersion) {
		this.malwareMajorVersion = malwareMajorVersion;
	}
	
	/**
	 * Gets the malware minor version.
	 * 
	 * @return the malware minor version
	 */
	@Basic
	public Long getMalwareMinorVersion() {
		return malwareMinorVersion;
	}
	
	/**
	 * Sets the malware minor version.
	 * 
	 * @param malwareMinorVersion the new malware minor version
	 */
	public void setMalwareMinorVersion(Long malwareMinorVersion) {
		this.malwareMinorVersion = malwareMinorVersion;
	}
	
	/**
	 * Gets the last mention id.
	 * 
	 * @return the last mention id
	 */
	@Basic
	public Long getLastMentionId() {
		return lastMentionId;
	}
	
	/**
	 * Sets the last mention id.
	 * 
	 * @param lastMentionId the new last mention id
	 */
	public void setLastMentionId(Long lastMentionId) {
		this.lastMentionId = lastMentionId;
	}

	/**
	 * Gets the delicious session handle.
	 * 
	 * @return the delicious session handle
	 */
	@Basic
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
	 * Gets the delicious token.
	 * 
	 * @return the delicious token
	 */
	@Basic
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
	@Basic
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
}
