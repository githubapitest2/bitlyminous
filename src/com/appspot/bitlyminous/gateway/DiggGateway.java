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
package com.appspot.bitlyminous.gateway;

import java.util.List;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.entity.Story;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * The Class DiggGateway.
 */
public class DiggGateway extends BaseGateway {
	
	/**
	 * Instantiates a new digg gateway.
	 * 
	 * @param consumerKey the consumer key
	 * @param consumerSecret the consumer secret
	 * @param accessToken the access token
	 * @param accessTokenSecret the access token secret
	 */
	DiggGateway(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
		super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}

	/**
	 * Gets the popular stories.
	 * 
	 * @param tag the tag
	 * @param count the count
	 * 
	 * @return the popular stories
	 */
	public List<Story> getPopularStories(String tag, int count) {
		GatewayApiUrlBuilder builder = createApiUrlBuilder(ApplicationConstants.DIGG_SEARCH_URL);
        String                apiUrl  = builder.withParameter("query", tag).withParameter("count", String.valueOf(count)).buildUrl();
        JsonObject json = parseJson(callApiGet(apiUrl)).getAsJsonObject();
        return unmarshall(new TypeToken<List<Story>>(){}, json.get("stories"));
	}
}
