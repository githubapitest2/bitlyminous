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
package com.appspot.bitlyminous.dao;

/**
 * The Interface NamedQueries.
 */
public interface NamedQueries {
	
	/** The Constant FIND_CURRENT_VERSION. */
	public static final String FIND_CURRENT_VERSION = "findCurrentVersion";
	
	/** The Constant FIND_URL_BY_HASH. */
	public static final String FIND_URL_BY_HASH = "findUrlByHash";
	
	/** The Constant FIND_USER_BY_SCREEN_NAME. */
	public static final String FIND_USER_BY_SCREEN_NAME = "findUserByScreenName";
	
	/** The Constant FIND_ALL_BLACKLIST. */
	public static final String FIND_ALL_BLACKLIST = "findAllBlacklist";

	/** The Constant DELETE_ALL_BLACKLIST. */
	public static final String DELETE_ALL_BLACKLIST = "deleteAllBlacklist";
	
	/** The Constant FIND_BLACKLIST_BY_HASH. */
	public static final String FIND_BLACKLIST_BY_HASH = "findBlacklistByHash";

	/** The Constant DELETE_BLACKLIST_BY_HASH. */
	public static final String DELETE_BLACKLIST_BY_HASH = "deleteBlacklistByHash";
	
	/** The Constant FIND_ALL_MALWARE. */
	public static final String FIND_ALL_MALWARE = "findAllMalware";

	/** The Constant DELETE_ALL_MALWARE. */
	public static final String DELETE_ALL_MALWARE = "deleteAllMalware";

	/** The Constant FIND_MALWARE_BY_HASH. */
	public static final String FIND_MALWARE_BY_HASH = "findMalwareByHash";

	/** The Constant DELETE_MALWARE_BY_HASH. */
	public static final String DELETE_MALWARE_BY_HASH = "deleteMalwareByHash";

	/** The Constant FIND_SIMILAR_USERS. */
	public static final String FIND_SIMILAR_USERS = "findSimilarUsers";
	
	/** The Constant FIND_SIMILAR_URLS. */
	public static final String FIND_SIMILAR_URLS = "findSimilarUrls";

	/** The Constant FIND_AUTHENTICATED_USERS. */
	public static final String FIND_AUTHENTICATED_USERS = "findAuthenticatedUsers";
}
