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

import com.appspot.bitlyminous.constant.ApplicationConstants;

/**
 * The Class GoogleStaticMapsGateway.
 */
public class GoogleStaticMapsGateway {

	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// constants
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	/** The Constant GmapStaticURI. */
	public static final String GmapStaticURI = "http://maps.google.com/staticmap";
	
	/** The Constant GmapLicenseKey. */
	public static final String GmapLicenseKey = "key";

	/** The Constant CenterKey. */
	public static final String CenterKey = "center";

	/** The Constant ZoomKey. */
	public static final String ZoomKey = "zoom";
	
	/** The Constant ZoomMax. */
	public static final int ZoomMax = 19;
	
	/** The Constant ZoomMin. */
	public static final int ZoomMin = 0;
	
	/** The Constant ZoomDefault. */
	public static final int ZoomDefault = 10;

	/** The Constant SizeKey. */
	public static final String SizeKey = "size";
	
	/** The Constant SizeSeparator. */
	public static final String SizeSeparator = "x";
	
	/** The Constant SizeMin. */
	public static final int SizeMin = 10;
	
	/** The Constant SizeMax. */
	public static final int SizeMax = 512;
	
	/** The Constant SizeDefault. */
	public static final int SizeDefault = SizeMax;

	/** The Constant MarkerSeparator. */
	public static final String MarkerSeparator = "|";
	
	/** The Constant MarkersKey. */
	public static final String MarkersKey = "markers";

	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// data
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	/** The Gmap license. */
	public String GmapLicense = "";

	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// set the license key
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	/**
	 * Instantiates a new google static maps gateway.
	 * 
	 * @param lic the lic
	 */
	public GoogleStaticMapsGateway(String lic) {
		GmapLicense = lic;
	}

	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// methods
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	/**
	 * Gets the map.
	 * 
	 * @param lat the lat
	 * @param lon the lon
	 * 
	 * @return the map
	 */
	public String getMap(double lat, double lon) {
		return getMap(lat, lon, SizeMax, SizeMax);
	}

	/**
	 * Gets the map.
	 * 
	 * @param lat the lat
	 * @param lon the lon
	 * @param sizeW the size w
	 * @param sizeH the size h
	 * 
	 * @return the map
	 */
	public String getMap(double lat, double lon, int sizeW, int sizeH) {
		return getMap(lat, lon, sizeW, sizeH, ZoomDefault);
	}

	/**
	 * Gets the map.
	 * 
	 * @param lat the lat
	 * @param lon the lon
	 * @param sizeW the size w
	 * @param sizeH the size h
	 * @param zoom the zoom
	 * 
	 * @return the map
	 */
	public String getMap(double lat, double lon, int sizeW, int sizeH,
			int zoom) {
		return getURI(lat, lon, sizeW, sizeH, zoom);
	}

	/**
	 * Gets the map.
	 * 
	 * @param lat the lat
	 * @param lon the lon
	 * @param sizeW the size w
	 * @param sizeH the size h
	 * @param markers the markers
	 * 
	 * @return the map
	 */
	public String getMap(double lat, double lon, int sizeW, int sizeH,
			MapMarker... markers) {
		return getURI(lat, lon, sizeW, sizeH, markers);
	}

	/**
	 * Gets the map.
	 * 
	 * @param lat the lat
	 * @param lon the lon
	 * @param markers the markers
	 * 
	 * @return the map
	 */
	public String getMap(double lat, double lon, MapMarker... markers) {
		return getMap(lat, lon, SizeMax, SizeMax, markers);
	}

	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// param handling and uri generation
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	/**
	 * Gets the uRI.
	 * 
	 * @param lat the lat
	 * @param lon the lon
	 * @param sizeW the size w
	 * @param sizeH the size h
	 * @param markers the markers
	 * 
	 * @return the uRI
	 */
	public String getURI(double lat, double lon, int sizeW, int sizeH,
			MapMarker... markers) {
		_validateParams(sizeW, sizeH, ZoomDefault);

		// generate the URI
		StringBuilder sb = new StringBuilder();
		sb.append(GmapStaticURI);

		// size key
		sb.append("?").append(SizeKey).append("=").append(sizeW).append(
				SizeSeparator).append(sizeH);

		// markers key
		sb.append("&").append(MarkerUtils.toString(markers));

		// maps key
		sb.append("&").append(GmapLicenseKey).append("=").append(GmapLicense);

		return sb.toString();
	}

	/**
	 * Gets the uRI.
	 * 
	 * @param lat the lat
	 * @param lon the lon
	 * @param sizeW the size w
	 * @param sizeH the size h
	 * @param zoom the zoom
	 * 
	 * @return the uRI
	 */
	public String getURI(double lat, double lon, int sizeW, int sizeH, int zoom) {
		_validateParams(sizeW, sizeH, zoom);

		// generate the URI
		StringBuilder sb = new StringBuilder();
		sb.append(GmapStaticURI);

		// center key
		sb.append("?").append(CenterKey).append("=").append(lat).append(",")
				.append(lon);

		// zoom key
		sb.append("&").append(ZoomKey).append("=").append(zoom);

		// size key
		sb.append("&").append(SizeKey).append("=").append(sizeW).append(
				SizeSeparator).append(sizeH);

		// markers key
		sb.append("&").append(MarkerUtils.toString(new MapMarker(lat, lon)));

		// maps key
		sb.append("&").append(GmapLicenseKey).append("=").append(GmapLicense);

		return sb.toString();
	}

	/**
	 * _validate params.
	 * 
	 * @param sizeW the size w
	 * @param sizeH the size h
	 * @param zoom the zoom
	 */
	private void _validateParams(int sizeW, int sizeH, int zoom) {
		if (zoom < ZoomMin || zoom > ZoomMax)
			throw new IllegalArgumentException("zoom value is out of range ["
					+ ZoomMin + "-" + ZoomMax + "]");

		if (sizeW < SizeMin || sizeW > SizeMax)
			throw new IllegalArgumentException("width is out of range ["
					+ SizeMin + "-" + SizeMax + "]");

		if (sizeH < SizeMin || sizeH > SizeMax)
			throw new IllegalArgumentException("height is out of range ["
					+ SizeMin + "-" + SizeMax + "]");
	}

	/**
	 * The Class MarkerUtils.
	 */
	public static class MarkerUtils {
		
		/**
		 * To string.
		 * 
		 * @param markers the markers
		 * 
		 * @return the string
		 */
		public static String toString(MapMarker... markers) {
			if (markers.length > 0) {
				StringBuilder sb = new StringBuilder();

				sb.append(MarkersKey).append("=");

				for (int i = 0; i < markers.length; i++) {
					sb.append(markers[i].toString());
					if (i != markers.length - 1)
						sb.append(MarkerSeparator);
				}

				return sb.toString();
			} else {
				return "";
			}
		}
	}// class MarkerUtils

	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// self test method
	// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		GoogleStaticMapsGateway map = new GoogleStaticMapsGateway(ApplicationConstants.GOOGLE_API_KEY);

		double lat = 38.931099;
		double lon = -77.3489;

		double lat1 = 40.742100;
		double lon1 = -74.001801;

		String u1 = map.getMap(lat, lon);
		System.out.println(u1);

		String u2 = map.getMap(lat, lon, 256, 256);
		System.out.println(u2);

		String u3 = map.getMap(lat, lon, new MapMarker(lat, lon,
				MapMarker.MarkerColor.blue, 'a'));
		System.out.println(u3);

		String u4 = map.getMap(lat, lon, 250, 500, new MapMarker(lat, lon,
				MapMarker.MarkerColor.green, 'v'), new MapMarker(lat1, lon1,
				MapMarker.MarkerColor.red, 'n'));
		System.out.println(u4);

	}

}// end class MapLookup
