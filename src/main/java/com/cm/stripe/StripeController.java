package com.cm.stripe;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cm.config.CanonicalPlan;
import com.cm.config.Configuration;
import com.cm.stripe.transfer.StripeCard;
import com.cm.usermanagement.user.StripeChargeEmailBuilder;
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
	 * @param canonicalPlanId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stripe/subscribe", method = RequestMethod.POST)
	public ModelAndView subscribe(@RequestParam String stripeToken,
			@RequestParam String canonicalPlanId, ModelMap model) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering subscribe");

			List<ValidationError> errors = new ArrayList<ValidationError>();
			User lUser = userService.getLoggedInUser();
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Received token: " + stripeToken + " for user "
						+ lUser.getUsername());
			try {
				Stripe.apiKey = Configuration.STRIPE_PRIVATE_API_KEY;

				StripeCustomer lStoredStripeCustomer = stripeCustomerService
						.get(lUser.getAccountId());
				// customer does not exist or exists but with CC expiring or
				// expired
				if (lStoredStripeCustomer == null
						|| Utils.isCCExpired(
								lStoredStripeCustomer.getCardExpYear(),
								lStoredStripeCustomer.getCardExpMonth())
						|| Utils.isCCExpiring(
								lStoredStripeCustomer.getCardExpYear(),
								lStoredStripeCustomer.getCardExpMonth())) {
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Processing new customer");
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
					lSubscriptionParams.put("plan", canonicalPlanId);

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
					lStripeCustomer.setCanonicalPlanId(canonicalPlanId);
					lStripeCustomer.setUserId(lUser.getId());
					lStripeCustomer.setUsername(lUser.getUsername());

					// Stripe specific fields
					lStripeCustomer.setStripeId(lCustomer.getId());
					{
						lStripeCustomer
								.setSubscriptionId(lSubscription.getId());
						lStripeCustomer.setSubscriptionStatus(lSubscription
								.getStatus());
						lStripeCustomer
								.setSubscriptionCurrentPeriodStart(lSubscription
										.getCurrentPeriodStart());
						lStripeCustomer
								.setSubscriptionCurrentPeriodEnd(lSubscription
										.getCurrentPeriodEnd());
					}
					{
						String lDefaultCardId = lCustomer.getDefaultCard();
						CustomerCardCollection lCardCollection = lCustomer
								.getCards();
						Card lDefaultCard = lCardCollection
								.retrieve(lDefaultCardId);

						lStripeCustomer.setCardBrand(lDefaultCard.getBrand());
						lStripeCustomer.setCardLast4(lDefaultCard.getLast4());
						lStripeCustomer.setCardExpMonth(lDefaultCard
								.getExpMonth());
						lStripeCustomer.setCardExpYear(lDefaultCard
								.getExpYear());
						lStripeCustomer.setCardAddressZip(lDefaultCard
								.getAddressZip());
						lStripeCustomer.setCardFunding(lDefaultCard
								.getFunding());
					}

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
					// trigger a message to update Quota, based on the selected
					// plan
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
				model.addAttribute("canonicalPlanIdFree",
						CanonicalPlan.FREE.getId());
				model.addAttribute("canonicalPlanIdMicro",
						CanonicalPlan.MICRO.getId());
				model.addAttribute("canonicalPlanIdSmall",
						CanonicalPlan.SMALL.getId());
				model.addAttribute("canonicalPlanIdMedium",
						CanonicalPlan.MEDIUM.getId());
				model.addAttribute("canonicalPlanIdLarge",
						CanonicalPlan.LARGE.getId());
				StripeCustomer lStripeCustomer = stripeCustomerService
						.get(lUser.getAccountId());
				if (lStripeCustomer == null) {
					model.addAttribute("isSubscribed", false);
					// default to free
					model.addAttribute("subscribedCanonicalPlanId",
							CanonicalPlan.FREE.getId());
				} else {
					model.addAttribute("isSubscribed", true);
					model.addAttribute("subscribedCanonicalPlanId",
							lStripeCustomer.getCanonicalPlanId());
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
				return new ModelAndView("redirect:/account/plans");
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting subscribe");
		}
	}

	@Deprecated
	@RequestMapping(value = "/stripe/subscribe/update", method = RequestMethod.POST)
	public ModelAndView updateSubscription(
			@RequestParam("canonicalPlanId") String canonicalPlanId,
			ModelMap model) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateSubscription");

			User lUser = userService.getLoggedInUser();
			Stripe.apiKey = Configuration.STRIPE_PRIVATE_API_KEY;
			StripeCustomer lStripeCustomer = stripeCustomerService.get(lUser
					.getAccountId());

			if (lStripeCustomer != null) {
				try {
					// found an existing customer;
					Customer lCustomer = Customer.retrieve(lStripeCustomer
							.getStripeId());

					Map<String, Object> lSubscriptionParams = new HashMap<String, Object>();
					// subscribe to the new plan selected
					lSubscriptionParams.put("plan", canonicalPlanId);
					lSubscriptionParams.put("prorate", false);
					Subscription lSubscription = lCustomer
							.updateSubscription(lSubscriptionParams);

					// update StripeCustomer and save locally
					// TODO: handle scenario where update subscription succeed
					// but saving StripeCustomer to DB fails
					lStripeCustomer.setCanonicalPlanId(canonicalPlanId);
					lStripeCustomer.setSubscriptionId(lSubscription.getId());
					lStripeCustomer
							.setTimeUpdatedMs(System.currentTimeMillis());
					lStripeCustomer
							.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
									.getDefault().getOffset(
											System.currentTimeMillis()));

					stripeCustomerService.update(lStripeCustomer);
					// trigger a message to update Quota, based on the selected
					// plan
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
				model.addAttribute("canonicalPlanIdFree",
						CanonicalPlan.FREE.getId());
				model.addAttribute("canonicalPlanIdMicro",
						CanonicalPlan.MICRO.getId());
				model.addAttribute("canonicalPlanIdSmall",
						CanonicalPlan.SMALL.getId());
				model.addAttribute("canonicalPlanIdMedium",
						CanonicalPlan.MEDIUM.getId());
				model.addAttribute("canonicalPlanIdLarge",
						CanonicalPlan.LARGE.getId());
				lStripeCustomer = stripeCustomerService.get(lUser
						.getAccountId());
				if (lStripeCustomer == null) {
					model.addAttribute("isSubscribed", false);
					// default to free
					model.addAttribute("subscribedCanonicalPlanId",
							CanonicalPlan.FREE.getId());
				} else {
					model.addAttribute("isSubscribed", true);
					model.addAttribute("subscribedCanonicalPlanId",
							lStripeCustomer.getCanonicalPlanId());
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
				return new ModelAndView("redirect:/account/plans");
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateSubscription");
		}
	}

	@RequestMapping(value = "/stripe/subscribe/update/{canonicalPlanId}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<ValidationError> updatePlan(@PathVariable String canonicalPlanId,
			HttpServletResponse response) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering ");

			User lUser = userService.getLoggedInUser();
			Stripe.apiKey = Configuration.STRIPE_PRIVATE_API_KEY;
			StripeCustomer lStripeCustomer = stripeCustomerService.get(lUser
					.getAccountId());

			if (lStripeCustomer != null) {
				try {
					// found an existing customer;
					Customer lCustomer = Customer.retrieve(lStripeCustomer
							.getStripeId());

					Map<String, Object> lSubscriptionParams = new HashMap<String, Object>();
					// subscribe to the new plan selected
					lSubscriptionParams.put("plan", canonicalPlanId);
					// the quota related changes come into
					// effect right away
					lSubscriptionParams.put("prorate", true);
					Subscription lSubscription = lCustomer
							.updateSubscription(lSubscriptionParams);

					// update StripeCustomer and save locally
					// TODO: handle scenario where update subscription succeed
					// but saving StripeCustomer to DB fails
					lStripeCustomer.setCanonicalPlanId(canonicalPlanId);
					{
						lStripeCustomer
								.setSubscriptionId(lSubscription.getId());
						lStripeCustomer.setSubscriptionStatus(lSubscription
								.getStatus());
						lStripeCustomer
								.setSubscriptionCurrentPeriodStart(lSubscription
										.getCurrentPeriodStart());
						lStripeCustomer
								.setSubscriptionCurrentPeriodEnd(lSubscription
										.getCurrentPeriodEnd());
					}
					lStripeCustomer
							.setTimeUpdatedMs(System.currentTimeMillis());
					lStripeCustomer
							.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
									.getDefault().getOffset(
											System.currentTimeMillis()));

					stripeCustomerService.update(lStripeCustomer);
					// trigger a message to update Quota, based on the selected
					// plan
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
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				return errors;
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return null;

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/secured/stripe/customer", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	StripeCustomer getStripeCustomer(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering ");

			User lUser = userService.getLoggedInUser();
			Stripe.apiKey = Configuration.STRIPE_PRIVATE_API_KEY;
			StripeCustomer lStripeCustomer = stripeCustomerService.get(lUser
					.getAccountId());
			if (lStripeCustomer != null) {
				response.setStatus(HttpServletResponse.SC_OK);
				return lStripeCustomer;
			} else {
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				return null;
			}

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/secured/stripe/customer/card", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> updateCard(@RequestBody StripeCard stripeCard,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			List<ValidationError> errors = new ArrayList<ValidationError>();
			User lUser = userService.getLoggedInUser();
			try {
				Stripe.apiKey = Configuration.STRIPE_PRIVATE_API_KEY;

				StripeCustomer lStoredStripeCustomer = stripeCustomerService
						.get(lUser.getAccountId());
				// customer does not exist or exists but with CC expiring or
				// expired
				if (lStoredStripeCustomer == null) {
					ValidationError error = new ValidationError();
					error.setCode("customer");
					error.setDescription("Customer is not yet subscribed");
					errors.add(error);
					LOGGER.log(Level.WARNING, "Customer is not yet subscribed");
					response.setStatus(HttpServletResponse.SC_CONFLICT);
					return errors;
				}
				// validate

				Customer lCustomer = Customer.retrieve(lStoredStripeCustomer
						.getStripeId());
				Card lCard = lCustomer.getCards().retrieve(
						lCustomer.getDefaultCard());

				Map<String, Object> lCardUpdateParams = new HashMap<String, Object>();

				// required fields
				lCardUpdateParams
						.put("address_zip", stripeCard.getAddressZip());
				lCardUpdateParams.put("exp_month", stripeCard.getExpMonth());
				lCardUpdateParams.put("exp_year", stripeCard.getExpYear());

				// optional fields
				if (!Utils.isEmpty(stripeCard.getName()))
					lCardUpdateParams.put("name", stripeCard.getName());
				if (!Utils.isEmpty(stripeCard.getAddressCity()))
					lCardUpdateParams.put("address_city",
							stripeCard.getAddressCity());
				if (!Utils.isEmpty(stripeCard.getAddressCountry()))
					lCardUpdateParams.put("address_country",
							stripeCard.getAddressCountry());
				if (!Utils.isEmpty(stripeCard.getAddressLine1()))
					lCardUpdateParams.put("address_line1",
							stripeCard.getAddressLine1());
				if (!Utils.isEmpty(stripeCard.getAddressLine2()))
					lCardUpdateParams.put("address_line2",
							stripeCard.getAddressLine2());
				if (!Utils.isEmpty(stripeCard.getAddressState()))
					lCardUpdateParams.put("address_state",
							stripeCard.getAddressState());

				lCard = lCard.update(lCardUpdateParams);

				boolean isValidCard = true;
				// validate
				{
					if (!lCard.getAddressZipCheck().equals("pass")) {
						ValidationError error = new ValidationError();
						error.setCode("addressZip");
						error.setDescription("The postal code is not valid");
						errors.add(error);
						LOGGER.log(Level.WARNING,
								"The postal code is not valid");
						isValidCard = false;
					}

				}

				if (isValidCard) {
					lStoredStripeCustomer.setCardFunding(lCard.getFunding());
					lStoredStripeCustomer.setCardAddressZip(lCard
							.getAddressZip());
					lStoredStripeCustomer.setCardExpMonth(lCard.getExpMonth());
					lStoredStripeCustomer.setCardExpYear(lCard.getExpYear());
					lStoredStripeCustomer.setTimeUpdatedMs(System
							.currentTimeMillis());
					lStoredStripeCustomer
							.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
									.getDefault().getOffset(
											System.currentTimeMillis()));
					stripeCustomerService.update(lStoredStripeCustomer);
				}

			} catch (Exception e) {
				errors.addAll(processStripeExceptions(e));
			}
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				return errors;
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return errors;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/secured/stripe/customer/card", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> newCard(@RequestBody StripeCard stripeCard,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			List<ValidationError> errors = new ArrayList<ValidationError>();
			User lUser = userService.getLoggedInUser();
			try {
				Stripe.apiKey = Configuration.STRIPE_PRIVATE_API_KEY;

				StripeCustomer lStoredStripeCustomer = stripeCustomerService
						.get(lUser.getAccountId());
				// customer does not exist or exists but with CC expiring or
				// expired
				if (lStoredStripeCustomer == null) {
					ValidationError error = new ValidationError();
					error.setCode("customer");
					error.setDescription("Customer is not yet subscribed");
					errors.add(error);
					LOGGER.log(Level.WARNING, "Customer is not yet subscribed");
					response.setStatus(HttpServletResponse.SC_CONFLICT);
					return errors;
				}

				Customer lCustomer = Customer.retrieve(lStoredStripeCustomer
						.getStripeId());

				Map<String, Object> lNewCardParams = new HashMap<String, Object>();

				// required fields
				lNewCardParams.put("card", stripeCard.getStripeToken());
				Card lCard = lCustomer.createCard(lNewCardParams);

				// set this as the default card
				// lCustomer.setDefaultCard(lCard.getId());
				Map<String, Object> lUpdateParams = new HashMap<String, Object>();
				lUpdateParams.put("default_card", lCard.getId());
				lCustomer.update(lUpdateParams);

				// then save it locally
				lStoredStripeCustomer.setCardFunding(lCard.getFunding());
				lStoredStripeCustomer.setCardBrand(lCard.getBrand());
				lStoredStripeCustomer.setCardLast4(lCard.getLast4());
				lStoredStripeCustomer.setCardExpMonth(lCard.getExpMonth());
				lStoredStripeCustomer.setCardExpYear(lCard.getExpYear());
				lStoredStripeCustomer.setCardAddressZip(lCard.getAddressZip());
				lStoredStripeCustomer.setTimeUpdatedMs(System
						.currentTimeMillis());
				lStoredStripeCustomer
						.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
								.getDefault().getOffset(
										System.currentTimeMillis()));
				stripeCustomerService.update(lStoredStripeCustomer);

			} catch (Exception e) {
				errors.addAll(processStripeExceptions(e));
			}
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				return errors;
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return errors;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/webhook/stripe", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void processWebhook(@RequestBody JSONObject json,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			LOGGER.info(json.toString());
			String lStripeId = null;
			StripeCustomer lStripeCustomer = null;

			Map lData = (Map) json.get("data");

			// String lCurrency = (String) lCharge.get("currency");
			StripeChargeEmailBuilder lEmailBuilder = new StripeChargeEmailBuilder();

			String lType = (String) json.get("type");
			if (lType.equals("charge.succeeded")) {
				// TODO: send out an email
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Charge Succeeded");
				Map lCharge = (Map) lData.get("object");

				Map lCard = (Map) lCharge.get("card");
				String lBrand = (String) lCard.get("brand");
				String lLast4 = (String) lCard.get("last4");
				Integer lAmount = (Integer) lCharge.get("amount");
				BigDecimal bg1, bg2, bg3;

				bg1 = new BigDecimal(lAmount);
				bg2 = new BigDecimal("100");

				// divide bg1 with bg2 with 3 scale
				bg3 = bg1.divide(bg2);
				lStripeId = (String) lCharge.get("customer");
				lStripeCustomer = stripeCustomerService
						.getByStripeId(lStripeId);
				if (lStripeCustomer == null) {
					LOGGER.log(Level.SEVERE,
							"Stripe Customer not found for Webhook");
					response.setStatus(HttpServletResponse.SC_OK);
					return;
				}
				String lInvoice = (String) lCharge.get("invoice");
				StringBuilder lHtmlFormattedHeader = new StringBuilder();

				lHtmlFormattedHeader.append("<p class=\"lead\">Hi,</p>");
				lHtmlFormattedHeader
						.append("<p class=\"lead\">We have received your payment for your Skok subscription. You can keep this receipt for your records.</p>");
				lHtmlFormattedHeader
						.append("<p>&nbsp;</p><p class=\"lead\">Thank you!</p>");

				StringBuilder lHtmlFormattedCallout = new StringBuilder();
				lHtmlFormattedCallout
						.append("<p><b><u>Skok Receipt</u></b></p>");
				lHtmlFormattedCallout.append("<p>Plan: "
						+ lStripeCustomer.getCanonicalPlanId() + "</p>");

				lHtmlFormattedCallout.append("<p>Amount: $" + bg3 + "USD</p>");
				lHtmlFormattedCallout.append("<p>Charged to: " + lBrand
						+ " card ending in " + lLast4 + "</p>");
				lHtmlFormattedCallout
						.append("<p>Invoice: " + lInvoice + "</p>");
				String lEmailTemplate = lEmailBuilder.build(
						lHtmlFormattedHeader.toString(),
						lHtmlFormattedCallout.toString());
				// Email sent by SKOK
				// try {
				// Utils.sendEmail(Configuration.FROM_EMAIL_ADDRESS,
				// Configuration.FROM_NAME,
				// lStripeCustomer.getUsername(), "",
				// Configuration.SITE_NAME + " Payment Receipt",
				// lEmailTemplate, null);
				// } catch (UnsupportedEncodingException e) {
				// LOGGER.log(Level.SEVERE, e.getMessage(), e);
				// } catch (MessagingException e) {
				// LOGGER.log(Level.SEVERE, e.getMessage(), e);
				// }

			} else if (lType.equals("charge.failed")) {
				// TODO: send out an email
				LOGGER.warning("Charge Failed!!!");
				Map lCharge = (Map) lData.get("object");

				Map lCard = (Map) lCharge.get("card");
				String lBrand = (String) lCard.get("brand");
				String lLast4 = (String) lCard.get("last4");
				Integer lAmount = (Integer) lCharge.get("amount");
				BigDecimal bg1, bg2, bg3;

				bg1 = new BigDecimal(lAmount);
				bg2 = new BigDecimal("100");

				// divide bg1 with bg2 with 3 scale
				bg3 = bg1.divide(bg2);

				String lFailureCode = (String) lCharge.get("failure_code");
				if (lFailureCode.equals("expired_card")
						|| lFailureCode.equals("card_declined")
						|| lFailureCode.equals("incorrect_number")
						|| lFailureCode.equals("invalid_number")
						|| lFailureCode.equals("invalid_expiry_month")
						|| lFailureCode.equals("invalid_expiry_year")
						|| lFailureCode.equals("invalid_cvc")
						|| lFailureCode.equals("incorrect_cvc")
						|| lFailureCode.equals("incorrect_zip")) {
					lStripeId = (String) lCharge.get("customer");
					lStripeCustomer = stripeCustomerService
							.getByStripeId(lStripeId);

					if (lStripeCustomer == null) {
						LOGGER.log(Level.SEVERE,
								"Stripe Customer not found for Webhook");
						response.setStatus(HttpServletResponse.SC_OK);
						return;
					}
					String lInvoice = (String) lCharge.get("invoice");
					StringBuilder lHtmlFormattedHeader = new StringBuilder();

					lHtmlFormattedHeader.append("<p class=\"lead\">Hi,</p>");
					lHtmlFormattedHeader
							.append("<p class=\"lead\">We were unable to process your payment for your Skok subscription.</p>");

					lHtmlFormattedHeader
							.append("<p class=\"lead\">Please login to http://skok.co and update your Billing information.</p>");
					lHtmlFormattedHeader
							.append("<p>&nbsp;</p><p class=\"lead\">Thank you!</p>");

					StringBuilder lHtmlFormattedCallout = new StringBuilder();
					CanonicalPlan lPlan = CanonicalPlan
							.findById(lStripeCustomer.getCanonicalPlanId());

					lHtmlFormattedCallout.append("<p>Plan: "
							+ lPlan.getPlanName() + "</p>");

					lHtmlFormattedCallout.append("<p>Amount: $" + bg3
							+ "USD</p>");
					lHtmlFormattedCallout.append("<p>Charged to: " + lBrand
							+ " card ending in " + lLast4 + "</p>");
					lHtmlFormattedCallout.append("<p>Invoice: " + lInvoice
							+ "</p>");
					String lEmailTemplate = lEmailBuilder.build(
							lHtmlFormattedHeader.toString(),
							lHtmlFormattedCallout.toString());
					try {
						Utils.sendEmail(Configuration.FROM_EMAIL_ADDRESS,
								Configuration.FROM_NAME,
								lStripeCustomer.getUsername(), "",
								Configuration.SITE_NAME + " Payment Failure",
								lEmailTemplate, null);
					} catch (UnsupportedEncodingException e) {
						LOGGER.log(Level.SEVERE, e.getMessage(), e);
					} catch (MessagingException e) {
						LOGGER.log(Level.SEVERE, e.getMessage(), e);
					}

				}
			} else if (lType.equals("customer.card.updated")) {
				// TODO: send out an email
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Customer Card updated");
				Map lObject = (Map) lData.get("object");

				String lBrand = (String) lObject.get("brand");
				String lLast4 = (String) lObject.get("last4");
				lStripeId = (String) lObject.get("customer");
				lStripeCustomer = stripeCustomerService
						.getByStripeId(lStripeId);
				if (lStripeCustomer == null) {
					LOGGER.log(Level.SEVERE,
							"Stripe Customer not found for Webhook");
					response.setStatus(HttpServletResponse.SC_OK);
					return;
				}
				StringBuilder lHtmlFormattedHeader = new StringBuilder();

				lHtmlFormattedHeader.append("<p class=\"lead\">Hi,</p>");
				lHtmlFormattedHeader
						.append("<p class=\"lead\">You have successfully updated your payment information for your Skok subscription.</p>");
				lHtmlFormattedHeader
						.append("<p>&nbsp;</p><p class=\"lead\">Thank you!</p>");

				StringBuilder lHtmlFormattedCallout = new StringBuilder();
				CanonicalPlan lPlan = CanonicalPlan.findById(lStripeCustomer
						.getCanonicalPlanId());

				lHtmlFormattedCallout.append("<p>Plan: " + lPlan.getPlanName()
						+ "</p>");

				lHtmlFormattedCallout.append("<p>Card: " + lBrand
						+ " card ending in " + lLast4 + "</p>");
				String lEmailTemplate = lEmailBuilder.build(
						lHtmlFormattedHeader.toString(),
						lHtmlFormattedCallout.toString());
				try {
					Utils.sendEmail(Configuration.FROM_EMAIL_ADDRESS,
							Configuration.FROM_NAME,
							lStripeCustomer.getUsername(), "",
							Configuration.SITE_NAME
									+ " Payment Information Updated",
							lEmailTemplate, null);
				} catch (UnsupportedEncodingException e) {
					LOGGER.log(Level.SEVERE, e.getMessage(), e);
				} catch (MessagingException e) {
					LOGGER.log(Level.SEVERE, e.getMessage(), e);
				}
			} else if (lType.equals("customer.subscription.created")
					|| lType.equals("customer.subscription.updated")) {
				// TODO: send out an email
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Customer Subscription");
				Map lObject = (Map) lData.get("object");

				lStripeId = (String) lObject.get("customer");
				lStripeCustomer = stripeCustomerService
						.getByStripeId(lStripeId);
				if (lStripeCustomer == null) {
					LOGGER.log(Level.SEVERE,
							"Stripe Customer not found for Webhook");
					response.setStatus(HttpServletResponse.SC_OK);
					return;
				}
				StringBuilder lHtmlFormattedHeader = new StringBuilder();

				lHtmlFormattedHeader.append("<p class=\"lead\">Hi,</p>");
				lHtmlFormattedHeader
						.append("<p class=\"lead\">You have successfully subscribed to the following plan.</p>");
				lHtmlFormattedHeader
						.append("<p>&nbsp;</p><p class=\"lead\">Thank you!</p>");

				StringBuilder lHtmlFormattedCallout = new StringBuilder();
				CanonicalPlan lPlan = CanonicalPlan.findById(lStripeCustomer
						.getCanonicalPlanId());

				lHtmlFormattedCallout.append("<p>Plan: " + lPlan.getPlanName()
						+ "</p>");
				lHtmlFormattedCallout.append("<p>Amount: "
						+ lPlan.getDisplayPrice() + "</p>");

				String lEmailTemplate = lEmailBuilder.build(
						lHtmlFormattedHeader.toString(),
						lHtmlFormattedCallout.toString());
				try {
					Utils.sendEmail(Configuration.FROM_EMAIL_ADDRESS,
							Configuration.FROM_NAME,
							lStripeCustomer.getUsername(), "",
							Configuration.SITE_NAME + " Subscription Updated",
							lEmailTemplate, null);
				} catch (UnsupportedEncodingException e) {
					LOGGER.log(Level.SEVERE, e.getMessage(), e);
				} catch (MessagingException e) {
					LOGGER.log(Level.SEVERE, e.getMessage(), e);
				}

			}
			response.setStatus(HttpServletResponse.SC_OK);

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
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
