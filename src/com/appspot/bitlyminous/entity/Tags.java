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
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * The Class Tags.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tag"
})
@XmlRootElement(name = "tags")
public class Tags implements Serializable
{

    /** The Constant serialVersionUID. */
    private final static long serialVersionUID = 2461660169443089969L;
    
    /** The tag. */
    @XmlElement(required = true)
    protected List<Tag> tag;

    /**
     * Gets the tag.
     * 
     * @return the tag
     */
    public List<Tag> getTag() {
        if (tag == null) {
            tag = new ArrayList<Tag>();
        }
        return this.tag;
    }

}
