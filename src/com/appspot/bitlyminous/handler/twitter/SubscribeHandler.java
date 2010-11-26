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

import com.appspot.bitlyminous.entity.Tag;
import com.appspot.bitlyminous.entity.Tags;
import com.appspot.bitlyminous.entity.User;


/**
 * The Class SubscribeHandler.
 */
public class SubscribeHandler extends AbstractTwitterHandler {

	/**
	 * Instantiates a new subscribe handler.
	 * 
	 * @param commandName the command name
	 * @param context the context
	 */
	public SubscribeHandler(String commandName, TwitterContext context) {
		super(commandName, context);
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
			if (user != null) {
				if (user.getTags() == null) {
					user.setTags(new Tags());
				}
				
				List<Tag> tags = user.getTags().getTag();
				String[] mentionTags = getMentionText(tweet).split("\\s+");
				for (String tag : mentionTags) {
					if (!isExisting(tags, tag)) {
						Tag obj = new Tag();
						obj.setTag(tag);
						obj.setCount(5L);
						tags.add(obj);
					}
				}
				user.setTags(user.getTags());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while subscribing tags.", e);
		}
		return null;
	}

	/**
	 * Checks if is existing.
	 * 
	 * @param tags the tags
	 * @param newTag the new tag
	 * 
	 * @return true, if is existing
	 */
	protected boolean isExisting(List<Tag> tags, String newTag) {
		for (Tag tag : tags) {
			if (tag.getTag().equals(newTag)) {
				return true;				
			}
		}
		return false;
	}
}
