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
package com.appspot.bitlyminous.command.async;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.bitlyminous.constant.ApplicationConstants;
import com.appspot.bitlyminous.dao.NamedQueries;
import com.appspot.bitlyminous.entity.Blacklist;
import com.appspot.bitlyminous.entity.Malware;
import com.appspot.bitlyminous.entity.Version;

/**
 * The Class UploadSafeBrowsingData.
 */
public class UploadSafeBrowsingData extends AbstractAsyncCommand {

	/* (non-Javadoc)
	 * @see com.appspot.bitlyminous.command.servlet.ServletCommand#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		long blacklistMinor = Integer.parseInt(req.getParameter("blacklistMinor"));
		long blacklistMajor = Integer.parseInt(req.getParameter("blacklistMajor"));
		long malwareMinor = Integer.parseInt(req.getParameter("malwareMinor"));
		long malwareMajor = Integer.parseInt(req.getParameter("malwareMajor"));
//		logger.warning("Updating blacklist.");
//		deleteBlacklist();
//		logger.warning("Updating malware.");
//		deleteMalware();
		logger.warning("Updating version.");
		updateVersion(blacklistMinor, blacklistMajor, malwareMinor, malwareMajor);
	}

	/**
	 * Update blacklist.
	 */
	private void updateBlacklist() {
		EntityManager entityManager = null;
		try {
			entityManager = createEntityManager();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(getClass().getClassLoader().getResourceAsStream("resources/data/blacklist.csv.gz")), ApplicationConstants.CONTENT_ENCODING));
			String line = null;
			while ((line = reader.readLine()) != null) {
				Blacklist blacklist = new Blacklist();
				blacklist.setHash(line);
				entityManager.persist(blacklist);
			}
			reader.close();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while uploading blacklist.", e);
		} finally {
			entityManager.close();
		}
	}

	/**
	 * Delete blacklist.
	 */
	private void deleteBlacklist() {
		EntityManager entityManager = null;
		try {
			entityManager = createEntityManager();
			Query query = entityManager.createNamedQuery(NamedQueries.DELETE_ALL_BLACKLIST);
			query.executeUpdate();
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while uploading blacklist.", e);
		} finally {
			entityManager.close();
		}
	}
	
	/**
	 * Update malware.
	 */
	private void updateMalware() {
		EntityManager entityManager = null;
		try {
			entityManager = createEntityManager();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(getClass().getClassLoader().getResourceAsStream("resources/data/malware.csv.gz")), ApplicationConstants.CONTENT_ENCODING));
			String line = null;
			while ((line = reader.readLine()) != null) {
				Malware malware = new Malware();
				malware.setHash(line);
				entityManager.persist(malware);
			}
			reader.close();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while uploading malware.", e);
		} finally {
			entityManager.close();
		}
	}

	/**
	 * Delete malware.
	 */
	private void deleteMalware() {
		EntityManager entityManager = null;
		try {
			entityManager = createEntityManager();
			
			entityManager = createEntityManager();
			Query query = entityManager.createNamedQuery(NamedQueries.DELETE_ALL_MALWARE);
			query.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while uploading malware.", e);
		} finally {
			entityManager.close();
		}
	}
	
	/**
	 * Update version.
	 * 
	 * @param blacklistMinor the blacklist minor
	 * @param blacklistMajor the blacklist major
	 * @param malwareMinor the malware minor
	 * @param malwareMajor the malware major
	 */
	private void updateVersion(Long blacklistMinor, Long blacklistMajor, Long malwareMinor, Long malwareMajor) {
		EntityManager entityManager = null;
		try {
			entityManager = createEntityManager();
			
			Version v = getCurrentVersion();
			v.setBlacklistMinorVersion(blacklistMinor);
			v.setBlacklistMajorVersion(blacklistMajor);
			v.setMalwareMinorVersion(malwareMinor);
			v.setMalwareMajorVersion(malwareMajor);
			v.setLastHomeTweetId(1L);
			v.setLastSearchTweetId(1L);
			v.setLastMentionId(1L);
			entityManager.persist(v);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while uploading safe browsing data.", e);
		} finally {
			entityManager.close();
		}
	}
}
