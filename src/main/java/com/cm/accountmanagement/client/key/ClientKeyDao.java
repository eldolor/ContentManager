package com.cm.accountmanagement.client.key;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Component;

import com.cm.util.PMF;

@Component
class ClientKeyDao {
	private static final Logger LOGGER = Logger.getLogger(ClientKeyDao.class
			.getName());

	List<ClientKey> getClientKeys(Long accountId) {
		PersistenceManager pm = null;

		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(ClientKey.class);
			q.setFilter("accountId == accountIdParam && deleted == deletedParam");
			q.declareParameters("String accountIdParam, Boolean deletedParam");
			q.setOrdering("timeCreatedMs desc");
			return (List<ClientKey>) q.execute(accountId,
					Boolean.valueOf(false));
		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	List<ClientKey> getClientKeys(Long accountId, boolean includeDeleted) {
		PersistenceManager pm = null;

		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(ClientKey.class);
			if (includeDeleted) {
				q.setFilter("accountId == accountIdParam");
				q.declareParameters("String accountIdParam");
				q.setOrdering("timeCreatedMs desc");
				return (List<ClientKey>) q.execute(accountId);
			} else {
				q.setFilter("accountId == accountIdParam && deleted == deletedParam");
				q.declareParameters("String accountIdParam, Boolean deletedParam");
				q.setOrdering("timeCreatedMs desc");
				return (List<ClientKey>) q.execute(accountId,
						Boolean.valueOf(false));
			}
		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	ClientKey add(ClientKey clientKey) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			return pm.makePersistent(clientKey);

		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	ClientKey get(Long id) {
		PersistenceManager pm = null;

		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			ClientKey _account = pm.getObjectById(ClientKey.class, id);
			return _account;

		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	void delete(Long id, Long timeUpdatedMs, Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering delete");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				ClientKey lClientKey = pm.getObjectById(ClientKey.class, id);
				if (lClientKey != null) {
					lClientKey.setDeleted(true);
					lClientKey.setTimeUpdatedMs(timeUpdatedMs);
					lClientKey
							.setTimeUpdatedTimeZoneOffsetMs(timeUpdatedTimeZoneOffsetMs);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(id + " ClientKey marked for deletion");
				} else {
					LOGGER.log(Level.WARNING, id + "  CLIENT KEY NOT FOUND!");
				}
			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting delete");
		}
	}

	void restore(Long id, Long timeUpdatedMs, Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				ClientKey lClientKey = pm.getObjectById(ClientKey.class, id);
				if (lClientKey != null) {
					lClientKey.setDeleted(false);
					lClientKey.setTimeUpdatedMs(timeUpdatedMs);
					lClientKey
							.setTimeUpdatedTimeZoneOffsetMs(timeUpdatedTimeZoneOffsetMs);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(id + " ClientKey restored");
				} else {
					LOGGER.log(Level.WARNING, id + "  CLIENT KEY NOT FOUND!");
				}
			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

}
