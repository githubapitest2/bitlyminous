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
package com.appspot.bitlyminous.service.impl;

import iweb2.ch3.collaborative.data.BaseDataset;
import iweb2.ch3.collaborative.data.ContentItem;
import iweb2.ch3.collaborative.model.Content;
import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.Item;
import iweb2.ch3.collaborative.model.Rating;
import iweb2.ch3.collaborative.model.SimilarItem;
import iweb2.ch3.collaborative.model.SimilarUser;
import iweb2.ch3.collaborative.recommender.Delphi;
import iweb2.ch3.collaborative.similarity.RecommendationType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.entity.Tag;
import com.appspot.bitlyminous.entity.Url;
import com.appspot.bitlyminous.entity.User;
import com.appspot.bitlyminous.gateway.DeliciousGateway;
import com.appspot.bitlyminous.gateway.GatewayFactory;
import com.appspot.bitlyminous.service.RecommendationService;

/**
 * The Class RecommendationServiceImpl.
 */
public class RecommendationServiceImpl extends BaseService implements
		RecommendationService {

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.RecommendationService#getUrlSimilarities(com.appspot.bitlyminous.entity.Url, java.util.List)
	 */
	public List<Map.Entry<Url, Double>> getUrlSimilarities(Url url, List<Url> relatedUrls) {
		Dataset ds = createUrlDataSet(url, relatedUrls);
		Delphi delphi = new Delphi(ds, RecommendationType.ITEM_CONTENT_BASED);
		SimilarItem[] similarItems = delphi.findSimilarItems(createItemFromUrl(url));
		Map<Url, Double> similaritiesMap = new HashMap<Url, Double>();
		for (SimilarItem similarItem : similarItems) {
			similaritiesMap.put(createUrlFromItem(similarItem.getItem()), similarItem.getSimilarity());
		}
		
		List<Map.Entry<Url, Double>> similarities = new ArrayList<Map.Entry<Url, Double>>(similaritiesMap.entrySet());
		Collections.sort(similarities, new SimilarityComparatorDesc<Url>());
		
		return similarities;
	}
	
	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.RecommendationService#getUserSimilarities(com.appspot.bitlyminous.entity.User, java.util.List)
	 */
	public List<Map.Entry<User, Double>> getUserSimilarities(User user, List<User> relatedUsers) {
		Dataset ds = createUserDataSet(user, relatedUsers);
		Delphi delphi = new Delphi(ds, RecommendationType.USER_CONTENT_BASED);
		SimilarUser[] similarUsers = delphi.findSimilarUsers(createUserFromUser(user));
		Map<User, Double> similaritiesMap = new HashMap<User, Double>();
		for (SimilarUser similarUser : similarUsers) {
			similaritiesMap.put(createUserFromUser(similarUser.getUser()), similarUser.getSimilarity());
		}
		
		List<Map.Entry<User, Double>> similarities = new ArrayList<Map.Entry<User, Double>>(similaritiesMap.entrySet());
		Collections.sort(similarities, new SimilarityComparatorDesc<User>());
		
		return similarities;
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.RecommendationService#getTextSimilarities(java.lang.String, java.util.List)
	 */
	public List<Map.Entry<String, Double>> getTextSimilarities(String text, List<String> relatedTexts) {
		Dataset ds = createTextDataSet(text, relatedTexts);
		Delphi delphi = new Delphi(ds, RecommendationType.ITEM_CONTENT_BASED);
		SimilarItem[] similarItems = delphi.findSimilarItems(createItemFromText(text));
		Map<String, Double> similaritiesMap = new HashMap<String, Double>();
		for (SimilarItem similarItem : similarItems) {
			similaritiesMap.put(createTextFromItem(similarItem.getItem()), similarItem.getSimilarity());
		}
		
		List<Map.Entry<String, Double>> similarities = new ArrayList<Map.Entry<String, Double>>(similaritiesMap.entrySet());
		Collections.sort(similarities, new SimilarityComparatorDesc<String>());
		
		return similarities;
	}
	
	protected Dataset createTextDataSet(String refText, List<String> relatedTexts) {
		BaseDataset dataset = new BaseDataset();
		dataset.addItem(createItemFromText(refText));
		for (String text : relatedTexts) {
			dataset.addItem(createItemFromText(text));
		}
		return dataset;
	}

	protected Dataset createUrlDataSet(Url refUrl, List<Url> urls) {
		BaseDataset dataset = new BaseDataset();
		dataset.addItem(createItemFromUrl(refUrl));
		for (Url url : urls) {
			dataset.addItem(createItemFromUrl(url));
		}
		return dataset;
	}
	
	protected Dataset createUserDataSet(User refUser, List<User> users) {
		BaseDataset dataset = new BaseDataset();
		dataset.add(createUserFromUser(refUser));
		for (User user : users) {
			dataset.add(createUserFromUser(user));
		}
		return dataset;
	}
	
	protected Item createItemFromUrl(Url url) {
		return new ContentItem(url.getUrl().hashCode(), url.getUrl(), new Content("url-" + url.getUrl().hashCode(), getUrlContent(url)));
	}
	
	protected Url createUrlFromItem(Item item) {
		ContentItem contentItem = (ContentItem) item;
		Url url = new Url();
		url.setDescription(item.getItemContent().getText().replaceAll("\\[.*\\]", ""));
		url.setUrl(contentItem.getName());
		url.setTags(Arrays.asList(contentItem.getItemContent().getTerms()));
		return url;
	}
	
	protected Item createItemFromText(String text) {
		return new ContentItem(text.hashCode(), "item-" + text.hashCode(), new Content("text-" + text.hashCode(), text));
	}

	protected String createTextFromItem(Item item) {
		return ((ContentItem) item).getItemContent().getText();
	}
	
	protected iweb2.ch3.collaborative.model.User createUserFromUser(User user) {
		iweb2.ch3.collaborative.model.User model = new iweb2.ch3.collaborative.model.User(user.getId().hashCode(), user.getScreenName());
		if (user.getTags() != null) {
			for (Tag tag : user.getTags().getTag()) {
				model.addRating(new Rating(user.getId().hashCode(), tag.getTag().hashCode(), tag.getCount().intValue()));
			}
		}
		return model;
	}
	
	protected User createUserFromUser(iweb2.ch3.collaborative.model.User user) {
		User entity = new User();
		entity.setScreenName(user.getName());
		return entity;
	}
	
	protected String getUrlContent(Url url) {
		StringBuilder builder = new StringBuilder();
		builder.append(url.getDescription());
		builder.append("[");
		for (String tag : url.getTags()) {
			builder.append(tag);
			builder.append(" ");
		}
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) {
		RecommendationService service = new RecommendationServiceImpl();
		String[] relatedTexts = {"An introduction to Hadoop - ThinkPHP /dev/blog", "Welcome to Apache Hadoop!", "Apache Mahout:: Scalable machine-learning and data-mining library", "Distributed data processing with Hadoop, Part 3: Application development", "Programming Hadoop with Clojure", "Scaling Big Time with Hadoop", "Getting Started on Hadoop"};
		List<Entry<String, Double>> textSimilarities = service.getTextSimilarities("Getting Started on Hadoop", Arrays.asList(relatedTexts));
		System.out.println(textSimilarities);
		
		DeliciousGateway delicious = GatewayFactory.newInstance().createDeliciousGateway(ApplicationConstants.DELICIOUS_CONSUMER_KEY, ApplicationConstants.DELICIOUS_CONSUMER_SECRET, ApplicationConstants.DELICIOUS_ACCESS_TOKEN, ApplicationConstants.DELICIOUS_ACCESS_TOKEN_SECRET);
		List<Url> popularUrls = delicious.getPopularUrls("hadoop");
		System.out.println(popularUrls.get(0));
		List<Entry<Url, Double>> urlSimilarities = service.getUrlSimilarities(popularUrls.get(0), popularUrls);
		System.out.println(urlSimilarities);
	
	}
}
