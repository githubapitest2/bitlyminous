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

import java.util.logging.Level;

import twitter4j.Status;
import twitter4j.StatusUpdate;

import com.appspot.bitlyminous.entity.User;


/**
 * The Class SaveUserHandler.
 */
public class SaveUserHandler extends AbstractTwitterHandler {

	public SaveUserHandler(TwitterContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.handler.twitter.TwitterHandler#execute(twitter4j.Status)
	 */
	@Override
	public StatusUpdate execute(Status tweet) {
		try {
			User user = getUserByScreenName(tweet.getUser().getScreenName());
			if (user == null) {
				user = new User();
				user.setScreenName(tweet.getUser().getScreenName());
				user.setLevel(User.Level.DIRECT_USER);
			}
			context.setUser(user);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while persisting urls.", e);
		}
		
		return null;
	}

}
