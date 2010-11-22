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
package com.appspot.bitlyminous.command.scheduled;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.gateway.GoogleSafeBrowsingGateway;

/**
 * The Class UpdateSafeBrowsingDataCommand.
 */
public class UpdateSafeBrowsingDataCommand extends AbstractScheduledCommand {

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.command.scheduled.ScheduledCommand#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		logger.info("Executing command '" + getClass().getSimpleName() + "'.");
		
		GoogleSafeBrowsingGateway gsb = new GoogleSafeBrowsingGateway(ApplicationConstants.GOOGLE_SAFE_BROWSING_API_KEY, false);
		
		// Update blacklist and malwarelist (if you have never run this before, all data will be downloaded)
		long t1 = System.currentTimeMillis();
		
		logger.info("Updating malware.");
		gsb.updateMalwarelist();
		long t2 = System.currentTimeMillis();
		logger.info("Time malware:" + (t2 - t1));
		logger.info("Updating blacklist.");
		gsb.updateBlacklist();
		long t3 = System.currentTimeMillis();
		logger.info("Time blacklist:" + (t3 - t2));
	}
}
