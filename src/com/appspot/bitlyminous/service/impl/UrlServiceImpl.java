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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.appspot.bitlyminous.dao.NamedQueries;
import com.appspot.bitlyminous.entity.Url;
import com.appspot.bitlyminous.service.UrlService;

/**
 * The Class UrlServiceImpl.
 */
public class UrlServiceImpl extends BaseService implements UrlService {

	/**
	 * Instantiates a new url service impl.
	 */
	public UrlServiceImpl() {
		super("url-service");
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.UrlService#getUrlByHash(java.lang.String)
	 */
	public Url getUrlByHash(String url) {
		EntityManager entityManager = createEntityManager();
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("shortUrl", url);
			return getDao(entityManager).getSingleResult(Url.class, NamedQueries.FIND_URL_BY_HASH, parameters);
		} finally {
			closeEntityManager(entityManager);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.UrlService#getSimilarUrls(com.appspot.bitlyminous.entity.Url, int)
	 */
	public List<Url> getSimilarUrls(Url url, int count) { 
		EntityManager entityManager = createEntityManager();
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("url", url);
			return getDao(entityManager).getResultList(Url.class, NamedQueries.FIND_SIMILAR_URLS, parameters, count);
		} finally {
			closeEntityManager(entityManager);
		}
	}

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.service.UrlService#saveUrls(java.util.List)
	 */
	@Override
	public void saveUrls(List<Url> urls) {
		EntityManager entityManager = createEntityManager();
		try {
			getDao(entityManager).saveOrUpdateAll(urls);
		} finally {
			closeEntityManager(entityManager);
		}
	}
}
