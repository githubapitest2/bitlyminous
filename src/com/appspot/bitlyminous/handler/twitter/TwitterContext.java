package com.appspot.bitlyminous.handler.twitter;

import java.util.ArrayList;
import java.util.List;

import com.appspot.bitlyminous.entity.Url;
import com.appspot.bitlyminous.entity.User;
import com.appspot.bitlyminous.entity.Version;

public class TwitterContext {
	private User user;
	private Version version;
	private List<Url> urls = new ArrayList<Url>();
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the version
	 */
	public Version getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Version version) {
		this.version = version;
	}
	/**
	 * @return the urls
	 */
	public List<Url> getUrls() {
		return urls;
	}
	/**
	 * @param urls the urls to set
	 */
	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}
}
