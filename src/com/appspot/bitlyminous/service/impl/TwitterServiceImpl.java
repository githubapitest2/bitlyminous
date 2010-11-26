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
package com.appspot.bitlyminous.service.impl;

import static com.rosaloves.bitlyj.Bitly.as;
import static com.rosaloves.bitlyj.Bitly.clicks;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.http.AccessToken;
import twitter4j.http.Authorization;
import twitter4j.http.OAuthAuthorization;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.constant.ApplicationResources;
import com.appspot.bitlyminous.entity.Url;
import com.appspot.bitlyminous.entity.Version;
import com.appspot.bitlyminous.handler.twitter.AddUrlToDeliciousHandler;
import com.appspot.bitlyminous.handler.twitter.AddUrlToGoogleCSEHandler;
import com.appspot.bitlyminous.handler.twitter.RetweetRelatedTweetsHandler;
import com.appspot.bitlyminous.handler.twitter.SaveUrlHandler;
import com.appspot.bitlyminous.handler.twitter.SaveUserHandler;
import com.appspot.bitlyminous.handler.twitter.ScanLinkHandler;
import com.appspot.bitlyminous.handler.twitter.SendRelatedUrlsHandler;
import com.appspot.bitlyminous.handler.twitter.TwitterContext;
import com.appspot.bitlyminous.handler.twitter.TwitterHandler;
import com.appspot.bitlyminous.handler.twitter.TwitterHandlerFactory;
import com.appspot.bitlyminous.service.ServiceException;
import com.appspot.bitlyminous.service.ServiceFactory;
import com.appspot.bitlyminous.service.TwitterService;
import com.appspot.bitlyminous.service.UserService;
import com.rosaloves.bitlyj.UrlClicks;
import com.rosaloves.bitlyj.Bitly.Provider;

/**
 * The Class TwitterServiceImpl.
 */
public class TwitterServiceImpl extends BaseService implements TwitterService {
	
	/**
	 * Instantiates a new twitter service impl.
	 */
	public TwitterServiceImpl() {
		super("twitter-service");
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.TwitterService#handleMentions()
	 */
	public void handleMentions() {
		try {
			Twitter twitter = getTwitterClient();
			TwitterHandlerFactory factory = TwitterHandlerFactory.newInstance(new TwitterContext());
			Version version = getCurrentVersion();
			ResponseList<Status> homeTimeline = twitter.getMentions(new Paging(getCurrentVersion().getLastMentionId()));
			long lastMentionId = 0L;
			for (Status tweet : homeTimeline) {
				if (tweet.getId() > version.getLastMentionId()) {
					if (tweet.getId() > lastMentionId) {
						lastMentionId = tweet.getId();
					}
					TwitterHandler command = factory.createCommand(tweet);
					StatusUpdate reply = command.execute(tweet);
					if (reply != null) {
						twitter.updateStatus(reply);
					}
				}
			}
			if (lastMentionId > version.getLastMentionId()) {
				version.setLastMentionId(lastMentionId);
				EntityManager entityManager = createEntityManager();
				entityManager.persist(version);
				closeEntityManager(entityManager);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.TwitterService#handleFriendsTimeline()
	 */
	public void handleFriendsTimeline() {
		try {
			Version version = getCurrentVersion();
			Twitter twitter = getTwitterClient();
			List<Status> homeTimeline = twitter.getFriendsTimeline(new Paging(version.getLastHomeTweetId()));
			long lastTweetId = 0L;
			for (Status status : homeTimeline) {
				if (status.getId() > lastTweetId) {
					lastTweetId = status.getId();
				}
				if (!ApplicationConstants.TWITTER_SCREEN_NAME.equalsIgnoreCase(status.getUser().getScreenName())) {
					TwitterContext context = new TwitterContext();
					context.setVersion(version);
					for(TwitterHandler command : getTimelineHandlers(context)) {
						StatusUpdate reply = command.execute(status);
						if (reply != null) {
							twitter.updateStatus(reply);
						}
					}
					saveUser(context.getUser());
				}
			}
			if (lastTweetId > version.getLastHomeTweetId()) {
				version.setLastHomeTweetId(lastTweetId);
				saveVersion(version);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.TwitterService#searchPublicTimeline()
	 */
	public void searchPublicTimeline() {
		try {
			UserService userService = ServiceFactory.newInstance().createUserService();
			for (com.appspot.bitlyminous.entity.User user : userService.getAllAuthenticatedUsers()) {
				Twitter twitter = getTwitterClient(user.getTwitterToken().getValue(), user.getTwitterSecret().getValue());
				List<Status> Status = twitter.getFriendsTimeline();
				long lastTweetId = 0L;
				for (Status tweet : Status) {
					if (tweet.getId() > lastTweetId) {
						lastTweetId = tweet.getId();
					}
					ScanLinkHandler handler = new ScanLinkHandler(null);
					handler.execute(tweet);
					// if the tweet is from a friend or a friend of friend check for malware 
					// if the tweet is from a friend show related urls and tweets.
					// if the tweet is from a friend add it to the database against user to compute statistics and suggest people.
					// if the tweet is from a friend add to dig if he has signed up.
//						IDs retweetedByIDs = twitter.getRetweetedByIDs(tweet.getId());
				}
				if (lastTweetId > user.getLastFriendFeedId()) {
					user.setLastFriendFeedId(lastTweetId);
					saveUser(user);
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.TwitterService#sendUrlStatistics()
	 */
	public void sendUrlStatistics() {
		try {
			Twitter twitter = getTwitterClient();
			UserService userService = ServiceFactory.newInstance().createUserService();
			for (User user : twitter.getFriendsStatuses()) {
				com.appspot.bitlyminous.entity.User entity = userService.getUserByScreenName(user.getScreenName());
				List<Url> urls = entity.getUrls();
				twitter.sendDirectMessage(user.getId(), buildUrlStatisticsStatus(urls));
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.TwitterService#sendFollowFridays()
	 */
	public void sendFollowFridays() {
		try {
			Twitter twitter = getTwitterClient();
			UserService userService = ServiceFactory.newInstance().createUserService();
			for (User user : twitter.getFriendsStatuses()) {
				com.appspot.bitlyminous.entity.User entity = userService.getUserByScreenName(user.getScreenName());
				List<com.appspot.bitlyminous.entity.User> similarUsers = userService.getSimilarUsers(entity, 5);
				if (!similarUsers.isEmpty()) {
					twitter.updateStatus(buildFollowFridayStatus(entity, similarUsers));
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.TwitterService#sendPopularLinks()
	 */
	public void sendPopularLinks() {
		try {
			Twitter twitter = getTwitterClient();
			UserService userService = ServiceFactory.newInstance().createUserService();
			for (User user : twitter.getFriendsStatuses()) {
				com.appspot.bitlyminous.entity.User entity = userService.getUserByScreenName(user.getScreenName());
				// TODO-NM: Find popular links based on user tags.
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.TwitterService#sendRelatedTweets()
	 */
	public void sendRelatedTweets() {
		// TODO-NM: Implement this method.
	}
	
	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.TwitterService#importFriends()
	 */
	public void importFriends() {
		try {
			Twitter twitter = getTwitterClient();
			UserService userService = ServiceFactory.newInstance().createUserService();
            boolean exists = true;
            long cursor = -1;
            
            while(exists) {
            	//return 100 follower with status per request
            	PagableResponseList<User> twitterFollowers = twitter.getFollowersStatuses(cursor);

    			for (User user : twitterFollowers) {
    				com.appspot.bitlyminous.entity.User entity = userService.getUserByScreenName(user.getScreenName());
    				if (entity == null) {
    					entity = new com.appspot.bitlyminous.entity.User();
    					entity.setScreenName(user.getScreenName());
    					entity.setLevel(com.appspot.bitlyminous.entity.User.Level.DIRECT_USER);
    					userService.updateUser(entity);
    					twitter.createFriendship(user.getScreenName());
    					twitter.sendDirectMessage(user.getScreenName(), ApplicationResources.getLocalizedString("com.appspot.bitlyminous.message.welcome"));
    					// following fofs will hit the limit soon.
//    					IDs friendsIDs = twitter.getFriendsIDs(user.getId());
//    					for (int id : friendsIDs.getIDs()) {
//    						twitter.createFriendship(id);
//    					}
    				} else if (entity.getLevel() != com.appspot.bitlyminous.entity.User.Level.DIRECT_USER) {
    					entity.setLevel(com.appspot.bitlyminous.entity.User.Level.DIRECT_USER);
    					userService.updateUser(entity);
    					twitter.createFriendship(user.getScreenName());
    					twitter.sendDirectMessage(user.getScreenName(), ApplicationResources.getLocalizedString("com.appspot.bitlyminous.message.welcome"));
    					// following fofs will hit the limit soon.
//    					IDs friendsIDs = twitter.getFriendsIDs(user.getId());
//    					for (int id : friendsIDs.getIDs()) {
//    						twitter.createFriendship(id);
//    					}
    				}
    			}
    			
                exists = twitterFollowers.hasNext();
                cursor = twitterFollowers.getNextCursor();
            }
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Builds the url statistics status.
	 * 
	 * @param urls the urls
	 * 
	 * @return the string
	 */
	private String buildUrlStatisticsStatus(List<Url> urls) {
		Provider bitly = getBitlyClient();
		StringBuilder builder = new StringBuilder();
		for (Url url : urls) {
			UrlClicks clicks = bitly.call(clicks(url.getShortUrl()));			
			builder.append(" ");
			builder.append(url.getShortUrl());
			builder.append("[");
			builder.append(clicks.getUserClicks());
			builder.append(":");
			builder.append(clicks.getGlobalClicks());
			builder.append("]");
		}
		
		return ApplicationResources.getLocalizedString("com.appspot.bitlyminous.message.sendstatistics", new String[] {builder.toString()});
	}
	
	/**
	 * Builds the follow friday status.
	 * 
	 * @param user the user
	 * @param similarUsers the similar users
	 * 
	 * @return the string
	 */
	private String buildFollowFridayStatus(
			com.appspot.bitlyminous.entity.User user, List<com.appspot.bitlyminous.entity.User> similarUsers) {
		StringBuilder builder = new StringBuilder();
		builder.append("@");
		builder.append(user.getScreenName());
		for (com.appspot.bitlyminous.entity.User similarUser : similarUsers) {
			builder.append(" @");
			builder.append(similarUser.getScreenName());
		}
		return builder.toString();
	}

	/**
	 * Gets the authorization.
	 * 
	 * @return the authorization
	 */
	protected Authorization getAuthorization() {
		return getAuthorization(ApplicationConstants.TWITTER_ACCESS_TOKEN, ApplicationConstants.TWITTER_ACCESS_TOKEN_SECRET);
	}
	
	/**
	 * Gets the authorization.
	 * 
	 * @param token the token
	 * @param tokenSecret the token secret
	 * 
	 * @return the authorization
	 */
	protected Authorization getAuthorization(String token, String tokenSecret) {
		Configuration configuration = new ConfigurationBuilder().build(); 
		return new OAuthAuthorization(configuration, ApplicationConstants.TWITTER_CONSUMER_KEY, ApplicationConstants.TWITTER_CONSUMER_SECRET, new AccessToken(token, tokenSecret));
	}
	
	/**
	 * Gets the twitter client.
	 * 
	 * @return the twitter client
	 */
	protected Twitter getTwitterClient() { 
		return new TwitterFactory().getInstance(getAuthorization());
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
		return new TwitterFactory().getInstance(getAuthorization(token, tokenSecret));
	}
	
	/**
	 * Gets the bitly client.
	 * 
	 * @return the bitly client
	 */
	protected Provider getBitlyClient() {
		return as(ApplicationConstants.BITLY_USERNAME, ApplicationConstants.BITLY_KEY);
	}
	
	/**
	 * Gets the timeline handlers.
	 * 
	 * @param context the context
	 * 
	 * @return the timeline handlers
	 */
	protected List<TwitterHandler> getTimelineHandlers(TwitterContext context) {
		List<TwitterHandler> timelineHandlers = new ArrayList<TwitterHandler>();
		timelineHandlers.add(new SaveUserHandler(context));		
		timelineHandlers.add(new ScanLinkHandler(context));
		timelineHandlers.add(new SaveUrlHandler(context));
		timelineHandlers.add(new SendRelatedUrlsHandler(context));
		timelineHandlers.add(new RetweetRelatedTweetsHandler(context));
		timelineHandlers.add(new AddUrlToDeliciousHandler(context));
		timelineHandlers.add(new AddUrlToGoogleCSEHandler(context));
		return timelineHandlers;
	}
	
	/**
	 * Save version.
	 * 
	 * @param version the version
	 */
	private void saveVersion(Version version) {
		EntityManager entityManager = createEntityManager();
		try {
			getDao(entityManager).saveOrUpdate(version);
		} finally {
			closeEntityManager(entityManager);
		}
	}

	/**
	 * Save user.
	 * 
	 * @param user the user
	 */
	private void saveUser(com.appspot.bitlyminous.entity.User user) {
		UserService userService = ServiceFactory.newInstance().createUserService();
		userService.updateUser(user);
	}
}
