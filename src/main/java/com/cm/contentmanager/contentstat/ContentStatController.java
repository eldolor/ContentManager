package com.cm.contentmanager.contentstat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cm.accountmanagement.account.Account;
import com.cm.accountmanagement.account.AccountService;
import com.cm.common.entity.Result;
import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.application.ApplicationService;
import com.cm.contentmanager.content.Content;
import com.cm.contentmanager.content.ContentService;
import com.cm.contentmanager.contentgroup.ContentGroup;
import com.cm.contentmanager.contentgroup.ContentGroupService;
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
	@Autowired
	private ContentGroupService contentGroupService;
	@Autowired
	private ContentService contentService;

	private static final Logger LOGGER = Logger
			.getLogger(ContentStatController.class.getName());

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/analytics/applications", method = RequestMethod.GET)
	public ModelAndView displayApplications(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering");
		try {
			return new ModelAndView("analytics_applications", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/analytics/{applicationId}/contentgroups", method = RequestMethod.GET)
	public ModelAndView displayContentGroups(@PathVariable Long applicationId,
			ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering");
		try {
			model.addAttribute("applicationId", applicationId);
			return new ModelAndView("analytics_content_groups", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/analytics/{contentGroupId}/content", method = RequestMethod.GET)
	public ModelAndView displayContent(@PathVariable Long contentGroupId,
			ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering");
		try {
			model.addAttribute("contentGroupId", contentGroupId);
			return new ModelAndView("analytics_content", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

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

	@RequestMapping(value = "/contentdownloadstats", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	Result doCreateContentDownloadStat(
			@RequestBody List<com.cm.contentmanager.contentstat.transfer.ContentDownloadStat> contentDownloadStats,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			List<ContentDownloadStat> lContentDownloadStats = convertToContentDownloadStatDomainObject(contentDownloadStats);
			long lTotalSizeInBytes = 0L;
			long lApplicationId = 0L;
			for (ContentDownloadStat lContentDownloadStat : lContentDownloadStats) {
				contentStatService
						.saveContentDownloadStat(lContentDownloadStat);
				if (lApplicationId == 0L) {
					lApplicationId = lContentDownloadStat.getApplicationId();
				}
				lTotalSizeInBytes += lContentDownloadStat.getSizeInBytes();
			}
			Utils.triggerUpdateBandwidthUtilizationMessage(lApplicationId,
					lTotalSizeInBytes, 0);
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
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/contentstats/application/daily", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<List<com.cm.contentmanager.contentstat.transfer.ContentStatByApplicationSummary>> getApplicationSummary(
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			User lUser = userService.getLoggedInUser();
			List<List<com.cm.contentmanager.contentstat.transfer.ContentStatByApplicationSummary>> lContentStats = new ArrayList<List<com.cm.contentmanager.contentstat.transfer.ContentStatByApplicationSummary>>();
			List<Application> lApplications = applicationService
					.getApplicationsByAccountId(lUser.getAccountId(), false);
			// for each application in the account
			for (Application application : lApplications) {
				List<ContentStatByApplicationSummary> lList = contentStatService
						.getSummaryByApplication(application.getId());
				if (lList != null && (!lList.isEmpty())) {
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Retrieved " + lList.size()
								+ " rows for application id "
								+ application.getId());
					lContentStats.add(convert(application, lList));
				}
			}

			response.setStatus(HttpServletResponse.SC_OK);
			return lContentStats;
		} catch (Throwable e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/contentstats/{applicationId}/contentgroups/daily", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<List<com.cm.contentmanager.contentstat.transfer.ContentStatByContentGroupSummary>> getContentGroupSummary(
			@PathVariable Long applicationId, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<List<com.cm.contentmanager.contentstat.transfer.ContentStatByContentGroupSummary>> lContentStats = new ArrayList<List<com.cm.contentmanager.contentstat.transfer.ContentStatByContentGroupSummary>>();

			List<ContentGroup> lContentGroups = contentGroupService.get(
					applicationId, false);
			for (ContentGroup contentGroup : lContentGroups) {
				List<ContentStatByContentGroupSummary> lList = contentStatService
						.getSummaryByContentGroup(contentGroup.getId());
				if (lList != null && (!lList.isEmpty())) {
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Retrieved " + lList.size()
								+ " rows for content group id "
								+ contentGroup.getId());
					lContentStats.add(convert(contentGroup, lList));
				}
			}

			response.setStatus(HttpServletResponse.SC_OK);
			return lContentStats;
		} catch (Throwable e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/contentstats/{contentGroupId}/content/daily", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<List<com.cm.contentmanager.contentstat.transfer.ContentStatByContentSummary>> getContentSummary(
			@PathVariable Long contentGroupId, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<List<com.cm.contentmanager.contentstat.transfer.ContentStatByContentSummary>> lContentStats = new ArrayList<List<com.cm.contentmanager.contentstat.transfer.ContentStatByContentSummary>>();

			List<Content> lContents = contentService.getByContentGroupId(
					contentGroupId, false);
			for (Content content : lContents) {
				List<ContentStatByContentSummary> lList = contentStatService
						.getSummaryByContent(content.getId());
				if (lList != null && (!lList.isEmpty())) {
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Retrieved " + lList.size()
								+ " rows for content id " + content.getId());
					lContentStats.add(convert(content, lList));
				}
			}

			response.setStatus(HttpServletResponse.SC_OK);
			return lContentStats;
		} catch (Throwable e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	private List<com.cm.contentmanager.contentstat.transfer.ContentStatByContentSummary> convert(
			Content content, List<ContentStatByContentSummary> lList) {
		List<com.cm.contentmanager.contentstat.transfer.ContentStatByContentSummary> lRtnList = new ArrayList<com.cm.contentmanager.contentstat.transfer.ContentStatByContentSummary>();
		for (ContentStatByContentSummary contentStatByContentSummary : lList) {
			lRtnList.add(convert(content, contentStatByContentSummary));
		}
		return lRtnList;
	}

	private com.cm.contentmanager.contentstat.transfer.ContentStatByContentSummary convert(
			Content content,
			ContentStatByContentSummary contentStatByContentSummary) {
		com.cm.contentmanager.contentstat.transfer.ContentStatByContentSummary lSummary = new com.cm.contentmanager.contentstat.transfer.ContentStatByContentSummary();
		lSummary.setContentId(contentStatByContentSummary.getContentId());
		lSummary.setCount(contentStatByContentSummary.getCount());
		lSummary.setEventStartTimeMs(contentStatByContentSummary
				.getEventStartTimeMs());
		lSummary.setEventEndTimeMs(contentStatByContentSummary
				.getEventEndTimeMs());
		lSummary.setEventTimeZoneOffsetMs(contentStatByContentSummary
				.getEventTimeZoneOffsetMs());
		//
		lSummary.setName(content.getName());

		return lSummary;
	}

	private List<com.cm.contentmanager.contentstat.transfer.ContentStatByContentGroupSummary> convert(
			ContentGroup contentGroup,
			List<ContentStatByContentGroupSummary> lList) {
		List<com.cm.contentmanager.contentstat.transfer.ContentStatByContentGroupSummary> lRtnList = new ArrayList<com.cm.contentmanager.contentstat.transfer.ContentStatByContentGroupSummary>();
		for (ContentStatByContentGroupSummary contentStatByContentGroupSummary : lList) {
			lRtnList.add(convert(contentGroup, contentStatByContentGroupSummary));
		}
		return lRtnList;
	}

	private com.cm.contentmanager.contentstat.transfer.ContentStatByContentGroupSummary convert(
			ContentGroup contentGroup,
			ContentStatByContentGroupSummary contentStatByContentGroupSummary) {
		com.cm.contentmanager.contentstat.transfer.ContentStatByContentGroupSummary lSummary = new com.cm.contentmanager.contentstat.transfer.ContentStatByContentGroupSummary();
		lSummary.setContentGroupId(contentStatByContentGroupSummary
				.getContentGroupId());
		lSummary.setCount(contentStatByContentGroupSummary.getCount());
		lSummary.setEventStartTimeMs(contentStatByContentGroupSummary
				.getEventStartTimeMs());
		lSummary.setEventEndTimeMs(contentStatByContentGroupSummary
				.getEventEndTimeMs());
		lSummary.setEventTimeZoneOffsetMs(contentStatByContentGroupSummary
				.getEventTimeZoneOffsetMs());
		//
		lSummary.setName(contentGroup.getName());

		return lSummary;
	}

	private List<com.cm.contentmanager.contentstat.transfer.ContentStatByApplicationSummary> convert(
			Application application, List<ContentStatByApplicationSummary> lList) {
		List<com.cm.contentmanager.contentstat.transfer.ContentStatByApplicationSummary> lRtnList = new ArrayList<com.cm.contentmanager.contentstat.transfer.ContentStatByApplicationSummary>();
		for (ContentStatByApplicationSummary contentStatByApplicationSummary : lList) {
			lRtnList.add(convert(application, contentStatByApplicationSummary));
		}
		return lRtnList;
	}

	private com.cm.contentmanager.contentstat.transfer.ContentStatByApplicationSummary convert(
			Application application,
			ContentStatByApplicationSummary contentStatByApplicationSummary) {
		com.cm.contentmanager.contentstat.transfer.ContentStatByApplicationSummary lSummary = new com.cm.contentmanager.contentstat.transfer.ContentStatByApplicationSummary();
		lSummary.setApplicationId(contentStatByApplicationSummary
				.getApplicationId());
		lSummary.setCount(contentStatByApplicationSummary.getCount());
		lSummary.setEventStartTimeMs(contentStatByApplicationSummary
				.getEventStartTimeMs());
		lSummary.setEventEndTimeMs(contentStatByApplicationSummary
				.getEventEndTimeMs());
		lSummary.setEventTimeZoneOffsetMs(contentStatByApplicationSummary
				.getEventTimeZoneOffsetMs());
		//
		lSummary.setName(application.getName());
		lSummary.setTrackingId(application.getTrackingId());

		return lSummary;
	}

	// TODO: delete later
	@RequestMapping(value = "/test/rollup/contentstats/{eventTimeMs}", method = RequestMethod.GET)
	public void rollupDailySummaryTest(@PathVariable Long eventTimeMs,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<Account> lAccounts = accountService.getAccounts();
			Calendar lEod = Utils.getEndOfDay(eventTimeMs,
					TimeZone.getTimeZone("UTC"));
			Calendar lSod = Utils.getStartOfDay(eventTimeMs,
					TimeZone.getTimeZone("UTC"));
			LOGGER.info("Processing: " + lSod.getTime().toLocaleString() + "::"
					+ lEod.getTime().toLocaleString());
			// for each account
			for (Account account : lAccounts) {
				List<Application> lApplications = applicationService
						.getApplicationsByAccountId(account.getId(), false);
				// for each application in the account
				for (Application application : lApplications) {
					Utils.triggerRollupMessage(application.getId(),
							lSod.getTimeInMillis(), lEod.getTimeInMillis(), 0);
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

	// TODO: delete later
	@RequestMapping(value = "/test/contentstats/create/{eventTimeMs}", method = RequestMethod.GET)
	public void createTestContentStats(@PathVariable Long eventTimeMs,
			HttpServletResponse response) {
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
					List<Content> lContents = contentService.get(
							application.getId(), false);
					for (Content content : lContents) {
						int lRandom = Utils.getRandomNumber(1, 10);
						for (int i = 0; i < lRandom; i++) {
							contentStatService
									.saveContentStat(createTestContentStat(
											content.getApplicationId(),
											content.getContentGroupId(),
											content.getId(), eventTimeMs));
						}
					}
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

	@RequestMapping(value = "/test/contentstats/create/period/{lastNDays}", method = RequestMethod.GET)
	public void createTestContentStatsN(@PathVariable Long lastNDays,
			HttpServletResponse response) {
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
					Calendar lEod = Utils.getEndOfDayToday(TimeZone
							.getTimeZone("UTC"));
					List<Content> lContents = contentService.get(
							application.getId(), false);
					for (int i = 0; i < lastNDays; i++) {
						LOGGER.info("Processing: "
								+ lEod.getTime().toLocaleString());
						for (Content content : lContents) {
							int lRandom = Utils.getRandomNumber(1, 10);
							for (int j = 0; j < lRandom; j++) {
								contentStatService
										.saveContentStat(createTestContentStat(
												content.getApplicationId(),
												content.getContentGroupId(),
												content.getId(),
												lEod.getTimeInMillis()));
							}
						}
						lEod.add(Calendar.DATE, -1);
					}
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

	@RequestMapping(value = "/admin/rollup/contentstats/period/{lastNDays}", method = RequestMethod.GET)
	public void rollupDailySummaryN(@PathVariable Long lastNDays,
			HttpServletResponse response) {
		int lMessagesEnqueued = 0;
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
					Calendar lEod = Utils.getEndOfDayToday(TimeZone
							.getTimeZone("UTC"));
					Calendar lSod = Utils.getStartOfDayToday(TimeZone
							.getTimeZone("UTC"));
					for (int i = 0; i < lastNDays; i++) {
						LOGGER.info("Processing: "
								+ lSod.getTime().toLocaleString() + "::"
								+ lEod.getTime().toLocaleString());
						Utils.triggerRollupMessage(application.getId(),
								lSod.getTimeInMillis(), lEod.getTimeInMillis(),
								0);
						lMessagesEnqueued++;
						lSod.add(Calendar.DATE, -1);
						lEod.add(Calendar.DATE, -1);
					}
				}
			}

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(lMessagesEnqueued + " messages enqueued");
				LOGGER.info("Exiting");
			}
		}
	}

	@RequestMapping(value = "/cron/rollup/contentstats/daily", method = RequestMethod.GET)
	public void rollupDailySummary(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<Account> lAccounts = accountService.getAccounts();
			Calendar lEod = Utils.getEndOfDayYesterday(TimeZone
					.getTimeZone("UTC"));
			Calendar lSod = Utils.getStartOfDayYesterday(TimeZone
					.getTimeZone("UTC"));
			// for each account
			for (Account account : lAccounts) {
				List<Application> lApplications = applicationService
						.getApplicationsByAccountId(account.getId(), false);
				// for each application in the account
				for (Application application : lApplications) {
					Utils.triggerRollupMessage(application.getId(),
							lSod.getTimeInMillis(), lEod.getTimeInMillis(), 0);
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

	@RequestMapping(value = "/tasks/rollup/contentstats/{id}/{eventStartTimeMs}/{eventEndTimeMs}", method = RequestMethod.POST)
	public void rollupDailySummaryByApplication(@PathVariable Long id,
			@PathVariable Long eventStartTimeMs,
			@PathVariable Long eventEndTimeMs, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			contentStatService.rollupSummary(id, eventStartTimeMs,
					eventEndTimeMs);

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
				LOGGER.info("Entering");
			List<ContentStat> lContentStats = new ArrayList<ContentStat>();
			for (com.cm.contentmanager.contentstat.transfer.ContentStat lContentStat : pContentStats) {
				lContentStats.add(convertToDomainObject(lContentStat));
			}
			return lContentStats;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}

	}

	private ContentStat convertToDomainObject(
			com.cm.contentmanager.contentstat.transfer.ContentStat pContentStat) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
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
				LOGGER.info("Exiting");
		}

	}

	private List<ContentDownloadStat> convertToContentDownloadStatDomainObject(
			List<com.cm.contentmanager.contentstat.transfer.ContentDownloadStat> pContentDownloadStats) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<ContentDownloadStat> lContentDownloadStats = new ArrayList<ContentDownloadStat>();
			for (com.cm.contentmanager.contentstat.transfer.ContentDownloadStat lContentDownloadStat : pContentDownloadStats) {
				lContentDownloadStats
						.add(convertToContentDownloadStatDomainObject(lContentDownloadStat));
			}
			return lContentDownloadStats;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}

	}

	private ContentDownloadStat convertToContentDownloadStatDomainObject(
			com.cm.contentmanager.contentstat.transfer.ContentDownloadStat pContentDownloadStat) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			ContentDownloadStat lContentDownloadStat = new ContentDownloadStat();
			lContentDownloadStat.setApplicationId(pContentDownloadStat
					.getApplicationId());
			lContentDownloadStat.setContentGroupId(pContentDownloadStat
					.getContentGroupId());
			lContentDownloadStat.setContentId(pContentDownloadStat
					.getContentId());
			lContentDownloadStat.setEventTimeMs(pContentDownloadStat
					.getEventTimeMs());
			lContentDownloadStat.setEventTimeZoneOffsetMs(pContentDownloadStat
					.getEventTimeZoneOffsetMs());
			lContentDownloadStat.setSizeInBytes(pContentDownloadStat
					.getSizeInBytes());
			return lContentDownloadStat;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}

	}

	private ContentStat createTestContentStat(long applicationId,
			long contentGroupId, long contentId, long eventTime) {
		ContentStat lContentStat = new ContentStat();
		lContentStat.setApplicationId(applicationId);
		lContentStat.setContentGroupId(contentGroupId);
		lContentStat.setContentId(contentId);
		lContentStat.setEventTimeMs(eventTime);
		lContentStat.setEventTimeZoneOffsetMs((long)TimeZone
				.getTimeZone("UTC").getRawOffset());
		lContentStat.setEventType("impression");
		return lContentStat;
	}
}
