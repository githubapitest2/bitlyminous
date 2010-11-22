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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

/**
 * The Class TwitterExample.
 */
public class TwitterExample {

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 * 
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		// The factory instance is re-useable and thread safe.
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("671O1zUicFBCYHHYVp0XA", "cP7WvfAtxXGSaf1Sz0ksZtWGcENosFcRr46WR7a9jU");
		RequestToken requestToken = twitter.getOAuthRequestToken();
		AccessToken accessToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (null == accessToken) {
			System.out
					.println("Open the following URL and grant access to your account:");
			System.out.println(requestToken.getAuthorizationURL());
			System.out
					.print("Enter the PIN(if available) or just hit enter.[PIN]:");
			String pin = br.readLine();
			try {
				if (pin.length() > 0) {
					accessToken = twitter
							.getOAuthAccessToken(requestToken, pin);
				} else {
					accessToken = twitter.getOAuthAccessToken();
				}
			} catch (TwitterException te) {
				if (401 == te.getStatusCode()) {
					System.out.println("Unable to get the access token.");
				} else {
					te.printStackTrace();
				}
			}
		}
		// persist to the accessToken for future reference.
		System.out.println("Screen name:" + accessToken.getScreenName());
		System.out.println("Token:" + accessToken.getToken());
		System.out.println("Secret:" + accessToken.getTokenSecret());
		
		IDs friendsIDs = twitter.getFriendsIDs("gapinginfinity");
		System.out.println(Arrays.toString(friendsIDs.getIDs()));
		
		ResponseList<Status> favorites = twitter.getFavorites("gapinginfinity");
		System.out.println(favorites.size());
		System.exit(0);
	}

}
