package com.cm.contentmanager.content;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.usermanagement.user.User;
import com.cm.util.Utils;

@Service
public class ContentService {
	@Autowired
	private ContentDao contentDao;

	private static final Logger LOGGER = Logger.getLogger(ContentService.class
			.getName());

	public List<Content> getAllContent(Long applicationId, Long contentGroupId,
			boolean deleted, boolean enabled) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getAllContent");
			return contentDao.getAllContent(applicationId, contentGroupId,
					deleted, enabled);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getAllContent");
		}
	}

	public List<Content> getAllContent(Long applicationId, Long contentGroupId,
			boolean deleted) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getAllContent");
			return contentDao.getAllContent(applicationId, contentGroupId,
					deleted);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getAllContent");
		}
	}

	public List<Content> getAllContent(Long applicationId, Long contentGroupId,
			String type, boolean deleted, boolean enabled) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getAllContent");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("contentGroupId: " + contentGroupId + " Type: "
						+ type);
			return contentDao.getAllContent(applicationId, contentGroupId,
					type, deleted, enabled);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getAllContent");
		}
	}

	public List<Content> getAllContent(boolean deleted, boolean enabled) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getAllContent");
			return contentDao.getAllContent(deleted, enabled);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getAllContent");
		}
	}

	public Content getContent(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContent");
			return contentDao.getContent(id);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContent");
		}
	}

	public Content saveContent(User user, Content content) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering saveContent");

			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Setting user and account ids for user "
						+ user.getUsername());
			content.setUserId(user.getId());
			content.setAccountId(user.getAccountId());
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("start date: " + content.getStartDateIso8601());

			// convert from ISO 8601 format to Milliseconds
			if (!Utils.isEmpty(content.getStartDateIso8601()))
				content.setStartDateMs(javax.xml.bind.DatatypeConverter
						.parseDateTime(content.getStartDateIso8601()).getTime()
						.getTime());
			if (!Utils.isEmpty(content.getEndDateIso8601()))
				content.setEndDateMs(javax.xml.bind.DatatypeConverter
						.parseDateTime(content.getEndDateIso8601()).getTime()
						.getTime());
			else
				// set high date
				content.setEndDateMs(Long.MAX_VALUE);

			return contentDao.saveContent(content);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting saveContent");
		}
	}

	public void deleteContent(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteContent");
			contentDao.deleteContent(id, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteContent");
		}

	}

	public void restoreContent(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering restoreContent");
			contentDao.restoreContent(id, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting restoreContent");
		}

	}

	public void updateContent(Content content) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateContent");
			// convert from ISO 8601 format to Milliseconds
			if (!Utils.isEmpty(content.getStartDateIso8601()))
				content.setStartDateMs(javax.xml.bind.DatatypeConverter
						.parseDateTime(content.getStartDateIso8601()).getTime()
						.getTime());
			if (!Utils.isEmpty(content.getEndDateIso8601()))
				content.setEndDateMs(javax.xml.bind.DatatypeConverter
						.parseDateTime(content.getEndDateIso8601()).getTime()
						.getTime());
			else
				// set high date
				content.setEndDateMs(Long.MAX_VALUE);
			contentDao.updateContent(content);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateContent");
		}
	}

	public void deleteAllContent(Long applicationId, Long contentGroupId,
			Long timeUpdatedMs, Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteAllContents");
			contentDao.deleteAllContent(applicationId, contentGroupId,
					timeUpdatedMs, timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteAllContents");
		}

	}

	public void updateEnabled(Content content) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateEnabled");
			Content lContent = this.getContent(content.getId());
			if (lContent != null) {
				lContent.setEnabled(content.isEnabled());
				lContent.setTimeUpdatedTimeZoneOffsetMs(content
						.getTimeUpdatedMs());
				lContent.setTimeUpdatedTimeZoneOffsetMs(content
						.getTimeUpdatedTimeZoneOffsetMs());
				contentDao.updateContent(lContent);
			} else {
				LOGGER.log(Level.WARNING, "Content not found");
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateEnabled");
		}
	}
	public void updateContentSize(Long id, Long size) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateContentSize");
			contentDao.updateContentSize(id, size);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateContentSize");
		}
	}

}
