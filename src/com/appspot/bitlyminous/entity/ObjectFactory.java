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

package com.appspot.bitlyminous.entity;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * A factory for creating Object objects.
 */
@XmlRegistry
public class ObjectFactory {

    /** The Constant _Popular_QNAME. */
    private final static QName _Popular_QNAME = new QName("", "popular");
    
    /** The Constant _Recommended_QNAME. */
    private final static QName _Recommended_QNAME = new QName("", "recommended");

    /**
     * Instantiates a new object factory.
     */
    public ObjectFactory() {
    }

    /**
     * Creates a new Object object.
     * 
     * @return the suggest
     */
    public Suggest createSuggest() {
        return new Suggest();
    }

    /**
     * Creates a new Object object.
     * 
     * @return the tag
     */
    public Tag createTag() {
        return new Tag();
    }

    /**
     * Creates a new Object object.
     * 
     * @return the tags
     */
    public Tags createTags() {
        return new Tags();
    }

    /**
     * Creates a new Object object.
     * 
     * @param value the value
     * 
     * @return the JAXB element< string>
     */
    @XmlElementDecl(namespace = "", name = "popular")
    public JAXBElement<String> createPopular(String value) {
        return new JAXBElement<String>(_Popular_QNAME, String.class, null, value);
    }

    /**
     * Creates a new Object object.
     * 
     * @param value the value
     * 
     * @return the JAXB element< string>
     */
    @XmlElementDecl(namespace = "", name = "recommended")
    public JAXBElement<String> createRecommended(String value) {
        return new JAXBElement<String>(_Recommended_QNAME, String.class, null, value);
    }

}
