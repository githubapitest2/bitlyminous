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
package com.appspot.bitlyminous.constant;

/**
 * The Interface ParameterNames.
 */
public interface ParameterNames {
	
	/** The Constant COMMAND_PARAM. */
	public static final String COMMAND_PARAM = "command";	
	
	/** The Constant AUTHENTICATION_PARAM. */
	public static final String AUTHENTICATION_PARAM = "authentication";
	
	/** The Constant OAUTH_VERIFIER. */
	public static final String OAUTH_VERIFIER = "oauth_verifier";

	/** The Constant REQUEST_TOKEN_PARAM. */
	public static final String REQUEST_TOKEN_PARAM = "requestToken";
	
	/** The Constant SESSION_USER. */
	public static final String SESSION_USER = "user";
	
	/** The Constant REQUEST_TOKEN_SECRET_PARAM. */
	public static final String REQUEST_TOKEN_SECRET_PARAM = "requestTokenSecret";
	
	/** The Constant CALLBACK_PARAM. */
	public static final String CALLBACK_PARAM = "callback";
	
	/** The Constant BIT_LY_AUTH. */
	public static final String BIT_LY_AUTH = "BitlyAuth";
	
	/** The Constant TWITTER_AUTH. */
	public static final String TWITTER_AUTH = "TwitterAuth";
	
	/** The Constant DELICIOUS_AUTH. */
	public static final String DELICIOUS_AUTH = "DeliciousAuth";
	
	/** The Constant DIGG_AUTH. */
	public static final String DIGG_AUTH = "DiggAuth";
	
	/** The Constant FOURSQUARE_AUTH. */
	public static final String FOURSQUARE_AUTH = "FoursquareAuth";

	/** The Constant LOGOUT_AUTH. */
	public static final String LOGOUT_AUTH = "Logout";
	
	/** The Constant TWITTER_MAIL. */
	public static final String TWITTER_MAIL = "/_ah/mail/twitter@bitlyminous.appspotmail.com";
	
	/** The Constant BITLY_MAIL. */
	public static final String BITLY_MAIL = "/_ah/mail/bitly@bitlyminous.appspotmail.com";
	
	/** The Constant SUPPORT_MAIL. */
	public static final String SUPPORT_MAIL = "/_ah/mail/support@bitlyminous.appspotmail.com";

	/** The Constant CHECK_TWEETS_SCHED. */
	public static final String CHECK_TWEETS_SCHED = "/cron/checkTweets";
	
	/** The Constant CHECK_MENTIONS_SCHED. */
	public static final String CHECK_MENTIONS_SCHED = "/cron/checkMentions";
	
	/** The Constant SEARCH_TWEETS_SCHED. */
	public static final String SEARCH_TWEETS_SCHED = "/cron/searchTweets";
	
	/** The Constant SEND_STATISTICS_SCHED. */
	public static final String SEND_STATISTICS_SCHED = "/cron/sendStatistics";
	
	/** The Constant SEND_FF_SCHED. */
	public static final String SEND_FF_SCHED = "/cron/sendFollowFriday";
	
	/** The Constant SEND_POPULAR_LINKS_SCHED. */
	public static final String SEND_POPULAR_LINKS_SCHED = "/cron/sendPopularLinks";

	/** The Constant SEND_RELATED_TWEETS_SCHED. */
	public static final String SEND_RELATED_TWEETS_SCHED = "/cron/sendRelatedTweets";
	
	/** The Constant UPDATE_GSB_DATA_SCHED. */
	public static final String UPDATE_GSB_DATA_SCHED = "/cron/updateSafeBrowsingData";

	/** The Constant RUN_MAINTENANCE_SCHED. */
	public static final String RUN_MAINTENANCE_SCHED = "/cron/runMaintenance";
	
	/** The Constant IMPORT_FRIENDS_SCHED. */
	public static final String IMPORT_FRIENDS_SCHED = "/cron/importFriends";
	
	/** The Constant UPLOAD_GSB_DATA. */
	public static final String UPLOAD_GSB_DATA = "uploadSafeBrowsingData";
	
	/** The Constant UPLOAD_GSB_DATA_ASYNC. */
	public static final String UPLOAD_GSB_DATA_ASYNC = "/_ah/queue/uploadSafeBrowsingData";
}
