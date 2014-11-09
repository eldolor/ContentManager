package com.cm.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.accountmanagement.account.Account;
import com.cm.accountmanagement.client.key.ClientKeyService;
import com.cm.admin.plan.Plan;
import com.cm.common.entity.Result;
import com.cm.config.CanonicalPlan;
import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.application.ApplicationService;
import com.cm.contentmanager.content.Content;
import com.cm.contentmanager.content.ContentService;
import com.cm.contentmanager.contentgroup.ContentGroup;
import com.cm.gcm.GcmRegistrationRequest;
import com.cm.quota.Quota;
import com.cm.quota.QuotaService;
import com.cm.usermanagement.user.User;
import com.cm.usermanagement.user.UserService;
import com.cm.util.PMF;
import com.cm.util.Utils;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@Controller
public class AdminController {

	private static final Logger LOGGER = Logger.getLogger(AdminController.class
			.getName());

	@Autowired
	private QuotaService quotaService;
	@Autowired
	private UserService userService;
	@Autowired
	private com.cm.accountmanagement.account.AccountService accountService;
	@Autowired
	private com.cm.admin.plan.PlanDao planDao;
	@Autowired
	private ContentService contentService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ClientKeyService clientKeyService;

	@RequestMapping(value = "/admin/delete/users", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result deleteUsers(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteUsers");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(User.class);
				// Anshu: commented out for safety
				// q.deletePersistentAll();
				response.setStatus(HttpServletResponse.SC_OK);
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteUsers");
		}
	}

	@RequestMapping(value = "/admin/delete/blobstore", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result deleteBlobStore(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteBlobStore");
			BlobstoreService blobstoreService = BlobstoreServiceFactory
					.getBlobstoreService();
			Iterator<BlobInfo> iterator = new BlobInfoFactory()
					.queryBlobInfos();

			while (iterator.hasNext()) {
				// Anshu: commented out for safety
				// blobstoreService.delete(iterator.next().getBlobKey());

			}
			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteBlobStore");
		}
	}

	@RequestMapping(value = "/cron/cleanup/blobstore", method = RequestMethod.GET)
	public void cleanupBlobStoreCron(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			Result lResult = this.cleanupBlobStore(response);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info(lResult.toString());
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/admin/cleanup/blobstore", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result cleanupBlobStore(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<String> lUris = new ArrayList<String>();

			List<Account> lAccounts = accountService.getAccounts();
			int lCountContent = 0;
			for (Account account : lAccounts) {

				List<Application> lApplications = applicationService
						.getApplicationsByAccountId(account.getId(), /**
						 * include
						 * deleted
						 **/
						true);
				for (Application lApplication : lApplications) {
					List<Content> lContentList = contentService.get(
							lApplication.getId(), /** include deleted **/
							true);
					for (Content lContent : lContentList) {
						lUris.add(lContent.getUri());
						lCountContent++;
					}
				}
			}
			BlobstoreService blobstoreService = BlobstoreServiceFactory
					.getBlobstoreService();
			Iterator<BlobInfo> iterator = new BlobInfoFactory()
					.queryBlobInfos();
			int lCountDeleted = 0, lCountSkipped = 0;
			while (iterator.hasNext()) {
				BlobInfo lBlobInfo = iterator.next();
				BlobKey lBlobKey = lBlobInfo.getBlobKey();
				if (!lUris.contains(lBlobKey.getKeyString())) {
					blobstoreService.delete(lBlobKey);
					lCountDeleted++;
				} else {
					lCountSkipped++;
				}
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Content Count is " + lCountContent + " "
						+ lCountDeleted + " blobs deleted. " + lCountSkipped
						+ " blobs skipped.");
			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/admin/create/default/su", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result createSu(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering createSu");
			long lTime = System.currentTimeMillis();
			Account lAccount = accountService
					.getAccountByAccountName("SUPER USER ACCOUNT");
			if (lAccount == null) {
				lAccount = new Account();
				lAccount.setName("SUPER USER ACCOUNT");
				lAccount.setDescription("This is the default account for the 'su' user. It must not be used to create any campaigns.");
				lAccount.setTimeCreatedMs(lTime);
				lAccount.setTimeCreatedTimeZoneOffsetMs(0L);
				lAccount.setTimeUpdatedMs(lTime);
				lAccount.setTimeUpdatedTimeZoneOffsetMs(0L);
				accountService.saveAccount(lAccount);

				lAccount = accountService
						.getAccountByAccountName("SUPER USER ACCOUNT");
			}

			User lUser = userService
					.getUserByUserName(User.DEFAULT_SUPER_ADMIN_USER_NAME);
			if (lUser == null) {
				lUser = new User();
				lUser.setUsername(User.DEFAULT_SUPER_ADMIN_USER_NAME);
				lUser.setEmail(User.DEFAULT_SUPER_ADMIN_USER_NAME);
				lUser.setFirstName("Super");
				lUser.setLastName("User");
				// default to true
				lUser.setEnabled(true);
				lUser.setRole(User.ROLE_SUPER_ADMIN);
				lUser.setAccountId(lAccount.getId());

				lUser.setUsername(User.DEFAULT_SUPER_ADMIN_USER_NAME);
				// Match the password encoder configured in spring-security.xml
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				lUser.setPassword(encoder
						.encode(User.DEFAULT_SUPER_ADMIN_PASSWORD));

				lUser.setTimeCreatedMs(lTime);
				lUser.setTimeCreatedTimeZoneOffsetMs(0L);
				lUser.setTimeUpdatedMs(lTime);
				lUser.setTimeUpdatedTimeZoneOffsetMs(0L);

				userService.saveUser(lUser);
				response.setStatus(HttpServletResponse.SC_CREATED);
			}

			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting createSu");
		}
	}

	@RequestMapping(value = "/admin/create/default/plans", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result createPlans(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering createPlans");
			long lTime = System.currentTimeMillis();
			long lTimezoneOffset = (long) TimeZone.getTimeZone("UTC")
					.getRawOffset();
			CanonicalPlan[] lCanonicalPlanNames = CanonicalPlan.values();
			for (int i = 0; i < lCanonicalPlanNames.length; i++) {
				Plan lPlan = new Plan();
				lPlan.setCanonicalPlanName(lCanonicalPlanNames[i].getName());
				lPlan.setCurrency("usd");
				long lAmountInCents = 0;
				if (lCanonicalPlanNames[i].equals(CanonicalPlan.FREE))
					lAmountInCents = 0;
				else if (lCanonicalPlanNames[i].equals(CanonicalPlan.LARGE))
					lAmountInCents = 5000;
				else if (lCanonicalPlanNames[i].equals(CanonicalPlan.MEDIUM))
					lAmountInCents = 2500;
				else if (lCanonicalPlanNames[i].equals(CanonicalPlan.MICRO))
					lAmountInCents = 1500;
				else if (lCanonicalPlanNames[i].equals(CanonicalPlan.SMALL))
					lAmountInCents = 700;
				lPlan.setAmountInCents(lAmountInCents);
				lPlan.setTimeCreatedMs(lTime);
				lPlan.setTimeCreatedTimeZoneOffsetMs(lTimezoneOffset);
				planDao.save(lPlan);
			}
			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting createPlans");
		}
	}

	@RequestMapping(value = "/admin/assign/default/value/gcmregistrationrequests", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result updateGcmRegistrationRequests(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateGcmRegistrationRequests");
			try {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Entering getGcmRegistrationRequests");

				PersistenceManager pm = null;

				try {
					pm = PMF.get().getPersistenceManager();
					Query q = pm.newQuery(GcmRegistrationRequest.class);
					List<GcmRegistrationRequest> lList = (List<GcmRegistrationRequest>) q
							.execute();
					for (GcmRegistrationRequest gcmRegistrationRequest : lList) {
						gcmRegistrationRequest.setDeprecated(Boolean
								.valueOf(false));
						gcmRegistrationRequest
								.setGcmDeviceNotRegistered(Boolean
										.valueOf(false));
						gcmRegistrationRequest
								.setGcmDeviceHasMultipleRegistrations(Boolean
										.valueOf(false));
					}
				} finally {
					if (pm != null) {
						pm.close();
					}
				}
			} finally {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Exiting getGcmRegistrationRequests");
			}

			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateGcmRegistrationRequests");
		}
	}

	@RequestMapping(value = "/admin/reset/quota", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result resetQuotaLimits(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Quota.class);
				List<Quota> lList = (List<Quota>) q.execute();
				for (Quota lQuota : lList) {

					String lPlanName = lQuota.getCanonicalPlanName();
					if (lPlanName.equals(CanonicalPlan.FREE.getName())) {
						lQuota.setBandwidthLimitInBytes(CanonicalPlan.FREE
								.getBandwidthQuota());
						lQuota.setStorageLimitInBytes(CanonicalPlan.FREE
								.getStorageQuota());
						lQuota.setApplicationLimit(CanonicalPlan.FREE
								.getApplicationQuota());
					} else if (lPlanName.equals(CanonicalPlan.LARGE.getName())) {
						lQuota.setBandwidthLimitInBytes(CanonicalPlan.LARGE
								.getBandwidthQuota());
						lQuota.setStorageLimitInBytes(CanonicalPlan.LARGE
								.getStorageQuota());
						lQuota.setApplicationLimit(CanonicalPlan.LARGE
								.getApplicationQuota());
					} else if (lPlanName.equals(CanonicalPlan.MEDIUM.getName())) {
						lQuota.setBandwidthLimitInBytes(CanonicalPlan.MEDIUM
								.getBandwidthQuota());
						lQuota.setStorageLimitInBytes(CanonicalPlan.MEDIUM
								.getStorageQuota());
						lQuota.setApplicationLimit(CanonicalPlan.MEDIUM
								.getApplicationQuota());
					} else if (lPlanName.equals(CanonicalPlan.MICRO.getName())) {
						lQuota.setBandwidthLimitInBytes(CanonicalPlan.MICRO
								.getBandwidthQuota());
						lQuota.setStorageLimitInBytes(CanonicalPlan.MICRO
								.getStorageQuota());
						lQuota.setApplicationLimit(CanonicalPlan.MICRO
								.getApplicationQuota());
					} else if (lPlanName.equals(CanonicalPlan.SMALL.getName())) {
						lQuota.setBandwidthLimitInBytes(CanonicalPlan.SMALL
								.getBandwidthQuota());
						lQuota.setStorageLimitInBytes(CanonicalPlan.SMALL
								.getStorageQuota());
						lQuota.setApplicationLimit(CanonicalPlan.SMALL
								.getApplicationQuota());
					}

					lQuota.setTimeCreatedMs(System.currentTimeMillis());
					lQuota.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
							.getDefault().getRawOffset());

				}
			} finally {
				if (pm != null) {
					pm.close();
				}
			}

			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateGcmRegistrationRequests");
		}
	}

	@RequestMapping(value = "/admin/assign/default/quota", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result assignFreeQuotas(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateGcmRegistrationRequests");
			try {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Entering getGcmRegistrationRequests");

				PersistenceManager pm = null;

				try {
					pm = PMF.get().getPersistenceManager();
					Query q = pm.newQuery(Account.class);
					List<Account> lList = (List<Account>) q.execute();
					for (Account lAccount : lList) {
						// assign them the free quota
						Quota lQuota = new Quota();
						lQuota.setAccountId(lAccount.getId());
						// default to free
						lQuota.setCanonicalPlanName(CanonicalPlan.FREE
								.getName());
						lQuota.setStorageLimitInBytes(CanonicalPlan.FREE
								.getStorageQuota());
						lQuota.setApplicationLimit(CanonicalPlan.FREE
								.getApplicationQuota());

						lQuota.setTimeCreatedMs(System.currentTimeMillis());
						lQuota.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
								.getDefault().getRawOffset());
						// save
						pm.makePersistent(lQuota);

					}
				} finally {
					if (pm != null) {
						pm.close();
					}
				}
			} finally {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Exiting getGcmRegistrationRequests");
			}

			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateGcmRegistrationRequests");
		}
	}

	@RequestMapping(value = "/admin/assign/default/tags", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result assignDefaultTags(HttpServletResponse response) {
		PersistenceManager pm = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			String[] lDefaultImageTag = { "image" };
			String[] lDefaultVideoTag = { "video" };
			pm = PMF.get().getPersistenceManager();

			List<Content> lContentList = contentService.getAll();
			for (Content lContent : lContentList) {

				Content _content = pm.getObjectById(Content.class,
						lContent.getId());
				if (_content.getTags() == null) {
					if (_content.getType().equals("image")) {
						_content.setTags(lDefaultImageTag);
					} else if (_content.getType().equals("video")) {
						_content.setTags(lDefaultVideoTag);
					}
				} else {
					String[] lTags = _content.getTags();
					boolean lContainsImageTag = false;
					boolean lContainsVideoTag = false;
					for (int i = 0; i < lTags.length; i++) {
						if (lTags[i].equals("image"))
							lContainsImageTag = true;
						else if (lTags[i].equals("video"))
							lContainsVideoTag = true;
					}
					if (_content.getType().equals("image")
							&& (!lContainsImageTag)) {
						_content.setTags(lDefaultImageTag);
					} else if (_content.getType().equals("video")
							&& (!lContainsVideoTag)) {
						_content.setTags(lDefaultVideoTag);
					}
				}

			}
			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;
		} finally {
			if (pm != null) {
				pm.close();
			}
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/admin/assign/clientkeys", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result assignClientKeys(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			try {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Entering");

				PersistenceManager pm = null;

				try {
					pm = PMF.get().getPersistenceManager();
					Query q = pm.newQuery(Account.class);
					List<Account> lList = (List<Account>) q.execute();
					for (Account lAccount : lList) {
						// assign
						clientKeyService.generateClientKey(lAccount.getId());
					}
				} finally {
					if (pm != null) {
						pm.close();
					}
				}
			} finally {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Exiting");
			}

			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	@RequestMapping(value = "/admin/content/size/update", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result updateSize(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<Account> lAccounts = accountService.getAccounts();
			for (Account account : lAccounts) {
				List<Application> lApplications = applicationService
						.getApplicationsByAccountId(account.getId());
				for (Application lApplication : lApplications) {
					List<Content> lContentList = contentService.get(
							lApplication.getId(), false);
					for (Content lContent : lContentList) {
						if (!Utils.isEmpty(lContent.getUri())) {
							// over the next
							long lDelayInMs = new Random().nextInt(60 * 1000);
							if (LOGGER.isLoggable(Level.INFO))
								LOGGER.info("Adding delay of " + lDelayInMs
										+ " ms before attempting to process");
							Utils.triggerUpdateContentSizeInBytesMessage(
									lContent.getId(), lDelayInMs);
						}
					}
				}
			}
			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	@RequestMapping(value = "/admin/quota/utilization/update", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result updateQuotaUtilization(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<Account> lAccounts = accountService.getAccounts();
			for (Account account : lAccounts) {
				List<Application> lApplications = applicationService
						.getApplicationsByAccountId(account.getId());
				int lApplicationsUsed = lApplications.size();
				quotaService.upsertApplicationUtilization(account.getId(),
						lApplicationsUsed);
				for (Application lApplication : lApplications) {
					long lStorageUsedInBytes = 0;
					List<Content> lContentList = contentService.get(
							lApplication.getId(), false);
					for (Content lContent : lContentList) {
						// roll up size
						lStorageUsedInBytes += lContent.getSizeInBytes();
					}
					// update for each application
					quotaService.upsertStorageUtilization(lApplication,
							lStorageUsedInBytes);
				}
			}
			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	@RequestMapping(value = "/admin/assign/default/value/changesstaged", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result defaultChangesStaged(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<Account> lAccounts = accountService.getAccounts();
			for (Account account : lAccounts) {
				List<Application> lApplications = applicationService
						.getApplicationsByAccountId(account.getId(), /**
						 * include
						 * deleted
						 **/
						true);
				for (Application lApplication : lApplications) {
					applicationService.updateChangesStaged(
							lApplication.getId(), /** assign default value **/
							false);
				}
			}
			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	@RequestMapping(value = "/admin/assign/default/value/deletedonplandowngrade", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result defaultDeletedOnPlanDowngrade(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			List<Account> lAccounts = accountService.getAccounts();
			for (Account account : lAccounts) {
				List<Application> lApplications = applicationService
						.getApplicationsByAccountId(account.getId(), /**
						 * include
						 * deleted
						 **/
						true);
				for (Application lApplication : lApplications) {
					PersistenceManager pm = null;
					try {
						pm = PMF.get().getPersistenceManager();
						Application _Application = pm.getObjectById(
								Application.class, lApplication.getId());
						// assign default value
						_Application.setDeletedOnPlanDowngrade(false);

					} finally {
						if (pm != null) {
							pm.close();
						}
					}

				}
			}
			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	@RequestMapping(value = "/admin/search/indexes/update", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result updateSearchIndexes(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			{
				PersistenceManager pm = null;

				try {
					pm = PMF.get().getPersistenceManager();
					Query q = pm.newQuery(Application.class);
					List<Application> lApplications = (List<Application>) q
							.execute();
					for (Application application : lApplications) {
						application.setNameIdx(application.getName()
								.toLowerCase());
					}
				} finally {
					if (pm != null) {
						pm.close();
					}
				}

			}
			{
				PersistenceManager pm = null;

				try {
					pm = PMF.get().getPersistenceManager();
					Query q = pm.newQuery(ContentGroup.class);
					List<ContentGroup> lContentGroups = (List<ContentGroup>) q
							.execute();
					for (ContentGroup contentGroup : lContentGroups) {
						contentGroup.setNameIdx(contentGroup.getName()
								.toLowerCase());
					}
				} finally {
					if (pm != null) {
						pm.close();
					}
				}

			}
			{
				PersistenceManager pm = null;

				try {
					pm = PMF.get().getPersistenceManager();
					Query q = pm.newQuery(Content.class);
					List<Content> lContents = (List<Content>) q.execute();
					for (Content content : lContents) {
						content.setNameIdx(content.getName().toLowerCase());
					}
				} finally {
					if (pm != null) {
						pm.close();
					}
				}

			}
			Result result = new Result();
			result.setResult(Result.SUCCESS);
			return result;

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

}
