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

/**
 * The Class OAuthAuthentication.
 */
public class OAuthAuthentication implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8025001526098038948L;
	
	/** The consumer key. */
	private String consumerKey;
	
	/** The consumer secret. */
	private String consumerSecret;
	
	/** The access token. */
	private String accessToken;
	
	/** The access token secret. */
	private String accessTokenSecret;
	
	private String oauthSessionHandle;

	/**
	 * Instantiates a new o auth authentication.
	 */
	public OAuthAuthentication() {
		super();
	}
	
	/**
	 * Instantiates a new o auth authentication.
	 * 
	 * @param consumerKey the consumer key
	 * @param consumerSecret the consumer secret
	 * @param accessToken the access token
	 * @param accessTokenSecret the access token secret
	 */
	public OAuthAuthentication(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
		super();
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.accessToken = accessToken;
		this.accessTokenSecret = accessTokenSecret;
	}



	/**
	 * Gets the consumer key.
	 * 
	 * @return the consumer key
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	/**
	 * Sets the consumer key.
	 * 
	 * @param consumerKey the new consumer key
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * Gets the consumer secret.
	 * 
	 * @return the consumer secret
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}

	/**
	 * Sets the consumer secret.
	 * 
	 * @param consumerSecret the new consumer secret
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	/**
	 * Gets the access token.
	 * 
	 * @return the access token
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Sets the access token.
	 * 
	 * @param accessToken the new access token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Gets the access token secret.
	 * 
	 * @return the access token secret
	 */
	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	/**
	 * Sets the access token secret.
	 * 
	 * @param accessTokenSecret the new access token secret
	 */
	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	/**
	 * @return the oauthSessionHandle
	 */
	public String getOauthSessionHandle() {
		return oauthSessionHandle;
	}

	/**
	 * @param oauthSessionHandle the oauthSessionHandle to set
	 */
	public void setOauthSessionHandle(String oauthSessionHandle) {
		this.oauthSessionHandle = oauthSessionHandle;
	}
}