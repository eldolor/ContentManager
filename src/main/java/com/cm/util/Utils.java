package com.cm.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import com.cm.contentmanager.content.Content;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;

public class Utils {
	private static final Logger LOGGER = Logger
			.getLogger(Utils.class.getName());

	public static boolean isEmpty(String string) {
		return ((string != null) && (!string.equals(""))) ? false : true;
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

}
