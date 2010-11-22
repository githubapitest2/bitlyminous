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
 * The Interface TwitterHandler.
 */
public interface TwitterHandler {
	
	/**
	 * Execute.
	 * 
	 * @param tweet the tweet
	 * 
	 * @return the status update
	 */
	public StatusUpdate execute(Status tweet);
}
