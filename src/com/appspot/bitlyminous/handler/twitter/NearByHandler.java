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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.Place;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.http.AccessToken;
import twitter4j.http.OAuthAuthorization;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.constant.ApplicationResources;
import com.appspot.bitlyminous.entity.Tip;
import com.appspot.bitlyminous.entity.Venue;
import com.appspot.bitlyminous.gateway.FoursquareGateway;
import com.appspot.bitlyminous.gateway.GatewayFactory;
import com.appspot.bitlyminous.gateway.GoogleStaticMapsGateway;
import com.appspot.bitlyminous.gateway.MapMarker;


/**
 * The Class NearByHandler.
 */
public class NearByHandler extends AbstractTwitterHandler {
	
	/**
	 * Instantiates a new near by handler.
	 * 
	 * @param commandName the command name
	 * @param context the context
	 */
	public NearByHandler(String commandName, TwitterContext context) {
		super(commandName, context);
	}

	/** The Constant GATEWAY_FACTORY. */
	private static final GatewayFactory GATEWAY_FACTORY = GatewayFactory.newInstance();

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.handler.twitter.TwitterHandler#execute(twitter4j.Status)
	 */
	@Override
	public StatusUpdate execute(Status tweet) {
		GeoLocation geoLocation = tweet.getGeoLocation();
		String text = getMentionText(tweet);
		if (geoLocation == null) {
			StatusUpdate reply = new StatusUpdate(ApplicationResources.getLocalizedString("com.appspot.bitlyminous.message.noLocation", new String[] {"@" + tweet.getUser().getScreenName()}));
			reply.setInReplyToStatusId(tweet.getId());
			return reply;
		} else {
			try {
				GeoQuery query = new GeoQuery(geoLocation);
				List<Tip> nearbyTips = getFoursquareGateway().getNearbyTips(new com.appspot.bitlyminous.entity.GeoLocation(geoLocation.getLatitude(), geoLocation.getLongitude()), 1);
				String status = buildTipsStatus(nearbyTips);
				
				if (nearbyTips.isEmpty()) {
					List<Venue> nearbyVenues = new ArrayList<Venue>();
					if (text != null) {
						nearbyVenues = getFoursquareGateway().getNearbyVenues(new com.appspot.bitlyminous.entity.GeoLocation(geoLocation.getLatitude(), geoLocation.getLongitude()), text, 1);
					}
					status = status + buildVenuesStatus(nearbyVenues);
					if (nearbyVenues.isEmpty()) {
						List<Place> nearbyPlaces = getTwitterClient().getNearbyPlaces(query);
						status = status + buildPlacesStatus(nearbyPlaces);
					}
					
				}
				status = "@" + tweet.getUser().getScreenName() + " " + status;
				StatusUpdate reply = new StatusUpdate(status);
				reply.setInReplyToStatusId(tweet.getId());
				return reply;
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error while getting nearby places", e);
			}
			return null;
		}
	}
	
	/**
	 * Builds the tips status.
	 * 
	 * @param nearbyTips the nearby tips
	 * 
	 * @return the string
	 */
	private String buildTipsStatus(List<Tip> nearbyTips) {
		StringBuilder builder = new StringBuilder();
		GoogleStaticMapsGateway googleStaticMapsGateway = getGoogleStaticMapsGateway();
		for (Tip tip : nearbyTips) {
			builder.append(trimText(tip.getText(), 100));
			builder.append("...");
			builder.append(shortenUrl(googleStaticMapsGateway.getMap(tip.getVenue().getGeolat(), tip.getVenue().getGeolong(), new MapMarker(tip.getVenue().getGeolat(), tip.getVenue().getGeolong(),
					MapMarker.MarkerColor.blue, 'a'))));
			builder.append(".");
			break;
		}
		return builder.toString();
	}

	/**
	 * Builds the venues status.
	 * 
	 * @param nearbyVenues the nearby venues
	 * 
	 * @return the string
	 */
	private String buildVenuesStatus(List<Venue> nearbyVenues) {
		StringBuilder builder = new StringBuilder();
		GoogleStaticMapsGateway googleStaticMapsGateway = getGoogleStaticMapsGateway();
		for (Venue venue : nearbyVenues) {
			builder.append(venue.getName());
			builder.append(" ");
			builder.append(venue.getAddress());
			builder.append(".");
			builder.append(shortenUrl(googleStaticMapsGateway.getMap(venue.getGeolat(), venue.getGeolong(), new MapMarker(venue.getGeolat(), venue.getGeolong(),
					MapMarker.MarkerColor.blue, 'a'))));
			builder.append(".");
			break;
		}
		return builder.toString();
	}
	
	/**
	 * Builds the places status.
	 * 
	 * @param nearbyPlaces the nearby places
	 * 
	 * @return the string
	 */
	private String buildPlacesStatus(List<Place> nearbyPlaces) {
		StringBuilder builder = new StringBuilder();
//		GoogleStaticMapsGateway googleStaticMapsGateway = getGoogleStaticMapsGateway();
		for (Place place : nearbyPlaces) {
			builder.append(place.getFullName());
			builder.append(" ");
			builder.append(place.getStreetAddress());
			builder.append(".");
			// TODO-Later. Fix the bounding box problem.
//			builder.append(shortenUrl(googleStaticMapsGateway.getMap(place.getGeolat(), place.getGeolong())));
			builder.append(".");
			break;
		}
		return builder.toString();
	}
	
	/**
	 * Gets the foursquare gateway.
	 * 
	 * @return the foursquare gateway
	 */
	protected FoursquareGateway getFoursquareGateway() {
		return GATEWAY_FACTORY.createFoursquareGateway(ApplicationConstants.FOURSQUARE_CONSUMER_KEY,
				ApplicationConstants.FOURSQUARE_CONSUMER_SECRET,
				ApplicationConstants.FOURSQUARE_ACCESS_TOKEN,
				ApplicationConstants.FOURSQUARE_ACCESS_TOKEN_SECRET);
	}
	
	/**
	 * Gets the twitter client.
	 * 
	 * @return the twitter client
	 */
	protected Twitter getTwitterClient() { 
		return new TwitterFactory().getInstance(new OAuthAuthorization(new ConfigurationBuilder().build(), ApplicationConstants.TWITTER_CONSUMER_KEY, ApplicationConstants.TWITTER_CONSUMER_SECRET, new AccessToken(ApplicationConstants.TWITTER_ACCESS_TOKEN, ApplicationConstants.TWITTER_ACCESS_TOKEN_SECRET)));
	}
	
	/**
	 * Gets the google static maps gateway.
	 * 
	 * @return the google static maps gateway
	 */
	protected GoogleStaticMapsGateway getGoogleStaticMapsGateway() {
		return new GoogleStaticMapsGateway(ApplicationConstants.GOOGLE_API_KEY);
	}
	
}
