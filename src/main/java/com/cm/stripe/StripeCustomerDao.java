package com.cm.stripe;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

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
				Query q = pm.newQuery(StripeCustomer.class);
				q.setFilter("accountId == accountIdParam && deleted == deletedParam");
				q.declareParameters("Long accountIdParam, Boolean deletedParam");
				List<StripeCustomer> lList = (List<StripeCustomer>) q.execute(
						accountId, Boolean.valueOf(false));
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

	StripeCustomer getByStripeId(String stripeId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(StripeCustomer.class);
				q.setFilter("stripeId == stripeIdParam && deleted == deletedParam");
				q.declareParameters("String stripeIdParam, Boolean deletedParam");
				List<StripeCustomer> lList = (List<StripeCustomer>) q.execute(
						stripeId, Boolean.valueOf(false));
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

	void update(StripeCustomer pCustomer) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering update");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				StripeCustomer lStripeCustomer = pm.getObjectById(
						StripeCustomer.class, pCustomer.getId());
				// only update the following fields
				lStripeCustomer.setCanonicalPlanName(pCustomer
						.getCanonicalPlanName());
				lStripeCustomer
						.setSubscriptionId(pCustomer.getSubscriptionId());
				lStripeCustomer
						.setSubscriptionId(pCustomer.getSubscriptionId());
				lStripeCustomer.setSubscriptionStatus(pCustomer
						.getSubscriptionStatus());
				lStripeCustomer.setSubscriptionCurrentPeriodStart(pCustomer
						.getSubscriptionCurrentPeriodStart());
				lStripeCustomer.setSubscriptionCurrentPeriodEnd(pCustomer
						.getSubscriptionCurrentPeriodEnd());
				lStripeCustomer.setCardBrand(pCustomer.getCardBrand());
				lStripeCustomer.setCardLast4(pCustomer.getCardLast4());
				lStripeCustomer.setCardExpMonth(pCustomer.getCardExpMonth());
				lStripeCustomer.setCardExpYear(pCustomer.getCardExpYear());
				lStripeCustomer
						.setCardAddressZip(pCustomer.getCardAddressZip());
				lStripeCustomer.setCardFunding(pCustomer.getCardFunding());

				lStripeCustomer.setTimeUpdatedMs(pCustomer.getTimeUpdatedMs());
				lStripeCustomer.setTimeUpdatedTimeZoneOffsetMs(pCustomer
						.getTimeUpdatedTimeZoneOffsetMs());

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateApplication");
		}
	}

	void delete(Long id, Long timeUpdatedMs, Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering delete");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				StripeCustomer lCustomer = pm.getObjectById(
						StripeCustomer.class, id);
				if (lCustomer != null) {
					lCustomer.setDeleted(true);
					lCustomer.setTimeUpdatedMs(timeUpdatedMs);
					lCustomer
							.setTimeUpdatedTimeZoneOffsetMs(timeUpdatedTimeZoneOffsetMs);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(id + " StripeCustomer marked for deletion");
				} else {
					LOGGER.log(Level.WARNING, id
							+ "  StripeCustomer NOT FOUND!");
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

}
