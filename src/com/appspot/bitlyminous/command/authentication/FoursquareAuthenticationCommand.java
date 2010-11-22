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

import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oauth.signpost.basic.DefaultOAuthProvider;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.constant.ParameterNames;
import com.appspot.bitlyminous.entity.OAuthAuthentication;
import com.appspot.bitlyminous.entity.User;
import com.google.appengine.api.datastore.Text;

/**
 * The Class FoursquareAuthenticationCommand.
 */
public class FoursquareAuthenticationCommand extends
		AbstractAuthenticationCommand {
	
	/**
	 * Instantiates a new foursquare authentication command.
	 */
	FoursquareAuthenticationCommand() {
		this.provider = new DefaultOAuthProvider (
                "http://foursquare.com/oauth/request_token",
                "http://foursquare.com/oauth/access_token",
                "http://foursquare.com/oauth/authorize");
				
		provider.setOAuth10a(true);
		this.callbackUrl = ApplicationConstants.FOURSQUARE_CALLBACK_URL;
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.command.authentication.AuthenticationCommand#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		logger.info("Executing command '" + getClass().getSimpleName() + "'.");
		OAuthAuthentication authentication = new OAuthAuthentication(ApplicationConstants.FOURSQUARE_CONSUMER_KEY, ApplicationConstants.FOURSQUARE_CONSUMER_SECRET, null, null);
		HttpSession session = req.getSession(true);
		try {
			User user = (User) session.getAttribute(ParameterNames.SESSION_USER);
			if (user == null) {
				executeTwitterOAuth(req, resp, callbackUrl);
			}
			user = (User) session.getAttribute(ParameterNames.SESSION_USER);
			if (user != null) {
				if (executeOAuthFlow(req, resp, authentication)) {
					user.setDeliciousToken(new Text(authentication.getAccessToken()));
					user.setDeliciousSecret(new Text(authentication.getAccessTokenSecret()));
					user.setDeliciousSessionHandle(new Text(req.getParameter("oauth_session_handle")));
					userService.updateUser(user);				
					session.removeAttribute(ParameterNames.SESSION_USER);
					req.getRequestDispatcher("/auth/foursquareSuccess.jsp").forward(req, resp);
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while executing OAuth flow.", e);
			try {
				req.getRequestDispatcher("/auth/foursquareError.jsp").forward(req, resp);
			} catch (Exception e1) {
				logger.log(Level.SEVERE, "Error while executing OAuth flow.", e);
			}
		}
	}
}
