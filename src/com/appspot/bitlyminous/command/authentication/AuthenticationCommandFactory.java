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

import java.util.HashMap;
import java.util.Map;

import com.appspot.bitlyminous.constant.ParameterNames;


/**
 * A factory for creating AuthenticationCommand objects.
 */
public class AuthenticationCommandFactory {
	
	/** The Constant COMMANDS. */
	private static final Map<String, AuthenticationCommand> COMMANDS = new HashMap<String, AuthenticationCommand>();

	static {
		COMMANDS.put(ParameterNames.BIT_LY_AUTH, new BitlyAuthenticationCommand());
		COMMANDS.put(ParameterNames.TWITTER_AUTH, new TwitterAuthenticationCommand());
		COMMANDS.put(ParameterNames.DELICIOUS_AUTH, new DeliciousAuthenticationCommand());
		COMMANDS.put(ParameterNames.FOURSQUARE_AUTH, new FoursquareAuthenticationCommand());
		COMMANDS.put(ParameterNames.DIGG_AUTH, new DiggAuthenticationCommand());
		COMMANDS.put(ParameterNames.LOGOUT_AUTH, new LogoutAuthenticationCommand());
	}
	
    /**
     * Instantiates a new authentication command factory.
     */
    private AuthenticationCommandFactory() {}

    /**
     * New instance.
     * 
     * @return the authentication command factory
     */
    public static AuthenticationCommandFactory newInstance() {
        return new AuthenticationCommandFactory();
    }

    /**
     * Creates a new AuthenticationCommand object.
     * 
     * @param action the action
     * 
     * @return the authentication command
     */
    public AuthenticationCommand createCommand(String action) {
		if (COMMANDS.containsKey(action)) {
			return COMMANDS.get(action);
		}
		throw new IllegalArgumentException("Unrecognizable command." + action);
    }
}
