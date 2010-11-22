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

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.appspot.bitlyminous.entity.Url;
import com.appspot.bitlyminous.entity.User;

/**
 * The Interface RecommendationService.
 */
public interface RecommendationService {
	
	/**
	 * The Class SimilarityComparatorDesc.
	 */
	public static class SimilarityComparatorDesc<T> implements Comparator<Map.Entry<T, Double>> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Entry<T, Double> o1, Entry<T, Double> o2) {
			return o2.getValue().compareTo(o1.getValue());
		}
	}

	/**
	 * Gets the url similarities.
	 * 
	 * @param url the url
	 * @param relatedUrls the related urls
	 * 
	 * @return the url similarities
	 */
	public List<Map.Entry<Url, Double>> getUrlSimilarities(Url url, List<Url> relatedUrls);

	/**
	 * Gets the user similarities.
	 * 
	 * @param user the user
	 * @param relatedUsers the related users
	 * 
	 * @return the user similarities
	 */
	public List<Map.Entry<User, Double>> getUserSimilarities(User user, List<User> relatedUsers);

	/**
	 * Gets the text similarities.
	 * 
	 * @param text the text
	 * @param relatedTexts the related texts
	 * 
	 * @return the text similarities
	 */
	public List<Map.Entry<String, Double>> getTextSimilarities(String text, List<String> relatedTexts);
}
