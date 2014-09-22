package com.cm.contentmanager.contentstat;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.accountmanagement.account.Account;
import com.cm.accountmanagement.account.AccountService;
import com.cm.common.entity.Result;
import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.application.ApplicationService;
import com.cm.usermanagement.user.User;
import com.cm.usermanagement.user.UserService;
import com.cm.util.Utils;

@Controller
public class ContentStatController {
	@Autowired
	private ContentStatService contentStatService;
	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private AccountService accountService;

	private static final Logger LOGGER = Logger
			.getLogger(ContentStatController.class.getName());

	@RequestMapping(value = "/contentstats", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	Result doCreateContentStat(
			@RequestBody List<com.cm.contentmanager.contentstat.transfer.ContentStat> contentStats,
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

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doCreateContentStat");
		}
	}

	@RequestMapping(value = "/contentstats/daily", method = RequestMethod.GET)
	public List<ContentStatDailySummary> get(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			User user = userService.getLoggedInUser();
			List<ContentStatDailySummary> lContentStats = new ArrayList<ContentStatDailySummary>();

			response.setStatus(HttpServletResponse.SC_OK);
			return lContentStats;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/tasks/rollup/daily", method = RequestMethod.POST)
	public void rollupDailySummary(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<Account> lAccounts = accountService.getAccounts();
			// for each account
			for (Account account : lAccounts) {
				List<Application> lApplications = applicationService
						.getApplicationsByAccountId(account.getId(), false);
				// for each application in the account
				for (Application application : lApplications) {
					Utils.triggerDailyRollupMessage(application.getId(), 0);
				}
			}

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/tasks/rollup/daily/update/{id}", method = RequestMethod.POST)
	public void rollupDailySummaryByApplication(@PathVariable Long id,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			contentStatService.rollupDailySummary(id);

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	private List<ContentStat> convertToDomainObject(
			List<com.cm.contentmanager.contentstat.transfer.ContentStat> pContentStats) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering convertToDomainObject");
			List<ContentStat> lContentStats = new ArrayList<ContentStat>();
			for (com.cm.contentmanager.contentstat.transfer.ContentStat lContentStat : pContentStats) {
				lContentStats.add(convertToDomainObject(lContentStat));
			}
			return lContentStats;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting convertToDomainObject");
		}

	}

	private ContentStat convertToDomainObject(
			com.cm.contentmanager.contentstat.transfer.ContentStat pContentStat) {
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
