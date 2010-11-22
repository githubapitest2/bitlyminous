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

import java.util.HashMap;
import java.util.Map;


/**
 * A factory for creating XmppCommand objects.
 */
public class XmppCommandFactory {
	
	/** The Constant COMMANDS. */
	private static final Map<String, XmppCommand> COMMANDS = new HashMap<String, XmppCommand>();

	static {
	}
	
    /**
     * Instantiates a new xmpp command factory.
     */
    private XmppCommandFactory() {}

    /**
     * New instance.
     * 
     * @return the xmpp command factory
     */
    public static XmppCommandFactory newInstance() {
        return new XmppCommandFactory();
    }

    /**
     * Creates a new XmppCommand object.
     * 
     * @param action the action
     * 
     * @return the xmpp command
     */
    public XmppCommand createCommand(String action) {
		if (COMMANDS.containsKey(action)) {
			return COMMANDS.get(action);
		}
		throw new IllegalArgumentException("Unrecognizable command." + action);
    }
}
