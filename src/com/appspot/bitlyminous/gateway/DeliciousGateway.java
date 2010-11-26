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
package com.appspot.bitlyminous.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import oauth.signpost.OAuth;
import oauth.signpost.http.HttpParameters;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.entity.OAuthAuthentication;
import com.appspot.bitlyminous.entity.Suggest;
import com.appspot.bitlyminous.entity.Tag;
import com.appspot.bitlyminous.entity.Tags;
import com.appspot.bitlyminous.entity.Url;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * The Class DeliciousGateway.
 */
public class DeliciousGateway extends BaseGateway {
	
	/**
	 * Instantiates a new delicious gateway.
	 * 
	 * @param consumerKey the consumer key
	 * @param consumerSecret the consumer secret
	 * @param accessToken the access token
	 * @param accessTokenSecret the access token secret
	 */
	DeliciousGateway(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
		super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}

	/**
	 * Gets the suggested tags.
	 * 
	 * @param url the url
	 * 
	 * @return the suggested tags
	 */
	public List<String> getSuggestedTags(String url) {
		GatewayApiUrlBuilder builder = createApiUrlBuilder(ApplicationConstants.DELICIOUS_SUGGESTED_TAGS_URL);
        String                apiUrl  = builder.withParameter("url", url).buildUrl();
        Suggest suggest = unmarshall(Suggest.class, callApiGet(apiUrl));
        List<String> tags = new ArrayList<String>();
        if (suggest.getRecommended() != null) {
            tags.add(suggest.getRecommended());
        }
        tags.addAll(suggest.getPopular());
        return tags;
	}
	
	/**
	 * Adds the url.
	 * 
	 * @param url the url
	 */
	public void addUrl(Url url) {
		GatewayApiUrlBuilder builder = createApiUrlBuilder(ApplicationConstants.DELICIOUS_ADD_POST_URL);
        String                apiUrl  = builder.withParameter("url", url.getUrl()).withParameter("description", url.getDescription(), false).withParameter("replace", "no").buildUrl();
        callApiGet(apiUrl);
	}
	
	/**
	 * Gets the user tags.
	 * 
	 * @return the user tags
	 */
	public List<Tag> getUserTags() {
		GatewayApiUrlBuilder builder = createApiUrlBuilder(ApplicationConstants.DELICIOUS_USER_TAGS_URL);
        String                apiUrl  = builder.buildUrl();
        Tags tags = unmarshall(Tags.class, callApiGet(apiUrl));
        return tags.getTag();
	}
	
	/**
	 * Gets the recent urls.
	 * 
	 * @param tag the tag
	 * 
	 * @return the recent urls
	 */
	public List<Url> getRecentUrls(String tag) {
		GatewayApiUrlBuilder builder = createApiUrlBuilder(ApplicationConstants.DELICIOUS_RECENT_BY_TAG_URL);
        String                apiUrl  = builder.withField("tag", tag).buildUrl();
        JsonElement json = parseJson(callApiGet(apiUrl));
        return unmarshall(new TypeToken<List<Url>>(){}, json.getAsJsonArray());
	}
	
	/**
	 * Gets the popular urls.
	 * 
	 * @param tag the tag
	 * 
	 * @return the popular urls
	 */
	public List<Url> getPopularUrls(String tag) {
		GatewayApiUrlBuilder builder = createApiUrlBuilder(ApplicationConstants.DELICIOUS_POPULAR_BY_TAG_URL);
        String                apiUrl  = builder.withField("tag", tag).buildUrl();
        JsonElement json = parseJson(callApiGet(apiUrl));
        return unmarshall(new TypeToken<List<Url>>(){}, json.getAsJsonArray());
	}
	
	/**
	 * Refresh token.
	 * 
	 * @param sessionHandle the session handle
	 */
	public void refreshToken(final String sessionHandle) {
		try {
			OAuthAuthentication temp = this.authentication;
			this.authentication = null;
			GatewayApiUrlBuilder builder = createApiUrlBuilder(ApplicationConstants.DELICIOUS_REFRESH_TOKEN_URL);
			String url = builder.withParameter("oauth_nonce", "3423rw3d")
								.withParameter("oauth_consumer_key", temp.getConsumerKey())
								.withParameter("oauth_signature_method", "plaintext")
								.withParameter("oauth_signature", temp.getConsumerSecret() + "&" + temp.getAccessTokenSecret())
								.withParameter("oauth_version", "1.0")
								.withParameter("oauth_token", temp.getAccessToken())
								.withParameter("oauth_timestamp", String.valueOf(System.currentTimeMillis()))
								.withParameter("oauth_session_handle", sessionHandle).buildUrl();
			HttpParameters form = OAuth.decodeForm(callApiGet(url));
						
		    authentication = new OAuthAuthentication(temp.getConsumerKey(), temp.getConsumerSecret(), form.getFirst("oauth_token"), form.getFirst("oauth_token_secret"));
		    authentication.setOauthSessionHandle(form.getFirst("oauth_session_handle"));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while refreshing token.", e);
		}
	    
	}
	
	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.gateway.BaseGateway#getGsonBuilder()
	 */
	@Override
	protected GsonBuilder getGsonBuilder() {
		GsonBuilder gson = super.getGsonBuilder();
		gson.setDateFormat(ApplicationConstants.DELICIOUS_DATE_PATTERN);
		return gson;
	}
}
