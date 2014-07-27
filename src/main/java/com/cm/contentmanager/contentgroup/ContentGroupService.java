package com.cm.contentmanager.contentgroup;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.usermanagement.user.User;
import com.cm.util.Util;

@Service
public class ContentGroupService {
	@Autowired
	private ContentGroupDao contentGroupDao;

	private static final Logger LOGGER = Logger
			.getLogger(ContentGroupService.class.getName());

	public List<ContentGroup> getContentGroupsByApplicationId(Long applicationId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getAllContentGroups");
			List<ContentGroup> contentGroups = contentGroupDao
					.getContentGroupsByApplicationId(applicationId);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + contentGroups.size()
						+ " content groups");
			return contentGroups;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getAllContentGroups");
		}
	}

	public List<ContentGroup> getContentGroupsByUserId(Long userId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContentGroupsByUserId");
			List<ContentGroup> contentGroups = contentGroupDao
					.getContentGroupsByUserId(userId);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + contentGroups.size()
						+ " content groups");
			return contentGroups;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContentGroupsByUserId");
		}
	}

	public List<ContentGroup> getContentGroupsByAccountId(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContentGroupsByAccountId");
			List<ContentGroup> contentGroups = contentGroupDao
					.getContentGroupsByAccountId(accountId);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + contentGroups.size()
						+ " content groups");
			return contentGroups;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContentGroupsByAccountId");
		}
	}

	public ContentGroup getContentGroup(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContentGroup");
			return contentGroupDao.getContentGroup(id);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContentGroup");
		}
	}

	public void saveContentGroup(User user, ContentGroup contentGroup) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering saveContentGroup");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Setting user and account ids for user "
						+ user.getUsername());
			contentGroup.setUserId(user.getId());
			contentGroup.setAccountId(user.getAccountId());
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("start date: " + contentGroup.getStartDateIso8601());

			// convert from ISO 8601 format to Milliseconds
			if (!Util.isEmpty(contentGroup.getStartDateIso8601()))
				contentGroup.setStartDateMs(javax.xml.bind.DatatypeConverter
						.parseDateTime(contentGroup.getStartDateIso8601())
						.getTime().getTime());
			if (!Util.isEmpty(contentGroup.getEndDateIso8601()))
				contentGroup.setEndDateMs(javax.xml.bind.DatatypeConverter
						.parseDateTime(contentGroup.getEndDateIso8601())
						.getTime().getTime());
			else
				// set high date
				contentGroup.setEndDateMs(Long.MAX_VALUE);

			contentGroupDao.saveContentGroup(contentGroup);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting saveContentGroup");
		}
	}

	public void deleteContentGroup(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteContentGroup");
			contentGroupDao.deleteContentGroup(id, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteContentGroup");
		}

	}

	public void restoreContentGroup(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering restoreContentGroup");
			contentGroupDao.restoreContentGroup(id, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting restoreContentGroup");
		}

	}

	public void updateContentGroup(ContentGroup contentGroup) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateContentGroup");
			contentGroupDao.updateContentGroup(contentGroup);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateContentGroup");
		}
	}

}
