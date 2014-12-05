package com.cm.stripe;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StripeCustomerService {
	private static final Logger LOGGER = Logger
			.getLogger(StripeCustomerService.class.getName());
	@Autowired
	private StripeCustomerDao stripeCustomerDao;

	public void save(StripeCustomer customer) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			stripeCustomerDao.save(customer);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public StripeCustomer get(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			return stripeCustomerDao.get(accountId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}
	
	public StripeCustomer getByStripeId(String stripeId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			return stripeCustomerDao.getByStripeId(stripeId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public void update(StripeCustomer customer) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			stripeCustomerDao.update(customer);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public void delete(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			stripeCustomerDao.delete(id, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}
}
