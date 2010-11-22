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

import static com.rosaloves.bitlyj.Bitly.*;


import com.rosaloves.bitlyj.ShortenedUrl;
import com.rosaloves.bitlyj.Url;
import com.rosaloves.bitlyj.UrlClicks;
import com.rosaloves.bitlyj.UrlInfo;
import com.rosaloves.bitlyj.Bitly.Provider;

/**
 * The Class BitlyExample.
 */
public class BitlyExample {

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Provider bitly = as("bitlyminous", "R_1ff9cacf41194bcaa13b8ca7ba0985d9");
		ShortenedUrl shortUrl = bitly.call(shorten("http://www.socialsignal.com/blog/alexandra-samuel/3-great-options-twitter-and-delicious-integration"));
		System.out.println(shortUrl.getShortUrl());
		UrlClicks clicks = bitly.call(clicks(shortUrl.getShortUrl()));
		System.out.println(clicks.getGlobalClicks() + ":" + clicks.getUserClicks());
		UrlInfo info = bitly.call(info(shortUrl.getShortUrl()));
		System.out.println(info.getTitle() + ":" + info.getCreatedBy());
		Url expand = bitly.call(expand("http://bit.ly/9tdhzL"));
		System.out.println(expand.getLongUrl());
		clicks = bitly.call(clicks("http://bit.ly/9tdhzL"));
		System.out.println(clicks.getGlobalClicks() + ":" + clicks.getUserClicks());
		info = bitly.call(info("http://bit.ly/9tdhzL"));
		System.out.println(info.getTitle() + ":" + info.getCreatedBy());		
	}
}
