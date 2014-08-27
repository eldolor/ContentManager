package com.cm.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.content.Content;
import com.cm.contentmanager.contentgroup.ContentGroup;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

public class Utils {
	private static final Logger LOGGER = Logger
			.getLogger(Utils.class.getName());
	public static void triggerUpdateQuotaMessage(Long pAccountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering triggerUpdateQuotaMessage");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to mark changes staged");
			Queue queue = QueueFactory.getQueue("contentqueue");
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl(
							"/tasks/quota/update/"
									+ pAccountId)
					.param("accountId", String.valueOf(pAccountId))
					.method(Method.POST);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting triggerUpdateQuotaMessage");
		}
	}

	public static void triggerChangesStagedMessage(Long pApplicationId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering triggerChangesStagedMessage");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to mark changes staged");
			Queue queue = QueueFactory.getQueue("contentqueue");
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl(
							"/tasks/application/changesstaged/"
									+ pApplicationId)
					.param("applicationId", String.valueOf(pApplicationId))
					.method(Method.POST);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting triggerSendContentListMessage");
		}
	}

	public static void triggerUpdateContentSizeInBytesMessage(Long pContentId,
			String pUri) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering triggerUpdateContentSizeInBytesMessage");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to update content size in bytes.");
			Queue queue = QueueFactory.getQueue("contentqueue");
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl(
							"/tasks/content/updatesize/" + pContentId + "/"
									+ pUri)
					.param("id", String.valueOf(pContentId))
					.param("uri", String.valueOf(pUri)).method(Method.POST);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting triggerUpdateContentSizeInBytesMessage");
		}
	}

	public static void triggerSendContentListMessages(String pTrackingId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering triggerSendContentListMessage");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to send content list messages to handsets");
			Queue queue = QueueFactory.getQueue("gcmqueue");
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl(
							"/tasks/gcm/sendcontentlistmessages/" + pTrackingId)
					.param("trackingId", pTrackingId).method(Method.POST);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting triggerSendContentListMessage");
		}
	}

	public static void triggerSendContentListMessage(String pTrackingId,
			String pGcmId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering triggerSendContentListMessage");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to send content list message to a single handset");
			Queue queue = QueueFactory.getQueue("gcmqueue");
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl(
							"/tasks/gcm/sendcontentlistmessage/" + pTrackingId
									+ pGcmId).param("gcmId", pGcmId)
					.method(Method.POST);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting triggerSendContentListMessage");
		}
	}

	public static void triggerUpdateLastKnownTimestampMessage(String pTrackingId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering triggerUpdateLastKnownTimestampMessage");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to update last known timestamp in Memcache");
			Queue queue = QueueFactory.getQueue("contentqueue");
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl(
							"/tasks/contentserver/updatelastknowntimestamp/"
									+ pTrackingId)
					.param("trackingId", pTrackingId).method(Method.POST);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting triggerUpdateLastKnownTimestampMessage");
		}
	}

	public static long getLastKnownTimestamp(Application pApplication,
			List<ContentGroup> pContentGroups, List<Content> pContents) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getLastKnownTimestamp");

			long lLastKnownTimestamp = 0L;

			if (pApplication != null)
				lLastKnownTimestamp = pApplication.getTimeUpdatedMs();

			for (Iterator<ContentGroup> iterator = pContentGroups.iterator(); iterator
					.hasNext();) {
				ContentGroup lContentGroup = iterator.next();
				if (lContentGroup.getTimeUpdatedMs() > lLastKnownTimestamp) {
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("doHandshake:: Content Group was updated after "
								+ lLastKnownTimestamp);
					lLastKnownTimestamp = lContentGroup.getTimeUpdatedMs();
				}
			}
			for (Iterator<Content> iterator = pContents.iterator(); iterator
					.hasNext();) {
				Content lContent = iterator.next();
				if (lContent.getTimeUpdatedMs() > lLastKnownTimestamp) {
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("doHandshake:: Content was updated after "
								+ lLastKnownTimestamp);
					lLastKnownTimestamp = lContent.getTimeUpdatedMs();
				}
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("LastKnownTimestamp is " + lLastKnownTimestamp);

			return lLastKnownTimestamp;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getLastKnownTimestamp");
		}
	}

	public static void sleepFor(long ms) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Begin sleeping for " + ms + " ms");
			Thread.sleep(ms);
		} catch (InterruptedException e1) {
			// Activity finished before we complete - exit.
			Thread.currentThread().interrupt();
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Woke up after " + ms + " ms");
		}

	}

	public static boolean isEmpty(String string) {
		return ((string != null) && (!string.equals(""))) ? false : true;
	}

	public static boolean isEmpty(Long pLong) {
		return ((pLong != null)) ? false : true;
	}

	public static String getBlobFileName(String key) {
		BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
		BlobKey blobKey = new BlobKey(key);
		final BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
		if (blobInfo != null)
			return blobInfo.getFilename();
		else
			return null;

	}

	public static List<com.cm.contentserver.transfer.Content> convertToTransferFormat(
			List<Content> pContentList) {
		List<com.cm.contentserver.transfer.Content> lContentListTransferFormat = new ArrayList<com.cm.contentserver.transfer.Content>();
		for (Content lContent : pContentList) {
			lContentListTransferFormat.add(Utils
					.convertToTransferFormat(lContent));
		}
		return lContentListTransferFormat;
	}

	public static com.cm.contentserver.transfer.Content convertToTransferFormat(
			Content pContent) {
		com.cm.contentserver.transfer.Content lContent = new com.cm.contentserver.transfer.Content();
		lContent.setApplicationId(pContent.getApplicationId());
		lContent.setContentGroupId(pContent.getContentGroupId());
		lContent.setId(pContent.getId());
		lContent.setName(pContent.getName());
		lContent.setTimeCreatedMs(pContent.getTimeCreatedMs());
		lContent.setTimeUpdatedMs(pContent.getTimeUpdatedMs());
		lContent.setType(pContent.getType());
		lContent.setUri(pContent.getUri());
		lContent.setSizeInBytes(pContent.getSizeInBytes());
		return lContent;

	}

	public static void LogInfo(String log) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info(log);
	}

	/**
	 * 
	 * @param fromEmailAddress
	 * @param fromName
	 * @param toEmailAddress
	 * @param toName
	 * @param subject
	 * @param htmlBody
	 * @param textBody
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendEmail(String fromEmailAddress, String fromName,
			String toEmailAddress, String toName, String subject,
			String htmlBody, String textBody) throws MessagingException,
			UnsupportedEncodingException {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering sendEmail");
		Multipart mp = new MimeMultipart();
		MimeBodyPart htmlPart = new MimeBodyPart();
		if (htmlBody != null) {
			htmlPart.setContent(htmlBody, "text/html");
			mp.addBodyPart(htmlPart);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("sendEmail: Html body part: " + htmlBody);
		}

		MimeBodyPart textPart = new MimeBodyPart();
		if (textBody != null) {
			textPart.setContent(textBody, "text/plain");
			mp.addBodyPart(textPart);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("sendEmail: Text body part: " + textBody);
		}

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(fromEmailAddress, fromName));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
				toEmailAddress, toName));
		msg.setSubject(subject);
		msg.setContent(mp);
		Transport.send(msg);
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting sendEmail");
	}

	/**
	 * 
	 * @param pYear
	 * @param pMonth
	 * @return
	 */
	public static boolean isCCExpiring(int pYear, int pMonth) {
		Calendar lCalendar = Calendar.getInstance();
		int lCurrentYear = lCalendar.get(Calendar.YEAR);
		// The first month is a Zero
		int lCurrentMonth = lCalendar.get(Calendar.MONTH);
		// increment to match what's on StripeCustomer
		++lCurrentMonth;
		// TODO: handle CC that is expiring in January of next year
		// match the year and month or month -1
		if ((pYear == lCurrentYear)
				&& (pMonth == (lCurrentMonth - 1) || (pMonth == (lCurrentMonth))))
			return true;

		return false;

	}

	/**
	 * 
	 * @param pYear
	 * @param pMonth
	 * @return
	 */
	public static boolean isCCExpired(int pYear, int pMonth) {
		Calendar lCalendar = Calendar.getInstance();
		// The first month is a Zero
		int lCurrentMonth = lCalendar.get(Calendar.MONTH);
		// increment to match what's on StripeCustomer
		++lCurrentMonth;

		int lCurrentYear = lCalendar.get(Calendar.YEAR);
		// TODO: handle CC that is expiring in January of next year
		if (((pYear < lCurrentYear) || (pYear == lCurrentYear))
				&& (pMonth < lCurrentMonth))
			return true;
		return false;

	}

}
