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

/**
 * The Class FoursquareAuthenticationCommand.
 */
public class LogoutAuthenticationCommand extends
		AbstractAuthenticationCommand {
	
	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.command.authentication.AuthenticationCommand#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		logger.info("Executing command '" + getClass().getSimpleName() + "'.");
		HttpSession session = req.getSession(true);
		session.invalidate();
		try {
			req.getRequestDispatcher("/auth/logout.jsp").forward(req, resp);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while forwarding to logout.", e);
		}
	}
}
