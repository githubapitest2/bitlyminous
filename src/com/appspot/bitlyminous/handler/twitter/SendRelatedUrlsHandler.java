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
import java.util.Map.Entry;
import java.util.logging.Level;

import twitter4j.Status;
import twitter4j.StatusUpdate;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.entity.Story;
import com.appspot.bitlyminous.entity.Url;
import com.appspot.bitlyminous.entity.Version;
import com.appspot.bitlyminous.gateway.DeliciousGateway;
import com.appspot.bitlyminous.gateway.DiggGateway;
import com.appspot.bitlyminous.gateway.GatewayFactory;
import com.appspot.bitlyminous.service.RecommendationService;
import com.appspot.bitlyminous.service.ServiceFactory;
import com.googleapis.ajax.common.PagedList;
import com.googleapis.ajax.schema.WebResult;
import com.googleapis.ajax.services.WebSearchQuery;


/**
 * The Class SendRelatedUrlsHandler.
 */
public class SendRelatedUrlsHandler extends AbstractTwitterHandler {
	
	public SendRelatedUrlsHandler(TwitterContext context) {
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
			DeliciousGateway deliciousGateway = getDeliciousGateway(context.getVersion());
			DiggGateway diggGateway = getDiggGateway();
			List<Url> urls = context.getUrls();
			for (Url entity : urls) {
				WebSearchQuery googleQuery = getGoogleWebSearchQuery();
				googleQuery.withRelatedSite(entity.getUrl());
				List<Url> relatedUrls = new ArrayList<Url>();
				List<String> tags = entity.getTags();
				for (String tag : tags) {
					try {
						relatedUrls.addAll(deliciousGateway.getPopularUrls(tag));
						relatedUrls.addAll(convertToUrls(googleQuery.withQuery(tag).list()));
						relatedUrls.addAll(convertToUrls(diggGateway.getPopularStories(tag, 5)));
					} catch (Exception e) {
						logger.log(Level.WARNING, "Error while getting related urls.", e);
					}
				}
				relatedUrls = getBestMatches(entity, relatedUrls, 2);
				
				if (!relatedUrls.isEmpty()) {
					StatusUpdate reply = new StatusUpdate("@" + tweet.getUser().getScreenName() + " " + buildStatus(relatedUrls));
					reply.setInReplyToStatusId(tweet.getId());
					return reply;
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while sending related urls.", e);
		}
		
		return null;
	}
	
	private List<Url> convertToUrls(PagedList<WebResult> results) {
		List<Url> urls = new ArrayList<Url>();
		for (WebResult result : results) {
			Url url = new Url();
			url.setDescription(result.getTitleNoFormatting());
			url.setUrl(result.getUnescapedUrl());
			
			urls.add(url);
		}
		return urls;
	}

	/**
	 * Convert to urls.
	 * 
	 * @param popularStories the popular stories
	 * 
	 * @return the list< url>
	 */
	private List<Url> convertToUrls(List<Story> popularStories) {
		List<Url> urls = new ArrayList<Url>();
		for (Story story : popularStories) {
			Url url = new Url();
			url.setDescription(story.getDescription());
			url.setUrl(story.getLink());
			
			urls.add(url);
		}
		return urls;
	}

	/**
	 * Builds the status.
	 * 
	 * @param relatedUrls the related urls
	 * 
	 * @return the string
	 */
	private String buildStatus(List<Url> relatedUrls) {
		StringBuilder builder = new StringBuilder();
		for (Url url : relatedUrls) {
			builder.append(trimText(url.getDescription(), 100));
			builder.append("...");
			builder.append(shortenUrl(url.getUrl()));
			break;
		}
		return builder.toString();
	}

	/**
	 * Gets the best matches.
	 * 
	 * @param url the url
	 * @param relatedUrls the related urls
	 * @param count the count
	 * 
	 * @return the best matches
	 */
	private List<Url> getBestMatches(Url url, List<Url> relatedUrls, int count) {
		RecommendationService recommendationService = ServiceFactory.newInstance().createRecommendationService();
		List<Entry<Url, Double>> urlSimilarities = recommendationService.getUrlSimilarities(url, relatedUrls);

		List<Url> similarUrls = new ArrayList<Url>();
		for (int i = 0; i < urlSimilarities.size() && i < count; i++) {
			similarUrls.add(urlSimilarities.get(i).getKey());
		}
		return similarUrls;
	}

	/**
	 * Gets the delicious gateway.
	 * @param version 
	 * 
	 * @return the delicious gateway
	 */
	protected DeliciousGateway getDeliciousGateway(Version version) {
		return GATEWAY_FACTORY.createDeliciousGateway(ApplicationConstants.DELICIOUS_CONSUMER_KEY,
				ApplicationConstants.DELICIOUS_CONSUMER_SECRET,
				version.getDeliciousToken().getValue(),
				version.getDeliciousSecret().getValue());
	}
	
	/**
	 * Gets the digg gateway.
	 * 
	 * @return the digg gateway
	 */
	protected DiggGateway getDiggGateway() {
		return GATEWAY_FACTORY.createDiggGateway(ApplicationConstants.DIGG_CONSUMER_KEY,
				ApplicationConstants.DIGG_CONSUMER_SECRET,
				ApplicationConstants.DIGG_ACCESS_TOKEN,
				ApplicationConstants.DIGG_ACCESS_TOKEN_SECRET);
	}
}
