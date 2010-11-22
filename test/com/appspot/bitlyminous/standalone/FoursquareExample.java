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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

import com.appspot.bitlyminous.constant.ApplicationConstants;

/**
 * The Class FoursquareExample.
 */
public class FoursquareExample {
	
	/** The Constant TEST_URLS. */
	private static final String[] TEST_URLS = {"http://api.foursquare.com/v1/tips.json?geolat=24.869742&geolong=67.038116&filter=nearby",
		"http://api.foursquare.com/v1/venues.json?geolat=24.869742&geolong=67.038116"};
	
	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 * 
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
    	OAuthConsumer consumer = new DefaultOAuthConsumer(ApplicationConstants.FOURSQUARE_CONSUMER_KEY,
    			ApplicationConstants.FOURSQUARE_CONSUMER_SECRET);
//		consumer.setMessageSigner(new HmacSha1MessageSigner());
//		consumer.setSigningStrategy(new AuthorizationHeaderSigningStrategy());
//
//        OAuthProvider provider = new DefaultOAuthProvider(
//                "http://foursquare.com/oauth/request_token",
//                "http://foursquare.com/oauth/access_token",
//                "http://foursquare.com/oauth/authorize");
//				
//		provider.setOAuth10a(true);
//
//        System.out.println("Fetching request token from Foursquare...");
//
//        // we do not support callbacks, thus pass OOB
//        String authUrl = provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND);
//
//        System.out.println("Request token: " + consumer.getToken());
//        System.out.println("Token secret: " + consumer.getTokenSecret());
//
//        System.out.println("Now visit:\n" + authUrl
//                + "\n... and grant this app authorization");
//        System.out.println("Enter the PIN code and hit ENTER when you're done:");
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String pin = br.readLine();
//
//        System.out.println("Fetching access token from Foursquare...");
//
//        provider.retrieveAccessToken(consumer, pin);
//
//        System.out.println("Access token: " + consumer.getToken());
//        System.out.println("Token secret: " + consumer.getTokenSecret());
    	
    	consumer.setTokenWithSecret(ApplicationConstants.FOURSQUARE_ACCESS_TOKEN, ApplicationConstants.FOURSQUARE_ACCESS_TOKEN_SECRET);

    	for (String testUrl : TEST_URLS) {
            URL url = new URL(testUrl);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();

            consumer.sign(request);

            System.out.println("Sending request to Foursquare..." + testUrl);
            request.connect();
    		
            if (request.getResponseCode() == 200) {
    	        String responseBody = convertStreamToString(request.getInputStream());
    	        
    	        System.out.println("Response: " + request.getResponseCode() + " "
    	                + request.getResponseMessage() + "\n\n" + responseBody);
    		} else {
    	        String responseBody = convertStreamToString(request.getErrorStream());
    	        
    	        System.out.println("Response: " + request.getResponseCode() + " "
    	                + request.getResponseMessage() + "\n\n" + responseBody);
    		}
            
            System.out.println("==========================================================");
    	}
	}
	
    // Stolen liberally from http://www.kodejava.org/examples/266.html
    /**
     * Convert stream to string.
     * 
     * @param is the is
     * 
     * @return the string
     */
    public static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        return sb.toString();
    }
}
