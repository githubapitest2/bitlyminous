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
package com.appspot.bitlyminous.standalone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;

import com.appspot.bitlyminous.constant.ApplicationConstants;

/**
 * The Class DeliciousExample.
 */
public class DeliciousExample {
	
	/** The Constant TEST_URLS. */
	private static final String[] TEST_URLS = 
	{
		"http://api.del.icio.us/v2/posts/suggest?url=" + encodeUrl("http://hadoop.apache.org"),
		"http://api.del.icio.us/v2/tags/get",
//		"http://feeds.delicious.com/v2/json/java",
//		"http://feeds.delicious.com/v2/json/popular/java"
		};
	
//	private static final String REFRESH_URL = "https://api.login.yahoo.com/oauth/v2/get_token?oauth_nonce=3423rw3d&oauth_consumer_key=" + OAuth.percentEncode(ApplicationConstants.DELICIOUS_CONSUMER_KEY) + "&oauth_signature_method=plaintext&oauth_signature=" + OAuth.percentEncode(ApplicationConstants.DELICIOUS_CONSUMER_SECRET + "&2cf929568ed679a8d45b58d7679981620e50acc7") + "&oauth_version=1.0&oauth_token=" + OAuth.percentEncode("A=Cchf3j_YiQOHyTwha4s45AZyvkf1VLvl.gPHhSO0.Nm6YO4FQIGt1ZwJQHG79FHpvIfiNhBIuHTt3GnuXb4iaukJZXiL9IBnJkNOGPwcUL_rnp8FAMdf0cttZZm7wAv2PVgSEYkugdxM6KTE1EaAJpdvf9RTDTjLyQIJbZ_z_YV_7s40sWRgxD5pvakasdcq5p.No.t5pwrzHxSeougLP4H9UOWZcjB0EAdKu8AkuSUvl447y7DbRIXOTUfCQhnjshHXA7IqJUU6KheFCkAVjsJNpUQAX1OyUQKFOjKZ7Jd_LVQUiYLgOftmVV6KNlfbXctVRf9MCumi4p92dJfK_9_stUIFYsFDEtZ5ApA5OTyMkWwc9kGa7RQ5rOJaWAbzcOIOp5ebDs7OrnMt8otyyxkqkPNeY5iVaXB17oLYE2WPFShgXl3HplYHTSojKLI0i6iPKymGOU441erVMWw1.46kc8szLIJnak_B1WBl7IUi_3n3aWpMwqKpt5_jPCNIplXazmBYRbmML7S1M84VO5l7G5.fVLda_oVazYctQ88JAwIRVB769lDMtEaVOS.h9RYZFwB28v._oekjI_HP1bzjo_U4LrnldlqPEftVESLISrTk.i_hV9u3AyI3nwDoD64_6oFvGgJmR2sg2LH5h4FQ1W6djnF2yEAP5eLzLvi.aAzI3DENxJwwflhxMPnCRQVXmzSmasZkTKqb6_ZPMXlm2MsoQOTFRED8atXtw.gD") + "&oauth_timestamp=" + System.currentTimeMillis() + "&oauth_session_handle=" + OAuth.percentEncode("sessionhandle");
	

	/**
 * The main method.
 * 
 * @param args the arguments
 * 
 * @throws Exception the exception
 */
	public static void main(String[] args) throws Exception {
    	OAuthConsumer consumer = new DefaultOAuthConsumer(ApplicationConstants.DELICIOUS_CONSUMER_KEY,
    			ApplicationConstants.DELICIOUS_CONSUMER_SECRET);

        OAuthProvider provider = new DefaultOAuthProvider (
                "https://api.login.yahoo.com/oauth/v2/get_request_token",
                "https://api.login.yahoo.com/oauth/v2/get_token",
                "https://api.login.yahoo.com/oauth/v2/request_auth");
				
		provider.setOAuth10a(true);

        System.out.println("Fetching request token from Delicious...");

        // we do not support callbacks, thus pass OOB
        String authUrl = provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND);

        System.out.println("Request token: " + consumer.getToken());
        System.out.println("Token secret: " + consumer.getTokenSecret());

        System.out.println("Now visit:\n" + authUrl
                + "\n... and grant this app authorization");
        System.out.println("Enter the PIN code and hit ENTER when you're done:");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String pin = br.readLine();

        System.out.println("Fetching access token from Delicious...");

        provider.retrieveAccessToken(consumer, pin);

        System.out.println("Access token: " + consumer.getToken());
        System.out.println("Token secret: " + consumer.getTokenSecret());
    	System.out.println("Response params: " + provider.getResponseParameters().entrySet());
    	
//    	consumer.setTokenWithSecret(ApplicationConstants.DELICIOUS_ACCESS_TOKEN, ApplicationConstants.DELICIOUS_ACCESS_TOKEN_SECRET);
    	
    	for (String testUrl : TEST_URLS) {
            URL url = new URL(testUrl);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();

            consumer.sign(request);

            System.out.println("Sending request to Delicious..." + testUrl);
            request.connect();
    		
            if (request.getResponseCode() == 200) {
    	        String responseBody = convertStreamToString(request.getInputStream());
    	        
    	        System.out.println("Response: " + request.getResponseCode() + " "
    	                + request.getResponseMessage() + "\n\n" + responseBody);
    		} else {
    	        String responseBody = convertStreamToString(request.getErrorStream());
    	        
    	        System.out.println("Response: " + request.getResponseCode() + " "
    	                + request.getResponseMessage() + "\n\n" + responseBody);
    		}
            
            System.out.println("==========================================================");
		}
	}
	
    // Stolen liberally from http://www.kodejava.org/examples/266.html
    /**
     * Convert stream to string.
     * 
     * @param is the is
     * 
     * @return the string
     */
    public static String convertStreamToString(InputStream is) {
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
