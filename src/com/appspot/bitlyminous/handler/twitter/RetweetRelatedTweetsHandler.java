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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.http.AccessToken;
import twitter4j.http.OAuthAuthorization;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.entity.Url;
import com.appspot.bitlyminous.service.RecommendationService;
import com.appspot.bitlyminous.service.ServiceFactory;
import com.googleapis.ajax.schema.WebResult;
import com.googleapis.ajax.services.WebSearchQuery;


/**
 * The Class RetweetRelatedTweetsHandler.
 */
public class RetweetRelatedTweetsHandler extends AbstractTwitterHandler {

	/**
	 * Instantiates a new retweet related tweets handler.
	 * 
	 * @param context the context
	 */
	public RetweetRelatedTweetsHandler(TwitterContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.handler.twitter.TwitterHandler#execute(twitter4j.Status)
	 */
	@Override
	public StatusUpdate execute(Status tweet) {
		try {
			WebSearchQuery webSearchQuery = getGoogleWebSearchQuery();
			webSearchQuery.withCustomeSearchEngineId(ApplicationConstants.GOOGLE_API_CSE);
			List<Url> urls = context.getUrls();
			for (Url entity : urls) {
//				List<Tweet> relatedTweets = new ArrayList<Tweet>();
				List<String> tags = entity.getTags();
				if (!tags.isEmpty()) {
					List<WebResult> relatedTweets = webSearchQuery.withQuery(createQuery(tags)).list();
//					List<Tweet> tweets = getTweetsFromCSE(results);
//					relatedTweets.addAll(tweets);
					relatedTweets = getBestMatches(tweet, relatedTweets, 1);
					
					if (!relatedTweets.isEmpty()) {
						StatusUpdate reply = new StatusUpdate(buildStatus(tweet, relatedTweets));
						reply.setInReplyToStatusId(tweet.getId());
						return reply;
					}
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while persisting urls.", e);
		}
		
		return null;
	}


	/**
	 * Builds the status.
	 * 
	 * @param tweet the tweet
	 * @param relatedTweets the related tweets
	 * 
	 * @return the string
	 */
	private String buildStatus(Status tweet, List<WebResult> relatedTweets) {
		for (WebResult webResult : relatedTweets) {
			String user = getUserFromResult(webResult.getUnescapedUrl());
			if (user != null) {
				return "@" + tweet.getUser().getScreenName() + " RT @" + user + trimText(webResult.getContent(), 100);
			}
		}
		
		return "";
	}

	/**
	 * Gets the user from result.
	 * 
	 * @param url the url
	 * 
	 * @return the user from result
	 */
	private String getUserFromResult(String url) {
		Matcher matcher = ApplicationConstants.GOOGLE_API_TWITTER_PATTERN.matcher(url);
		if (matcher.matches() && matcher.groupCount() == 2) {
			return matcher.group(1);
		}
		return null;
	}

	/**
	 * Creates the query.
	 * 
	 * @param tags the tags
	 * 
	 * @return the string
	 */
	private String createQuery(List<String> tags) {
		StringBuilder builder = new StringBuilder();
		builder.append("http://bit.ly ");
		boolean first = true;
		for (String tag : tags) {
			if (!first) {
				builder.append(" OR ");
			}
			builder.append(tag);
			first = false;
		}
		return builder.toString();
	}

	/**
	 * Gets the best matches.
	 * 
	 * @param status the status
	 * @param relatedTweets the related tweets
	 * @param count the count
	 * 
	 * @return the best matches
	 */
//	private List<Tweet> getBestMatches(Status status, List<Tweet> relatedTweets, int count) {
//		RecommendationService recommendationService = ServiceFactory.newInstance().createRecommendationService();
//		Map<String, Tweet> tweetTexts = new HashMap<String, Tweet>();
//		for (Tweet tweet : relatedTweets) {
//			tweetTexts.put(tweet.getText(), tweet);
//		}
//		List<Map.Entry<String, Double>> textSimilarities = recommendationService.getTextSimilarities(status.getText(), new ArrayList<String>(tweetTexts.keySet()));
//		
//		List<Tweet> similarTweets = new ArrayList<Tweet>();
//		for (int i = 0; i < textSimilarities.size() && i < count; i++) {
//			similarTweets.add(tweetTexts.get(textSimilarities.get(i).getKey()));
//		}
//		return similarTweets;
//	}
	
	private List<WebResult> getBestMatches(Status status, List<WebResult> relatedTweets, int count) {
		RecommendationService recommendationService = ServiceFactory.newInstance().createRecommendationService();
		Map<String, WebResult> tweetTexts = new HashMap<String, WebResult>();
		for (WebResult tweet : relatedTweets) {
			tweetTexts.put(tweet.getContent(), tweet);
		}
		List<Map.Entry<String, Double>> textSimilarities = recommendationService.getTextSimilarities(status.getText(), new ArrayList<String>(tweetTexts.keySet()));
		
		List<WebResult> similarTweets = new ArrayList<WebResult>();
		for (int i = 0; i < textSimilarities.size() && i < count; i++) {
			similarTweets.add(tweetTexts.get(textSimilarities.get(i).getKey()));
		}
		return similarTweets;
	}
	
	/**
	 * Gets the twitter client.
	 * 
	 * @return the twitter client
	 */
	protected Twitter getTwitterClient() { 
		return new TwitterFactory().getInstance(new OAuthAuthorization(new ConfigurationBuilder().build(), ApplicationConstants.TWITTER_CONSUMER_KEY, ApplicationConstants.TWITTER_CONSUMER_SECRET, new AccessToken(ApplicationConstants.TWITTER_ACCESS_TOKEN, ApplicationConstants.TWITTER_ACCESS_TOKEN_SECRET)));
	}
}
