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
package com.appspot.bitlyminous.handler.twitter;

import twitter4j.Status;
import twitter4j.StatusUpdate;


/**
 * The Class MineLinksHandler.
 */
public class MineLinksHandler extends AbstractTwitterHandler {

	/**
	 * Instantiates a new mine links handler.
	 * 
	 * @param commandName the command name
	 * @param context the context
	 */
	public MineLinksHandler(String commandName, TwitterContext context) {
		super(commandName, context);
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.handler.twitter.TwitterHandler#execute(twitter4j.Status)
	 */
	@Override
	public StatusUpdate execute(Status tweet) {
		// TODO-Later Auto-generated method stub
		return null;
	}

}
