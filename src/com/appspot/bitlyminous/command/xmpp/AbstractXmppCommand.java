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
package com.appspot.bitlyminous.command.xmpp;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;





/**
 * The Class AbstractXmppCommand.
 */
public abstract class AbstractXmppCommand implements XmppCommand {
	
	/** The logger. */
	protected final Logger logger = Logger.getLogger(getClass().getCanonicalName());
	
	/**
	 * Gets the message from request.
	 * 
	 * @param req the req
	 * 
	 * @return the message from request
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected Message getMessageFromRequest(HttpServletRequest req) throws IOException {
		XMPPService xmpp = XMPPServiceFactory.getXMPPService();
        return xmpp.parseMessage(req);
	}
}
