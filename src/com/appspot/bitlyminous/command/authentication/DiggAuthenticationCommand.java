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
 * The Class DiggAuthenticationCommand.
 */
public class DiggAuthenticationCommand extends
		AbstractAuthenticationCommand {
	
	/**
	 * Instantiates a new digg authentication command.
	 */
	public DiggAuthenticationCommand() {
      this.provider = new DefaultOAuthProvider(
	      "http://services.digg.com/oauth/request_token",
	      "http://services.digg.com/oauth/access_token",
	      "http://digg.com/oauth/authenticate");
		
      provider.setOAuth10a(true);
      this.callbackUrl = ApplicationConstants.DIGG_CALLBACK_URL;
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.command.authentication.AuthenticationCommand#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		logger.info("Executing command '" + getClass().getSimpleName() + "'.");
		logger.info("Executing command '" + getClass().getSimpleName() + "'.");
		OAuthAuthentication authentication = new OAuthAuthentication(ApplicationConstants.DIGG_CONSUMER_KEY, ApplicationConstants.DIGG_CONSUMER_SECRET, null, null);
		HttpSession session = req.getSession(true);
		try {
			User user = (User) session.getAttribute(ParameterNames.SESSION_USER);
			if (user == null) {
				executeTwitterOAuth(req, resp, callbackUrl);
			}
			user = (User) session.getAttribute(ParameterNames.SESSION_USER);
			if (user != null) {
				if (executeOAuthFlow(req, resp, authentication)) {
					user.setDiggToken(new Text(authentication.getAccessToken()));
					user.setDiggSecret(new Text(authentication.getAccessTokenSecret()));
					userService.updateUser(user);
					session.removeAttribute(ParameterNames.SESSION_USER);
					req.getRequestDispatcher("/auth/diggSuccess.jsp").forward(req, resp);
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while executing OAuth flow.", e);
			try {
				req.getRequestDispatcher("/auth/diggError.jsp").forward(req, resp);
			} catch (Exception e1) {
				logger.log(Level.SEVERE, "Error while executing OAuth flow.", e);
			}
		}
	}
}
