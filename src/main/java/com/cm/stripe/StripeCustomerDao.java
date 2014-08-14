package com.cm.stripe;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.contentmanager.application.Application;
import com.cm.util.PMF;

@Component
public class StripeCustomerDao {
	private static final Logger LOGGER = Logger
			.getLogger(StripeCustomerDao.class.getName());

	public void save(StripeCustomer customer) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				pm.makePersistent(customer);

			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting save");
		}
	}

	StripeCustomer get(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Application.class);
				q.setFilter("accountId == accountIdParam && deleted == deletedParam");
				q.declareParameters("Long accountIdParam, Boolean deletedParam");
				List<StripeCustomer> lList = (List<StripeCustomer>) q.execute(
						accountId, new Boolean(false));
				if (lList != null && (!lList.isEmpty()))
					return lList.get(0);
				else
					return null;

			} catch (JDOObjectNotFoundException e) {
				LOGGER.log(Level.WARNING, e.getMessage());
				return null;
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}
}
