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

	public List<Content> search(String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<Content> applications = contentDao.search(searchTerm);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + applications.size()
						+ " applications");
			return applications;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public List<Content> searchByUserId(Long userId, String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<Content> applications = contentDao.searchByUserId(userId,
					searchTerm);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + applications.size()
						+ " applications");
			return applications;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public List<Content> searchByAccountId(Long accountId, String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<Content> applications = contentDao.searchByAccountId(
					accountId, searchTerm);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + applications.size()
						+ " applications");
			return applications;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public List<Content> get(Long applicationId, Long contentGroupId,
			boolean deleted, boolean enabled) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			return contentDao.get(applicationId, contentGroupId, deleted,
					enabled);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	public List<Content> get(Long applicationId, Long contentGroupId,
			boolean deleted) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			return contentDao.get(applicationId, contentGroupId, deleted);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	public List<Content> get(Long applicationId, Long contentGroupId,
			String type, boolean deleted, boolean enabled) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("contentGroupId: " + contentGroupId + " Type: "
						+ type);
			return contentDao.get(applicationId, contentGroupId, type, deleted,
					enabled);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	public List<Content> get(Long applicationId, boolean includeDeleted) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			List<Content> contents = contentDao.get(applicationId,
					includeDeleted);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + contents.size() + " contents");
			return contents;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	public List<Content> get(boolean deleted, boolean enabled) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			return contentDao.get(deleted, enabled);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	public Content get(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			return contentDao.get(id);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	public Content get(String uri) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			return contentDao.get(uri);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	public Content save(User user, Content content) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");

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

			return contentDao.save(content);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting save");
		}
	}

	public void delete(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering delete");
			contentDao.delete(id, timeUpdatedMs, timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deletedeleteContent");
		}

	}

	public void restore(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering restoreContent");
			contentDao.restore(id, timeUpdatedMs, timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting restore");
		}

	}

	public void update(Content content) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering update");
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
			contentDao.update(content);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting update");
		}
	}

	public void delete(Long applicationId, Long contentGroupId,
			Long timeUpdatedMs, Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteAllContents");
			contentDao.delete(applicationId, contentGroupId, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting delete");
		}

	}

	public void updateEnabled(Content content) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateEnabled");
			Content lContent = this.get(content.getId());
			if (lContent != null) {
				lContent.setEnabled(content.isEnabled());
				lContent.setTimeUpdatedTimeZoneOffsetMs(content
						.getTimeUpdatedMs());
				lContent.setTimeUpdatedTimeZoneOffsetMs(content
						.getTimeUpdatedTimeZoneOffsetMs());
				contentDao.update(lContent);
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
