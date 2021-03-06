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
package com.appspot.bitlyminous.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.bitlyminous.command.mail.MailCommand;
import com.appspot.bitlyminous.command.mail.MailCommandFactory;

/**
 * The Class MailBotServlet.
 */
@SuppressWarnings("serial")
public class MailBotServlet extends HttpServlet {
	
	/** The Constant FACTORY. */
	private static final MailCommandFactory FACTORY = MailCommandFactory.newInstance();
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String commandName = req.getRequestURI();
		MailCommand command = FACTORY.createCommand(commandName);
		if (command != null) {
			command.execute(req, resp);
		}
	}
}
