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
				LOGGER.info("Entering save");
			stripeCustomerDao.save(customer);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting save");
		}
	}

	public StripeCustomer get(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			return stripeCustomerDao.get(accountId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	public void update(StripeCustomer customer) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering update");
			stripeCustomerDao.update(customer);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateApplication");
		}
	}

}
