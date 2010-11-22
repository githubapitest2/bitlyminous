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

import java.util.List;

import com.appspot.bitlyminous.entity.Url;

/**
 * The Interface UrlService.
 */
public interface UrlService {

	/**
	 * Gets the url by hash.
	 * 
	 * @param url the url
	 * 
	 * @return the url by hash
	 */
	public Url getUrlByHash(String url);

	/**
	 * Gets the similar urls.
	 * 
	 * @param url the url
	 * @param count the count
	 * 
	 * @return the similar urls
	 */
	public List<Url> getSimilarUrls(Url url, int count);
	
	public void saveUrls(List<Url> urls);

}
