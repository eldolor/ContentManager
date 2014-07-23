package com.cm.accountmanagement.account;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Component;

import com.cm.util.PMF;

@Component
 class AccountDao {
	private static final Logger LOGGER = Logger.getLogger(AccountDao.class
			.getName());

	 List<Account> getAccounts() {
		PersistenceManager pm = null;

		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Account.class);

			return (List<Account>) q.execute();
		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	 Account getAccountByAccountName(String accountName) {
		PersistenceManager pm = null;

		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Account.class);
			q.setFilter("name == nameParam");
			q.declareParameters("String nameParam");
			List<Account> accounts = (List<Account>) q.execute(accountName);
			Account account = null;
			if (accounts != null && (accounts.size() > 0)) {
				account = accounts.get(0);
			}
			return account;
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

	 void saveAccount(Account account) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			pm.makePersistent(account);

		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	 void updateAccount(Account account) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Account _account = pm.getObjectById(Account.class, account.getId());
			_account.setEnabled(account.isEnabled());
			_account.setName(account.getName());
			_account.setDescription(account.getDescription());
			_account.setTimeUpdatedMs(account.getTimeUpdatedMs());
			_account.setTimeUpdatedTimeZoneOffsetMs(account
					.getTimeUpdatedTimeZoneOffsetMs());

		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	 Account getAccount(Long id) {
		PersistenceManager pm = null;

		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Account _account = pm.getObjectById(Account.class, id);
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

	 void updateApiKey(Account account) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			pm = PMF.get().getPersistenceManager();
			Account _account = pm.getObjectById(Account.class, account.getId());
			_account.setApiKey(account.getApiKey());
			_account.setTimeUpdatedMs(account.getTimeUpdatedMs());
			_account.setTimeUpdatedTimeZoneOffsetMs(account
					.getTimeUpdatedTimeZoneOffsetMs());

		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

}
