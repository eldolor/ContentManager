package com.cm.stripe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cm.config.CanonicalPlanName;
import com.cm.config.Configuration;
import com.cm.usermanagement.user.User;
import com.cm.usermanagement.user.UserService;
import com.cm.util.Utils;
import com.cm.util.ValidationError;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCardCollection;
import com.stripe.model.Subscription;

@Controller
public class StripeController {

	private static final Logger LOGGER = Logger
			.getLogger(StripeController.class.getName());
	@Autowired
	private UserService userService;
	@Autowired
	private StripeCustomerService stripeCustomerService;

	/**
	 * secured uri
	 * 
	 * @param stripeToken
	 * @param canonicalPlanName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stripe/subscribe", method = RequestMethod.POST)
	public ModelAndView subscribe(@RequestParam String stripeToken,
			@RequestParam String canonicalPlanName, ModelMap model) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering subscribe");

			List<ValidationError> errors = new ArrayList<ValidationError>();
			User lUser = userService.getLoggedInUser();
			LOGGER.info("Received token: " + stripeToken + " for user "
					+ lUser.getUsername());
			try {
				Stripe.apiKey = Configuration.STRIPE_API_KEY;

				StripeCustomer lStoredStripeCustomer = stripeCustomerService
						.get(lUser.getAccountId());
				// customer does not exist or exists but with CC expiring or
				// expired
				if (lStoredStripeCustomer == null
						|| Utils.isCCExpired(
								lStoredStripeCustomer.getCardExpirationYear(),
								lStoredStripeCustomer.getCardExpirationMonth())
						|| Utils.isCCExpiring(
								lStoredStripeCustomer.getCardExpirationYear(),
								lStoredStripeCustomer.getCardExpirationMonth())) {

					Map<String, Object> lCustomerParams = new HashMap<String, Object>();
					Map<String, Object> lSubscriptionParams = new HashMap<String, Object>();
					// Stripe tokens can only be used once
					lCustomerParams.put("card", stripeToken); // obtained
																// with
					// Stripe.js
					lCustomerParams.put("email", lUser.getEmail());
					Map<String, String> lCustomerMetadata = new HashMap<String, String>();
					lCustomerMetadata.put("userId",
							String.valueOf(lUser.getId()));
					lCustomerMetadata.put("accountId",
							String.valueOf(lUser.getAccountId()));
					lCustomerMetadata.put("username", lUser.getUsername());

					lCustomerParams.put("metadata", lCustomerMetadata);

					// subscribe to the new plan selected
					lSubscriptionParams.put("plan", canonicalPlanName);
					
					Customer lCustomer = null;
					Subscription lSubscription = null;
					if (lStoredStripeCustomer == null) {
						lCustomer = Customer.create(lCustomerParams);
						lSubscription = lCustomer
								.createSubscription(lSubscriptionParams);
					} else {
						// customer exists but CC is either expiring or has
						// expired;
						lCustomer = Customer.retrieve(lStoredStripeCustomer
								.getStripeId());
						lCustomer = lCustomer.update(lCustomerParams);
						lSubscription = lCustomer
								.updateSubscription(lSubscriptionParams);
					}

					// create StripeCustomer
					// TODO: handle scenario where customer and subscription
					// succeed but saving StripeCustomer to DB fails
					StripeCustomer lStripeCustomer = null;

					if (lStoredStripeCustomer == null)
						lStripeCustomer = new StripeCustomer();
					else
						lStripeCustomer = lStoredStripeCustomer;

					lStripeCustomer.setAccountId(lUser.getAccountId());
					lStripeCustomer.setCanonicalPlanName(canonicalPlanName);
					lStripeCustomer.setUserId(lUser.getId());
					lStripeCustomer.setUsername(lUser.getUsername());

					// Stripe specific fields
					lStripeCustomer.setStripeId(lCustomer.getId());
					lStripeCustomer.setSubscriptionId(lSubscription.getId());
					String lDefaultCardId = lCustomer.getDefaultCard();
					CustomerCardCollection lCardCollection = lCustomer
							.getCards();
					Card lDefaultCard = lCardCollection
							.retrieve(lDefaultCardId);

					lStripeCustomer.setCardBrand(lDefaultCard.getBrand());
					lStripeCustomer.setCardLast4(lDefaultCard.getLast4());
					lStripeCustomer.setCardExpirationMonth(lDefaultCard
							.getExpMonth());
					lStripeCustomer.setCardExpirationYear(lDefaultCard
							.getExpYear());

					if (lStoredStripeCustomer == null) {
						lStripeCustomer.setTimeCreatedMs(System
								.currentTimeMillis());
						lStripeCustomer
								.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
										.getDefault().getOffset(
												System.currentTimeMillis()));
						stripeCustomerService.save(lStripeCustomer);
					} else {
						lStripeCustomer.setTimeUpdatedMs(System
								.currentTimeMillis());
						lStripeCustomer
								.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
										.getDefault().getOffset(
												System.currentTimeMillis()));
						stripeCustomerService.update(lStripeCustomer);
					}
					//trigger a message to update Quota, based on the selected plan
					Utils.triggerUpdateQuotaMessage(lUser.getAccountId(), 0);
					
				} else {
					ValidationError error = new ValidationError();
					error.setCode("customer");
					error.setDescription("Customer already exists. Try updating subscription instead of creating a new subscription");
					errors.add(error);
					LOGGER.log(
							Level.WARNING,
							"Customer already exists. Try updating subscription instead of creating a new subscription");
				}
			} catch (Exception e) {
				errors.addAll(processStripeExceptions(e));
			}
			if (!errors.isEmpty()) {
				// Begin: copied over from
				// UserManagementController.displayAccountSettings()
				model.addAttribute("canonicalPlanNameFree",
						CanonicalPlanName.FREE.getValue());
				model.addAttribute("canonicalPlanNameMicro",
						CanonicalPlanName.MICRO.getValue());
				model.addAttribute("canonicalPlanNameSmall",
						CanonicalPlanName.SMALL.getValue());
				model.addAttribute("canonicalPlanNameMedium",
						CanonicalPlanName.MEDIUM.getValue());
				model.addAttribute("canonicalPlanNameLarge",
						CanonicalPlanName.LARGE.getValue());
				StripeCustomer lStripeCustomer = stripeCustomerService
						.get(lUser.getAccountId());
				if (lStripeCustomer == null) {
					model.addAttribute("isSubscribed", false);
					// default to free
					model.addAttribute("subscribedCanonicalPlanName",
							CanonicalPlanName.FREE.getValue());
				} else {
					model.addAttribute("isSubscribed", true);
					model.addAttribute("subscribedCanonicalPlanName",
							lStripeCustomer.getCanonicalPlanName());
				}
				// End: copied over from
				// UserManagementController.displayAccountSettings()
				// now add the errors for display
				model.addAttribute("isError", true);
				StringBuilder lSb = new StringBuilder();
				for (ValidationError validationError : errors) {
					lSb.append(validationError.getDescription());
					lSb.append("|");
				}
				model.addAttribute("errors", lSb.toString());
				return new ModelAndView("account_settings", model);
			} else {
				return new ModelAndView("redirect:/account");
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting subscribe");
		}
	}

	@RequestMapping(value = "/stripe/subscribe/update", method = RequestMethod.POST)
	public ModelAndView updateSubscription(
			@RequestParam("canonicalPlanName") String canonicalPlanName,
			ModelMap model) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateSubscription");

			User lUser = userService.getLoggedInUser();
			Stripe.apiKey = Configuration.STRIPE_API_KEY;
			StripeCustomer lStripeCustomer = stripeCustomerService.get(lUser
					.getAccountId());

			if (lStripeCustomer != null) {
				try {
					// found an existing customer;
					Customer lCustomer = Customer.retrieve(lStripeCustomer
							.getStripeId());

					Map<String, Object> lSubscriptionParams = new HashMap<String, Object>();
					// subscribe to the new plan selected
					lSubscriptionParams.put("plan", canonicalPlanName);
					lSubscriptionParams.put("prorate", false);
					Subscription lSubscription = lCustomer
							.updateSubscription(lSubscriptionParams);

					// update StripeCustomer and save locally
					// TODO: handle scenario where update subscription succeed
					// but saving StripeCustomer to DB fails
					lStripeCustomer.setCanonicalPlanName(canonicalPlanName);
					lStripeCustomer.setSubscriptionId(lSubscription.getId());
					lStripeCustomer
							.setTimeUpdatedMs(System.currentTimeMillis());
					lStripeCustomer
							.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
									.getDefault().getOffset(
											System.currentTimeMillis()));

					stripeCustomerService.update(lStripeCustomer);
					//trigger a message to update Quota, based on the selected plan
					Utils.triggerUpdateQuotaMessage(lUser.getAccountId(), 0);
				} catch (Exception e) {
					errors.addAll(processStripeExceptions(e));
				}

			} else {
				ValidationError error = new ValidationError();
				error.setCode("customer");
				error.setDescription("Customer is not yet subscribed");
				errors.add(error);
				LOGGER.log(Level.WARNING, "Customer is not yet subscribed");
			}
			if (!errors.isEmpty()) {
				// Begin: copied over from
				// UserManagementController.displayAccountSettings()
				model.addAttribute("canonicalPlanNameFree",
						CanonicalPlanName.FREE.getValue());
				model.addAttribute("canonicalPlanNameMicro",
						CanonicalPlanName.MICRO.getValue());
				model.addAttribute("canonicalPlanNameSmall",
						CanonicalPlanName.SMALL.getValue());
				model.addAttribute("canonicalPlanNameMedium",
						CanonicalPlanName.MEDIUM.getValue());
				model.addAttribute("canonicalPlanNameLarge",
						CanonicalPlanName.LARGE.getValue());
				lStripeCustomer = stripeCustomerService.get(lUser
						.getAccountId());
				if (lStripeCustomer == null) {
					model.addAttribute("isSubscribed", false);
					// default to free
					model.addAttribute("subscribedCanonicalPlanName",
							CanonicalPlanName.FREE.getValue());
				} else {
					model.addAttribute("isSubscribed", true);
					model.addAttribute("subscribedCanonicalPlanName",
							lStripeCustomer.getCanonicalPlanName());
				}
				// End: copied over from
				// UserManagementController.displayAccountSettings()
				// now add the errors for display
				model.addAttribute("isError", true);
				StringBuilder lSb = new StringBuilder();
				for (ValidationError validationError : errors) {
					lSb.append(validationError.getDescription());
					lSb.append("|");
				}
				model.addAttribute("errors", lSb.toString());
				return new ModelAndView("account_settings", model);
			} else {
				return new ModelAndView("redirect:/account");
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateSubscription");
		}
	}

	private List<ValidationError> processStripeExceptions(Exception e) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering processStripeExceptions");
			List<ValidationError> lErrors = new ArrayList<ValidationError>();

			if (e instanceof AuthenticationException) {
				// Authentication with Stripe's API failed
				// (maybe you changed API keys recently)
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				ValidationError error = new ValidationError();
				error.setCode("AuthenticationException");
				error.setDescription("Authentication with Stripe's API failed");
				lErrors.add(error);
			} else if (e instanceof InvalidRequestException) {
				// Invalid parameters were supplied to Stripe's API so fail
				// the request
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				ValidationError error = new ValidationError();
				error.setCode("InvalidRequestException");
				error.setDescription("Invalid parameters were supplied to Stripe's API");
				lErrors.add(error);
			} else if (e instanceof APIConnectionException) {
				// Network communication with Stripe failed
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				ValidationError error = new ValidationError();
				error.setCode("APIConnectionException");
				error.setDescription("Network communication with Stripe failed");
				lErrors.add(error);
			} else if (e instanceof CardException) {
				// The UI must process this exception. present the page for
				// the user to enter CC info
				LOGGER.log(
						Level.SEVERE,
						"Credit card has been declined. The status is "
								+ ((CardException) e).getCode()
								+ ", and the message is "
								+ ((CardException) e).getParam(), e);
				ValidationError error = new ValidationError();
				error.setCode("CardException");
				error.setDescription("Credit card has been declined. The status is "
						+ ((CardException) e).getCode()
						+ ", and the message is "
						+ ((CardException) e).getParam());
				lErrors.add(error);
			} else if (e instanceof APIException) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				ValidationError error = new ValidationError();
				error.setCode("APIException");
				error.setDescription("Network communication with Stripe failed");
				lErrors.add(error);
			} else {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				ValidationError error = new ValidationError();
				error.setCode("Error");
				error.setDescription("Unable to process the request. Please try again later");
				lErrors.add(error);
			}

			return lErrors;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting processStripeExceptions");
		}
	}
}
