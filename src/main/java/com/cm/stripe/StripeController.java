package com.cm.stripe;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cm.usermanagement.user.User;
import com.cm.usermanagement.user.UserService;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Customer;

@Controller
public class StripeController {

	private static final Logger LOGGER = Logger
			.getLogger(StripeController.class.getName());
	@Autowired
	private UserService userService;
	@Autowired
	private StripeCustomerDao stripeCustomerDao;

	@RequestMapping(value = "/stripe/token", method = RequestMethod.POST)
	public ModelAndView processToken(
			@RequestParam("stripeToken") String stripeToken,
			@RequestParam("canonicalPlanName") String canonicalPlanName,
			ModelMap model) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering processToken");

			User lUser = userService.getLoggedInUser();
			LOGGER.info("Received token: " + stripeToken + " for user "
					+ lUser.getUsername());
			try {
				Stripe.apiKey = "sk_test_4aEiOFaIp1sl35p1Gqjco3Is";

				// TODO: check to see if the customer already exists, then
				// update the plan
				// or create a new customer
				StripeCustomer lStripeCustomer = stripeCustomerDao.get(lUser
						.getAccountId());
				//customer does not exist
				if (lStripeCustomer == null) {

					Map<String, Object> lCustomerParams = new HashMap<String, Object>();
					lCustomerParams.put("description",
							"Customer for test@example.com");
					lCustomerParams.put("card", stripeToken); // obtained with
																// Stripe.js
					lCustomerParams.put("email", lUser.getEmail());
					Map<String, String> lCustomerMetadata = new HashMap<String, String>();
					lCustomerMetadata.put("id", String.valueOf(lUser.getId()));
					lCustomerMetadata.put("accountId",
							String.valueOf(lUser.getAccountId()));
					lCustomerMetadata.put("username", lUser.getUsername());

					lCustomerParams.put("metadata", lCustomerMetadata);
					lCustomerParams.put("plan", canonicalPlanName);

					// TODO: convert to domain object and save; add userId,
					// accountId, & userName
					Customer lCustomer = Customer.create(lCustomerParams);
				} else {
					//found an existing customer; so update
					Map<String, Object> lCustomerParams = new HashMap<String, Object>();
					lCustomerParams.put("card", stripeToken); // obtained with
					// Stripe.js
					lCustomerParams.put("plan", canonicalPlanName);

					// TODO: convert to domain object and save; add userId,
					// accountId, & userName
					Customer lCustomer = Customer.retrieve(lStripeCustomer.getStripeId());
					lCustomer.update(lCustomerParams);
				
				}

			} catch (AuthenticationException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			} catch (InvalidRequestException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			} catch (APIConnectionException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			} catch (CardException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			} catch (APIException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
			return new ModelAndView("account_settings", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting processToken");
		}
	}
}
