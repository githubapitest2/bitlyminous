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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import uk.co.mccann.gsb.engine.util.URLUtils;
import uk.co.mccann.gsb.exceptions.GSBException;
import uk.co.mccann.gsb.interfaces.GSBDAO;
import uk.co.mccann.gsb.interfaces.GSBEngine;
import uk.co.mccann.gsb.interfaces.GSBEngineConfiguration;
import uk.co.mccann.gsb.interfaces.GSBSession;
import uk.co.mccann.gsb.model.CheckURL;
import uk.co.mccann.gsb.model.ListURL;
import uk.co.mccann.gsb.model.SessionImpl;

import com.appspot.bitlyminous.dao.EMF;
import com.appspot.bitlyminous.dao.NamedQueries;
import com.appspot.bitlyminous.entity.Blacklist;
import com.appspot.bitlyminous.entity.Malware;
import com.appspot.bitlyminous.entity.Version;

/**
 * The Class GoogleSafeBrowsingGateway.
 */
public class GoogleSafeBrowsingGateway implements GSBEngine {
	
    /** The Constant logger. */
    protected static final Logger logger = Logger.getLogger(GoogleSafeBrowsingGateway.class.getCanonicalName());
	
	/** The dao. */
	private AppEngineDAO dao = new AppEngineDAO();

	/** The config. */
	private GSBEngineConfiguration config;
	
	/** The session. */
	private GSBSession session;

	/**
	 * Instantiates a new google safe browsing gateway.
	 * 
	 * @param apiKey the api key
	 * @param useKey the use key
	 */
	public GoogleSafeBrowsingGateway(String apiKey, boolean useKey) {
		
		try {
			GSBEngineConfiguration config = new AppEngineConfigImpl(dao);
			config.setAPIKey(apiKey);
			config.useKey(useKey);
			this.config = config;

			/* create a new session */
			this.session = new SessionImpl();
			this.session.startSession(this.config);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while starting session.", e);
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.mccann.gsb.interfaces.GSBEngine#isBlacklisted(java.lang.String)
	 */
	public boolean isBlacklisted(String url) throws GSBException {
		URLUtils urlutils = new URLUtils();
		ArrayList<CheckURL> urls = urlutils.getLookupURLs(url);

		for (CheckURL checkURL : urls) {
			if (this.dao.locateBlacklistHash(checkURL.getMD5Hash())) {
				return true;
			}
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see uk.co.mccann.gsb.interfaces.GSBEngine#isMalwarelisted(java.lang.String)
	 */
	public boolean isMalwarelisted(String url) throws GSBException {
		URLUtils urlutils = new URLUtils();
		ArrayList<CheckURL> urls = urlutils.getLookupURLs(url);

		for (CheckURL checkURL : urls) {

			if (this.dao.locateMalwareHash(checkURL.getMD5Hash())) {
				return true;
			}
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see uk.co.mccann.gsb.interfaces.GSBEngine#isDangerous(java.lang.String)
	 */
	public boolean isDangerous(String url) throws GSBException {

		/* update lists */
		// this.updateBlacklist();
		// this.updateMalwarelist();
		URLUtils urlutils = new URLUtils();
		ArrayList<CheckURL> urls = urlutils.getLookupURLs(url);

		for (CheckURL checkURL : urls) {
			if (this.dao.locateBlacklistHash(checkURL.getMD5Hash())
					|| this.dao.locateMalwareHash(checkURL.getMD5Hash())) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see uk.co.mccann.gsb.interfaces.GSBEngine#updateBlacklist()
	 */
	public void updateBlacklist() {

		if (this.dao != null) {
			try {
				HashMap<String, ListURL> data = this.session.getBlacklist();
				if (this.session.isBlacklistUpdated()) {
					if (data.size() > 0) {
					}
				} else {
					if (data.size() > 0) {
						this.dao.replaceBlacklist(data, this.config);
					}
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error while updating malware.", e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.mccann.gsb.interfaces.GSBEngine#updateMalwarelist()
	 */
	public void updateMalwarelist() {
		if (this.dao != null) {
			try {
				HashMap<String, ListURL> data = this.session.getMalwarelist();
				if (this.session.isMalwareUpdated()) {
					if (data.size() > 0) {
					}
				} else {
					if (data.size() > 0) {
						this.dao.replaceMalwarelist(data, this.config);
					}
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error while updating malware.", e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.mccann.gsb.interfaces.GSBEngine#registerDAO(uk.co.mccann.gsb.interfaces.GSBDAO)
	 */
	public void registerDAO(GSBDAO dao) {
	}
	
	/**
	 * The Class AppEngineDAO.
	 */
	public static class AppEngineDAO implements GSBDAO {
		
		/**
		 * Instantiates a new app engine dao.
		 */
		public AppEngineDAO() {
		}
		
		
		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBDAO#readBlacklist(uk.co.mccann.gsb.interfaces.GSBEngineConfiguration)
		 */
		@SuppressWarnings("unchecked")
		public HashMap<String, ListURL> readBlacklist(GSBEngineConfiguration config) {
			HashMap<String, ListURL> dataMap = new HashMap<String, ListURL>();
			EntityManager entityManager = null;
			try {
				entityManager = EMF.get().createEntityManager();
				
				Query query = entityManager.createNamedQuery(NamedQueries.FIND_ALL_BLACKLIST);
				List<Blacklist> result = query.getResultList();
				
				for (Blacklist blacklist : result) {
					String hash = blacklist.getHash();
					dataMap.put(hash, new ListURL(hash));
				} 
			} catch (Exception exp) {
				logger.log(Level.SEVERE, "Unable to read blacklist: ", exp);
			} finally {
				entityManager.close();
			}
			
			return dataMap;
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBDAO#readMalwarelist(uk.co.mccann.gsb.interfaces.GSBEngineConfiguration)
		 */
		@SuppressWarnings("unchecked")
		public HashMap<String, ListURL> readMalwarelist(GSBEngineConfiguration config) {
			EntityManager entityManager = null;
			HashMap<String, ListURL> dataMap = new HashMap<String, ListURL>();
			try {
				entityManager = EMF.get().createEntityManager();
				
				Query query = entityManager.createNamedQuery(NamedQueries.FIND_ALL_MALWARE);
				List<Malware> result = query.getResultList();
				
				for (Malware malware : result) {
					String hash = malware.getHash();
					dataMap.put(hash, new ListURL(hash));
				} 
			} catch (Exception exp) {
				logger.log(Level.SEVERE, "Unable to read malware: ", exp);
			} finally {
				entityManager.close();
			}
			return dataMap;
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBDAO#replaceBlacklist(java.util.HashMap, uk.co.mccann.gsb.interfaces.GSBEngineConfiguration)
		 */
		public void replaceBlacklist(HashMap<String, ListURL> list, GSBEngineConfiguration config) {
			
			EntityManager entityManager = null;
			try {
				entityManager = EMF.get().createEntityManager();
				
				Query query = entityManager.createNamedQuery(NamedQueries.DELETE_ALL_BLACKLIST);
				query.executeUpdate();
				
				ListURL url;
				for(String key : list.keySet()) {
					url = list.get(key);
					Blacklist blacklist = new Blacklist();
					blacklist.setHash(url.getMD5Hash());
					entityManager.persist(blacklist);
				}
			} catch (Exception exp) {
				logger.log(Level.SEVERE, "Unable to replace blacklist: ", exp);
			} finally {
				entityManager.close();
			}
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBDAO#replaceMalwarelist(java.util.HashMap, uk.co.mccann.gsb.interfaces.GSBEngineConfiguration)
		 */
		public void replaceMalwarelist(HashMap<String, ListURL> list, GSBEngineConfiguration config) {
			EntityManager entityManager = null;
			try {
				entityManager = EMF.get().createEntityManager();
				
				Query query = entityManager.createNamedQuery(NamedQueries.DELETE_ALL_MALWARE);
				query.executeUpdate();
				
				ListURL url;
				for(String key : list.keySet()) {
					url = list.get(key);
					Malware malware = new Malware();
					malware.setHash(url.getMD5Hash());
					entityManager.persist(malware);
				}
			} catch (Exception exp) {
				logger.log(Level.SEVERE, "Unable to replace malware: ", exp);
			} finally {
				entityManager.close();
			}
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBDAO#updateBlacklist(java.util.HashMap, uk.co.mccann.gsb.interfaces.GSBEngineConfiguration)
		 */
		public void updateBlacklist(HashMap<String, ListURL> list, GSBEngineConfiguration config) {
			EntityManager entityManager = null;
			try {
				entityManager = EMF.get().createEntityManager();
				
				Query query = entityManager.createNamedQuery(NamedQueries.DELETE_BLACKLIST_BY_HASH);
				
				ListURL url;
				for(String key : list.keySet()) {
					url = list.get(key);
					
					if(url.isAdded()) {
						Blacklist blacklist = new Blacklist();
						blacklist.setHash(url.getMD5Hash());
						entityManager.persist(blacklist);
					}
					if(url.isRemoved()) {
						query.setParameter("hash", url.getMD5Hash());
						query.executeUpdate();
					}
				}
			} catch (Exception exp) {
				logger.log(Level.SEVERE, "Unable to update blacklist: ", exp);
			} finally {
				entityManager.close();
			}
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBDAO#updateMalwarelist(java.util.HashMap, uk.co.mccann.gsb.interfaces.GSBEngineConfiguration)
		 */
		public void updateMalwarelist(HashMap<String, ListURL> list, GSBEngineConfiguration config) {
			EntityManager entityManager = null;
			try {
				entityManager = EMF.get().createEntityManager();
				
				Query query = entityManager.createNamedQuery(NamedQueries.DELETE_MALWARE_BY_HASH);
				
				ListURL url;
				for(String key : list.keySet()) {
					url = list.get(key);
					
					if(url.isAdded()) {
						Malware malware = new Malware();
						malware.setHash(url.getMD5Hash());
						entityManager.persist(malware);
					}
					if(url.isRemoved()) {
						query.setParameter("hash", url.getMD5Hash());
						query.executeUpdate();
					}
				}
			} catch (Exception exp) {
				logger.log(Level.SEVERE, "Unable to update malware: ", exp);
			} finally {
				entityManager.close();
			}
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBDAO#locateBlacklistHash(java.lang.String)
		 */
		public boolean locateBlacklistHash(String hashValue) {
			EntityManager entityManager = null;
			try {
				entityManager = EMF.get().createEntityManager();
				Query query = entityManager.createNamedQuery(NamedQueries.FIND_BLACKLIST_BY_HASH);
				query.setParameter("hash", hashValue);
				Object result = query.getSingleResult();
				if (result != null) {
					return true;
				} 
				return false;
			} catch (Exception exp) {
				return false;
			} finally {
				entityManager.close();
			}
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBDAO#locateMalwareHash(java.lang.String)
		 */
		public boolean locateMalwareHash(String hashValue) {
			EntityManager entityManager = null;
			try {
				entityManager = EMF.get().createEntityManager();
				Query query = entityManager.createNamedQuery(NamedQueries.FIND_MALWARE_BY_HASH);
				query.setParameter("hash", hashValue);
				Object result = query.getSingleResult();
				if (result != null) {
					return true;
				} 
				return false;
			} catch (Exception exp) {
				return false;
			} finally {
				entityManager.close();
			}
		}
		
		/**
		 * Save properties.
		 * 
		 * @param properties the properties
		 */
		public void saveProperties(Properties properties) {
			EntityManager entityManager = null;
			try {
				entityManager = EMF.get().createEntityManager();
				Query query = entityManager.createNamedQuery(NamedQueries.FIND_CURRENT_VERSION);
				Version version = (Version) query.getSingleResult();
				if (version != null) {
					version.setBlacklistMajorVersion(Long.parseLong(properties.getProperty("gsb.blacklist.version.major")));
					version.setBlacklistMinorVersion(Long.parseLong(properties.getProperty("gsb.blacklist.version.minor")));
					version.setMalwareMajorVersion(Long.parseLong(properties.getProperty("gsb.malware.version.major")));
					version.setMalwareMinorVersion(Long.parseLong(properties.getProperty("gsb.malware.version.minor")));
				} 
			} catch (Exception exp) {
				logger.log(Level.SEVERE, "Unable to save version: ", exp);
			} finally {
				entityManager.close();
			}
		}

		/**
		 * Load properties.
		 * 
		 * @param properties the properties
		 */
		public void loadProperties(Properties properties) {
			EntityManager entityManager = null;
			try {
				entityManager = EMF.get().createEntityManager();
				Query query = entityManager.createNamedQuery(NamedQueries.FIND_CURRENT_VERSION);
				Version version = (Version) query.getSingleResult();
				if (version != null) {
					properties.setProperty("gsb.blacklist.version.major", String.valueOf(version.getBlacklistMajorVersion()));
					properties.setProperty("gsb.blacklist.version.minor", String.valueOf(version.getBlacklistMinorVersion()));
					properties.setProperty("gsb.malware.version.major", String.valueOf(version.getMalwareMajorVersion()));
					properties.setProperty("gsb.malware.version.minor", String.valueOf(version.getMalwareMinorVersion()));
				} 
			} catch (Exception exp) {
				logger.log(Level.SEVERE, "Unable to load version: ", exp);
			} finally {
				entityManager.close();
			}
		}
	}
	
	/**
	 * The Class AppEngineConfigImpl.
	 */
	public static class AppEngineConfigImpl implements GSBEngineConfiguration {
		
		/** The api key. */
		private String apiKey;
		
		/** The use key. */
		private boolean useKey;
		
		/** The properties file. */
		private Properties propertiesFile;
		
		/** The dao. */
		private AppEngineDAO dao;
		
		/**
		 * Instantiates a new app engine config impl.
		 * 
		 * @param dao the dao
		 */
		public AppEngineConfigImpl(AppEngineDAO dao) {
			this.dao = dao;
			this.propertiesFile = new Properties();
	        try {
	        	propertiesFile.load(
	        			GoogleSafeBrowsingGateway.class.getResourceAsStream("gsb.properties"));
	        } catch (IOException e) {
	            logger.log(Level.SEVERE, "An error occurred while loading properties.", e);
	        }
			dao.loadProperties(this.propertiesFile);
		}
		
		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBEngineConfiguration#setAPIKey(java.lang.String)
		 */
		public void setAPIKey(String apikey) {
			this.apiKey = apikey;
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBEngineConfiguration#setPropertiesFile(java.lang.String)
		 */
		public void setPropertiesFile(String properties) {
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBEngineConfiguration#useKey(boolean)
		 */
		public void useKey(boolean useKey) {
			this.useKey = useKey;
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBEngineConfiguration#isUseKey()
		 */
		public boolean isUseKey() {
			if(this.useKey) return true;
			return false;
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBEngineConfiguration#getPropertiesFile()
		 */
		public Properties getPropertiesFile() {
			return this.propertiesFile;
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBEngineConfiguration#getAPIKey()
		 */
		public String getAPIKey() {
			return this.apiKey;
		}
		
		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBEngineConfiguration#savePropertiesFile()
		 */
		public void savePropertiesFile() throws IOException {
			dao.saveProperties(this.propertiesFile);
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBEngineConfiguration#getDataStoreDirectory()
		 */
		public String getDataStoreDirectory() {
			return null;
		}

		/* (non-Javadoc)
		 * @see uk.co.mccann.gsb.interfaces.GSBEngineConfiguration#setDataStoreDirectory(java.lang.String)
		 */
		public void setDataStoreDirectory(String directory) {
		}
	}
}
