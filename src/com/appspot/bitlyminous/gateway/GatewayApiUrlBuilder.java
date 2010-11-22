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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.appspot.bitlyminous.constant.ApplicationConstants;

/**
 * The Class GatewayApiUrlBuilder.
 */
public class GatewayApiUrlBuilder {
    
    /** The Constant API_URLS_PLACEHOLDER_START. */
    private static final char API_URLS_PLACEHOLDER_START = '{';

    /** The Constant API_URLS_PLACEHOLDER_END. */
    private static final char API_URLS_PLACEHOLDER_END = '}';
    
    /** The Constant QUERY_PARAMETERS_PLACEHOLDER. */
    private static final String QUERY_PARAMETERS_PLACEHOLDER = "queryParameters";
	
	/** The url format. */
    private String urlFormat;
	
    /** The url type. */
    @SuppressWarnings("unused")
	private String urlType;
	
	/** The parameters map. */
    private Map<String, Collection<String>> parametersMap = new HashMap<String, Collection<String>>();
	
    /** The fields map. */
    private Map<String, String> fieldsMap = new HashMap<String, String>();
	
	/**
	 * Instantiates a new gateway api url builder.
	 * 
	 * @param urlFormat the url format
	 */
    public GatewayApiUrlBuilder(String urlFormat) {
		this.urlFormat = urlFormat;    		
	}
	
	/**
	 * Instantiates a new gateway api url builder.
	 * 
	 * @param urlFormat the url format
	 * @param urlType the url type
	 */
    public GatewayApiUrlBuilder(String urlFormat, String urlType) {
		this.urlFormat = urlFormat;
		this.urlType = urlType;
	}
	
	/**
	 * With parameter.
	 * 
	 * @param name the name
	 * @param value the value
	 * 
	 * @return the gateway api url builder
	 */
    public GatewayApiUrlBuilder withParameter(String name, String value) {
    	if (value != null && value.length() > 0) {
    		parametersMap.put(name, Collections.singleton(encodeUrl(value)));
    	}
		
		return this;
	}
	
	/**
	 * With parameter.
	 * 
	 * @param name the name
	 * @param value the value
	 * @param escape the escape
	 * 
	 * @return the gateway api url builder
	 */
    public GatewayApiUrlBuilder withParameter(String name, String value, boolean escape) {
    	if (value != null && value.length() > 0) {
    		if (escape) {
    			withParameter(name, value);
    		} else {
        		parametersMap.put(name, Collections.singleton(encodeUrl(value).replaceAll("\\+", "%20")));
    		}
    	}
		
		return this;
	}

    /**
     * With parameters.
     * 
     * @param name the name
     * @param values the values
     * 
     * @return the gateway api url builder
     */
    public GatewayApiUrlBuilder withParameters(String name, Collection<String> values) {
    	List<String> encodedValues = new ArrayList<String>(values.size());
    	for (String value : values) {
    		encodedValues.add(encodeUrl(value));
    	}
		parametersMap.put(name, encodedValues);
		
		return this;
	}
	
	/**
	 * With empty field.
	 * 
	 * @param name the name
	 * 
	 * @return the gateway api url builder
	 */
    public GatewayApiUrlBuilder withEmptyField(String name) {
		fieldsMap.put(name, "");
		
		return this;
	}
    
	/**
	 * With field.
	 * 
	 * @param name the name
	 * @param value the value
	 * 
	 * @return the gateway api url builder
	 */
    public GatewayApiUrlBuilder withField(String name, String value) {
    	withField(name, value, false);
		
		return this;
	}
    
	/**
	 * With field.
	 * 
	 * @param name the name
	 * @param value the value
	 * @param escape the escape
	 * 
	 * @return the gateway api url builder
	 */
    public GatewayApiUrlBuilder withField(String name, String value, boolean escape) {
    	if (escape) {
    		fieldsMap.put(name, encodeUrl(value));
    	} else {
    		fieldsMap.put(name, value);
    	}
		
		return this;
	}
    
	/**
	 * Builds the url.
	 * 
	 * @return the string
	 */
	public String buildUrl() {
    	StringBuilder urlBuilder = new StringBuilder();
    	StringBuilder placeHolderBuilder = new StringBuilder();
    	boolean placeHolderFlag = false;
    	for (int i = 0; i < urlFormat.length(); i++) {
    		if (urlFormat.charAt(i) == API_URLS_PLACEHOLDER_START) {
    			placeHolderBuilder = new StringBuilder();
    			placeHolderFlag = true;
    		} else if (placeHolderFlag && urlFormat.charAt(i) == API_URLS_PLACEHOLDER_END) {
    			String placeHolder = placeHolderBuilder.toString();
    			if (fieldsMap.containsKey(placeHolder)) {
    				urlBuilder.append(fieldsMap.get(placeHolder));
    			} else if (QUERY_PARAMETERS_PLACEHOLDER.equals(placeHolder)) {
			    	StringBuilder builder = new StringBuilder();
			    	if (!parametersMap.isEmpty()) {
			        	builder.append("?");
			    		Iterator<String> iter = parametersMap.keySet().iterator();
			    		while (iter.hasNext()) {
			    			String name = iter.next();
		    				Collection<String> parameterValues = parametersMap.get(name);
		    				Iterator<String> iterParam = parameterValues.iterator();
		    				while (iterParam.hasNext()) {
			    				builder.append(name);
			    				builder.append("=");
			    				builder.append(iterParam.next());
			    				if (iterParam.hasNext()) {
    			    				builder.append("&");
			    				}
		    				}
			    			if (iter.hasNext()) {
			    				builder.append("&");
			    			}
			    		}
			    	}
			    	urlBuilder.append(builder.toString());
    			} else {
    				// we did not find a binding for the placeholder.
    				// append it as it is.
    				urlBuilder.append(API_URLS_PLACEHOLDER_START);
    				urlBuilder.append(placeHolder);
    				urlBuilder.append(API_URLS_PLACEHOLDER_END);
    			}
    			placeHolderFlag = false;
    		} else if (placeHolderFlag) {
    			placeHolderBuilder.append(urlFormat.charAt(i));
    		} else {
    			urlBuilder.append(urlFormat.charAt(i));
    		}
    	}

    	return urlBuilder.toString();
	}
	
    /**
     * Encode url.
     * 
     * @param original the original
     * 
     * @return the string
     */
    private static String encodeUrl(String original) {
    	try {
			return URLEncoder.encode(original, ApplicationConstants.CONTENT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			// should never be here..
			return original;
		}
    }
}