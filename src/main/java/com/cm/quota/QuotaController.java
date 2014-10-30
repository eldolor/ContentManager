/*
 * Copyright 2010-2012 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.cm.quota;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.config.CanonicalPlan;
import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.application.ApplicationService;
import com.cm.contentmanager.content.Content;
import com.cm.contentmanager.content.ContentService;
import com.cm.contentmanager.contentgroup.ContentGroup;
import com.cm.contentmanager.contentgroup.ContentGroupService;
import com.cm.stripe.StripeCustomer;
import com.cm.stripe.StripeCustomerService;
import com.cm.usermanagement.user.User;
import com.cm.usermanagement.user.UserService;
import com.cm.util.Utils;

@Controller
public class QuotaController {
	@Autowired
	private QuotaService quotaService;
	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private ContentGroupService contentGroupService;
	@Autowired
	private StripeCustomerService stripeCustomerService;

	private static final Logger LOGGER = Logger.getLogger(QuotaController.class
			.getName());

	public QuotaController() {
		super();
	}

	/**
	 * 
	 * @param uuid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/secured/quota", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	com.cm.quota.transfer.Quota getQuota(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getQuota");
			User lUser = userService.getLoggedInUser();

			com.cm.quota.transfer.Quota lQuota = convert(
					quotaService.get(lUser.getAccountId()),
					quotaService.getApplicationQuotaUsed(lUser.getAccountId()),
					quotaService.getStorageQuotaUsed(lUser.getAccountId()),
					quotaService.getBandwidthQuotaUsed(lUser.getAccountId()));
			response.setStatus(HttpServletResponse.SC_OK);
			return lQuota;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getQuota");
		}
	}

	@RequestMapping(value = "/secured/quota/{applicationId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	com.cm.quota.transfer.Quota getQuota(@PathVariable Long applicationId,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getQuota");

			User lUser = userService.getLoggedInUser();
			// Quota lQuota = quotaService.get(applicationId);
			//
			// response.setStatus(HttpServletResponse.SC_OK);
			// return convert(lQuota, quotaService.getApplicationQuotaUsed(lUser
			// .getAccountId()), quotaService.getStorageQuotaUsed(
			// lUser.getAccountId(), applicationId));
			//
			com.cm.quota.transfer.Quota lQuota = convert(quotaService.get(lUser
					.getAccountId()),
					quotaService.getApplicationQuotaUsed(lUser.getAccountId()),
					quotaService.getStorageQuotaUsed(lUser.getAccountId(),
							applicationId),
					quotaService.getBandwidthQuotaUsed(lUser.getAccountId()));
			response.setStatus(HttpServletResponse.SC_OK);
			return lQuota;

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getQuota");
		}
	}

	@RequestMapping(value = "/secured/quota/application/validate", method = RequestMethod.GET, produces = "application/json")
	public void validateApplicationQuota(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering ");
			response.setStatus(HttpServletResponse.SC_OK);
			User lUser = userService.getLoggedInUser();

			if (quotaService
					.hasSufficientApplicationQuota(lUser.getAccountId())) {
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
			}
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting ");
		}
	}

	@RequestMapping(value = "/secured/quota/bandwidth/validate", method = RequestMethod.GET, produces = "application/json")
	public void validateBandwidthQuota(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering ");
			response.setStatus(HttpServletResponse.SC_OK);
			User lUser = userService.getLoggedInUser();

			if (quotaService.hasSufficientBandwidthQuota(lUser.getAccountId())) {
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
			}
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting ");
		}
	}

	@RequestMapping(value = "/tasks/quota/utilization/update/{accountId}", method = RequestMethod.POST)
	public void updateQuotaUtilizaton(@PathVariable Long accountId,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			updateQuotaUtilizaton(accountId);

			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	@RequestMapping(value = "/tasks/quota/update/{accountId}", method = RequestMethod.POST)
	public void updateQuota(@PathVariable Long accountId,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			StripeCustomer lStripeCustomer = stripeCustomerService
					.get(accountId);
			String lNewCanonicalPlanName = lStripeCustomer
					.getCanonicalPlanName();

			Quota lExistingQuota = quotaService.get(accountId);

			boolean lIsDowngrade = isDowngrade(
					lExistingQuota.getCanonicalPlanName(),
					lNewCanonicalPlanName);

			// evaluate and update the quota allocated to the account
			// appropriately
			if (lNewCanonicalPlanName.equals(CanonicalPlan.FREE.getName())) {
				quotaService.updatePlan(accountId, CanonicalPlan.FREE);
				if (lIsDowngrade)
					deleteApplicationsOnPlanDowngrade(accountId,
							CanonicalPlan.FREE.getApplicationQuota());
				else
					restoreApplicationsOnPlanUpgrade(accountId,
							CanonicalPlan.FREE.getApplicationQuota());
			} else if (lNewCanonicalPlanName.equals(CanonicalPlan.LARGE
					.getName())) {
				quotaService.updatePlan(accountId, CanonicalPlan.LARGE);
				if (lIsDowngrade)
					deleteApplicationsOnPlanDowngrade(accountId,
							CanonicalPlan.LARGE.getApplicationQuota());
				else
					restoreApplicationsOnPlanUpgrade(accountId,
							CanonicalPlan.LARGE.getApplicationQuota());
			} else if (lNewCanonicalPlanName.equals(CanonicalPlan.MEDIUM
					.getName())) {
				quotaService.updatePlan(accountId, CanonicalPlan.MEDIUM);
				if (lIsDowngrade)
					deleteApplicationsOnPlanDowngrade(accountId,
							CanonicalPlan.MEDIUM.getApplicationQuota());
				else
					restoreApplicationsOnPlanUpgrade(accountId,
							CanonicalPlan.MEDIUM.getApplicationQuota());
			} else if (lNewCanonicalPlanName.equals(CanonicalPlan.MICRO
					.getName())) {
				quotaService.updatePlan(accountId, CanonicalPlan.MICRO);
				if (lIsDowngrade)
					deleteApplicationsOnPlanDowngrade(accountId,
							CanonicalPlan.MICRO.getApplicationQuota());
				else
					restoreApplicationsOnPlanUpgrade(accountId,
							CanonicalPlan.MICRO.getApplicationQuota());
			} else if (lNewCanonicalPlanName.equals(CanonicalPlan.SMALL
					.getName())) {
				quotaService.updatePlan(accountId, CanonicalPlan.SMALL);
				if (lIsDowngrade)
					deleteApplicationsOnPlanDowngrade(accountId,
							CanonicalPlan.SMALL.getApplicationQuota());
				else
					restoreApplicationsOnPlanUpgrade(accountId,
							CanonicalPlan.SMALL.getApplicationQuota());
			}

			// update the quota utilization; messaging will add the desired
			// latency required for the quota related changes to be committed to
			// the DB prior to lookup
			// TODO: Should this be removed for production environment?
			Utils.triggerUpdateQuotaUtilizationMessage(accountId, 3000);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	@RequestMapping(value = "/tasks/bandwidth/utilization/update/{uri}", method = RequestMethod.POST)
	public void updateBandwidthUtilizaton(@PathVariable String uri,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			Content lContent = contentService.get(uri);
			Application lApplication = applicationService
					.getApplication(lContent.getApplicationId());
			quotaService.upsertBandwidthUtilization(lApplication,
					lContent.getSizeInBytes());

			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	@RequestMapping(value = "/tasks/bandwidth/utilization/update/{applicationId}/{sizeInBytes}", method = RequestMethod.POST)
	public void updateBandwidthUtilizaton2(@PathVariable Long applicationId,
			@PathVariable Long sizeInBytes, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			Application lApplication = applicationService
					.getApplication(applicationId);
			quotaService.upsertBandwidthUtilization(lApplication, sizeInBytes);

			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	private void updateQuotaUtilizaton(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<Application> lApplications = applicationService
					.getApplicationsByAccountId(accountId, false);
			int lApplicationsUsed = lApplications.size();
			quotaService.upsertApplicationUtilization(accountId,
					lApplicationsUsed);
			for (Application lApplication : lApplications) {
				long lStorageUsedInBytes = 0;
				// only if the application is not deleted; Deleted apps are
				// excluded from storage quota
				if (!lApplication.isDeleted()) {
					// get content groups
					List<ContentGroup> lContentGroups = contentGroupService
							.get(lApplication.getId(), false);
					for (ContentGroup lContentGroup : lContentGroups) {
						// only if the content group is not deleted; Deleted
						// content groups are
						// excluded from storage quota
						if (!lContentGroup.isDeleted()) {

							List<Content> lContentList = contentService
									.getByContentGroupId(lContentGroup.getId(),
											false);
							for (Content lContent : lContentList) {
								// only if the content is not deleted; Deleted
								// content are
								// excluded from storage quota
								if (!lContent.isDeleted()) {
									// roll up size
									lStorageUsedInBytes += lContent
											.getSizeInBytes();
								}
							}
						}
					}
				}
				// update for each application
				quotaService.upsertStorageUtilization(lApplication,
						lStorageUsedInBytes);
			}// end application

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	private boolean isDowngrade(String pExistingPlanName,
			String pNewCanonicalPlanName) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (getCanonicalPlanLevel(pExistingPlanName) > getCanonicalPlanLevel(pNewCanonicalPlanName)) {
				return true;
			}
			return false;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	private int getCanonicalPlanLevel(String pCanonicalPlanName) {
		if (pCanonicalPlanName.equals(CanonicalPlan.FREE.getName())) {
			return CanonicalPlan.FREE.getLevel();
		} else if (pCanonicalPlanName.equals(CanonicalPlan.LARGE.getName())) {
			return CanonicalPlan.LARGE.getLevel();
		} else if (pCanonicalPlanName.equals(CanonicalPlan.MEDIUM.getName())) {
			return CanonicalPlan.MEDIUM.getLevel();
		} else if (pCanonicalPlanName.equals(CanonicalPlan.MICRO.getName())) {
			return CanonicalPlan.MICRO.getLevel();
		} else if (pCanonicalPlanName.equals(CanonicalPlan.SMALL.getName())) {
			return CanonicalPlan.SMALL.getLevel();
		}
		return -1;
	}

	private void deleteApplicationsOnPlanDowngrade(Long pAccountId, int quota) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			// only select those applications that have not been deleted by the
			// user(FIFO order)
			List<Application> lApplications = applicationService
					.getApplicationsByAccountId(pAccountId);
			// disable beyond the quota limit
			for (int i = quota; i < lApplications.size(); i++) {
				Application lApplication = lApplications.get(i);
				applicationService
						.deleteApplicationOnPlanDowngrade(lApplication.getId());

			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Disabled "
						+ ((lApplications.size() > quota) ? (lApplications
								.size() - quota) : 0) + " applications");

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	private void restoreApplicationsOnPlanUpgrade(Long pAccountId, int quota) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("New Quota: " + quota);
			// RESTORE for the entire account, and not just for a user
			List<Application> lApplications = applicationService
					.getDeletedApplicationsOnPlanDowngradeByAccountId(pAccountId);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Retrieved " + lApplications.size()
						+ " that need to be restored");
			int lRestoredCount = 0;
			// enable upto the quota limit
			for (int i = 0; (i < quota) && (i < lApplications.size()); i++) {
				Application lApplication = lApplications.get(i);
				applicationService.restoreApplicationOnPlanUpgrade(lApplication
						.getId());
				lRestoredCount++;
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Restored " + lRestoredCount + " applications");

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}

	}

	private com.cm.quota.transfer.Quota convert(Quota pQuota,
			ApplicationQuotaUsed pApplicationQuotaUsed,
			List<StorageQuotaUsed> pStorageQuotas,
			BandwidthQuotaUsed pBandwidthQuotaUsed) {
		List<com.cm.quota.transfer.StorageQuota> lList = new ArrayList<com.cm.quota.transfer.StorageQuota>();
		com.cm.quota.transfer.Quota lQuota = new com.cm.quota.transfer.Quota();
		lQuota.setCanonicalPlanName(pQuota.getCanonicalPlanName());

		lQuota.setApplicationLimit(pQuota.getApplicationLimit());
		lQuota.setApplicationsUsed(pApplicationQuotaUsed.getApplicationsUsed());

		try {
			// float lPercentageApplicationUtilized = (((pApplicationQuotaUsed
			// .getApplicationsUsed() * 100) / pQuota.getApplicationLimit()));
			// lQuota.setPercentageApplicationUsed(String
			// .valueOf(lPercentageApplicationUtilized));
			// if (LOGGER.isLoggable(Level.INFO))
			// LOGGER.info("setPercentageApplicationUsed: "
			// + lPercentageApplicationUtilized);

			BigDecimal lBd1 = new BigDecimal(
					pApplicationQuotaUsed.getApplicationsUsed() * 100);
			BigDecimal lBd2 = new BigDecimal(pQuota.getApplicationLimit());

			// divide bg1 with bg2 with 0 scale
			float lPercentageApplicationUtilized = (lBd1.divide(lBd2, 2,
					RoundingMode.CEILING).intValue());
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("setPercentageApplicationUsed: "
						+ lPercentageApplicationUtilized);
			lQuota.setPercentageApplicationUsed(String
					.valueOf(lPercentageApplicationUtilized));
		} catch (ArithmeticException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
		long lStorageUsed = 0L;
		for (StorageQuotaUsed lStorageQuotaUsed : pStorageQuotas) {
			lStorageUsed += lStorageQuotaUsed.getStorageUsedInBytes();
			lList.add(convertStorageQuota(pQuota, pApplicationQuotaUsed,
					lStorageQuotaUsed));
		}
		lQuota.setStorageQuota(lList);
		lQuota.setStorageUsed(lStorageUsed);
		lQuota.setStorageLimit(pQuota.getStorageLimitInBytes());

		try {
			// float lPercentageStorageUtilized = (((lStorageUsed * 100) /
			// pQuota
			// .getStorageLimitInBytes()));
			// lQuota.setPercentageStorageUsed(String
			// .valueOf(lPercentageStorageUtilized));
			// if (LOGGER.isLoggable(Level.INFO))
			// LOGGER.info("setPercentageStorageUsed: "
			// + lPercentageStorageUtilized);

			BigDecimal lBd1 = new BigDecimal(lStorageUsed * 100);
			BigDecimal lBd2 = new BigDecimal(pQuota.getStorageLimitInBytes());

			// divide bg1 with bg2 with 2 scale
			float lPercentageStorageUtilized = (lBd1.divide(lBd2, 2,
					RoundingMode.CEILING).intValue());
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("setPercentageStorageUsed: "
						+ lPercentageStorageUtilized);
			lQuota.setPercentageStorageUsed(String
					.valueOf(lPercentageStorageUtilized));
		} catch (ArithmeticException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}

		if (pBandwidthQuotaUsed != null) {
			// lQuota.setBandwidthUsed(pBandwidthQuotaUsed
			// .getBandwidthUsedInBytes());
			// float lPercentageBandwidthUtilized = (((pBandwidthQuotaUsed
			// .getBandwidthUsedInBytes() * 100) / pQuota
			// .getBandwidthLimitInBytes()));
			// if (LOGGER.isLoggable(Level.INFO))
			// LOGGER.info("setPercentageBandwidthUsed: "
			// + lPercentageBandwidthUtilized);
			//
			// lQuota.setPercentageBandwidthUsed(String
			// .valueOf(lPercentageBandwidthUtilized));
			// lQuota.setBandwidthLimit(pQuota.getBandwidthLimitInBytes());

			try {
				lQuota.setBandwidthUsed(pBandwidthQuotaUsed
						.getBandwidthUsedInBytes());
				lQuota.setBandwidthLimit(pQuota.getBandwidthLimitInBytes());
				BigDecimal lBd1 = new BigDecimal(
						pBandwidthQuotaUsed.getBandwidthUsedInBytes() * 100);
				BigDecimal lBd2 = new BigDecimal(
						pQuota.getBandwidthLimitInBytes());

				// divide bg1 with bg2 with 0 scale
				float lPercentageBandwidthUtilized = (lBd1.divide(lBd2, 2,
						RoundingMode.CEILING).intValue());
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("setPercentageBandwidthUsed: "
							+ lPercentageBandwidthUtilized);
				lQuota.setPercentageBandwidthUsed(String
						.valueOf(lPercentageBandwidthUtilized));
			} catch (ArithmeticException e) {
				LOGGER.log(Level.WARNING, e.getMessage(), e);
			}

		} else {
			lQuota.setBandwidthUsed(0L);
			lQuota.setPercentageBandwidthUsed(String.valueOf(0.0));
			lQuota.setBandwidthLimit(pQuota.getBandwidthLimitInBytes());
		}

		return lQuota;
	}

	private com.cm.quota.transfer.Quota convert(Quota pQuota,
			ApplicationQuotaUsed pApplicationQuotaUsed,
			StorageQuotaUsed pStorageQuotaUsed,
			BandwidthQuotaUsed pBandwidthQuotaUsed) {
		com.cm.quota.transfer.Quota lQuota = new com.cm.quota.transfer.Quota();
		lQuota.setCanonicalPlanName(pQuota.getCanonicalPlanName());

		lQuota.setApplicationLimit(pQuota.getApplicationLimit());
		lQuota.setApplicationsUsed((pApplicationQuotaUsed != null) ? pApplicationQuotaUsed
				.getApplicationsUsed() : 0);
		try {

			BigDecimal lBd1 = new BigDecimal(
					(pApplicationQuotaUsed != null) ? pApplicationQuotaUsed
							.getApplicationsUsed() : 0 * 100);
			BigDecimal lBd2 = new BigDecimal(pQuota.getApplicationLimit());

			// divide bg1 with bg2 with 0 scale
			float lPercentageApplicationUtilized = (lBd1.divide(lBd2, 2,
					RoundingMode.CEILING).intValue());
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("setPercentageApplicationUsed: "
						+ lPercentageApplicationUtilized);
			lQuota.setPercentageApplicationUsed(String
					.valueOf(lPercentageApplicationUtilized));
		} catch (ArithmeticException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}

		lQuota.addStorageQuota(convertStorageQuota(pQuota,
				pApplicationQuotaUsed, pStorageQuotaUsed));

		if (pBandwidthQuotaUsed != null) {
			try {
				lQuota.setBandwidthUsed(pBandwidthQuotaUsed
						.getBandwidthUsedInBytes());
				lQuota.setBandwidthLimit(pQuota.getBandwidthLimitInBytes());
				BigDecimal lBd1 = new BigDecimal(
						pBandwidthQuotaUsed.getBandwidthUsedInBytes() * 100);
				BigDecimal lBd2 = new BigDecimal(
						pQuota.getBandwidthLimitInBytes());

				// divide bg1 with bg2 with 0 scale
				float lPercentageBandwidthUtilized = (lBd1.divide(lBd2, 2,
						RoundingMode.CEILING).intValue());
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("setPercentageBandwidthUsed: "
							+ lPercentageBandwidthUtilized);
				lQuota.setPercentageBandwidthUsed(String
						.valueOf(lPercentageBandwidthUtilized));
			} catch (ArithmeticException e) {
				LOGGER.log(Level.WARNING, e.getMessage(), e);
			}

		} else {
			lQuota.setBandwidthUsed(0L);
			lQuota.setPercentageBandwidthUsed(String.valueOf(0.0));
			lQuota.setBandwidthLimit(pQuota.getBandwidthLimitInBytes());
		}
		return lQuota;

	}

	private com.cm.quota.transfer.StorageQuota convertStorageQuota(
			Quota pQuota, ApplicationQuotaUsed pApplicationQuotaUsed,
			StorageQuotaUsed pStorageQuotaUsed) {
		if (pStorageQuotaUsed == null) {
			LOGGER.log(Level.WARNING, "StorageQuotaUsed is NULL");
			return null;
		}
		com.cm.quota.transfer.StorageQuota lStorageQuota = new com.cm.quota.transfer.StorageQuota();
		lStorageQuota.setApplicationId(pStorageQuotaUsed.getApplicationId());
		lStorageQuota.setTrackingId(pStorageQuotaUsed.getTrackingId());
		// lStorageQuota.setStorageLimitInBytes(pQuota.getStorageLimitInBytes());
		lStorageQuota.setStorageUsedInBytes(pStorageQuotaUsed
				.getStorageUsedInBytes());

		// try {
		// BigDecimal lBd1 = new BigDecimal(
		// pStorageQuotaUsed.getStorageUsedInBytes() * 100);
		// BigDecimal lBd2 = new BigDecimal(pQuota.getStorageLimitInBytes());
		//
		// // divide bg1 with bg2 with 0 scale
		// int lPercentageStorageUtilized = (lBd1.divide(lBd2, 0,
		// RoundingMode.CEILING).intValue());
		// lStorageQuota.setPercentageStorageUsed(lPercentageStorageUtilized);
		// if (LOGGER.isLoggable(Level.INFO))
		// LOGGER.info("setPercentageStorageUsed: "
		// + lPercentageStorageUtilized);
		// } catch (ArithmeticException e) {
		// LOGGER.log(Level.WARNING, e.getMessage(), e);
		// }
		return lStorageQuota;

	}
}
