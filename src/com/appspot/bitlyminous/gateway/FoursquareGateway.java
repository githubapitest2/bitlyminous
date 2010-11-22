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

import java.util.ArrayList;
import java.util.List;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.entity.GeoLocation;
import com.appspot.bitlyminous.entity.Tip;
import com.appspot.bitlyminous.entity.Venue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * The Class FoursquareGateway.
 */
public class FoursquareGateway extends BaseGateway {
	
	/**
	 * Instantiates a new foursquare gateway.
	 * 
	 * @param consumerKey the consumer key
	 * @param consumerSecret the consumer secret
	 * @param accessToken the access token
	 * @param accessTokenSecret the access token secret
	 */
	FoursquareGateway(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
		super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}

	/**
	 * Gets the nearby tips.
	 * 
	 * @param location the location
	 * @param limit the limit
	 * 
	 * @return the nearby tips
	 */
	public List<Tip> getNearbyTips(GeoLocation location, int limit) {
		GatewayApiUrlBuilder builder = createApiUrlBuilder(ApplicationConstants.FOURSQUARE_NEAR_BY_TIPS_URL);
        String                apiUrl  = builder.withParameter("geolat", String.valueOf(location.getLatitude())).withParameter("geolong", String.valueOf(location.getLongitude())).withParameter("l", String.valueOf(limit)).withParameter("filter", "nearby").buildUrl();
        JsonObject json = parseJson(callApiGet(apiUrl)).getAsJsonObject();
        return unmarshall(new TypeToken<List<Tip>>(){}, json.get("tips"));
	}
	
	/**
	 * Gets the nearby venues.
	 * 
	 * @param location the location
	 * @param query the query
	 * @param limit the limit
	 * 
	 * @return the nearby venues
	 */
	public List<Venue> getNearbyVenues(GeoLocation location, String query, int limit) {
		GatewayApiUrlBuilder builder = createApiUrlBuilder(ApplicationConstants.FOURSQUARE_NEAR_BY_VENUES_URL);
        String                apiUrl  = builder.withParameter("geolat", String.valueOf(location.getLatitude())).withParameter("geolong", String.valueOf(location.getLongitude())).withParameter("l", String.valueOf(limit)).withParameter("q", query).buildUrl();
        JsonObject json = parseJson(callApiGet(apiUrl)).getAsJsonObject();
        JsonArray groups = json.get("groups").getAsJsonArray();
        if (groups.iterator().hasNext()) {
            return unmarshall(new TypeToken<List<Venue>>(){}, groups.iterator().next().getAsJsonObject().get("venues"));
        } else {
        	return new ArrayList<Venue>();
        }
	}
}
