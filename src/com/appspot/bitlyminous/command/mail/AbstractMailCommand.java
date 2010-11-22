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
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;





/**
 * The Class AbstractMailCommand.
 */
public abstract class AbstractMailCommand implements MailCommand {
	
	/** The logger. */
	protected final Logger logger = Logger.getLogger(getClass().getCanonicalName());	
	
	/**
	 * Gets the message from request.
	 * 
	 * @param req the req
	 * 
	 * @return the message from request
	 * 
	 * @throws MessagingException the messaging exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected Message getMessageFromRequest(HttpServletRequest req) throws MessagingException, IOException {
		Properties props = new Properties(); 
        Session session = Session.getDefaultInstance(props, null); 
        return new MimeMessage(session, req.getInputStream());		
	}
	
	/**
	 * Send message.
	 * 
	 * @param from the from
	 * @param to the to
	 * @param subject the subject
	 * @param text the text
	 * 
	 * @throws MessagingException the messaging exception
	 */
	protected void sendMessage(InternetAddress from, InternetAddress[] to, String subject, String text) throws MessagingException {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(from);
        msg.addRecipients(Message.RecipientType.TO, to);
        msg.setSubject(subject);
        msg.setText(text);
        Transport.send(msg);
     }

	/**
	 * Forward message.
	 * 
	 * @param message the message
	 * @param toAddress the to address
	 * 
	 * @throws MessagingException the messaging exception
	 * @throws AddressException the address exception
	 */
	protected void forwardMessage(Message message, String toAddress)
			throws MessagingException, AddressException {
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);
				
				String from = InternetAddress.toString(message.getFrom());
			
				// Create the message to forward
				Message forward = new MimeMessage(session);
			
				// Fill in header
				forward.setSubject("Fwd: " + message.getSubject());
				forward.setFrom(new InternetAddress("nabeelmukhtar@gmail.com"));
				forward.addRecipient(Message.RecipientType.TO,
				        new InternetAddress(toAddress));
			
				// Create a multi-part to combine the parts
				Multipart multipart = new MimeMultipart();
			
//				BodyPart messageBodyPart = new MimeBodyPart();
//				messageBodyPart.setText("Original message from: " + from);
//			
//				// Add part to multi part
//				multipart.addBodyPart(messageBodyPart);
				
				// Create and fill part for the forwarded content
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setDataHandler(message.getDataHandler());
			
				// Add part to multi part
				multipart.addBodyPart(messageBodyPart);
			
				// Associate multi-part with message
				forward.setContent(multipart);
				forward.setDataHandler(message.getDataHandler());
			
				// Send message
				Transport.send(forward);
				
				logger.info("Forwarded email from:" + from + " to:" + toAddress);
			}
}
