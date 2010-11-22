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
import java.util.List;
import java.util.logging.Level;

import twitter4j.Status;
import twitter4j.StatusUpdate;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.entity.Url;
import com.appspot.bitlyminous.entity.User;
import com.appspot.bitlyminous.gateway.DeliciousGateway;
import com.appspot.bitlyminous.gateway.GatewayException;
import com.appspot.bitlyminous.gateway.GatewayFactory;
import com.google.appengine.api.datastore.Text;


/**
 * The Class AddUrlToDeliciousHandler.
 */
public class AddUrlToDeliciousHandler extends AbstractTwitterHandler {
	
	public AddUrlToDeliciousHandler(TwitterContext context) {
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
			User user = context.getUser();
			if (user == null) {
				user = getUserByScreenName(tweet.getUser().getScreenName());
				context.setUser(user);
			}
			if (user.getDeliciousToken() != null && user.getDeliciousSecret() != null) {
				DeliciousGateway gateway = GATEWAY_FACTORY.createDeliciousGateway(ApplicationConstants.DELICIOUS_CONSUMER_KEY,
						ApplicationConstants.DELICIOUS_CONSUMER_SECRET,
						user.getDeliciousToken().getValue(),
						user.getDeliciousSecret().getValue());
				gateway.getOAuthAuthentication().setOauthSessionHandle(user.getDeliciousSessionHandle().getValue());
				List<Url> urls = context.getUrls();
				for (Url entity : urls) {
					try {
						gateway.addUrl(entity);
					} catch (GatewayException e) {
						if (e.getErrorCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
							gateway.refreshToken(user.getDeliciousSessionHandle().getValue());
							user.setDeliciousToken(new Text(gateway.getOAuthAuthentication().getAccessToken()));
							user.setDeliciousSecret(new Text(gateway.getOAuthAuthentication().getAccessTokenSecret()));
							user.setDeliciousSessionHandle(new Text(gateway.getOAuthAuthentication().getOauthSessionHandle()));
							gateway.addUrl(entity);
						} else {
							throw e;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while adding url to delicious.", e);
		}
		return null;
	}
}
