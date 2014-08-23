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
			List<Application> applications = applicationDao.get();
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

	public List<Application> getApplicationsByUserId(Long accountId,
			boolean includeDeleted) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplicationsByAccountId");
			List<Application> applications = applicationDao
					.getApplicationsByUserId(accountId, includeDeleted);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning " + applications.size()
						+ " applications");
			return applications;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getApplicationsByAccountId");
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
			return applicationDao.get(id);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getApplication");
		}
	}

	public Application getApplicationByTrackingId(String trackingId,
			boolean includeDeleted) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getApplicationByTrackingId");
			return applicationDao.get(trackingId, includeDeleted);
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

			return applicationDao.save(application);
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
			applicationDao.delete(id, timeUpdatedMs,
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
			applicationDao.restore(id, timeUpdatedMs,
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
			applicationDao.update(application);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateApplication");
		}
	}
	public void updateChangesStaged(Long pApplicationId, boolean pChangesStaged) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateChangesStaged");
			applicationDao.updateChangesStaged(pApplicationId, pChangesStaged);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateChangesStaged");
		}
	}

}
