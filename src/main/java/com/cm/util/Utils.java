package com.cm.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
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

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;

import com.cm.config.CanonicalCouponTypes;
import com.cm.config.Configuration;
import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.content.Content;
import com.cm.contentmanager.contentgroup.ContentGroup;
import com.cm.usermanagement.user.Coupon;
import com.cm.usermanagement.user.User;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

public class Utils {
	private static final String TEXT_HTML_CHARSET_UTF_8 = "text/html; charset=utf-8";
	private static final Logger LOGGER = Logger
			.getLogger(Utils.class.getName());

	public static void triggerSendNotificationMessages(String pTrackingId,
			String pMessage, long delayInMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to send notification messages to handsets");
			Queue queue = QueueFactory.getQueue(Configuration.GCM_QUEUE_NAME);
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl(
							"/tasks/gcm/sendnotificationmessages/"
									+ pTrackingId)
					.param("trackingId", pTrackingId)
					.param("message", pMessage).method(Method.POST)
					.countdownMillis(delayInMs);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public static void triggerSendNotificationMessage(String pGcmId,
			String pMessage, long delayInMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to send notification message to a single handset");
			Queue queue = QueueFactory.getQueue(Configuration.GCM_QUEUE_NAME);
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl("/tasks/gcm/sendnotificationmessage/" + pGcmId)
					.param("gcmId", pGcmId).param("message", pMessage)
					.method(Method.POST).countdownMillis(delayInMs);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public static void triggerRollupMessage(Long pApplicationId,
			Long eventStartTimeMs, Long eventEndTimeMs, long delayInMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			Queue queue = QueueFactory
					.getQueue(Configuration.CONTENT_STATS_QUEUE_NAME);
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl(
							"/tasks/rollup/contentstats/"
									+ String.valueOf(pApplicationId) + "/"
									+ String.valueOf(eventStartTimeMs) + "/"
									+ String.valueOf(eventEndTimeMs))
					.param("id", String.valueOf(pApplicationId))
					.param("eventStartTimeMs", String.valueOf(eventStartTimeMs))
					.param("eventEndTimeMs", String.valueOf(eventEndTimeMs))
					.method(Method.POST).countdownMillis(delayInMs);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}

	}

	// public static void triggerUpdateBandwidthUtilizationMessage(Long pId,
	// long delayInMs) {
	// try {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Entering");
	// Queue queue = QueueFactory
	// .getQueue(Configuration.CONTENT_QUEUE_NAME);
	// TaskOptions taskOptions = TaskOptions.Builder
	// .withUrl("/tasks/bandwidth/utilization/update/" + pId)
	// .param("id", String.valueOf(pId)).method(Method.POST)
	// .countdownMillis(delayInMs);
	// queue.add(taskOptions);
	// } finally {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Exiting");
	// }
	//
	// }

	public static void triggerUpdateBandwidthUtilizationMessage(
			Long applicationId, Long sizeInBytes, long delayInMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			Queue queue = QueueFactory
					.getQueue(Configuration.CONTENT_QUEUE_NAME);
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl(
							"/tasks/bandwidth/utilization/update/"
									+ applicationId + "/" + sizeInBytes)
					.param("applicationId", String.valueOf(applicationId))
					.param("sizeInBytes", String.valueOf(sizeInBytes))
					.method(Method.POST).countdownMillis(delayInMs);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}

	}

	public static void triggerForgotPasswordEmailMessage(String pGuid,
			long delayInMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			Queue queue = QueueFactory.getQueue(Configuration.EMAIL_QUEUE_NAME);
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl("/tasks/email/sendforgotpasswordemail/" + pGuid)
					.param("guid", pGuid).method(Method.POST)
					.countdownMillis(delayInMs);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}

	}

	public static void triggerUpdateQuotaMessage(Long pAccountId, long delayInMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to update quota");
			Queue queue = QueueFactory
					.getQueue(Configuration.CONTENT_QUEUE_NAME);
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl("/tasks/quota/update/" + pAccountId)
					.param("accountId", String.valueOf(pAccountId))
					.method(Method.POST).countdownMillis(delayInMs);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public static void triggerUpdateQuotaUtilizationMessage(Long pAccountId,
			long delayInMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to update quota utilization");
			Queue queue = QueueFactory
					.getQueue(Configuration.CONTENT_QUEUE_NAME);
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl("/tasks/quota/utilization/update/" + pAccountId)
					.param("accountId", String.valueOf(pAccountId))
					.method(Method.POST).countdownMillis(delayInMs);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public static void triggerChangesStagedMessage(Long pApplicationId,
			long delayInMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering triggerChangesStagedMessage");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to mark changes staged");
			Queue queue = QueueFactory
					.getQueue(Configuration.CONTENT_QUEUE_NAME);
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl(
							"/tasks/application/changesstaged/"
									+ pApplicationId)
					.param("applicationId", String.valueOf(pApplicationId))
					.method(Method.POST).countdownMillis(delayInMs);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting triggerSendContentListMessage");
		}
	}

	public static void triggerUpdateContentSizeInBytesMessage(Long pContentId,
			long delayInMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering triggerUpdateContentSizeInBytesMessage");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to update content size in bytes.");
			Queue queue = QueueFactory
					.getQueue(Configuration.CONTENT_QUEUE_NAME);
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl("/tasks/content/updatesize/" + pContentId)
					.param("id", String.valueOf(pContentId))
					.method(Method.POST).countdownMillis(delayInMs);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting triggerUpdateContentSizeInBytesMessage");
		}
	}

	public static void triggerSendContentListMessages(String pTrackingId,
			long delayInMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering triggerSendContentListMessage");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to send content list messages to handsets");
			Queue queue = QueueFactory.getQueue(Configuration.GCM_QUEUE_NAME);
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl(
							"/tasks/gcm/sendcontentlistmessages/" + pTrackingId)
					.param("trackingId", pTrackingId).method(Method.POST)
					.countdownMillis(delayInMs);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting triggerSendContentListMessage");
		}
	}

	public static void triggerSendContentListMessage(String pTrackingId,
			String pGcmId, long delayInMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering triggerSendContentListMessage");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to send content list message to a single handset");
			Queue queue = QueueFactory.getQueue(Configuration.GCM_QUEUE_NAME);
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl(
							"/tasks/gcm/sendcontentlistmessage/" + pTrackingId
									+ "/" + pGcmId)
					.param("trackingId", pTrackingId).param("gcmId", pGcmId)
					.method(Method.POST).countdownMillis(delayInMs);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting triggerSendContentListMessage");
		}
	}

	@Deprecated
	public static void triggerUpdateLastKnownTimestampMessage(
			String pTrackingId, long delayInMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering triggerUpdateLastKnownTimestampMessage");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Triggering message to update last known timestamp in Memcache");
			Queue queue = QueueFactory
					.getQueue(Configuration.CONTENT_QUEUE_NAME);
			TaskOptions taskOptions = TaskOptions.Builder
					.withUrl(
							"/tasks/contentserver/updatelastknowntimestamp/"
									+ pTrackingId)
					.param("trackingId", pTrackingId).method(Method.POST)
					.countdownMillis(delayInMs);
			queue.add(taskOptions);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting triggerUpdateLastKnownTimestampMessage");
		}
	}

	public static void updateLastKnownTimestamp(String pTrackingId,
			long pTimestamp, long delayInMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering triggerUpdateLastKnownTimestampMessage");
			try {
				CacheFactory cacheFactory = CacheManager.getInstance()
						.getCacheFactory();
				Cache lCache = cacheFactory.createCache(Collections.emptyMap());
				if (lCache != null) {
					lCache.put(pTrackingId, pTimestamp);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Added to Memcache: " + pTimestamp);
				}
			} catch (Throwable t) {
				LOGGER.log(Level.SEVERE, "Unable to add to Memcache", t);
			}
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
		lContent.setTags(pContent.getTags());

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
	public static void sendMultipartEmail(String fromEmailAddress,
			String fromName, String toEmailAddress, String toName,
			String subject, String htmlBody, String textBody)
			throws MessagingException, UnsupportedEncodingException {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering");
		Multipart lMultipart = new MimeMultipart();

		// according to the multipart MIME spec, the order of the parts are
		// important. They should be added in order from low fidelity to high
		// fidelity.
		// add text part first
		if (textBody != null) {
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText(textBody, "utf-8");
			lMultipart.addBodyPart(textPart);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Text body part: " + textBody);
		}

		if (htmlBody != null) {
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlBody, "text/html; charset=utf-8");
			lMultipart.addBodyPart(htmlPart);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Html body part: " + htmlBody);
		}

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(fromEmailAddress, fromName));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
				toEmailAddress, toName));
		msg.setSubject(subject);
		msg.setContent(lMultipart);
		Transport.send(msg);
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting");
	}

	/**
	 * 
	 * @param fromEmailAddress
	 * @param fromName
	 * @param toEmailAddress
	 * @param toName
	 * @param subject
	 * @param messageBody
	 * @param charset
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendEmail(String fromEmailAddress, String fromName,
			String toEmailAddress, String toName, String subject,
			String messageBody, String charset) throws MessagingException,
			UnsupportedEncodingException {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering");
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Message:" + messageBody);
		Properties props = new Properties();
		Session lSession = Session.getDefaultInstance(props, null);
		MimeMessage lMesssage = new MimeMessage(lSession);
		lMesssage.setContent(messageBody, charset);
		lMesssage.setFrom(new InternetAddress(fromEmailAddress, fromName));
		lMesssage.addRecipient(Message.RecipientType.TO, new InternetAddress(
				toEmailAddress, toName));
		lMesssage.setSubject(subject);
		Transport.send(lMesssage);
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting");
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

	public static Calendar getStartOfDay(long timeInMs, TimeZone timeZone) {
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTimeInMillis(timeInMs);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	public static Calendar getEndOfDay(long timeInMs, TimeZone timeZone) {
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTimeInMillis(timeInMs);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 23, 59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar;
	}

	public static Calendar getStartOfDayToday(TimeZone timeZone) {
		Calendar calendar = Calendar.getInstance(timeZone);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	public static Calendar getEndOfDayToday(TimeZone timeZone) {
		Calendar calendar = Calendar.getInstance(timeZone);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 23, 59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar;
	}

	public static Calendar getStartOfDayYesterday(TimeZone timeZone) {
		Calendar calendar = getStartOfDayToday(timeZone);
		calendar.add(Calendar.DATE, -1);
		return calendar;
	}

	public static Calendar getEndOfDayYesterday(TimeZone timeZone) {
		Calendar calendar = getEndOfDayToday(timeZone);
		calendar.add(Calendar.DATE, -1);
		return calendar;
	}

	public static Calendar getOneMonthFromToday(TimeZone timeZone) {
		Calendar calendar = getEndOfDayToday(timeZone);
		calendar.add(Calendar.MONTH, 1);
		return calendar;
	}

	public static Calendar getNMonthsFromToday(int months, TimeZone timeZone) {
		Calendar calendar = getEndOfDayToday(timeZone);
		calendar.add(Calendar.MONTH, months);
		return calendar;
	}

	public static int getRandomNumber(int rangeBegin, int rangeEnd) {
		Random r = new Random();
		return r.nextInt(rangeEnd - rangeBegin) + rangeBegin;
	}

	public static int getDaysBetweenTodayAndTimestamp(long timeInMs,
			TimeZone timeZone) {
		Calendar calendarToday = getEndOfDayToday(timeZone);

		return Days.daysBetween(new DateTime(timeInMs),
				new DateTime(calendarToday)).getDays();
	}

	public static long getEndOfDayMinusDays(int days, TimeZone timeZone) {
		DateTime lEod = DateTime.now(DateTimeZone.forTimeZone(timeZone));
		lEod = lEod.withTimeAtStartOfDay();
		// roll over to next day
		lEod = lEod.plusDays(1);
		// roll back 1 millisec to bring it to EOD time
		lEod = lEod.minus(1);
		// do the math
		lEod = lEod.minusDays(days);
		return lEod.getMillis();
	}

	public static long getEndOfDayPlusDays(int days, TimeZone timeZone) {
		DateTime lEod = DateTime.now(DateTimeZone.forTimeZone(timeZone));
		lEod = lEod.withTimeAtStartOfDay();
		// roll over to next day
		lEod = lEod.plusDays(1);
		// roll back 1 millisec to bring it to EOD time
		lEod = lEod.minus(1);
		// do the math
		lEod = lEod.plusDays(days);
		return lEod.getMillis();
	}

	public static long getStartOfDayMinusDays(int days, TimeZone timeZone) {
		DateTime lSod = DateTime.now(DateTimeZone.forTimeZone(timeZone));
		lSod = lSod.withTimeAtStartOfDay();
		// do the math
		lSod = lSod.minusDays(days);
		return lSod.getMillis();
	}

	public static long getStartOfDayPlusDays(int days, TimeZone timeZone) {
		DateTime lSod = DateTime.now(DateTimeZone.forTimeZone(timeZone));
		lSod = lSod.withTimeAtStartOfDay();
		// do the math
		lSod = lSod.plusDays(days);
		return lSod.getMillis();
	}

	public static String generatePromoCode() {
		char chars[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
				.toCharArray();
		int max = 100000000;
		int random = (int) (Math.random() * max);
		StringBuffer sb = new StringBuffer();
		while (random > 0) {
			sb.append(chars[random % chars.length]);
			random /= chars.length;
		}
		return sb.toString();
	}

	public static Coupon generateReferAFriendCoupon(User pUser) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			Coupon lCoupon = new Coupon();
			lCoupon.setAccountId(pUser.getAccountId());
			lCoupon.setUserId(pUser.getId());
			lCoupon.setType(CanonicalCouponTypes.REFER_A_FRIEND.getValue());
			lCoupon.setCode(Utils.generatePromoCode());
			// valid for the next 6 months, ends EOD for the user's timezone
			TimeZone lTimeZone = TimeZone.getTimeZone("UTC");
			lTimeZone.setRawOffset(pUser.getTimeCreatedTimeZoneOffsetMs()
					.intValue());
			lCoupon.setRedeemByMs(Utils.getNMonthsFromToday(Configuration.REFER_A_FRIEND_PERIOD_IN_MONTHS, lTimeZone)
					.getTimeInMillis());

			lCoupon.setTimeCreatedMs(pUser.getTimeCreatedMs());
			lCoupon.setTimeCreatedTimeZoneOffsetMs(pUser
					.getTimeCreatedTimeZoneOffsetMs());
			return lCoupon;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}

	}

	public static void sendReferAFriendPromoClaimedEmailToReferrer(User pUser,
			Coupon pCoupon) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			StringBuilder lHtmlFormattedHeader = new StringBuilder();

			lHtmlFormattedHeader
					.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">Thank you for referring a friend to Skok.</p>");
			lHtmlFormattedHeader
					.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">You get an additional 5GB of Bandwidth per Month, and an additional 5GB of Storage. See <a href=\"https://www.skok.co/account/usage\">Account Usage</a></p>");

			StringBuilder lHtmlFormattedCallout = new StringBuilder();
			lHtmlFormattedCallout
					.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\"><b>Powerful New Features</b></p>");
			lHtmlFormattedCallout.append("<ol>");
			lHtmlFormattedCallout.append("<li>Cloud-driven Architecture</li>");
			lHtmlFormattedCallout
					.append("<li>Advanced Content Management Platform</li>");
			lHtmlFormattedCallout
					.append("<li>Streamlined Content Delivery</li>");
			lHtmlFormattedCallout.append("<li>Auto-sizing of Images</li>");
			lHtmlFormattedCallout
					.append("<li>Say Goodbye to Google Play APK Expansion Files</li>");
			lHtmlFormattedCallout.append("<li>Continuous Content Updates</li>");
			lHtmlFormattedCallout.append("<li>No Extra Coding Required</li>");
			lHtmlFormattedCallout
					.append("<li>Easily-pluggable &amp; Feature-rich SDK</li>");
			lHtmlFormattedCallout.append("<li>Mobile Device Storage</li>");
			lHtmlFormattedCallout.append("<li>Advanced Caching on Device</li>");
			lHtmlFormattedCallout
					.append("<li>Non-Blocking Content Downloads</li>");
			lHtmlFormattedCallout
					.append("<li>Manages Content Downloads over Spotty Networks</li>");
			lHtmlFormattedCallout.append("<li>Download Notifications</li>");
			lHtmlFormattedCallout
					.append("<li>Analytics to Track Usage Statistics of your Content</li>");
			lHtmlFormattedCallout.append("</ol>");
			String lEmailTemplate = new SkokEmailBuilder().build(
					lHtmlFormattedHeader.toString(),
					lHtmlFormattedCallout.toString(), pCoupon);
			Utils.sendEmail(Configuration.FROM_EMAIL_ADDRESS,
					Configuration.FROM_NAME, pUser.getUsername(), "",
					"Thank you for referring a friend to "
							+ Configuration.SITE_NAME, lEmailTemplate,
					TEXT_HTML_CHARSET_UTF_8);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public static void sendWelcomeEmail(User pUser, Coupon pCoupon,
			boolean pIsReferral) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			StringBuilder lHtmlFormattedHeader = new StringBuilder();

			lHtmlFormattedHeader
					.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">Welcome to Skok.</p>");
			if (pIsReferral) {
				lHtmlFormattedHeader
						.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">You get an additional 5GB of Bandwidth per Month, and an additional 5GB of Storage, for being referred. See <a href=\"https://www.skok.co/account/usage\">Account Usage</a></p>");
			}
			lHtmlFormattedHeader
					.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">Skok is an Advanced Content Management and  Delivery platform for your Mobile Apps. Skok delivers rich content to your Mobile Apps, and stores it locally on mobile devices.</p>");
			lHtmlFormattedHeader
					.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">This elevates user experience of your Mobile Apps. Your content loads much faster, and users can engage with your rich content, even if they lose their data connection.</p>");

			lHtmlFormattedHeader
					.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">You can find out more at <a href=\"http://skok.co/docs/overview\">Skok</a> </p>");

			StringBuilder lHtmlFormattedCallout = new StringBuilder();
			lHtmlFormattedCallout
					.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\"><b>Powerful New Features</b></p>");
			lHtmlFormattedCallout.append("<ol>");
			lHtmlFormattedCallout.append("<li>Cloud-driven Architecture</li>");
			lHtmlFormattedCallout
					.append("<li>Advanced Content Management Platform</li>");
			lHtmlFormattedCallout
					.append("<li>Streamlined Content Delivery</li>");
			lHtmlFormattedCallout.append("<li>Auto-sizing of Images</li>");
			lHtmlFormattedCallout
					.append("<li>Say Goodbye to Google Play APK Expansion Files</li>");
			lHtmlFormattedCallout.append("<li>Continuous Content Updates</li>");
			lHtmlFormattedCallout.append("<li>No Extra Coding Required</li>");
			lHtmlFormattedCallout
					.append("<li>Easily-pluggable &amp; Feature-rich SDK</li>");
			lHtmlFormattedCallout.append("<li>Mobile Device Storage</li>");
			lHtmlFormattedCallout.append("<li>Advanced Caching on Device</li>");
			lHtmlFormattedCallout
					.append("<li>Non-Blocking Content Downloads</li>");
			lHtmlFormattedCallout
					.append("<li>Manages Content Downloads over Spotty Networks</li>");
			lHtmlFormattedCallout.append("<li>Download Notifications</li>");
			lHtmlFormattedCallout
					.append("<li>Analytics to Track Usage Statistics of your Content</li>");
			lHtmlFormattedCallout.append("</ol>");
			String lEmailTemplate = new SkokEmailBuilder().build(
					lHtmlFormattedHeader.toString(),
					lHtmlFormattedCallout.toString(), pCoupon);
			Utils.sendEmail(Configuration.FROM_EMAIL_ADDRESS,
					Configuration.FROM_NAME, pUser.getUsername(), "",
					"Welcome to " + Configuration.SITE_NAME, lEmailTemplate,
					TEXT_HTML_CHARSET_UTF_8);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public static void sendReferAFriendEmail(User pUser, Coupon pReferAFriendCoupon) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			String lEmailTemplate = new SkokEmailBuilder().build(null, null,
					pReferAFriendCoupon);
			Utils.sendEmail(Configuration.FROM_EMAIL_ADDRESS,
					Configuration.FROM_NAME, pUser.getUsername(), "",
					"Refer a friend to "
							+ Configuration.SITE_NAME, lEmailTemplate,
					TEXT_HTML_CHARSET_UTF_8);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

}
