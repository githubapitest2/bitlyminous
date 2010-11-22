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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.entity.OAuthAuthentication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * The Class BaseGateway.
 */
public class BaseGateway {
	
    /** The logger. */
    protected final Logger logger = Logger.getLogger(getClass().getCanonicalName());
    
    /** The Constant JAXB_PACKAGE_NAME. */
    protected static final String JAXB_PACKAGE_NAME = "com.appspot.bitlyminous.entity";
    
	/** The Constant UTF_8_CHAR_SET. */
	protected static final Charset UTF_8_CHAR_SET = Charset.forName(ApplicationConstants.CONTENT_ENCODING);
    
	/** The Constant GZIP_ENCODING. */
	private static final String GZIP_ENCODING = "gzip";
	
	/** The Constant REFERRER. */
	private static final String REFERRER = "Referer";
	
	/** The request headers. */
	protected Map<String, String> requestHeaders;
	
	/** The user ip address. */
	protected String userIpAddress;
	
    /** The Constant parser. */
    protected static final JsonParser parser = new JsonParser();
    
    /** The JAX b_ context. */
    protected static JAXBContext JAXB_CONTEXT;
    
    /** The authentication. */
    protected OAuthAuthentication authentication;
    
	
    /**
     * Instantiates a new base gateway.
     * 
     * @param consumerKey the consumer key
     * @param consumerSecret the consumer secret
     * @param accessToken the access token
     * @param accessTokenSecret the access token secret
     */
    BaseGateway(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
    	authentication = new OAuthAuthentication(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        requestHeaders = new HashMap<String, String>();

        // by default we compress contents
        requestHeaders.put("Accept-Encoding", "gzip, deflate");
        requestHeaders.put("User-Agent", "bitlyminous");
	}
    
	/**
	 * Sets the request headers.
	 * 
	 * @param requestHeaders the request headers
	 */
	public void setRequestHeaders(Map<String, String> requestHeaders) {
	    this.requestHeaders = requestHeaders;
	}

	/**
	 * Gets the request headers.
	 * 
	 * @return the request headers
	 */
	public Map<String, String> getRequestHeaders() {
	    return requestHeaders;
	}

	/**
	 * Adds the request header.
	 * 
	 * @param headerName the header name
	 * @param headerValue the header value
	 */
	public void addRequestHeader(String headerName, String headerValue) {
	    requestHeaders.put(headerName, headerValue);
	}

	/**
	 * Removes the request header.
	 * 
	 * @param headerName the header name
	 */
	public void removeRequestHeader(String headerName) {
	    requestHeaders.remove(headerName);
	}

	/**
	 * Sets the referrer.
	 * 
	 * @param referrer the new referrer
	 */
	public void setReferrer(String referrer) {
		requestHeaders.put(REFERRER, referrer);
	}

	/**
	 * Sets the user ip address.
	 * 
	 * @param userIpAddress the new user ip address
	 */
	public void setUserIpAddress(String userIpAddress) {
		this.userIpAddress = userIpAddress;
	}
	
	/**
	 * Convert stream to string.
	 * 
	 * @param is the is
	 * 
	 * @return the string
	 */
	protected static String convertStreamToString(InputStream is) {
	    /*
	     * To convert the InputStream to String we use the BufferedReader.readLine()
	     * method. We iterate until the BufferedReader return null which means
	     * there's no more data to read. Each line will appended to a StringBuilder
	     * and returned as String.
	     */
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	
	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	    return sb.toString();
	}

	/**
	 * Call api get.
	 * 
	 * @param apiUrl the api url
	 * 
	 * @return the input stream
	 */
	protected InputStream callApiGet(String apiUrl) {
		return callApiGet(apiUrl, HttpURLConnection.HTTP_OK);
	}

	/**
	 * Call api get.
	 * 
	 * @param apiUrl the api url
	 * @param expected the expected
	 * 
	 * @return the input stream
	 */
	protected InputStream callApiGet(String apiUrl, int expected) {
	    try {
	        URL               url     = new URL(apiUrl);
	        
	        HttpURLConnection request = (HttpURLConnection) url.openConnection();
	
	        if (ApplicationConstants.CONNECT_TIMEOUT > -1) {
	            request.setConnectTimeout(ApplicationConstants.CONNECT_TIMEOUT);
	        }
	
	        if (ApplicationConstants.READ_TIMEOUT > -1) {
	            request.setReadTimeout(ApplicationConstants.READ_TIMEOUT);
	        }
	
	        for (String headerName : requestHeaders.keySet()) {
	            request.setRequestProperty(headerName, requestHeaders.get(headerName));
	        }
	        
	        OAuthConsumer consumer = createOAuthConsumer();
	        if (consumer != null) {
		        consumer.sign(request);
	        }
	        
	        request.connect();
	        
	        if (request.getResponseCode() != expected) {
	            GatewayException gatewayException = new GatewayException(convertStreamToString(request.getErrorStream()));
	            gatewayException.setErrorCode(request.getResponseCode());
				throw gatewayException;
	        } else {
	            return getWrappedInputStream(request.getInputStream(),
	                                         GZIP_ENCODING.equalsIgnoreCase(request.getContentEncoding()));
	        }
	    } catch (GatewayException e) {
	        throw e;
	    } catch (Exception e) {
	        throw new GatewayException(e);
	    }
	}
	
	/**
	 * Call api post.
	 * 
	 * @param apiUrl the api url
	 * @param parameters the parameters
	 * 
	 * @return the input stream
	 */
	protected InputStream callApiPost(String apiUrl, Map<String, List<String>> parameters) {
		return callApiPost(apiUrl, parameters, HttpURLConnection.HTTP_OK);
	}
	
	/**
	 * Call api post.
	 * 
	 * @param apiUrl the api url
	 * @param parameters the parameters
	 * @param expected the expected
	 * 
	 * @return the input stream
	 */
	protected InputStream callApiPost(String apiUrl, Map<String, List<String>> parameters, int expected) {
		try {
            URL               url     = new URL(apiUrl);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();

            if (ApplicationConstants.CONNECT_TIMEOUT > -1) {
                request.setConnectTimeout(ApplicationConstants.CONNECT_TIMEOUT);
            }

            if (ApplicationConstants.READ_TIMEOUT > -1) {
                request.setReadTimeout(ApplicationConstants.READ_TIMEOUT);
            }
            
            for (String headerName : requestHeaders.keySet()) {
                request.setRequestProperty(headerName, requestHeaders.get(headerName));
            }
            
            request.setRequestMethod("POST");
            request.setDoOutput(true);

	        OAuthConsumer consumer = createOAuthConsumer();
	        if (consumer != null) {
		        consumer.sign(request);
	        }
            
            PrintStream out = new PrintStream(new BufferedOutputStream(request.getOutputStream()));
            
            out.print(getParametersString(parameters));
            out.flush();
            out.close();

            request.connect();
            
	        if (request.getResponseCode() != expected) {
	            GatewayException gatewayException = new GatewayException(convertStreamToString(request.getErrorStream()));
	            gatewayException.setErrorCode(request.getResponseCode());
				throw gatewayException;
	        } else {
	            return getWrappedInputStream(request.getInputStream(),
	                                         GZIP_ENCODING.equalsIgnoreCase(request.getContentEncoding()));
	        }
		} catch (GatewayException e) {
	        throw e;
	    } catch (Exception e) {
	        throw new GatewayException(e);
		}
	}
	
	/**
	 * Gets the parameters string.
	 * 
	 * @param parameters the parameters
	 * 
	 * @return the parameters string
	 */
	protected String getParametersString(Map<String, List<String>> parameters) {
		StringBuilder builder = new StringBuilder();
		for (Iterator<Map.Entry<String, List<String>>> iter1 = parameters.entrySet().iterator(); iter1.hasNext();) {
			Map.Entry<String, List<String>> entry = iter1.next();
			for (Iterator<String> iter2 = entry.getValue().iterator(); iter2.hasNext();) {
				builder.append(entry.getKey());
				builder.append("=");
				builder.append(encodeUrl(iter2.next()));
				if (iter2.hasNext()) {
					builder.append("&");				
				}
			}
			if (iter1.hasNext()) {
				builder.append("&");				
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * Call api method.
	 * 
	 * @param apiUrl the api url
	 * @param xmlContent the xml content
	 * @param contentType the content type
	 * @param method the method
	 * @param expected the expected
	 * 
	 * @return the input stream
	 */
	protected InputStream callApiMethod(String apiUrl, String xmlContent, String contentType,
			String method, int expected) {
	    try {
	        URL               url     = new URL(apiUrl);
	        HttpURLConnection request = (HttpURLConnection) url.openConnection();
	
	        if (ApplicationConstants.CONNECT_TIMEOUT > -1) {
	            request.setConnectTimeout(ApplicationConstants.CONNECT_TIMEOUT);
	        }
	
	        if (ApplicationConstants.READ_TIMEOUT > -1) {
	            request.setReadTimeout(ApplicationConstants.READ_TIMEOUT);
	        }
	
	        for (String headerName : requestHeaders.keySet()) {
	            request.setRequestProperty(headerName, requestHeaders.get(headerName));
	        }
	
	        request.setRequestMethod(method);
	        request.setDoOutput(true);
	
	        OAuthConsumer consumer = createOAuthConsumer();
	        if (consumer != null) {
		        consumer.sign(request);
	        }
	        
	        if (contentType != null) {
	            request.setRequestProperty("Content-Type", contentType);
	        }
	
	        if (xmlContent != null) {
	            PrintStream out = new PrintStream(new BufferedOutputStream(request.getOutputStream()));
	
	            out.print(xmlContent);
	            out.flush();
	            out.close();
	        }
	
	        request.connect();
	        
	        if (request.getResponseCode() != expected) {
	            GatewayException gatewayException = new GatewayException(convertStreamToString(request.getErrorStream()));
	            gatewayException.setErrorCode(request.getResponseCode());
				throw gatewayException;
	        } else {
	            return getWrappedInputStream(request.getInputStream(),
	                                         GZIP_ENCODING.equalsIgnoreCase(request.getContentEncoding()));
	        }
	    } catch (Exception e) {
	        throw new GatewayException(e);
	    }
	}

	/**
	 * Creates the o auth consumer.
	 * 
	 * @return the o auth consumer
	 */
	protected OAuthConsumer createOAuthConsumer() {
		if (authentication != null) {
			OAuthConsumer consumer = new DefaultOAuthConsumer(authentication.getConsumerKey(), authentication.getConsumerSecret()); 
			consumer.setTokenWithSecret(authentication.getAccessToken(), authentication.getAccessTokenSecret());
			return consumer;
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the o auth authentication.
	 * 
	 * @return the o auth authentication
	 */
	public OAuthAuthentication getOAuthAuthentication() {
		return authentication;
	}

	/**
	 * Close stream.
	 * 
	 * @param is the is
	 */
	protected void closeStream(InputStream is) {
	    try {
	    	if (is != null) {
		        is.close();
	    	}
	    } catch (IOException e) {
	    	logger.log(Level.SEVERE, "An error occurred while closing stream.", e);	
	    }
	}

	/**
	 * Close connection.
	 * 
	 * @param connection the connection
	 */
	protected void closeConnection(HttpURLConnection connection) {
	    try {
	    	if (connection != null) {
	    		connection.disconnect();
	    	}
	    } catch (Exception e) {
	    	logger.log(Level.SEVERE, "An error occurred while disconnecting connection.", e);	
	    }
	}

	/**
	 * Gets the wrapped input stream.
	 * 
	 * @param is the is
	 * @param gzip the gzip
	 * 
	 * @return the wrapped input stream
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected InputStream getWrappedInputStream(InputStream is, boolean gzip)
	throws IOException {
	    if (gzip) {
	        return new BufferedInputStream(new GZIPInputStream(is));
	    } else {
	        return new BufferedInputStream(is);
	    }
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
	
	/**
	 * Gets the gson builder.
	 * 
	 * @return the gson builder
	 */
	protected GsonBuilder getGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
//		builder.setDateFormat(ApplicationConstants.RFC822DATEFORMAT);
//		builder.registerTypeAdapter(ListingType.class, new JsonDeserializer<ListingType>() {
//
//			@Override
//			public ListingType deserialize(JsonElement arg0, Type arg1,
//					JsonDeserializationContext arg2) throws JsonParseException {
//				return ListingType.fromValue(arg0.getAsString());
//			}
//			
//		});
		
		return builder;
	}
	
	/**
	 * Unmarshall.
	 * 
	 * @param typeToken the type token
	 * @param response the response
	 * 
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	protected <T> T unmarshall(TypeToken<T> typeToken, JsonElement response) {
		Gson gson = getGsonBuilder().create();
		return (T) gson.fromJson(response, typeToken.getType());
	}
	
	/**
	 * Parses the json.
	 * 
	 * @param jsonContent the json content
	 * 
	 * @return the json element
	 */
	protected JsonElement parseJson(InputStream jsonContent) {
        try {
        	return parser.parse(new InputStreamReader(jsonContent, UTF_8_CHAR_SET));
        } catch (Exception e) {
            throw new GatewayException(e);
        } finally {
	        closeStream(jsonContent);
	    }
	}
	
    /**
     * Unmarshall.
     * 
     * @param clazz the clazz
     * @param xmlContent the xml content
     * 
     * @return the t
     */
    @SuppressWarnings("unchecked")
    protected <T> T unmarshall(Class<T> clazz, InputStream xmlContent) {
        try {
            Unmarshaller u  = getJaxbContext().createUnmarshaller();

            return (T) u.unmarshal(xmlContent);
        } catch (JAXBException e) {
            throw new GatewayException(e);
        }
    }
    
    /**
     * Gets the jaxb context.
     * 
     * @return the jaxb context
     * 
     * @throws JAXBException the JAXB exception
     */
	protected JAXBContext getJaxbContext() throws JAXBException {
		if (JAXB_CONTEXT == null ) {
			JAXB_CONTEXT = JAXBContext.newInstance(JAXB_PACKAGE_NAME);
		}
		return JAXB_CONTEXT;
	}
    
	/**
	 * Creates the api url builder.
	 * 
	 * @param urlType the url type
	 * 
	 * @return the gateway api url builder
	 */
	protected GatewayApiUrlBuilder createApiUrlBuilder(String urlType) {
		return new GatewayApiUrlBuilder(urlType);
	}
	
}
