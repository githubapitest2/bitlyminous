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

/**
 * The Class Venue.
 */
public class Venue {
    
    /** The address. */
    private String address;
    
    /** The city. */
    private String city;
    
    /** The cityid. */
    private String cityid;
    
    /** The crossstreet. */
    private String crossstreet;
    
    /** The distance. */
    private double distance;
    
    /** The geolat. */
    private double geolat;
    
    /** The geolong. */
    private double geolong;
    
    /** The id. */
    private String id;
    
    /** The name. */
    private String name;
    
    /** The phone. */
    private String phone;
    
    /** The state. */
    private String state;
    
    /** The tags. */
    private Tags tags;
    
    /** The twitter. */
    private String twitter;
    
    /** The zip. */
    private String zip;

    /**
     * Instantiates a new venue.
     */
    public Venue() {
    }

    /**
     * Gets the address.
     * 
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     * 
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the city.
     * 
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     * 
     * @param city the new city
     */
    public void setCity(String city) {
    	this.city = city;
    }

    /**
     * Gets the cityid.
     * 
     * @return the cityid
     */
    public String getCityid() {
        return cityid;
    }

    /**
     * Sets the cityid.
     * 
     * @param cityid the new cityid
     */
    public void setCityid(String cityid) {
    	this.cityid = cityid;
    }

    /**
     * Gets the crossstreet.
     * 
     * @return the crossstreet
     */
    public String getCrossstreet() {
        return crossstreet;
    }

    /**
     * Sets the crossstreet.
     * 
     * @param crossstreet the new crossstreet
     */
    public void setCrossstreet(String crossstreet) {
    	this.crossstreet = crossstreet;
    }

    /**
     * Gets the distance.
     * 
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Sets the distance.
     * 
     * @param distance the new distance
     */
    public void setDistance(double distance) {
    	this.distance = distance;
    }

    /**
     * Gets the geolat.
     * 
     * @return the geolat
     */
    public double getGeolat() {
        return geolat;
    }

    /**
     * Sets the geolat.
     * 
     * @param geolat the new geolat
     */
    public void setGeolat(double geolat) {
    	this.geolat = geolat;
    }

    /**
     * Gets the geolong.
     * 
     * @return the geolong
     */
    public double getGeolong() {
        return geolong;
    }

    /**
     * Sets the geolong.
     * 
     * @param geolong the new geolong
     */
    public void setGeolong(double geolong) {
    	this.geolong = geolong;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id the new id
     */
    public void setId(String id) {
    	this.id = id;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name the new name
     */
    public void setName(String name) {
    	this.name = name;
    }

    /**
     * Gets the phone.
     * 
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone.
     * 
     * @param phone the new phone
     */
    public void setPhone(String phone) {
    	this.phone = phone;
    }

    /**
     * Gets the state.
     * 
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     * 
     * @param state the new state
     */
    public void setState(String state) {
    	this.state = state;
    }

    /**
     * Gets the tags.
     * 
     * @return the tags
     */
    public Tags getTags() {
        return tags;
    }

    /**
     * Sets the tags.
     * 
     * @param tags the new tags
     */
    public void setTags(Tags tags) {
    	this.tags = tags;
    }

    /**
     * Gets the twitter.
     * 
     * @return the twitter
     */
    public String getTwitter() {
        return twitter;
    }

    /**
     * Sets the twitter.
     * 
     * @param twitter the new twitter
     */
    public void setTwitter(String twitter) {
    	this.twitter = twitter;
    }

    /**
     * Gets the zip.
     * 
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the zip.
     * 
     * @param zip the new zip
     */
    public void setZip(String zip) {
    	this.zip = zip;
    }
}
