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

import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.entity.Story;
import com.appspot.bitlyminous.gateway.DiggGateway;
import com.appspot.bitlyminous.gateway.GatewayFactory;

/**
 * The Class DiggGatewayTest.
 */
public class DiggGatewayTest extends TestCase {
	
	/** The Constant FACTORY. */
	protected static final GatewayFactory FACTORY = GatewayFactory.newInstance(); 
	
	/** The gateway. */
	protected DiggGateway gateway;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		gateway = FACTORY.createDiggGateway(ApplicationConstants.DIGG_CONSUMER_KEY, ApplicationConstants.DIGG_CONSUMER_SECRET, ApplicationConstants.DIGG_ACCESS_TOKEN, ApplicationConstants.DIGG_ACCESS_TOKEN_SECRET);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	public void tearDown() throws Exception {
		gateway = null;
	}

	/**
	 * Test get popular stories.
	 */
	@Test
	public void testGetPopularStories() {
		List<Story> stories = gateway.getPopularStories("hadoop", 5);
		assertNotNullOrEmpty("Popular stories cannot be null or empty.", stories);
		
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
