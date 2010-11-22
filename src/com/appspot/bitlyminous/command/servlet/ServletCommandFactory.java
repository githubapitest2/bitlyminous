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
package com.appspot.bitlyminous.command.servlet;

import java.util.HashMap;
import java.util.Map;

import com.appspot.bitlyminous.constant.ParameterNames;


/**
 * A factory for creating ServletCommand objects.
 */
public class ServletCommandFactory {
	
	/** The Constant COMMANDS. */
	private static final Map<String, ServletCommand> COMMANDS = new HashMap<String, ServletCommand>();

	static {
		COMMANDS.put(ParameterNames.UPLOAD_GSB_DATA, new UploadSafeBrowsingData());
	}
	
    /**
     * Instantiates a new servlet command factory.
     */
    private ServletCommandFactory() {}

    /**
     * New instance.
     * 
     * @return the servlet command factory
     */
    public static ServletCommandFactory newInstance() {
        return new ServletCommandFactory();
    }

    /**
     * Creates a new ServletCommand object.
     * 
     * @param action the action
     * 
     * @return the servlet command
     */
    public ServletCommand createCommand(String action) {
		if (COMMANDS.containsKey(action)) {
			return COMMANDS.get(action);
		}
		throw new IllegalArgumentException("Unrecognizable command." + action);
    }
}
