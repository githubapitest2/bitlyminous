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
import com.appspot.bitlyminous.entity.GeoLocation;
import com.appspot.bitlyminous.entity.Tip;
import com.appspot.bitlyminous.entity.Venue;
import com.appspot.bitlyminous.gateway.FoursquareGateway;
import com.appspot.bitlyminous.gateway.GatewayFactory;

/**
 * The Class FoursquareGatewayTest.
 */
public class FoursquareGatewayTest extends TestCase {
	
	/** The Constant FACTORY. */
	protected static final GatewayFactory FACTORY = GatewayFactory.newInstance(); 
	
	/** The gateway. */
	protected FoursquareGateway gateway;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		gateway = FACTORY.createFoursquareGateway(ApplicationConstants.FOURSQUARE_CONSUMER_KEY, ApplicationConstants.FOURSQUARE_CONSUMER_SECRET, ApplicationConstants.FOURSQUARE_ACCESS_TOKEN, ApplicationConstants.FOURSQUARE_ACCESS_TOKEN_SECRET);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	public void tearDown() throws Exception {
		gateway = null;
	}

	/**
	 * Test get nearby tips.
	 */
	@Test
	public void testGetNearbyTips() {
		List<Tip> nearbyTips = gateway.getNearbyTips(getTestLocation(), 5);
		assertNotNullOrEmpty("Nearby tips cannot be null or empty.", nearbyTips);
		for (Tip tip : nearbyTips) {
			assertNotNull("Tip venue cannot be null or empty.", tip.getVenue());
		}
		
	}

	/**
	 * Test get nearby venues.
	 */
	@Test
	public void testGetNearbyVenues() {
		List<Venue> nearbyVenues = gateway.getNearbyVenues(getTestLocation(), "karachi", 5);
		assertNotNullOrEmpty("Nearby venues cannot be null or empty.", nearbyVenues);
	}

	/**
	 * Gets the test location.
	 * 
	 * @return the test location
	 */
	protected GeoLocation getTestLocation() {
		return new GeoLocation(24.893379, 67.02831849);
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
