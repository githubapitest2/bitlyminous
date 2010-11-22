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

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * The Class ApplicationConstants.
 */
public final class ApplicationConstants {

    /** The Constant APP_CONSTANTS_FILE. */
    public static final String APP_CONSTANTS_FILE = "ApplicationConstants.properties";

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(ApplicationConstants.class.getCanonicalName());
    
    /** The Constant applicationConstants. */
    private static final Properties applicationConstants = new Properties();

    static {
        try {
            applicationConstants.load(
                ApplicationConstants.class.getResourceAsStream(APP_CONSTANTS_FILE));
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "An error occurred while loading properties.", e);
        }
    }
    
    /** The Constant GOOGLE_API_KEY. */
    public static final String GOOGLE_API_KEY = getProperty("com.appspot.bitlyminous.googleapi.key");
    
    /** The Constant GOOGLE_API_REFERER. */
    public static final String GOOGLE_API_REFERER = getProperty("com.appspot.bitlyminous.googleapi.referrer");
    
    /** The Constant GOOGLE_API_CSE. */
    public static final String GOOGLE_API_CSE = getProperty("com.appspot.bitlyminous.googleapi.cse");
    
    /** The Constant GOOGLE_API_TWITTER_PATTERN. */
    public static final Pattern GOOGLE_API_TWITTER_PATTERN = getPatternProperty("com.appspot.bitlyminous.googleapi.twitterPattern");
    
    /** The Constant TWITTER_SCREEN_NAME. */
    public static final String TWITTER_SCREEN_NAME = getProperty("com.appspot.bitlyminous.twitter.screenName");

    /** The Constant TWITTER_APP_NAME. */
    public static final String TWITTER_APP_NAME = getProperty("com.appspot.bitlyminous.twitter.appName");
    
    /** The Constant TWITTER_CONSUMER_KEY. */
    public static final String TWITTER_CONSUMER_KEY = getProperty("com.appspot.bitlyminous.twitter.consumerKey");
    
    /** The Constant TWITTER_CONSUMER_SECRET. */
    public static final String TWITTER_CONSUMER_SECRET = getProperty("com.appspot.bitlyminous.twitter.consumerSecret");

    /** The Constant TWITTER_ACCESS_TOKEN. */
    public static final String TWITTER_ACCESS_TOKEN = getProperty("com.appspot.bitlyminous.twitter.accessToken");
    
    /** The Constant TWITTER_ACCESS_TOKEN_SECRET. */
    public static final String TWITTER_ACCESS_TOKEN_SECRET = getProperty("com.appspot.bitlyminous.twitter.accessTokenSecret");
    
    /** The Constant TWITTER_PAGE_SIZE. */
    public static final int TWITTER_PAGE_SIZE = getIntProperty("com.appspot.bitlyminous.twitter.pageSize");
    
    /** The Constant TWITTER_BITLY_FILTER_LINKS. */
	public static final String TWITTER_BITLY_FILTER_LINKS = getProperty("com.appspot.bitlyminous.twitter.bitlyFilter");
    
    /** The Constant TWITTER_CALLBACK_URL. */
	public static final String TWITTER_CALLBACK_URL = getProperty("com.appspot.bitlyminous.twitter.callbackUrl");
	
    /** The Constant BITLY_USERNAME. */
    public static final String BITLY_USERNAME = getProperty("com.appspot.bitlyminous.bitly.username");

    /** The Constant BITLY_KEY. */
    public static final String BITLY_KEY = getProperty("com.appspot.bitlyminous.bitly.key");

    /** The Constant BITLY_APP_NAME. */
    public static final String BITLY_APP_NAME = getProperty("com.appspot.bitlyminous.bitly.appName");

    /** The Constant BITLY_CLIENT_ID. */
    public static final String BITLY_CLIENT_ID = getProperty("com.appspot.bitlyminous.bitly.clientId");

    /** The Constant BITLY_REG_EXP. */
    public static final Pattern BITLY_REG_EXP = getPatternProperty("com.appspot.bitlyminous.bitly.regExp");

    /** The Constant BITLY_SECRET. */
    public static final String BITLY_SECRET = getProperty("com.appspot.bitlyminous.bitly.secret");
    
    /** The Constant BITLY_CALLBACK_URL. */
	public static final String BITLY_CALLBACK_URL = getProperty("com.appspot.bitlyminous.bitly.callbackUrl");
    
    /** The Constant GOOGLE_SAFE_BROWSING_API_KEY. */
    public static final String GOOGLE_SAFE_BROWSING_API_KEY = getProperty("com.appspot.bitlyminous.googlesafebrowsing.key");
    
    /** The Constant DELICIOUS_CONSUMER_KEY. */
    public static final String DELICIOUS_CONSUMER_KEY = getProperty("com.appspot.bitlyminous.delicious.consumerKey");

    /** The Constant DELICIOUS_CONSUMER_SECRET. */
    public static final String DELICIOUS_CONSUMER_SECRET = getProperty("com.appspot.bitlyminous.delicious.consumerSecret");

    /** The Constant DELICIOUS_ACCESS_TOKEN. */
    public static final String DELICIOUS_ACCESS_TOKEN = getProperty("com.appspot.bitlyminous.delicious.accessToken");

    /** The Constant DELICIOUS_ACCESS_TOKEN_SECRET. */
    public static final String DELICIOUS_ACCESS_TOKEN_SECRET = getProperty("com.appspot.bitlyminous.delicious.accessTokenSecret");

    /** The Constant DELICIOUS_SUGGESTED_TAGS_URL. */
    public static final String DELICIOUS_SUGGESTED_TAGS_URL = getProperty("com.appspot.bitlyminous.delicious.suggestedTagsUrl");

    /** The Constant DELICIOUS_ADD_POST_URL. */
    public static final String DELICIOUS_ADD_POST_URL = getProperty("com.appspot.bitlyminous.delicious.addPostUrl");

    /** The Constant DELICIOUS_USER_TAGS_URL. */
    public static final String DELICIOUS_USER_TAGS_URL = getProperty("com.appspot.bitlyminous.delicious.userTagsUrl");

    /** The Constant DELICIOUS_RECENT_BY_TAG_URL. */
    public static final String DELICIOUS_RECENT_BY_TAG_URL = getProperty("com.appspot.bitlyminous.delicious.recentByTagUrl");

    /** The Constant DELICIOUS_POPULAR_BY_TAG_URL. */
    public static final String DELICIOUS_POPULAR_BY_TAG_URL = getProperty("com.appspot.bitlyminous.delicious.popularByTagUrl");
    
    /** The Constant DELICIOUS_APP_ID. */
    public static final String DELICIOUS_APP_ID = getProperty("com.appspot.bitlyminous.delicious.appId");

    /** The Constant DELICIOUS_APP_DOMAIN. */
    public static final String DELICIOUS_APP_DOMAIN = getProperty("com.appspot.bitlyminous.delicious.appDomain");
    
    /** The Constant DELICIOUS_CALLBACK_URL. */
	public static final String DELICIOUS_CALLBACK_URL = getProperty("com.appspot.bitlyminous.delicious.callbackUrl");
    
    /** The Constant DELICIOUS_REFRESH_TOKEN_URL. */
	public static final String DELICIOUS_REFRESH_TOKEN_URL = getProperty("com.appspot.bitlyminous.delicious.refreshTokenUrl");
	
    /** The Constant DELICIOUS_DATE_PATTERN. */
	public static final String DELICIOUS_DATE_PATTERN = getProperty("com.appspot.bitlyminous.delicious.datePattern");
	
    /** The Constant FOURSQUARE_APP_NAME. */
    public static final String FOURSQUARE_APP_NAME = getProperty("com.appspot.bitlyminous.foursquare.appName");
    
    /** The Constant FOURSQUARE_CONSUMER_KEY. */
    public static final String FOURSQUARE_CONSUMER_KEY = getProperty("com.appspot.bitlyminous.foursquare.consumerKey");
    
    /** The Constant FOURSQUARE_CONSUMER_SECRET. */
    public static final String FOURSQUARE_CONSUMER_SECRET = getProperty("com.appspot.bitlyminous.foursquare.consumerSecret");
    
    /** The Constant FOURSQUARE_ACCESS_TOKEN. */
    public static final String FOURSQUARE_ACCESS_TOKEN = getProperty("com.appspot.bitlyminous.foursquare.accessToken");
    
    /** The Constant FOURSQUARE_ACCESS_TOKEN_SECRET. */
    public static final String FOURSQUARE_ACCESS_TOKEN_SECRET = getProperty("com.appspot.bitlyminous.foursquare.accessTokenSecret");

    /** The Constant FOURSQUARE_NEAR_BY_TIPS_URL. */
    public static final String FOURSQUARE_NEAR_BY_TIPS_URL = getProperty("com.appspot.bitlyminous.foursquare.nearByTipsUrl");

    /** The Constant FOURSQUARE_NEAR_BY_VENUES_URL. */
    public static final String FOURSQUARE_NEAR_BY_VENUES_URL = getProperty("com.appspot.bitlyminous.foursquare.nearByVenuesUrl");
    
    /** The Constant FOURSQUARE_CALLBACK_URL. */
	public static final String FOURSQUARE_CALLBACK_URL = getProperty("com.appspot.bitlyminous.foursquare.callbackUrl");
	

    /** The Constant DIGG_APP_NAME. */
    public static final String DIGG_APP_NAME = getProperty("com.appspot.bitlyminous.digg.appName");
    
    /** The Constant DIGG_CONSUMER_KEY. */
    public static final String DIGG_CONSUMER_KEY = getProperty("com.appspot.bitlyminous.digg.consumerKey");
    
    /** The Constant DIGG_CONSUMER_SECRET. */
    public static final String DIGG_CONSUMER_SECRET = getProperty("com.appspot.bitlyminous.digg.consumerSecret");
    
    /** The Constant DIGG_ACCESS_TOKEN. */
    public static final String DIGG_ACCESS_TOKEN = getProperty("com.appspot.bitlyminous.digg.accessToken");
    
    /** The Constant DIGG_ACCESS_TOKEN_SECRET. */
    public static final String DIGG_ACCESS_TOKEN_SECRET = getProperty("com.appspot.bitlyminous.digg.accessTokenSecret");

    /** The Constant DIGG_SEARCH_URL. */
    public static final String DIGG_SEARCH_URL = getProperty("com.appspot.bitlyminous.digg.searchUrl");

    /** The Constant DIGG_CALLBACK_URL. */
	public static final String DIGG_CALLBACK_URL = getProperty("com.appspot.bitlyminous.digg.callbackUrl");
	
	
	/** The Constant TWITTER_ACCOUNT_EMAIL. */
    public static final String TWITTER_ACCOUNT_EMAIL = getProperty("com.appspot.bitlyminous.email.twitter");

    /** The Constant BITLY_ACCOUNT_EMAIL. */
    public static final String BITLY_ACCOUNT_EMAIL = getProperty("com.appspot.bitlyminous.email.bitly");
    
    /** The Constant SUPPORT_EMAIL. */
    public static final String SUPPORT_EMAIL = getProperty("com.appspot.bitlyminous.email.support");

    /** The Constant CONNECT_TIMEOUT. */
	public static final int CONNECT_TIMEOUT = getIntProperty("com.appspot.bitlyminous.gateway.connectTimeout");

    /** The Constant READ_TIMEOUT. */
	public static final int READ_TIMEOUT = getIntProperty("com.appspot.bitlyminous.gateway.readTimeout");

    /** The Constant CONTENT_ENCODING. */
	public static final String CONTENT_ENCODING = getProperty("com.appspot.bitlyminous.content.encoding");

    /**
     * Instantiates a new application constants.
     */
    private ApplicationConstants() {}

    /**
     * Gets the property.
     * 
     * @param key the key
     * 
     * @return the property
     */
    public static String getProperty(String key) {
        return applicationConstants.getProperty(key);
    }

    /**
     * Gets the formatted property.
     * 
     * @param key the key
     * @param arguments the arguments
     * 
     * @return the formatted property
     */
    public static String getFormattedProperty(String key, Object...arguments) {
        return MessageFormat.format(applicationConstants.getProperty(key), arguments);
    }
    
    /**
     * Gets the int property.
     * 
     * @param key the key
     * 
     * @return the int property
     */
    public static int getIntProperty(String key) {
        String property = applicationConstants.getProperty(key);

        if (isNullOrEmpty(property)) {
            return 0;
        } else {
            return Integer.parseInt(property);
        }
    }

    /**
     * Gets the boolean property.
     * 
     * @param key the key
     * 
     * @return the boolean property
     */
    public static boolean getBooleanProperty(String key) {
        String property = applicationConstants.getProperty(key);

        if (isNullOrEmpty(property)) {
            return false;
        } else {
            return Boolean.parseBoolean(property);
        }
    }

    /**
     * Gets the double property.
     * 
     * @param key the key
     * 
     * @return the double property
     */
    public static double getDoubleProperty(String key) {
        String property = applicationConstants.getProperty(key);

        if (isNullOrEmpty(property)) {
            return 0;
        } else {
            return Double.parseDouble(property);
        }
    }

    /**
     * Gets the long property.
     * 
     * @param key the key
     * 
     * @return the long property
     */
    public static long getLongProperty(String key) {
        String property = applicationConstants.getProperty(key);

        if (isNullOrEmpty(property)) {
            return 0;
        } else {
            return Long.parseLong(property);
        }
    }

    /**
     * Gets the pattern property.
     * 
     * @param key the key
     * 
     * @return the pattern property
     */
    public static Pattern getPatternProperty(String key) {
        String property = applicationConstants.getProperty(key);

        if (isNullOrEmpty(property)) {
            return null;
        } else {
            return Pattern.compile(property);
        }
    }
    
    /**
     * Gets the class property.
     * 
     * @param key the key
     * 
     * @return the class property
     */
    @SuppressWarnings("unchecked")
	public static <T> Class<T> getClassProperty(String key) {
        String property = applicationConstants.getProperty(key);

        if (isNullOrEmpty(property)) {
            return null;
        } else {
            try {
				return (Class<T>) Class.forName(property);
			} catch (Exception e) {
				return null;
			}
        }
    }
    
    /**
     * Gets the properties.
     * 
     * @return the properties
     */
    public static Properties getProperties() {
    	return new Properties(applicationConstants);
    }
    
    /**
     * Checks if is null or empty.
     * 
     * @param s the s
     * 
     * @return true, if is null or empty
     */
    private static boolean isNullOrEmpty(String s) {
        return ((s == null) || s.length() == 0);
    }
}
