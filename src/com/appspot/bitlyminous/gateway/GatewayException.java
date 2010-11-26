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

/**
 * The Class GatewayException.
 */
public class GatewayException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2392119987027760999L;
	
	/** The error code. */
	private int errorCode;

	/**
	 * Instantiates a new gateway exception.
	 */
	public GatewayException() {}

	/**
	 * Instantiates a new gateway exception.
	 * 
	 * @param message the message
	 */
	public GatewayException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new gateway exception.
	 * 
	 * @param cause the cause
	 */
	public GatewayException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new gateway exception.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public GatewayException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Gets the error code.
	 * 
	 * @return the error code
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 * 
	 * @param errorCode the new error code
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
