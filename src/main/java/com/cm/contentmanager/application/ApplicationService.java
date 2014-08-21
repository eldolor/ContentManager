package com.cm.contentmanager.application;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.usermanagement.user.User;

@Service
public class ApplicationService {
	@Autowired
	private ApplicationDao applicationDao;

	private static final Logger LOGGER = Logger
			.getLogger(ApplicationService.class.getName());

	public List<Application> getAllApplications() {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getAllApplications");
			List<Application> applications = applicationDao
					.getAllApplications();
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + applications.size()
						+ " applications");
			return applications;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getAllApplications");
		}
	}

	public List<Application> getApplicationsByUserId(Long userId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplicationsByUserId");
			List<Application> applications = applicationDao
					.getApplicationsByUserId(userId);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + applications.size()
						+ " applications");
			return applications;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getApplicationsByUserId");
		}
	}

	public List<Application> getApplicationsByAccountId(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplicationsByAccountId");
			List<Application> applications = applicationDao
					.getApplicationsByAccountId(accountId);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + applications.size()
						+ " applications");
			return applications;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getApplicationsByAccountId");
		}
	}

	public List<Application> getApplicationsByAccountId(Long accountId,
			boolean includeDeleted) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplicationsByAccountId");
			List<Application> applications = applicationDao
					.getApplicationsByAccountId(accountId, includeDeleted);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + applications.size()
						+ " applications");
			return applications;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getApplicationsByAccountId");
		}
	}

	public Application getApplication(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplication");
			return applicationDao.getApplication(id);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getApplication");
		}
	}

	public Application getApplicationByTrackingId(String trackingId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplicationByTrackingId");
			return applicationDao.getApplicationByTrackingId(trackingId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getApplicationByTrackingId");
		}
	}

	public Application saveApplication(User user, String trackingId,
			Application application) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering saveApplication");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Setting user and account ids for user "
						+ user.getUsername());
			application.setUserId(user.getId());
			application.setTrackingId(trackingId);
			application.setAccountId(user.getAccountId());

			return applicationDao.saveApplication(application);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting saveApplication");
		}
	}

	public void deleteApplication(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteApplication");
			applicationDao.deleteApplication(id, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteApplication");
		}

	}

	public void restoreApplication(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering restoreApplication");
			applicationDao.restoreApplication(id, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting restoreApplication");
		}

	}

	public void updateApplication(Application application) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateApplication");
			applicationDao.updateApplication(application);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateApplication");
		}
	}

}
