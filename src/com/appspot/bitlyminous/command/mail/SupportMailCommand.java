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
package com.appspot.bitlyminous.command.mail;

import java.io.IOException;
import java.util.logging.Level;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class SupportMailCommand.
 */
public class SupportMailCommand extends AbstractMailCommand {

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.command.mail.MailCommand#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		logger.info("Executing command '" + getClass().getSimpleName() + "'.");
		try {
			Message message = getMessageFromRequest(req);
			
			// TODO-Later: Implement this method.
			String toAddress = "admins";
			
			forwardMessage(message, toAddress);
		} catch (MessagingException e) {
			logger.log(Level.SEVERE, "Error while forwarding email.", e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error while forwarding email.", e);
		}
	}
}
