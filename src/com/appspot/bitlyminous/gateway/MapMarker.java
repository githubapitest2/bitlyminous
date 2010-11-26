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

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * The Class MapMarker.
 */
public class MapMarker implements Serializable {
	
	/** The Constant serialVersionUID. */
	static final long serialVersionUID = 5805831996822361347L;

	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// enum for marker colors
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	/**
	 * The Enum MarkerColor.
	 */
	public enum MarkerColor {
		
		/** The red. */
		red, 
 
 /** The green. */
 green, 
 
 /** The blue. */
 blue
	}// enum MarkerColor

	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// data
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	/** The _alpha. */
	private char _alpha = '1';
	
	/** The _color. */
	private MarkerColor _color = null;
	
	/** The _lat. */
	private double _lat = -1;
	
	/** The _lon. */
	private double _lon = -1;

	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// constructor
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	/**
	 * Instantiates a new map marker.
	 * 
	 * @param lat the lat
	 * @param lon the lon
	 * @param color the color
	 * @param alpha the alpha
	 */
	public MapMarker(double lat, double lon, MarkerColor color, char alpha) {
		_lat = lat;
		_lon = lon;
		_color = color;
		_alpha = alpha;

		StringBuffer buf = new StringBuffer();
		buf.append(alpha);
		if (!Pattern.matches("[a-zA-Z]", buf))
			throw new IllegalArgumentException(
					"marker alpha is not a char between a-z");

		if (color == null)
			throw new IllegalArgumentException("marker color can not be null");
	}

	/**
	 * Instantiates a new map marker.
	 * 
	 * @param lat the lat
	 * @param lon the lon
	 */
	public MapMarker(double lat, double lon) {
		_lat = lat;
		_lon = lon;
	}

	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// generate Google Maps uri
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(_lat).append(",").append(_lon);

		if (_color != null && _alpha != '1') {
			sb.append(",").append(_color.toString()).append(_alpha);
		}

		return sb.toString();
	}

}// class Marker
