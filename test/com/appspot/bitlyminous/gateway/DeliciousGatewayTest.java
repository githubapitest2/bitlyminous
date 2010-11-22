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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.entity.Tag;
import com.appspot.bitlyminous.entity.Url;
import com.appspot.bitlyminous.gateway.DeliciousGateway;
import com.appspot.bitlyminous.gateway.GatewayFactory;

import junit.framework.TestCase;

/**
 * The Class DeliciousGatewayTest.
 */
public class DeliciousGatewayTest extends TestCase {
	
	/** The Constant FACTORY. */
	protected static final GatewayFactory FACTORY = GatewayFactory.newInstance(); 
	
	/** The gateway. */
	protected DeliciousGateway gateway;

	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		gateway = FACTORY.createDeliciousGateway(ApplicationConstants.DELICIOUS_CONSUMER_KEY, ApplicationConstants.DELICIOUS_CONSUMER_SECRET, ApplicationConstants.DELICIOUS_ACCESS_TOKEN, ApplicationConstants.DELICIOUS_ACCESS_TOKEN_SECRET);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		gateway = null;
	}

	/**
	 * Test get suggested tags.
	 */
	public void testGetSuggestedTags() {
		List<String> suggestedTags = gateway.getSuggestedTags("http://hadoop.apache.org");
		assertNotNullOrEmpty("Suggested tags should not be null or empty.", suggestedTags);
	}

	/**
	 * Test add url.
	 */
	public void testAddUrl() {
		gateway.addUrl(getTestUrl());
	}

	/**
	 * Test get user tags.
	 */
	public void testGetUserTags() {
		List<Tag> userTags = gateway.getUserTags();
		assertNotNullOrEmpty("User tags should not be null or empty.", userTags);
	}

	/**
	 * Test get recent urls.
	 */
	public void testGetRecentUrls() {
		List<Url> recentUrls = gateway.getRecentUrls("java");
		assertNotNullOrEmpty("Recent urls should not be null or empty.", recentUrls);
	}

	/**
	 * Test get popular urls.
	 */
	public void testGetPopularUrls() {
		List<Url> popularUrls = gateway.getPopularUrls("java");
		assertNotNullOrEmpty("Popular urls should not be null or empty.", popularUrls);
	}

	/**
	 * Gets the test url.
	 * 
	 * @return the test url
	 */
	protected Url getTestUrl() {
		Url url = new Url();
		url.setDescription("Home of hadoop.");
		url.setTags(Arrays.asList("hadoop", "mapreduce"));
		url.setUrl("http://hadoop.apache.org");
		
		return url;
	}
	
	/**
	 * Assert not null or empty.
	 * 
	 * @param message the message
	 * @param value the value
	 */
	protected static void assertNotNullOrEmpty(String message, String value) {
		assertNotNull(message, value);
		assertFalse(message, "".equals(value));
	}
	
	/**
	 * Assert not null or empty.
	 * 
	 * @param message the message
	 * @param value the value
	 */
	protected static void assertNotNullOrEmpty(String message, Collection<?> value) {
		assertNotNull(message, value);
		assertFalse(message, value.isEmpty());
	}
}
