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

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import twitter4j.Status;
import twitter4j.StatusUpdate;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.entity.Tag;
import com.appspot.bitlyminous.entity.Tags;
import com.appspot.bitlyminous.entity.User;
import com.appspot.bitlyminous.entity.Version;
import com.appspot.bitlyminous.gateway.DeliciousGateway;
import com.appspot.bitlyminous.gateway.GatewayException;
import com.appspot.bitlyminous.gateway.GatewayFactory;
import com.google.appengine.api.datastore.Text;
import com.rosaloves.bitlyj.Bitly;
import com.rosaloves.bitlyj.Url;


/**
 * The Class SaveUrlHandler.
 */
public class SaveUrlHandler extends AbstractTwitterHandler {
	
	public SaveUrlHandler(TwitterContext context) {
		super(context);
	}

	/** The Constant GATEWAY_FACTORY. */
	private static final GatewayFactory GATEWAY_FACTORY = GatewayFactory.newInstance();

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.handler.twitter.TwitterHandler#execute(twitter4j.Status)
	 */
	@Override
	public StatusUpdate execute(Status tweet) {
		try {
			List<String> shortUrls = extractBitlyUrls(tweet.getText());
			if (!shortUrls.isEmpty()) {
				Set<Url> longUrls = getBitlyClient().call(Bitly.expand(shortUrls.toArray(new String[shortUrls.size()])));
				List<com.appspot.bitlyminous.entity.Url> entities = new ArrayList<com.appspot.bitlyminous.entity.Url>();
				for (Url url : longUrls) {
					com.appspot.bitlyminous.entity.Url entity = new com.appspot.bitlyminous.entity.Url();
					entity.setDescription(tweet.getText());
					entity.setUrl(url.getLongUrl());
					entity.setShortUrl(url.getShortUrl());
					entity.setDateSubmitted(tweet.getCreatedAt());
					List<String> suggestedTags = new ArrayList<String>();
					DeliciousGateway deliciousGateway = getDeliciousGateway(context.getVersion());
					try {
						suggestedTags = deliciousGateway.getSuggestedTags(url.getLongUrl());
					} catch (GatewayException e) {
						logger.log(Level.WARNING, "Seems like Delicious token expired again.", e);
						if (e.getErrorCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
							deliciousGateway.refreshToken(context.getVersion().getDeliciousSessionHandle().getValue());
							context.getVersion().setDeliciousToken(new Text(deliciousGateway.getOAuthAuthentication().getAccessToken()));
							context.getVersion().setDeliciousSecret(new Text(deliciousGateway.getOAuthAuthentication().getAccessTokenSecret()));
							context.getVersion().setDeliciousSessionHandle(new Text(deliciousGateway.getOAuthAuthentication().getOauthSessionHandle()));
							suggestedTags = deliciousGateway.getSuggestedTags(url.getLongUrl());
						} else {
							throw e;
						}
					}
					entity.setTags(suggestedTags);
					entity.setUser(context.getUser());
					updateUserTags(context.getUser(), suggestedTags);
					entities.add(entity);
				}
				context.setUrls(entities);
				updateUserUrls(context.getUser(), entities);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while persisting urls.", e);
		}
		
		return null;
	}
	
	private void updateUserUrls(User user, List<com.appspot.bitlyminous.entity.Url> entities) {
		List<com.appspot.bitlyminous.entity.Url> urls = user.getUrls();
		for (com.appspot.bitlyminous.entity.Url url: entities) {
			urls.add(url);
		}
//		user.setUrls(urls);
	}

	private void updateUserTags(User user, List<String> suggestedTags) {
		Tags tags = user.getTags();
		if (tags == null) {
			tags = new Tags();
		}
		
		for (String string : suggestedTags) {
			boolean found = false;
			for (Tag tag : tags.getTag()) {
				if (tag.getTag().equals(string)) {
					tag.setCount(tag.getCount() + 1);
					found = true;
				}
			}
			if (!found) {
				Tag tag = new Tag();
				tag.setTag(string);
				tag.setCount(1L);
				tags.getTag().add(tag);
			}
		}
		user.setTags(tags);
	}

	/**
	 * Gets the delicious gateway.
	 * 
	 * @return the delicious gateway
	 */
	protected DeliciousGateway getDeliciousGateway(Version version) {
		DeliciousGateway gateway = GATEWAY_FACTORY.createDeliciousGateway(ApplicationConstants.DELICIOUS_CONSUMER_KEY,
				ApplicationConstants.DELICIOUS_CONSUMER_SECRET,
				version.getDeliciousToken().getValue(),
				version.getDeliciousSecret().getValue());
		gateway.getOAuthAuthentication().setOauthSessionHandle(version.getDeliciousSessionHandle().getValue());
		return gateway;
	}
}
