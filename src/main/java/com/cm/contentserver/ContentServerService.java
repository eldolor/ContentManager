package com.cm.contentserver;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.application.ApplicationService;
import com.cm.contentmanager.content.Content;
import com.cm.contentmanager.content.ContentService;
import com.cm.contentmanager.contentgroup.ContentGroup;
import com.cm.contentmanager.contentgroup.ContentGroupService;
import com.cm.util.Utils;

@Service
public class ContentServerService {
	@Autowired
	private ContentServerDao contentServerDao;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ContentGroupService contentGroupService;
	@Autowired
	private ContentService contentService;

	private static final Logger LOGGER = Logger
			.getLogger(ContentServerService.class.getName());

	public List<Content> getContent(ContentRequest pContentRequest) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContent");
			Long lApplicationId = this.resolveApplicationId(pContentRequest
					.getTrackingId());
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Application Id is " + lApplicationId
						+ " for Tracking Id " + pContentRequest.getTrackingId());
			List<Content> lContents = new ArrayList<Content>();

			Application lApplication = applicationService
					.getApplication(lApplicationId);

			if (lApplication != null && (!lApplication.isDeleted())
					&& (!lApplication.isDeletedOnPlanDowngrade())
					&& (lApplication.isEnabled())) {
				// not deleted & enabled groups only
				List<ContentGroup> lContentGroups = filterContentGroupByEffectiveDate(contentGroupService
						.get(lApplicationId, false, true));

				for (ContentGroup lContentGroup : lContentGroups) {
					// not deleted & enabled content only
					lContents
							.addAll(validateContent(contentService.get(
									lApplicationId, lContentGroup.getId(),
									false, true)));
				}

			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.log(
							Level.WARNING,
							"Application does not exist, or is deleted, or is not enabled, or is deleted on plan downgrade");
			}
			contentServerDao.saveContentRequest(pContentRequest);
			return lContents;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContent");
		}

	}

	public boolean isUpdateOverWifiOnly(ContentRequest pContentRequest) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering isUpdateOverWifiOnly");
			Long lApplicationId = this.resolveApplicationId(pContentRequest
					.getTrackingId());
			Application lApplication = applicationService
					.getApplication(lApplicationId);
			return lApplication.isUpdateOverWifiOnly();
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting isUpdateOverWifiOnly");
		}

	}

	public boolean isCollectUsageData(ContentRequest pContentRequest) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			Long lApplicationId = this.resolveApplicationId(pContentRequest
					.getTrackingId());
			Application lApplication = applicationService
					.getApplication(lApplicationId);
			return lApplication.isCollectUsageData();
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}

	}

	public void upsert(Handshake pHandshake) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering upsert");
			contentServerDao.upsertHandshake(pHandshake);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting upsert");
		}
	}

	private Long resolveApplicationId(String trackingId) {
		Application lApplication = applicationService
				.getApplicationByTrackingId(trackingId, false);
		// TODO: hard-wired for now
		return (lApplication != null) ? lApplication.getId() : null;
	}

	private List<ContentGroup> filterContentGroupByEffectiveDate(
			List<ContentGroup> pContentGroups) {
		List<ContentGroup> lFilteredContentGroups = new ArrayList<ContentGroup>();
		for (ContentGroup lContentGroup : pContentGroups) {
			if (isEffectiveDateValid(lContentGroup.getStartDateMs(),
					lContentGroup.getEndDateMs())) {
				lFilteredContentGroups.add(lContentGroup);
			}
		}

		return lFilteredContentGroups;
	}

	private boolean isEffectiveDateValid(Long start, Long end) {
		Long todayMs = System.currentTimeMillis();
		if (start == null) {
			start = todayMs;
		}
		if (end == null) {
			end = todayMs;
		}
		if (start <= todayMs && end >= todayMs) {
			return true;
		} else {
			return false;
		}
	}

	private List<Content> validateContent(List<Content> pContents) {
		List<Content> lValidatedContent = new ArrayList<Content>();
		for (Content lContent : pContents) {
			// effective date
			if (isEffectiveDateValid(lContent.getStartDateMs(),
					lContent.getEndDateMs())) {
				// URI not empty
				if (!Utils.isEmpty(lContent.getUri())) {
					lValidatedContent.add(lContent);
				}
			}
		}

		return lValidatedContent;
	}

}
