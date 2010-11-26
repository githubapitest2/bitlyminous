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

import java.util.HashMap;
import java.util.Map;

import com.appspot.bitlyminous.constant.ApplicationConstants;

import twitter4j.Status;


/**
 * A factory for creating TwitterHandler objects.
 */
public class TwitterHandlerFactory {
	
	/** The context. */
	private TwitterContext context;
	
    /**
     * Instantiates a new twitter handler factory.
     * 
     * @param context the context
     */
    private TwitterHandlerFactory(TwitterContext context) {
    	this.context = context;
		HANDLERS.put("subscribe", new SubscribeHandler("subscribe", context));
		HANDLERS.put("nearby", new NearByHandler("nearby", context));
		HANDLERS.put("mine", new MineLinksHandler("mine", context));
		HANDLERS.put("tweetforme", new TweetForMeHandler("tweetforme", context));
    }

	/** The HANDLERS. */
	private final Map<String, TwitterHandler> HANDLERS = new HashMap<String, TwitterHandler>();

    /**
     * New instance.
     * 
     * @param context the context
     * 
     * @return the twitter handler factory
     */
    public static TwitterHandlerFactory newInstance(TwitterContext context) {
        return new TwitterHandlerFactory(context);
    }

    /**
     * Creates a new TwitterHandler object.
     * 
     * @param tweet the tweet
     * 
     * @return the twitter handler
     */
    public TwitterHandler createCommand(Status tweet) {
    	String action = tweet.getText().replaceAll("@" + ApplicationConstants.TWITTER_SCREEN_NAME, "").trim().split("\\s+")[0];
		if (HANDLERS.containsKey(action)) {
			return HANDLERS.get(action);
		}
		return new SearchHandler(context);
    }
}
