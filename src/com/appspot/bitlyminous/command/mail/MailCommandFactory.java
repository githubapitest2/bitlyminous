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

import java.util.HashMap;
import java.util.Map;

import com.appspot.bitlyminous.constant.ParameterNames;


/**
 * A factory for creating MailCommand objects.
 */
public class MailCommandFactory {
	
	/** The Constant COMMANDS. */
	private static final Map<String, MailCommand> COMMANDS = new HashMap<String, MailCommand>();

	static {
		COMMANDS.put(ParameterNames.TWITTER_MAIL, new TwitterMailCommand());
		COMMANDS.put(ParameterNames.SUPPORT_MAIL, new SupportMailCommand());
		COMMANDS.put(ParameterNames.BITLY_MAIL, new BitlyMailCommand());
	}
	
    /**
     * Instantiates a new mail command factory.
     */
    private MailCommandFactory() {}

    /**
     * New instance.
     * 
     * @return the mail command factory
     */
    public static MailCommandFactory newInstance() {
        return new MailCommandFactory();
    }

    /**
     * Creates a new MailCommand object.
     * 
     * @param action the action
     * 
     * @return the mail command
     */
    public MailCommand createCommand(String action) {
		if (COMMANDS.containsKey(action)) {
			return COMMANDS.get(action);
		}
		return new ForwardingMailCommand();
    }
}
