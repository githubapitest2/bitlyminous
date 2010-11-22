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

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * The Class Tag.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "tag")
public class Tag implements Serializable
{

    /** The Constant serialVersionUID. */
    private final static long serialVersionUID = 2461660169443089969L;
    
    /** The count. */
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "integer")
    protected Long count;
    
    /** The tag. */
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String tag;

    /**
     * Gets the count.
     * 
     * @return the count
     */
    public Long getCount() {
        return count;
    }

    /**
     * Sets the count.
     * 
     * @param value the new count
     */
    public void setCount(Long value) {
        this.count = value;
    }

    /**
     * Gets the tag.
     * 
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the tag.
     * 
     * @param value the new tag
     */
    public void setTag(String value) {
        this.tag = value;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tag [count=" + count + ", tag=" + tag + "]";
	}
}
