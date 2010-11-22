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
package com.appspot.bitlyminous.standalone;

import uk.co.mccann.gsb.engine.GoogleSafeBrowser;
import uk.co.mccann.gsb.interfaces.GSBDAO;
import uk.co.mccann.gsb.interfaces.GSBEngine;
import uk.co.mccann.gsb.model.MySQLDAO;

/**
 * The Class GoogleSafeBrowsingExample.
 */
public class GoogleSafeBrowsingExample {
	
	/** The SUSPICIOU s_ urls. */
	private static String[] SUSPICIOUS_URLS = {"http://malware.testing.google.test/testing/malware/", "http://38zu.cn", "http://googleanalytics.net", "http://lousecn.cn", "http://gumblar.cn", "http://orgsite.info", "http://martuz.cn"}; 

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 * 
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		GSBEngine gsb = new GoogleSafeBrowser("ABQIAAAAvK-Hosr7D1Kp6WTnrQEeehTZHb_jxy01d9W-LiCNV5YbDLHl0w", "C:/gsb/", false);
		
		// Use a flat file to read /store data (will create the files in the same directory as your preferences set in the session)
		//GSBDAO fileDao = new FileDAO();
		
		// Use MySQL Data Access Object to read / store data
		GSBDAO fileDao = new MySQLDAO("root", "olympic", "localhost", "gsb");
		
		// Register MySQL DAO with GoogleSafeBrowser
		gsb.registerDAO(fileDao);
		
		// Update blacklist and malwarelist (if you have never run this before, all data will be downloaded)
		long t1 = System.currentTimeMillis();
		System.out.println("Updating malware.");
		gsb.updateMalwarelist();
		long t2 = System.currentTimeMillis();
		System.out.println("Time malware:" + (t2 - t1));
		System.out.println("Updating blacklist.");
		gsb.updateBlacklist();
		long t3 = System.currentTimeMillis();
		System.out.println("Time blacklist:" + (t3 - t2));
		
		for (String url : SUSPICIOUS_URLS) {
			if(gsb.isDangerous(url)) {
				System.out.println("URL:" + url + " is dangerous!");
			} else {
				System.out.println("URL:" + url + " is not dangerous");
			}
		}
	}
}
