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
package com.appspot.bitlyminous.gateway;



/**
 * A factory for creating Gateway objects.
 */
public class GatewayFactory {
	
    /**
     * Instantiates a new gateway factory.
     */
    private GatewayFactory() {}

    /**
     * New instance.
     * 
     * @return the gateway factory
     */
    public static GatewayFactory newInstance() {
        return new GatewayFactory();
    }
    
    /**
     * Creates a new Gateway object.
     * 
     * @param consumerKey the consumer key
     * @param consumerSecret the consumer secret
     * @param accessToken the access token
     * @param accessTokenSecret the access token secret
     * 
     * @return the bitly gateway
     */
    public BitlyGateway createBitlyGateway(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
    	return new BitlyGateway(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
    
    /**
     * Creates a new Gateway object.
     * 
     * @param consumerKey the consumer key
     * @param consumerSecret the consumer secret
     * @param accessToken the access token
     * @param accessTokenSecret the access token secret
     * 
     * @return the delicious gateway
     */
    public DeliciousGateway createDeliciousGateway(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
    	return new DeliciousGateway(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
    
    /**
     * Creates a new Gateway object.
     * 
     * @param consumerKey the consumer key
     * @param consumerSecret the consumer secret
     * @param accessToken the access token
     * @param accessTokenSecret the access token secret
     * 
     * @return the foursquare gateway
     */
    public FoursquareGateway createFoursquareGateway(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
    	return new FoursquareGateway(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
    
    /**
     * Creates a new Gateway object.
     * 
     * @param consumerKey the consumer key
     * @param consumerSecret the consumer secret
     * @param accessToken the access token
     * @param accessTokenSecret the access token secret
     * 
     * @return the twitter gateway
     */
    public TwitterGateway createTwitterGateway(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
    	return new TwitterGateway(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
    
    /**
     * Creates a new Gateway object.
     * 
     * @param consumerKey the consumer key
     * @param consumerSecret the consumer secret
     * @param accessToken the access token
     * @param accessTokenSecret the access token secret
     * 
     * @return the digg gateway
     */
    public DiggGateway createDiggGateway(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
    	return new DiggGateway(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
}
