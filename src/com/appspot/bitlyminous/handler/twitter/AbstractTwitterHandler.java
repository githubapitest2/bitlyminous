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

import static com.rosaloves.bitlyj.Bitly.as;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import twitter4j.Status;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.entity.User;
import com.appspot.bitlyminous.service.ServiceFactory;
import com.googleapis.ajax.services.GoogleSearchQueryFactory;
import com.googleapis.ajax.services.WebSearchQuery;
import com.rosaloves.bitlyj.Bitly;
import com.rosaloves.bitlyj.Bitly.Provider;





/**
 * The Class AbstractTwitterHandler.
 */
public abstract class AbstractTwitterHandler implements TwitterHandler {
	
	/** The logger. */
	protected final Logger logger = Logger.getLogger(getClass().getCanonicalName());
	
	/** The context. */
	protected TwitterContext context;
	
	/** The command name. */
	protected String commandName;
	
	
	/**
	 * Instantiates a new abstract twitter handler.
	 * 
	 * @param context the context
	 */
	public AbstractTwitterHandler(TwitterContext context) {
		this.context = context;
	}
	
	/**
	 * Instantiates a new abstract twitter handler.
	 * 
	 * @param commandName the command name
	 * @param context the context
	 */
	public AbstractTwitterHandler(String commandName, TwitterContext context) {
		this.commandName = commandName;
		this.context = context;
	}
	
	/**
	 * Gets the bitly client.
	 * 
	 * @return the bitly client
	 */
	protected Provider getBitlyClient() {
		return as(ApplicationConstants.BITLY_USERNAME, ApplicationConstants.BITLY_KEY);
	}
	
	/**
	 * Gets the mention text.
	 * 
	 * @param tweet the tweet
	 * 
	 * @return the mention text
	 */
	protected String getMentionText(Status tweet) {
		String text = tweet.getText().replaceAll("@" + ApplicationConstants.TWITTER_SCREEN_NAME, "");
		if (commandName != null) {
			text = text.replaceFirst(commandName, "");
		}
		return text;
	}
	
	/**
	 * Extract bitly urls.
	 * 
	 * @param text the text
	 * 
	 * @return the list< string>
	 */
	protected List<String> extractBitlyUrls(String text) {
		List<String> urls = new ArrayList<String>();
		Matcher matcher = ApplicationConstants.BITLY_REG_EXP.matcher(text);
		while (matcher.find()) {
			urls.add(matcher.group(1));
		}
		return urls;
	}
	
	/**
	 * Shorten url.
	 * 
	 * @param longUrl the long url
	 * 
	 * @return the string
	 */
	protected String shortenUrl(String longUrl) {
		return getBitlyClient().call(Bitly.shorten(longUrl)).getShortUrl();
	}
	
	/**
	 * Gets the google web search query.
	 * 
	 * @return the google web search query
	 */
	protected WebSearchQuery getGoogleWebSearchQuery() {
		GoogleSearchQueryFactory factory = GoogleSearchQueryFactory.newInstance(ApplicationConstants.GOOGLE_API_KEY);
		return factory.newWebSearchQuery();
	}
	
	/**
	 * Gets the user by screen name.
	 * 
	 * @param screenName the screen name
	 * 
	 * @return the user by screen name
	 */
	protected User getUserByScreenName(String screenName) {
		ServiceFactory factory = ServiceFactory.newInstance();
		return factory.createUserService().getUserByScreenName(screenName);
	}
	
	/**
	 * Trim text.
	 * 
	 * @param original the original
	 * @param length the length
	 * 
	 * @return the string
	 */
	protected String trimText(String original, int length) {
		if (original == null) {
			return null;
		} else if (original.length() <= length) {
			return original;
		} else {
			return original.substring(0, length) + "...";
		}
	}
	
	/**
	 * Checks if is empty.
	 * 
	 * @param text the text
	 * 
	 * @return true, if is empty
	 */
	protected boolean isEmpty(String text) {
		return (text == null || text.trim().length() == 0);
	}
}
