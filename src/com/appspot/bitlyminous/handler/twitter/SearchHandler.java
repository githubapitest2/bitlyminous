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

import twitter4j.Status;
import twitter4j.StatusUpdate;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.googleapis.ajax.common.PagedList;
import com.googleapis.ajax.schema.WebResult;
import com.googleapis.ajax.services.GoogleSearchQueryFactory;
import com.googleapis.ajax.services.WebSearchQuery;
import com.rosaloves.bitlyj.Bitly;

/**
 * The Class SearchHandler.
 */
public class SearchHandler extends AbstractTwitterHandler {

	/**
	 * Instantiates a new search handler.
	 * 
	 * @param context the context
	 */
	public SearchHandler(TwitterContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.handler.twitter.TwitterHandler#execute(twitter4j.Status)
	 */
	@Override
	public StatusUpdate execute(Status tweet) {
		GoogleSearchQueryFactory factory = GoogleSearchQueryFactory.newInstance(ApplicationConstants.GOOGLE_API_KEY);
		WebSearchQuery query = factory.newWebSearchQuery();
		query.setReferrer(ApplicationConstants.GOOGLE_API_REFERER);
		PagedList<WebResult> response = query.withQuery(getMentionText(tweet)).list();
		if (response.isEmpty()) {
			return null;
		} else {
			StatusUpdate update = new StatusUpdate("@" + tweet.getUser().getScreenName() + " " + buildStatus(response.get(0)));
			update.setInReplyToStatusId(tweet.getId());
			return update;
		}
	}

	/**
	 * Builds the status.
	 * 
	 * @param webResult the web result
	 * 
	 * @return the string
	 */
	private String buildStatus(WebResult webResult) {
		StringBuilder builder = new StringBuilder();
		String title = webResult.getTitleNoFormatting();
		title = trimText(title, 100) + " ";
		builder.append(title);
		builder.append(shortenUrl(webResult.getUnescapedUrl()));
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.handler.twitter.AbstractTwitterHandler#shortenUrl(java.lang.String)
	 */
	protected String shortenUrl(String longUrl) {
		return getBitlyClient().call(Bitly.shorten(longUrl)).getShortUrl();
	}
}
