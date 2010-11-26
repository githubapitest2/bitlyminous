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

import java.util.List;
import java.util.logging.Level;

import twitter4j.Status;
import twitter4j.StatusUpdate;

import com.appspot.bitlyminous.entity.Url;
import com.appspot.bitlyminous.entity.User;


/**
 * The Class AddUrlToGoogleCSEHandler.
 */
public class AddUrlToGoogleCSEHandler extends AbstractTwitterHandler {

	/**
	 * Instantiates a new adds the url to google cse handler.
	 * 
	 * @param context the context
	 */
	public AddUrlToGoogleCSEHandler(TwitterContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.handler.twitter.TwitterHandler#execute(twitter4j.Status)
	 */
	@Override
	public StatusUpdate execute(Status tweet) {
		try {
			User user = context.getUser();
			if (user == null) {
				user = getUserByScreenName(tweet.getUser().getScreenName());
				context.setUser(user);
			}
			if (user.getSearchEngineId() != null) {
				List<Url> urls = context.getUrls();
				for (Url entity : urls) {
					// TODO-Later: Add to custom search engine.
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while adding url to delicious.", e);
		} 
		return null;
	}
}
