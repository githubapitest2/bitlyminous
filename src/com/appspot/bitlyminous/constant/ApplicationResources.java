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
package com.appspot.bitlyminous.constant;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The Class ApplicationResources.
 */
public class ApplicationResources {

    /** The Constant RESOURCE_BUNDLE_BASE. */
    private static final String RESOURCE_BUNDLE_BASE = "resources.ApplicationResources";    
    
    /** The Constant s_defaultBundle. */
    private static final ResourceBundle s_defaultBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE);    

    /**
     * Instantiates a new application resources.
     */
    private ApplicationResources() {}
    
    

    /**
     * Gets the bundle.
     * 
     * @param locale the locale
     * 
     * @return the bundle
     */
    private static ResourceBundle getBundle(Locale locale) {
        if (locale==null) {
            return s_defaultBundle;
        }
        else {
            return ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE, locale);
        }
    }
    
    
    /**
     * Gets the localized string.
     * 
     * @param key the key
     * 
     * @return the localized string
     */
    public static String getLocalizedString(String key) {
        return getLocalizedString(key, null, (Locale) null);
    }
    
    

    /**
     * Gets the localized string.
     * 
     * @param key the key
     * @param messageArgs the message args
     * 
     * @return the localized string
     */
    public static String getLocalizedString(String key, Serializable messageArgs[]) {
        return getLocalizedString(key, messageArgs, (Locale) null);
    }
    

    
    /**
     * Gets the localized string.
     * 
     * @param key the key
     * @param locale the locale
     * 
     * @return the localized string
     */    
    public static String getLocalizedString(String key, Locale locale) {
        return getLocalizedString(key, null, locale);
    }    
    
    /**
     * Gets the localized string.
     * 
     * @param key the key
     * @param language the language
     * 
     * @return the localized string
     */
    public static String getLocalizedString(String key, String language) {
        return getLocalizedString(key, null, language);
    }    
    
    /**
     * Gets the localized string.
     * 
     * @param key the key
     * @param messageArgs the message args
     * @param language the language
     * 
     * @return the localized string
     */
    public static String getLocalizedString(String key, Serializable messageArgs[], String language) {
    	if (language == null || language.isEmpty()) {
        	return getLocalizedString(key, messageArgs, (Locale) null);
    	} else {
        	return getLocalizedString(key, messageArgs, new Locale(language));
    	}
    }
    
    /**
     * Gets the localized string.
     * 
     * @param key the key
     * @param messageArgs the message args
     * @param locale the locale
     * 
     * @return the localized string
     */
    public static String getLocalizedString(String key, Serializable messageArgs[], Locale locale) {
        try {
            String msg = getBundle(locale).getString( key );

            // Now delegate formatting to Java.
            return MessageFormat.format(msg, (Object[])messageArgs);
        } catch (MissingResourceException e) {
            List<?> args = (messageArgs == null) ? null : Arrays.asList(messageArgs);
            return "!MISSING MESSAGE TEXT " + key + "! Args: " + args; 
        } catch (Exception e) {
            List<?> args = (messageArgs == null) ? null : Arrays.asList(messageArgs);
            return "!INVALID MESSAGE TEXT PATTERN " + key + "! Args: " + args; 
        }
    }     
}
