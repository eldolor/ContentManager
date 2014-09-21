package com.cm.admin.message;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.quota.QuotaService;
import com.cm.stripe.StripeCustomer;
import com.cm.stripe.StripeCustomerService;
import com.cm.usermanagement.user.User;
import com.cm.usermanagement.user.UserService;
import com.cm.util.Utils;

@Controller
public class MessageController {

	private static final Logger LOGGER = Logger
			.getLogger(MessageController.class.getName());
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private UserService userService;
	@Autowired
	private StripeCustomerService stripeCustomerService;
	@Autowired
	private QuotaService quotaService;

	/**
	 * Supports only 1 message for now
	 * 
	 * @param response
	 * @return the html contents of the message box
	 */
	@RequestMapping(value = "/secured/messages", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<com.cm.admin.message.transfer.Message> getMessages(
			HttpSession session, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getMessages");
			List<com.cm.admin.message.transfer.Message> lTfrMessages = new ArrayList<com.cm.admin.message.transfer.Message>();
			User lUser = userService.getLoggedInUser();

			try {
				// check to see if CC is expiring
				StripeCustomer lStripeCustomer = stripeCustomerService
						.get(lUser.getAccountId());

				/**** CC Related Messages ****/
				if (lStripeCustomer != null) {
					List<Message> lMessages = this.createCCRelatedMessages(
							lUser, lStripeCustomer);
					for (Message lMessage : lMessages) {
						// only one message per type will ever be displayed to
						// the user
						Message lUnviewedMessageByType = messageDao
								.getMessageByType(lMessage.getType());
						if (lUnviewedMessageByType == null) {
							// save message to db, which returns a message with
							// an
							// id
							lMessage = messageDao.save(lMessage);
						} else if /**
						 * different message for the same type
						 * generated
						 **/
						(!lUnviewedMessageByType.getMessage().equalsIgnoreCase(
								lMessage.getMessage())) {
							// delete the previous message
							messageDao.delete(lUnviewedMessageByType.getId());
							// save the new message
							lMessage = messageDao.save(lMessage);
						} else {
							if (session.getAttribute(lMessage.getType()) == null) {
								// set it in the user's session so that its not
								// displayed for the rest of the session
								session.setAttribute(lMessage.getType(),
										lMessage.getId());
								lMessage = lUnviewedMessageByType;
							} else {
								// its still in the session, implies that it's
								// already been displayed for the session, so
								// dont
								// display it again
								lMessage = null;
							}
						}
						if (lMessage != null)
							lTfrMessages.add(convert(lMessage));
					}

				}
			} catch (Throwable e) {
				LOGGER.warning(e.getMessage());
			}

			/*** Quota related messages ***/
			// Bandwidth Quota
			try {
				Message lQuota = createBandwidthQuotaRelatedMessage(lUser);
				if (lQuota != null) {
					lTfrMessages.add(convert(lQuota));
				}
			} catch (Throwable e) {
				LOGGER.warning(e.getMessage());
			}
			// Storage Quota
			try {
				Message lQuota = createStorageQuotaRelatedMessage(lUser);
				if (lQuota != null) {
					lTfrMessages.add(convert(lQuota));
				}
			} catch (Throwable e) {
				LOGGER.warning(e.getMessage());
			}

			response.setStatus(HttpServletResponse.SC_OK);
			if (lTfrMessages.isEmpty())
				return null;
			else
				return lTfrMessages;

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getMessages");
		}
	}

	@RequestMapping(value = "/secured/message/read", method = RequestMethod.POST, consumes = "application/json")
	public void markMessageAsRead(
			@RequestBody com.cm.admin.message.transfer.Message message,
			HttpSession session, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering markMessageAsRead");
			if (!Utils.isEmpty(message.getId()))
				messageDao.update(convert(message));
			else
				LOGGER.log(Level.SEVERE, "Message received with ID=NULL:"
						+ message.toString());

			response.setStatus(HttpServletResponse.SC_OK);

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting markMessageAsRead");
		}
	}

	private Message convert(com.cm.admin.message.transfer.Message pMessage) {
		Message lMessage = new Message();
		lMessage.setId(pMessage.getId());
		lMessage.setTimeViewedMs(pMessage.getTimeViewedMs());
		lMessage.setTimeViewedTimeZoneOffsetMs(pMessage
				.getTimeViewedTimeZoneOffsetMs());
		return lMessage;
	}

	private com.cm.admin.message.transfer.Message convert(Message pMessage) {
		com.cm.admin.message.transfer.Message lMessage = new com.cm.admin.message.transfer.Message();
		// send only these 2 values
		lMessage.setId(pMessage.getId());
		lMessage.setMessage(pMessage.getMessage());
		lMessage.setMessageClass(pMessage.getMessageClass());
		return lMessage;
	}

	private Message createBandwidthQuotaRelatedMessage(User pLoggedInUser) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			if (!quotaService.hasSufficientBandwidthQuota(pLoggedInUser
					.getAccountId())) {
				// create message
				Message lMessage = new Message();
				lMessage.setMessage("You have exceeded the bandwidth quota for your plan. Please click <a href=\"/account/plans\">here</a> to upgrade your plan.");
				lMessage.setAccountId(pLoggedInUser.getAccountId());
				lMessage.setUserId(pLoggedInUser.getId());
				lMessage.setUserName(pLoggedInUser.getUsername());
				lMessage.setType(MessageType.BANDWIDTH_QUOTA_EXCEEDED
						.getValue());
				lMessage.setMessageClass(MessageClass.ALERT.getValue());

				long lTime = System.currentTimeMillis();
				lMessage.setTimeCreatedMs(lTime);
				lMessage.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getOffset(lTime));
				return lMessage;
			} else {
				return null;
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	private Message createStorageQuotaRelatedMessage(User pLoggedInUser) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			if (!quotaService.hasSufficientStorageQuota(pLoggedInUser
					.getAccountId())) {
				// create message
				Message lMessage = new Message();
				lMessage.setMessage("You have exceeded the storage quota for your plan. Please click <a href=\"/account/plans\">here</a> to upgrade your plan.");
				lMessage.setAccountId(pLoggedInUser.getAccountId());
				lMessage.setUserId(pLoggedInUser.getId());
				lMessage.setUserName(pLoggedInUser.getUsername());
				lMessage.setType(MessageType.STORAGE_QUOTA_EXCEEDED.getValue());
				lMessage.setMessageClass(MessageClass.ALERT.getValue());

				long lTime = System.currentTimeMillis();
				lMessage.setTimeCreatedMs(lTime);
				lMessage.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getOffset(lTime));
				return lMessage;
			} else {
				return null;
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	private List<Message> createCCRelatedMessages(User pLoggedInUser,
			StripeCustomer pStripeCustomer) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering createMessages");
			List<Message> lMessages = new ArrayList<Message>();

			Message lCCExpringSoonMessage = this.getCCExpiringSoonMessage(
					pLoggedInUser, pStripeCustomer);
			// send either cc expiring soon or cc expired message to the user,
			// not both
			if (lCCExpringSoonMessage != null) {
				lMessages.add(lCCExpringSoonMessage);
			} else {
				Message lCCExpiredMessage = this.getCCExpiredMessage(
						pLoggedInUser, pStripeCustomer);
				if (lCCExpiredMessage != null) {
					lMessages.add(lCCExpiredMessage);
				}
			}

			return lMessages;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting createMessages");
		}
	}

	private Message getCCExpiringSoonMessage(User pLoggedInUser,
			StripeCustomer pStripeCustomer) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getCCExpiringSoonMessage");
			if (pStripeCustomer == null) {
				LOGGER.log(Level.WARNING, "Stripe Customer is null");
				return null;
			}

			if (Utils.isCCExpiring(pStripeCustomer.getCardExpYear(),
					pStripeCustomer.getCardExpMonth())) {

				// create message
				Message lMessage = new Message();
				lMessage.setMessage("Your "
						+ pStripeCustomer.getCardBrand()
						+ " card ending in "
						+ pStripeCustomer.getCardLast4()
						+ " is expiring soon. Please click <a href=\"/account/billing\">here</a> to update your credit card information.");
				lMessage.setAccountId(pLoggedInUser.getAccountId());
				lMessage.setUserId(pLoggedInUser.getId());
				lMessage.setUserName(pLoggedInUser.getUsername());
				lMessage.setType(MessageType.CREDIT_CARD_EXPIRING_SOON
						.getValue());
				lMessage.setMessageClass(MessageClass.WARNING.getValue());

				long lTime = System.currentTimeMillis();
				lMessage.setTimeCreatedMs(lTime);
				lMessage.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getOffset(lTime));
				return lMessage;

			}
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getCCExpiringSoonMessage");
		}
	}

	private Message getCCExpiredMessage(User pLoggedInUser,
			StripeCustomer pStripeCustomer) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getCCExpiredMessage");
			if (pStripeCustomer == null) {
				LOGGER.log(Level.WARNING, "Stripe Customer is null");
				return null;
			}
			if (Utils.isCCExpired(pStripeCustomer.getCardExpYear(),
					pStripeCustomer.getCardExpMonth())) {

				// create message
				Message lMessage = new Message();
				lMessage.setMessage("Your "
						+ pStripeCustomer.getCardBrand()
						+ " card ending in "
						+ pStripeCustomer.getCardLast4()
						+ " has expired. Please click <a href=\"/account/billing\">here</a> to update your credit card information.");
				lMessage.setAccountId(pLoggedInUser.getAccountId());
				lMessage.setUserId(pLoggedInUser.getId());
				lMessage.setUserName(pLoggedInUser.getUsername());
				lMessage.setType(MessageType.CREDIT_CARD_EXPIRING_SOON
						.getValue());
				lMessage.setMessageClass(MessageClass.ALERT.getValue());
				long lTime = System.currentTimeMillis();
				lMessage.setTimeCreatedMs(lTime);
				lMessage.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getOffset(lTime));
				return lMessage;

			}
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getCCExpiredMessage");
		}
	}
}
