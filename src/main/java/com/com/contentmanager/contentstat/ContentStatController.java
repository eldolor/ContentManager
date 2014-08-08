package com.com.contentmanager.contentstat;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.common.entity.Result;

@Controller
public class ContentStatController {
	@Autowired
	private ContentStatService contentStatService;

	private static final Logger LOGGER = Logger
			.getLogger(ContentStatController.class.getName());

	@RequestMapping(value = "/contentstats", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	Result doCreateContentStat(
			@RequestBody List<com.com.contentmanager.contentstat.transfer.ContentStat> contentStats,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doCreateContentStat");

			List<ContentStat> lContentStats = convertToDomainObject(contentStats);
			for (ContentStat lContentStat : lContentStats) {
				contentStatService.saveContentStat(lContentStat);
			}
			response.setStatus(HttpServletResponse.SC_CREATED);

			Result lResult = new Result();
			lResult.setResult(Result.SUCCESS);
			response.setStatus(HttpServletResponse.SC_CREATED);
			return lResult;

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doCreateContentStat");
		}
	}

	@RequestMapping(value = "/contentstats", method = RequestMethod.GET)
	public void doCreateContentStat(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doCreateContentStat");


			response.setStatus(HttpServletResponse.SC_CREATED);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doCreateContentStat");
		}
	}

	private List<ContentStat> convertToDomainObject(
			List<com.com.contentmanager.contentstat.transfer.ContentStat> pContentStats) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering convertToDomailObject");
			List<ContentStat> lContentStats = new ArrayList<ContentStat>();
			for (com.com.contentmanager.contentstat.transfer.ContentStat lContentStat : pContentStats) {
				lContentStats.add(convertToDomainObject(lContentStat));
			}
			return lContentStats;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting convertToDomailObject");
		}

	}

	private ContentStat convertToDomainObject(
			com.com.contentmanager.contentstat.transfer.ContentStat pContentStat) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering convertToDomailObject");
			ContentStat lContentStat = new ContentStat();
			lContentStat.setApplicationId(pContentStat.getApplicationId());
			lContentStat.setContentGroupId(pContentStat.getContentGroupId());
			lContentStat.setContentId(pContentStat.getContentId());
			lContentStat.setEventTimeMs(pContentStat.getEventTimeMs());
			lContentStat.setEventTimeZoneOffsetMs(pContentStat
					.getEventTimeZoneOffsetMs());
			lContentStat.setEventType(pContentStat.getEventType());
			return lContentStat;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting convertToDomailObject");
		}

	}
}
