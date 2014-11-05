package com.cm.contentmanager.contentgroup;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.usermanagement.user.User;
import com.cm.util.Utils;

@Service
public class ContentGroupService {
	@Autowired
	private ContentGroupDao contentGroupDao;

	private static final Logger LOGGER = Logger
			.getLogger(ContentGroupService.class.getName());


	public List<ContentGroup> search(String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<ContentGroup> applications = contentGroupDao.search(searchTerm);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + applications.size()
						+ " applications");
			return applications;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public List<ContentGroup> searchByUserId(Long userId, String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<ContentGroup> applications = contentGroupDao.searchByUserId(userId ,searchTerm);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + applications.size()
						+ " applications");
			return applications;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public List<ContentGroup> searchByAccountId(Long accountId, String searchTerm) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<ContentGroup> applications = contentGroupDao.searchByAccountId(accountId, searchTerm);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + applications.size()
						+ " applications");
			return applications;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public List<ContentGroup> get(Long applicationId, boolean includeDeleted) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			List<ContentGroup> contentGroups = contentGroupDao.get(
					applicationId, includeDeleted);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + contentGroups.size()
						+ " content groups");
			return contentGroups;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	public ContentGroup get(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			return contentGroupDao.get(id);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	public ContentGroup save(User user, ContentGroup contentGroup) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Setting user and account ids for user "
						+ user.getUsername());
			contentGroup.setUserId(user.getId());
			contentGroup.setAccountId(user.getAccountId());
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("start date: " + contentGroup.getStartDateIso8601());

			// convert from ISO 8601 format to Milliseconds
			if (!Utils.isEmpty(contentGroup.getStartDateIso8601()))
				contentGroup.setStartDateMs(javax.xml.bind.DatatypeConverter
						.parseDateTime(contentGroup.getStartDateIso8601())
						.getTime().getTime());
			if (!Utils.isEmpty(contentGroup.getEndDateIso8601()))
				contentGroup.setEndDateMs(javax.xml.bind.DatatypeConverter
						.parseDateTime(contentGroup.getEndDateIso8601())
						.getTime().getTime());
			else
				// set high date
				contentGroup.setEndDateMs(Long.MAX_VALUE);

			return contentGroupDao.save(contentGroup);
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
			contentGroupDao.delete(id, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting delete");
		}

	}

	public void restore(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering restoreContentGroup");
			contentGroupDao.restore(id, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting restore");
		}

	}

	public ContentGroup update(ContentGroup contentGroup) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering update");
			// convert from ISO 8601 format to Milliseconds
			if (!Utils.isEmpty(contentGroup.getStartDateIso8601()))
				contentGroup.setStartDateMs(javax.xml.bind.DatatypeConverter
						.parseDateTime(contentGroup.getStartDateIso8601())
						.getTime().getTime());
			if (!Utils.isEmpty(contentGroup.getEndDateIso8601()))
				contentGroup.setEndDateMs(javax.xml.bind.DatatypeConverter
						.parseDateTime(contentGroup.getEndDateIso8601())
						.getTime().getTime());
			else
				// set high date
				contentGroup.setEndDateMs(Long.MAX_VALUE);
			return contentGroupDao.update(contentGroup);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting update");
		}
	}

}
