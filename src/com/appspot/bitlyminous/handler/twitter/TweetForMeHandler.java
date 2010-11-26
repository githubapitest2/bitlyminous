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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.http.AccessToken;
import twitter4j.http.OAuthAuthorization;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.constant.ApplicationResources;
import com.appspot.bitlyminous.entity.Story;
import com.appspot.bitlyminous.entity.Tag;
import com.appspot.bitlyminous.entity.Tags;
import com.appspot.bitlyminous.entity.Url;
import com.appspot.bitlyminous.entity.User;
import com.appspot.bitlyminous.gateway.DeliciousGateway;
import com.appspot.bitlyminous.gateway.DiggGateway;
import com.appspot.bitlyminous.gateway.GatewayFactory;
import com.appspot.bitlyminous.service.RecommendationService;
import com.appspot.bitlyminous.service.ServiceFactory;
import com.googleapis.ajax.common.PagedList;
import com.googleapis.ajax.schema.WebResult;
import com.googleapis.ajax.services.WebSearchQuery;


/**
 * The Class TweetForMeHandler.
 */
public class TweetForMeHandler extends AbstractTwitterHandler {
	
	/** The Constant GATEWAY_FACTORY. */
	private static final GatewayFactory GATEWAY_FACTORY = GatewayFactory.newInstance();
	

	/**
	 * Instantiates a new tweet for me handler.
	 * 
	 * @param commandName the command name
	 * @param context the context
	 */
	public TweetForMeHandler(String commandName, TwitterContext context) {
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
			if (user == null || user.getTwitterToken() == null || user.getTwitterSecret() == null) {
				StatusUpdate reply = new StatusUpdate(ApplicationResources.getLocalizedString("com.appspot.bitlyminous.message.notAuthenticated", new String[] {"@" + tweet.getUser().getScreenName(), "Twitter", shortenUrl(ApplicationConstants.TWITTER_CALLBACK_URL)}));
				reply.setInReplyToStatusId(tweet.getId());
				return reply;
			} else {
				Twitter twitter = getTwitterClient(user.getTwitterToken().getValue(), user.getTwitterSecret().getValue());
				String status = buildStatus(user.getUrls(), user.getTags());
				if (status != null) {
					StatusUpdate reply = new StatusUpdate(status);
					reply.setInReplyToStatusId(tweet.getId());
					twitter.updateStatus(reply);
				}
				return null;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while tweeting on behalf of user.", e);
		} 
		return null;
	}

	/**
	 * Builds the status.
	 * 
	 * @param urls the urls
	 * @param tags the tags
	 * 
	 * @return the string
	 */
	private String buildStatus(List<Url> urls, Tags tags) {
		if (tags != null) {
			DeliciousGateway deliciousGateway = getDeliciousGateway();
			DiggGateway diggGateway = getDiggGateway();
			urls = topUrls(urls, 3);
			List<Tag> tagList = topTags(tags.getTag(), 3);
			for (Url entity : urls) {
				WebSearchQuery googleQuery = getGoogleWebSearchQuery();
				googleQuery.withRelatedSite(entity.getUrl());
				List<Url> relatedUrls = new ArrayList<Url>();
				if (!tagList.isEmpty()) {
					for (Tag tag : tagList) {
						try {
							relatedUrls.addAll(deliciousGateway.getPopularUrls(tag.getTag()));
							relatedUrls.addAll(convertToUrls(diggGateway.getPopularStories(tag.getTag(), 5)));
						} catch (Exception e) {
							logger.log(Level.WARNING, "Error while getting related urls.", e);
						}
					}
					relatedUrls.addAll(convertToUrls(googleQuery.withQuery(createQuery(tagList)).list()));
					relatedUrls = getBestMatches(entity, relatedUrls, 1);
				}
				
				if (!relatedUrls.isEmpty()) {
					StringBuilder builder = new StringBuilder();
					builder.append(trimText(relatedUrls.get(0).getDescription(), 100));
					builder.append("...");
					builder.append(shortenUrl(relatedUrls.get(0).getUrl()));
					return builder.toString();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Top tags.
	 * 
	 * @param tags the tags
	 * @param count the count
	 * 
	 * @return the list< tag>
	 */
	private List<Tag> topTags(List<Tag> tags, int count) {
		Collections.sort(tags, new Comparator<Tag>() {
			@Override
			public int compare(Tag o1, Tag o2) {
				return o2.getCount().compareTo(o1.getCount());
			}
		});
		
		List<Tag> retValue = new ArrayList<Tag>();
		for (int i = 0; i < tags.size() && i < count; i++) {
			retValue.add(tags.get(i));
		}
		
		return retValue;
	}

	/**
	 * Top urls.
	 * 
	 * @param urls the urls
	 * @param count the count
	 * 
	 * @return the list< url>
	 */
	private List<Url> topUrls(List<Url> urls, int count) {
		Collections.sort(urls, new Comparator<Url>() {
			@Override
			public int compare(Url o1, Url o2) {
				return o2.getDateSubmitted().compareTo(o1.getDateSubmitted());
			}
		});
		
		List<Url> retValue = new ArrayList<Url>();
		for (int i = 0; i < urls.size() && i < count; i++) {
			retValue.add(urls.get(i));
		}
		
		return retValue;
	}

	/**
	 * Gets the twitter client.
	 * 
	 * @param token the token
	 * @param tokenSecret the token secret
	 * 
	 * @return the twitter client
	 */
	protected Twitter getTwitterClient(String token, String tokenSecret) { 
		return new TwitterFactory().getInstance(new OAuthAuthorization(new ConfigurationBuilder().build(), ApplicationConstants.TWITTER_CONSUMER_KEY, ApplicationConstants.TWITTER_CONSUMER_SECRET, new AccessToken(token, tokenSecret)));
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
	 * 
	 * @return the delicious gateway
	 */
	protected DeliciousGateway getDeliciousGateway() {
		return GATEWAY_FACTORY.createDeliciousGateway(ApplicationConstants.DELICIOUS_CONSUMER_KEY,
				ApplicationConstants.DELICIOUS_CONSUMER_SECRET,
				"dummy-access-token",
				"dummy-token-secret");
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
	
	/**
	 * Convert to urls.
	 * 
	 * @param results the results
	 * 
	 * @return the list< url>
	 */
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
	 * Creates the query.
	 * 
	 * @param tags the tags
	 * 
	 * @return the string
	 */
	private String createQuery(List<Tag> tags) {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (Tag tag : tags) {
			if (!first) {
				builder.append(" OR ");
			}
			builder.append(tag.getTag());
			first = false;
		}
		return builder.toString();
	}
}
