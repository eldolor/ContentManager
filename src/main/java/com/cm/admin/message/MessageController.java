package com.cm.admin.message;

import java.util.ArrayList;
import java.util.Calendar;
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

			// check to see if CC is expiring
			User lUser = userService.getLoggedInUser();
			StripeCustomer lStripeCustomer = stripeCustomerService.get(lUser
					.getAccountId());

			if (lStripeCustomer != null) {
				List<com.cm.admin.message.transfer.Message> lTfrMessages = new ArrayList<com.cm.admin.message.transfer.Message>();
				List<Message> lMessages = this.createMessages(lUser,
						lStripeCustomer);
				if (lMessages.isEmpty())
					return null;

				for (Message lMessage : lMessages) {
					// only one message per type will ever be displayed to
					// the user
					Message lUnviewedMessageByType = messageDao
							.getMessageByType(lMessage.getType());
					if (lUnviewedMessageByType == null) {
						// save message to db, which returns a message with an
						// id
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
							// already been displayed for the session, so dont
							// display it again
							lMessage = null;
						}
					}
					if (lMessage != null)
						lTfrMessages.add(convert(lMessage));
				}
				response.setStatus(HttpServletResponse.SC_OK);
				return lTfrMessages;

			}

			response.setStatus(HttpServletResponse.SC_OK);
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
			messageDao.update(convert(message));

			response.setStatus(HttpServletResponse.SC_OK);

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

	private List<Message> createMessages(User pLoggedInUser,
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

			if (Utils.isCCExpiring(pStripeCustomer.getCardExpirationYear(),
					pStripeCustomer.getCardExpirationMonth())) {

				// create message
				Message lMessage = new Message();
				lMessage.setMessage("Your "
						+ pStripeCustomer.getCardBrand()
						+ " card ending in "
						+ pStripeCustomer.getCardLast4()
						+ " is expiring soon. Please click <a href=\"/account\">here</a> to update your credit card information.");
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
			if (Utils.isCCExpired(pStripeCustomer.getCardExpirationYear(),
					pStripeCustomer.getCardExpirationMonth())) {

				// create message
				Message lMessage = new Message();
				lMessage.setMessage("Your "
						+ pStripeCustomer.getCardBrand()
						+ " card ending in "
						+ pStripeCustomer.getCardLast4()
						+ " has expired. Please click <a href=\"/account\">here</a> to update your credit card information.");
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
