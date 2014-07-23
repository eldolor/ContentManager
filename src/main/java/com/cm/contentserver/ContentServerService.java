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
					.getApplicationId());

			List<ContentGroup> lContentGroups = filterContentGroupByEffectiveDate(contentGroupService
					.getContentGroupsByApplicationId(lApplicationId));

			List<Content> lContents = new ArrayList<Content>();

			for (ContentGroup lContentGroup : lContentGroups) {
				lContents.addAll(validateContent(contentService.getAllContent(
						lApplicationId, lContentGroup.getId())));
			}

			//TODO: flatten out event location while persisting contentRequest
			//contentServerDao.saveContentRequest(pContentRequest);
			return lContents;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContent");
		}

	}

	private Long resolveApplicationId(String trackingId) {
		Application lApplication = applicationService.getApplicationByTrackingId(trackingId);
		// TODO: hard-wired for now
		return lApplication.getId();
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
			if (isEffectiveDateValid(lContent.getStartDateMs(),
					lContent.getEndDateMs())) {

				lValidatedContent.add(lContent);
			}
		}

		return lValidatedContent;
	}

}
