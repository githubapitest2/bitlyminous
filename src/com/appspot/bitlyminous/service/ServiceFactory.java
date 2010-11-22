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
package com.appspot.bitlyminous.service;

import com.appspot.bitlyminous.service.impl.RecommendationServiceImpl;
import com.appspot.bitlyminous.service.impl.TwitterServiceImpl;
import com.appspot.bitlyminous.service.impl.UrlServiceImpl;
import com.appspot.bitlyminous.service.impl.UserServiceImpl;



/**
 * A factory for creating Service objects.
 */
public class ServiceFactory {
	
    /**
     * Instantiates a new service factory.
     */
    private ServiceFactory() {}

    /**
     * New instance.
     * 
     * @return the service factory
     */
    public static ServiceFactory newInstance() {
        return new ServiceFactory();
    }
    
    /**
     * Creates a new Service object.
     * 
     * @return the recommendation service
     */
    public RecommendationService createRecommendationService() {
    	return new RecommendationServiceImpl();
    }
    
    /**
     * Creates a new Service object.
     * 
     * @return the twitter service
     */
    public TwitterService createTwitterService() {
    	return new TwitterServiceImpl();
    }
    
    /**
     * Creates a new Service object.
     * 
     * @return the url service
     */
    public UrlService createUrlService() {
    	return new UrlServiceImpl();
    }
    
    /**
     * Creates a new Service object.
     * 
     * @return the user service
     */
    public UserService createUserService() {
    	return new UserServiceImpl();
    }
}
