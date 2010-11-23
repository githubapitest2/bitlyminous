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
import java.util.Set;
import java.util.logging.Level;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.constant.ApplicationResources;
import com.appspot.bitlyminous.gateway.GoogleSafeBrowsingGateway;
import com.rosaloves.bitlyj.Bitly;
import com.rosaloves.bitlyj.Url;

import twitter4j.Status;
import twitter4j.StatusUpdate;


/**
 * The Class ScanLinkHandler.
 */
public class ScanLinkHandler extends AbstractTwitterHandler {

	public ScanLinkHandler(TwitterContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.handler.twitter.TwitterHandler#execute(twitter4j.Status)
	 */
	@Override
	public StatusUpdate execute(Status tweet) {
		try {
			List<String> shortUrls = extractBitlyUrls(tweet.getText());
			if (!shortUrls.isEmpty()) {
				Set<Url> longUrls = getBitlyClient().call(Bitly.expand(shortUrls.toArray(new String[shortUrls.size()])));
				GoogleSafeBrowsingGateway gateway = getGoogleSafeBrowsingGateway();
				for (Url url : longUrls) {
					if (gateway.isBlacklisted(url.getLongUrl())) {
						StatusUpdate reply = new StatusUpdate(ApplicationResources.getLocalizedString("com.appspot.bitlyminous.message.badurl", new String[] {"@" + tweet.getUser().getScreenName(), trimText(tweet.getText(), 20), ApplicationConstants.GOOGLE_SAFE_BROWSING_REF_URL}));
						reply.setInReplyToStatusId(tweet.getId());
						return reply;
					}
					if (gateway.isMalwarelisted(url.getLongUrl())) {
						StatusUpdate reply = new StatusUpdate(ApplicationResources.getLocalizedString("com.appspot.bitlyminous.message.badurl", new String[] {"@" + tweet.getUser().getScreenName(), trimText(tweet.getText(), 20), ApplicationConstants.GOOGLE_SAFE_BROWSING_REF_URL}));
						reply.setInReplyToStatusId(tweet.getId());
						return reply;
					}
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while scanning urls.", e);
		}
		return null;
	}
	
	/**
	 * Gets the google safe browsing gateway.
	 * 
	 * @return the google safe browsing gateway
	 */
	protected GoogleSafeBrowsingGateway getGoogleSafeBrowsingGateway() {
		return new GoogleSafeBrowsingGateway(ApplicationConstants.GOOGLE_SAFE_BROWSING_API_KEY, true);
	}

}
