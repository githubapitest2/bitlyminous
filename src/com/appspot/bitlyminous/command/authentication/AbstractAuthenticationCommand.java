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
package com.appspot.bitlyminous.command.authentication;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.constant.ParameterNames;
import com.appspot.bitlyminous.entity.OAuthAuthentication;
import com.appspot.bitlyminous.entity.User;
import com.appspot.bitlyminous.service.ServiceFactory;
import com.appspot.bitlyminous.service.UserService;
import com.google.appengine.api.datastore.Text;

/**
 * The Class AbstractAuthenticationCommand.
 */
public abstract class AbstractAuthenticationCommand implements AuthenticationCommand {
	
	/** The logger. */
	protected final Logger logger = Logger.getLogger(getClass().getCanonicalName());
	
	/** The user service. */
	protected UserService userService = ServiceFactory.newInstance().createUserService();
	
	/** The provider. */
	protected OAuthProvider provider;
	
	/** The callback url. */
	protected String callbackUrl;
	
	/**
	 * Execute o auth flow.
	 * 
	 * @param req the req
	 * @param resp the resp
	 * @param authentication the authentication
	 * 
	 * @return true, if successful
	 * 
	 * @throws Exception the exception
	 */
	protected boolean executeOAuthFlow(HttpServletRequest req, HttpServletResponse resp, OAuthAuthentication authentication) throws Exception {
		OAuthConsumer consumer = new DefaultOAuthConsumer(authentication.getConsumerKey(), authentication.getConsumerSecret());
		HttpSession session = req.getSession(true);
		if (session.getAttribute(ParameterNames.REQUEST_TOKEN_PARAM) == null || session.getAttribute(ParameterNames.REQUEST_TOKEN_SECRET_PARAM) == null) {
			String authUrl = provider.retrieveRequestToken(consumer, callbackUrl);
			session.setAttribute(ParameterNames.REQUEST_TOKEN_PARAM, consumer.getToken());
			session.setAttribute(ParameterNames.REQUEST_TOKEN_SECRET_PARAM, consumer.getTokenSecret());
			resp.sendRedirect(authUrl);
			return false;
		} else {
			consumer.setTokenWithSecret((String) session.getAttribute(ParameterNames.REQUEST_TOKEN_PARAM), (String) session.getAttribute(ParameterNames.REQUEST_TOKEN_SECRET_PARAM));
			session.removeAttribute(ParameterNames.REQUEST_TOKEN_PARAM);
			session.removeAttribute(ParameterNames.REQUEST_TOKEN_SECRET_PARAM);
			provider.retrieveAccessToken(consumer, req.getParameter(ParameterNames.OAUTH_VERIFIER));
			authentication.setAccessToken(consumer.getToken());
			authentication.setAccessTokenSecret(consumer.getTokenSecret());
			authentication.setOauthSessionHandle(provider.getResponseParameters().getFirst("oauth_session_handle"));
			return true;
		}
	}
	
	/**
	 * Execute twitter o auth.
	 * 
	 * @param req the req
	 * @param resp the resp
	 * @param callbackUrl the callback url
	 * 
	 * @return true, if successful
	 * 
	 * @throws Exception the exception
	 */
	protected boolean executeTwitterOAuth(HttpServletRequest req, HttpServletResponse resp, String callbackUrl) throws Exception {
		HttpSession session = req.getSession(true);
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(ApplicationConstants.TWITTER_CONSUMER_KEY, ApplicationConstants.TWITTER_CONSUMER_SECRET);
		if (session.getAttribute(ParameterNames.REQUEST_TOKEN_PARAM) == null || session.getAttribute(ParameterNames.REQUEST_TOKEN_SECRET_PARAM) == null) {
			RequestToken requestToken = twitter.getOAuthRequestToken(callbackUrl);
			session.setAttribute(ParameterNames.REQUEST_TOKEN_PARAM, requestToken.getToken());
			session.setAttribute(ParameterNames.REQUEST_TOKEN_SECRET_PARAM, requestToken.getTokenSecret());
			resp.sendRedirect(requestToken.getAuthenticationURL());
			return false;
		} else {
			AccessToken accessToken = twitter.getOAuthAccessToken(new RequestToken((String) session.getAttribute(ParameterNames.REQUEST_TOKEN_PARAM), (String) session.getAttribute(ParameterNames.REQUEST_TOKEN_SECRET_PARAM)), req.getParameter(ParameterNames.OAUTH_VERIFIER));
			session.removeAttribute(ParameterNames.REQUEST_TOKEN_PARAM);
			session.removeAttribute(ParameterNames.REQUEST_TOKEN_SECRET_PARAM);
			User user = userService.getUserByScreenName(accessToken.getScreenName());
			if (user == null) {
				user = new User();
				user.setScreenName(accessToken.getScreenName());
			}
			user.setTwitterToken(new Text(accessToken.getToken()));
			user.setTwitterSecret(new Text(accessToken.getTokenSecret()));
			session.setAttribute(ParameterNames.SESSION_USER, user);
			return true;
		}
	}
}
