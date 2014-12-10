package com.cm.usermanagement.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cm.config.CanonicalContentType;
import com.cm.config.CanonicalCouponTypes;
import com.cm.config.CanonicalPlan;
import com.cm.config.Configuration;
import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.application.ApplicationService;
import com.cm.contentmanager.content.Content;
import com.cm.contentmanager.content.ContentService;
import com.cm.contentmanager.contentgroup.ContentGroup;
import com.cm.contentmanager.contentgroup.ContentGroupService;
import com.cm.contentmanager.contentstat.ContentStat;
import com.cm.contentmanager.contentstat.ContentStatService;
import com.cm.contentmanager.contentstat.UnmanagedContentStat;
import com.cm.quota.Quota;
import com.cm.quota.QuotaService;
import com.cm.stripe.StripeCustomer;
import com.cm.stripe.StripeCustomerService;
import com.cm.usermanagement.user.transfer.ForgotPasswordRequest;
import com.cm.usermanagement.user.transfer.PasswordChangeRequest;
import com.cm.util.Utils;
import com.cm.util.ValidationError;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

@Controller
public class UserManagementController {
	@Autowired
	private UserService userService;
	@Autowired
	private ForgotPasswordEmailBuilder forgotPasswordEmailBuilder;
	@Autowired
	private StripeCustomerService stripeCustomerService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ContentGroupService contentGroupService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private QuotaService quotaService;
	@Autowired
	private ContentStatService contentStatService;

	private static final Logger LOGGER = Logger
			.getLogger(UserManagementController.class.getName());
	private static final String TEXT_HTML_CHARSET_UTF_8 = "text/html; charset=utf-8";

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView displaySignup(ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displaySignup");
		try {
			return new ModelAndView("signup", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displaySignup");
		}
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody List<ValidationError> doSignup(
			@RequestBody com.cm.usermanagement.user.transfer.User pUser,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doSignup");

			List<ValidationError> errors = validateOnCreate(pUser);
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				// create the basic user object, additional work will be
				// performed in the DAO (transactionally)
				com.cm.usermanagement.user.User lUser = new com.cm.usermanagement.user.User();
				// standardize to lower case
				lUser.setUsername(pUser.getUserName().toLowerCase());
				// userName is the email address
				lUser.setEmail(pUser.getUserName());
				lUser.setPassword(new BCryptPasswordEncoder().encode(pUser
						.getPassword()));

				lUser.setRole(User.ROLE_USER);
				// default to true
				lUser.setEnabled(true);
				lUser.setTimeCreatedMs(pUser.getTimeCreatedMs());
				lUser.setTimeCreatedTimeZoneOffsetMs(pUser
						.getTimeCreatedTimeZoneOffsetMs());
				lUser.setTimeUpdatedMs(pUser.getTimeUpdatedMs());
				lUser.setTimeUpdatedTimeZoneOffsetMs(pUser
						.getTimeUpdatedTimeZoneOffsetMs());

				// new user created
				User lDomainUser = userService.signUpUser(lUser);
				// create the demo application
				Application lApplication = createDemoApplication(lUser);
				List<ContentGroup> lContentGroups = createDemoContentGroups(
						lDomainUser, lApplication);
				Long[] lContentIds = createDemoContent(lDomainUser,
						lApplication, lContentGroups);
				// create demo usage reports for the last 10 days;
				// asynchronously
				{
					int lLastNDays = 10;
					// createDemoUsageReports(lApplication.getId(), lLastNDays);
					Queue queue = QueueFactory
							.getQueue(Configuration.CONTENT_STATS_QUEUE_NAME);
					TaskOptions taskOptions = TaskOptions.Builder
							.withUrl(
									"/tasks/demo/usagereports/create/"
											+ String.valueOf(lApplication
													.getId()) + "/"
											+ String.valueOf(lLastNDays))
							.param("id", String.valueOf(lApplication.getId()))
							.param("lastNDays", String.valueOf(lLastNDays))
							.method(Method.POST).countdownMillis(5000);
					queue.add(taskOptions);
				}

				// assign them the free quota
				Quota lQuota = new Quota();
				lQuota.setAccountId(lUser.getAccountId());
				// default to free
				lQuota.setCanonicalPlanId(CanonicalPlan.FREE.getId());
				lQuota.setStorageLimitInBytes(CanonicalPlan.FREE
						.getStorageQuota());
				lQuota.setApplicationLimit(CanonicalPlan.FREE
						.getApplicationQuota());

				lQuota.setTimeCreatedMs(System.currentTimeMillis());
				lQuota.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
				quotaService.create(lQuota);
				// update utilization
				Utils.triggerUpdateQuotaUtilizationMessage(
						lApplication.getAccountId(), 1000);
				// Utils.triggerUpdateBandwidthUtilizationMessage(
				// lApplication.getId(), 0L, 10000);
				Coupon lCoupon = generateReferAFriendCoupon(lDomainUser);

				{
					String lReferAFriendPromoCode = lCoupon.getCode();
					// if the new user was referred
					if (!Utils.isEmpty(lReferAFriendPromoCode)) {
						Queue queue = QueueFactory
								.getQueue(Configuration.USER_QUEUE_NAME);
						TaskOptions taskOptions = TaskOptions.Builder
								.withUrl(
										"/tasks/process/promocode/referafriend")
								.param("referredUserId",
										String.valueOf(pUser.getId()))
								.param("promoCode", pUser.getPromoCode())
								.method(Method.POST).countdownMillis(5000);
						queue.add(taskOptions);

					}
				}

				sendWelcomeEmail(lDomainUser, lCoupon);

				response.setStatus(HttpServletResponse.SC_CREATED);
				return null;
			}
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doSignup");
		}

	}

	private Coupon generateReferAFriendCoupon(User pUser) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			Coupon lCoupon = new Coupon();
			lCoupon.setAccountId(pUser.getAccountId());
			lCoupon.setUserId(pUser.getId());
			lCoupon.setType(CanonicalCouponTypes.REFER_A_FRIEND.getValue());
			lCoupon.setCode(Utils.generatePromoCode());
			// valid for the next 6 months, ends EOD for the user's timezone
			TimeZone lTimeZone = TimeZone.getTimeZone("UTC");
			lTimeZone.setRawOffset(pUser.getTimeCreatedTimeZoneOffsetMs()
					.intValue());
			lCoupon.setRedeemByMs(Utils.getNMonthsFromToday(6, lTimeZone)
					.getTimeInMillis());

			lCoupon.setTimeCreatedMs(pUser.getTimeCreatedMs());
			lCoupon.setTimeCreatedTimeZoneOffsetMs(pUser
					.getTimeCreatedTimeZoneOffsetMs());

			// save the coupon
			return userService.saveCoupon(lCoupon);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}

	}

	@RequestMapping(value = "/tasks/process/promocode/referafriend", method = RequestMethod.POST)
	public void processReferAFriendPromoCode(@RequestParam Long referredUserId,
			@RequestParam String promoCode, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			// validate the promo code
			Coupon lCoupon = userService.getCoupon(promoCode);
			User lUser = userService.getUser(lCoupon.getUserId());

			if (lUser != null) {
				User lReferredUser = userService.getUser(referredUserId);
				// if valid, then add storage and bandwidth quota
				{
					Quota lUserQuota = quotaService.get(lUser.getAccountId());

					lUserQuota.setBandwidthLimitInBytes(lUserQuota
							.getBandwidthLimitInBytes()
							+ Configuration.REFERERAL_BONUS_IN_BYTES);
					lUserQuota.setStorageLimitInBytes(lUserQuota
							.getStorageLimitInBytes()
							+ Configuration.REFERERAL_BONUS_IN_BYTES);
					lUserQuota.setTimeUpdatedMs(System.currentTimeMillis());
					lUserQuota.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
							.getDefault().getRawOffset());
					quotaService.update(lUserQuota);
				}
				// referred user
				{
					Quota lReferredUserQuota = quotaService.get(lReferredUser
							.getAccountId());
					lReferredUserQuota.setBandwidthLimitInBytes(lReferredUserQuota
							.getBandwidthLimitInBytes()
							+ Configuration.REFERERAL_BONUS_IN_BYTES);
					lReferredUserQuota.setStorageLimitInBytes(lReferredUserQuota
							.getStorageLimitInBytes()
							+ Configuration.REFERERAL_BONUS_IN_BYTES);
					lReferredUserQuota.setTimeUpdatedMs(System
							.currentTimeMillis());
					lReferredUserQuota
							.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
									.getDefault().getRawOffset());
					quotaService.update(lReferredUserQuota);
				}
				// make a record of the changes to the quota
				{
					lCoupon.setNumberOfTimesRedeemed((lCoupon
							.getNumberOfTimesRedeemed() + 1));
					lCoupon.setTimeUpdatedMs(System.currentTimeMillis());
					long lTimezoneOffset = (long) TimeZone.getTimeZone("UTC")
							.getRawOffset();
					lCoupon.setTimeUpdatedTimeZoneOffsetMs(lTimezoneOffset);
					userService.updateCouponRedemption(lCoupon);
				}
				// send email indicating that coupon was used and quota was
				// added
				{
					
				}
			}
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Throwable e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}

	}

	private void sendWelcomeEmail(User pUser, Coupon pCoupon) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			StringBuilder lHtmlFormattedHeader = new StringBuilder();

			lHtmlFormattedHeader
					.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">Welcome to Skok.</p>");
			lHtmlFormattedHeader
					.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">Skok is an Advanced Content Management and  Delivery platform for your Mobile Apps. Skok delivers rich content to your Mobile Apps, and stores it locally on mobile devices.</p>");
			lHtmlFormattedHeader
					.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">This elevates user experience of your Mobile Apps. Your content loads much faster, and users can engage with your rich content, even if they lose their data connection.</p>");

			lHtmlFormattedHeader
					.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">You can find out more at <a href=\"http://skok.co/docs/overview\">Skok</a> </p>");

			StringBuilder lHtmlFormattedCallout = new StringBuilder();
			lHtmlFormattedCallout
					.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\"><b>Powerful New Features</b></p>");
			lHtmlFormattedCallout.append("<ol>");
			lHtmlFormattedCallout.append("<li>Cloud-driven Architecture</li>");
			lHtmlFormattedCallout
					.append("<li>Advanced Content Management Platform</li>");
			lHtmlFormattedCallout
					.append("<li>Streamlined Content Delivery</li>");
			lHtmlFormattedCallout.append("<li>Auto-sizing of Images</li>");
			lHtmlFormattedCallout
					.append("<li>Say Goodbye to Google Play APK Expansion Files</li>");
			lHtmlFormattedCallout.append("<li>Continuous Content Updates</li>");
			lHtmlFormattedCallout.append("<li>No Extra Coding Required</li>");
			lHtmlFormattedCallout
					.append("<li>Easily-pluggable &amp; Feature-rich SDK</li>");
			lHtmlFormattedCallout.append("<li>Mobile Device Storage</li>");
			lHtmlFormattedCallout.append("<li>Advanced Caching on Device</li>");
			lHtmlFormattedCallout
					.append("<li>Non-Blocking Content Downloads</li>");
			lHtmlFormattedCallout
					.append("<li>Manages Content Downloads over Spotty Networks</li>");
			lHtmlFormattedCallout.append("<li>Download Notifications</li>");
			lHtmlFormattedCallout
					.append("<li>Analytics to Track Usage Statistics of your Content</li>");
			lHtmlFormattedCallout.append("</ol>");
			String lEmailTemplate = new StripeChargeEmailBuilder().build(
					lHtmlFormattedHeader.toString(),
					lHtmlFormattedCallout.toString());
			Utils.sendEmail(Configuration.FROM_EMAIL_ADDRESS,
					Configuration.FROM_NAME, pUser.getUsername(), "",
					"Welcome to " + Configuration.SITE_NAME, lEmailTemplate,
					TEXT_HTML_CHARSET_UTF_8);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	private Application createDemoApplication(User pUser) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering createDemoApplication");
			Application lApplication = new Application();
			lApplication.setAccountId(pUser.getAccountId());
			lApplication
					.setDescription("We've created a demo application for illustrative purposes only. The application contains 2 content groups, and a few Image and Video type contents. The demo application is enabled by default, and is configured such that the users can only download the contents (images & videos) over a Wi-Fi network, and not over a Cellular Network. This helps conserve the cellular data usage.");
			lApplication.setEnabled(true);
			lApplication.setCollectUsageData(true);
			lApplication.setName("Demo Application");
			long lTime = System.currentTimeMillis();
			lApplication.setTimeCreatedMs(lTime);
			lApplication.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
					.getDefault().getOffset(lTime));
			lApplication.setUpdateOverWifiOnly(true);
			lApplication.setUserId(pUser.getId());

			// user is not logged in yet, was just created
			lApplication = applicationService.saveApplication(pUser,
					createTrackingId(pUser), lApplication);
			return lApplication;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting createDemoApplication");
		}

	}

	private List<ContentGroup> createDemoContentGroups(User pUser,
			Application pApplication) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering createDemoContentGroup");
			List<ContentGroup> lContentGroups = new ArrayList<ContentGroup>();

			{
				ContentGroup lContentGroup = new ContentGroup();
				lContentGroup.setAccountId(pUser.getAccountId());
				lContentGroup
						.setDescription("This content group is used to organize Image type content.");
				lContentGroup.setEnabled(true);
				lContentGroup.setName("Demo Image Content Group");
				long lTime = System.currentTimeMillis();
				lContentGroup.setTimeCreatedMs(lTime);
				lContentGroup.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getOffset(lTime));
				lContentGroup.setUserId(pUser.getId());

				lContentGroup.setApplicationId(pApplication.getId());

				DateTime lDt = new DateTime();
				DateTimeFormatter lFmt = ISODateTimeFormat.dateTime();
				String lStartDateIso8601 = lFmt.print(lDt);
				lContentGroup.setStartDateIso8601(lStartDateIso8601);

				// set high date
				lContentGroup.setEndDateMs(Long.MAX_VALUE);

				lContentGroup = contentGroupService.save(pUser, lContentGroup);
				lContentGroups.add(lContentGroup);
			}
			{
				ContentGroup lContentGroup = new ContentGroup();
				lContentGroup.setAccountId(pUser.getAccountId());
				lContentGroup
						.setDescription("This content group is used to organize Video type content.");
				lContentGroup.setEnabled(true);
				lContentGroup.setName("Demo Video Content Group");
				long lTime = System.currentTimeMillis();
				lContentGroup.setTimeCreatedMs(lTime);
				lContentGroup.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getOffset(lTime));
				lContentGroup.setUserId(pUser.getId());

				lContentGroup.setApplicationId(pApplication.getId());

				DateTime lDt = new DateTime();
				DateTimeFormatter lFmt = ISODateTimeFormat.dateTime();
				String lStartDateIso8601 = lFmt.print(lDt);
				lContentGroup.setStartDateIso8601(lStartDateIso8601);

				// set high date
				lContentGroup.setEndDateMs(Long.MAX_VALUE);

				lContentGroup = contentGroupService.save(pUser, lContentGroup);
				lContentGroups.add(lContentGroup);
			}
			return lContentGroups;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting createDemoContentGroup");
		}

	}

	private Long[] createDemoContent(User pUser, Application pApplication,
			List<ContentGroup> pContentGroups) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering createDemoContent");
			Long[] lContentIds = new Long[2];

			{
				Content lContent = new Content();
				lContent.setAccountId(pUser.getAccountId());
				lContent.setDescription("This is a demo Image type content.");
				lContent.setEnabled(true);
				lContent.setName("Demo Image - with no attached image");
				long lTime = System.currentTimeMillis();
				lContent.setTimeCreatedMs(lTime);
				lContent.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getOffset(lTime));
				lContent.setUserId(pUser.getId());
				lContent.setApplicationId(pApplication.getId());
				lContent.setContentGroupId(pContentGroups.get(0).getId());
				lContent.setType(CanonicalContentType.IMAGE.getValue());

				DateTime lDt = new DateTime();
				DateTimeFormatter lFmt = ISODateTimeFormat.dateTime();
				String lStartDateIso8601 = lFmt.print(lDt);
				lContent.setStartDateIso8601(lStartDateIso8601);

				// set high date
				lContent.setEndDateMs(Long.MAX_VALUE);

				lContent = contentService.save(pUser, lContent);
				lContentIds[0] = lContent.getId();
			}

			{
				Content lContent = new Content();
				lContent.setAccountId(pUser.getAccountId());
				lContent.setDescription("This is a demo Video type content.");
				lContent.setEnabled(true);
				lContent.setName("Demo Video - with no attached video");
				long lTime = System.currentTimeMillis();
				lContent.setTimeCreatedMs(lTime);
				lContent.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getOffset(lTime));
				lContent.setUserId(pUser.getId());
				lContent.setApplicationId(pApplication.getId());
				lContent.setContentGroupId(pContentGroups.get(1).getId());
				lContent.setType(CanonicalContentType.VIDEO.getValue());

				DateTime lDt = new DateTime();
				DateTimeFormatter lFmt = ISODateTimeFormat.dateTime();
				String lStartDateIso8601 = lFmt.print(lDt);
				lContent.setStartDateIso8601(lStartDateIso8601);

				// set high date
				lContent.setEndDateMs(Long.MAX_VALUE);

				lContent = contentService.save(pUser, lContent);
				lContentIds[1] = lContent.getId();
			}

			return lContentIds;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting createDemoContentGroup");
		}

	}

	@RequestMapping(value = "/tasks/demo/usagereports/create/{applicationId}/{lastNDays}", method = RequestMethod.POST)
	public void createDemoUsageReports(@PathVariable long applicationId,
			@PathVariable int lastNDays, HttpServletResponse response) {

		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			{
				Calendar lEod = Utils.getEndOfDayToday(TimeZone
						.getTimeZone("UTC"));
				List<Content> lContents = contentService.get(applicationId,
						false);
				for (int i = 0; i < lastNDays; i++) {
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Processing: "
								+ lEod.getTime().toLocaleString());
					for (Content content : lContents) {
						int lRandom = Utils.getRandomNumber(1, 10);
						for (int j = 0; j < lRandom; j++) {
							contentStatService
									.saveContentStat(createDemoContentStat(
											content.getApplicationId(),
											content.getContentGroupId(),
											content.getId(),
											lEod.getTimeInMillis()));
						}
					}
					lEod.add(Calendar.DATE, -1);
				}

				List<UnmanagedContentStat> lContentStats = new ArrayList<UnmanagedContentStat>();

				for (int i = 0; i < lastNDays; i++) {
					int lRandom = Utils.getRandomNumber(1, 10);
					for (int j = 0; j < lRandom; j++) {
						long lEodTimeMs = Utils.getEndOfDayMinusDays(i,
								TimeZone.getTimeZone("UTC"));
						if (LOGGER.isLoggable(Level.INFO))
							LOGGER.info("Processing: " + lEod);
						lContentStats.add(createDemoUnmanagedContentStat(
								applicationId,
								"e5eecc2bbea951f134d72b02b5aa900a6814396b",
								"http://mywebsite.com/mycontent/myimage",
								lEodTimeMs));
					}

				}
				for (int i = 0; i < lastNDays; i++) {
					int lRandom = Utils.getRandomNumber(1, 10);
					for (int j = 0; j < lRandom; j++) {
						long lEodTimeMs = Utils.getEndOfDayMinusDays(i,
								TimeZone.getTimeZone("UTC"));
						if (LOGGER.isLoggable(Level.INFO))
							LOGGER.info("Processing: " + lEod);
						lContentStats.add(createDemoUnmanagedContentStat(
								applicationId,
								"f6af154aab2bca72d6d91317c07471a582041bb8",
								"http://mywebsite.com/mycontent/myvideo",
								lEodTimeMs));
					}

				}

				contentStatService
						.rollupUnmanagedSummaryRealTime(lContentStats);

			}

			{
				Calendar lEod = Utils.getEndOfDayToday(TimeZone
						.getTimeZone("UTC"));
				Calendar lSod = Utils.getStartOfDayToday(TimeZone
						.getTimeZone("UTC"));
				for (int i = 0; i < lastNDays; i++) {
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Processing: "
								+ lSod.getTime().toLocaleString() + "::"
								+ lEod.getTime().toLocaleString());
					// Utils.triggerRollupMessage(applicationId,
					// lSod.getTimeInMillis(), lEod.getTimeInMillis(), 0);
					contentStatService.rollupSummary(applicationId,
							lSod.getTimeInMillis(), lEod.getTimeInMillis());
					lSod.add(Calendar.DATE, -1);
					lEod.add(Calendar.DATE, -1);
				}
			}
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

	@RequestMapping(value = "/test/rollupsummaryrealtime/{applicationId}/{lastNDays}", method = RequestMethod.GET)
	public void testRollupSummaryRealTime(@PathVariable long applicationId,
			@PathVariable int lastNDays, HttpServletResponse response) {

		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			{
				List<Content> lContents = contentService.get(applicationId,
						false);
				List<ContentStat> lContentStats = new ArrayList<ContentStat>();

				for (int i = 0; i < lastNDays; i++) {
					long lEod = Utils.getEndOfDayMinusDays(i,
							TimeZone.getTimeZone("UTC"));
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Processing: " + lEod);
					for (Content content : lContents) {
						int lRandom = Utils.getRandomNumber(1, 2);
						for (int j = 0; j < lRandom; j++) {
							lContentStats.add(createDemoContentStat(
									content.getApplicationId(),
									content.getContentGroupId(),
									content.getId(), lEod));
						}
					}
				}
				contentStatService.rollupSummaryRealTime(lContentStats);
			}

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

	private ContentStat createDemoContentStat(long applicationId,
			long contentGroupId, long contentId, long eventTime) {
		ContentStat lContentStat = new ContentStat();
		lContentStat.setApplicationId(applicationId);
		lContentStat.setContentGroupId(contentGroupId);
		lContentStat.setContentId(contentId);
		lContentStat.setEventTimeMs(eventTime);
		lContentStat.setEventTimeZoneOffsetMs((long) TimeZone
				.getTimeZone("UTC").getRawOffset());
		lContentStat.setEventType("impression");
		return lContentStat;
	}

	@RequestMapping(value = "/test/rollupunmanagedsummaryrealtime/{applicationId}/{lastNDays}", method = RequestMethod.GET)
	public void testRollupUnmanagedSummaryRealTime(
			@PathVariable long applicationId, @PathVariable int lastNDays,
			HttpServletResponse response) {

		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			List<UnmanagedContentStat> lContentStats = new ArrayList<UnmanagedContentStat>();

			for (int i = 0; i < lastNDays; i++) {
				long lEod = Utils.getEndOfDayMinusDays(i,
						TimeZone.getTimeZone("UTC"));
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Processing: " + lEod);
				lContentStats.add(createDemoUnmanagedContentStat(applicationId,
						"738ddf35b3a85a7a6ba7b232bd3d5f1e4d284ad1",
						"http://www.google.com", lEod));

			}
			contentStatService.rollupUnmanagedSummaryRealTime(lContentStats);

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

	private UnmanagedContentStat createDemoUnmanagedContentStat(
			long applicationId, String urlHash, String url, long eventTime) {
		UnmanagedContentStat lContentStat = new UnmanagedContentStat();
		lContentStat.setApplicationId(applicationId);
		lContentStat.setUrlHash(urlHash);
		lContentStat.setUrl(url);
		lContentStat.setEventTimeMs(eventTime);
		lContentStat.setEventTimeZoneOffsetMs((long) TimeZone
				.getTimeZone("UTC").getRawOffset());
		lContentStat.setEventType("impression");
		return lContentStat;
	}

	private String createTrackingId(User pUser) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering createTrackingId");
			Long lAccountId = pUser.getAccountId();

			// include all deleted applications, to ensure that the tracking id
			// of a deleted application is not reassigned.
			List<Application> lApplications = applicationService
					.getApplicationsByAccountId(lAccountId, true);

			String lTrackingId = Configuration.TRACKING_ID_PREFIX + lAccountId
					+ "_" + (lApplications.size() + 1); // the collection will
														// have
														// size 0 at first

			return lTrackingId;

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting createTrackingId");
		}
	}

	/**
	 * Secured uri
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public ModelAndView displayAccountSettings(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displayAccountSettings");
		try {
			model.addAttribute("canonicalPlanIdFree",
					CanonicalPlan.FREE.getId());
			model.addAttribute("canonicalPlanIdMicro",
					CanonicalPlan.MICRO.getId());
			model.addAttribute("canonicalPlanIdSmall",
					CanonicalPlan.SMALL.getId());
			model.addAttribute("canonicalPlanIdMedium",
					CanonicalPlan.MEDIUM.getId());
			model.addAttribute("canonicalPlanIdLarge",
					CanonicalPlan.LARGE.getId());
			User lUser = userService.getLoggedInUser();
			StripeCustomer lStripeCustomer = stripeCustomerService.get(lUser
					.getAccountId());
			boolean lIsUpdateCCInfo = false;

			if (lStripeCustomer == null) {
				model.addAttribute("isSubscribed", false);
				// default to free
				model.addAttribute("subscribedCanonicalPlanId",
						CanonicalPlan.FREE.getId());
			} else {
				// check to see if the user's CC is expiring or has expired
				if (Utils.isCCExpired(lStripeCustomer.getCardExpYear(),
						lStripeCustomer.getCardExpMonth())
						|| Utils.isCCExpiring(lStripeCustomer.getCardExpYear(),
								lStripeCustomer.getCardExpMonth())) {
					lIsUpdateCCInfo = true;
				}
				model.addAttribute("isSubscribed", true);
				model.addAttribute("subscribedCanonicalPlanId",
						lStripeCustomer.getCanonicalPlanId());
			}
			model.addAttribute("isUpdateCCInfo", lIsUpdateCCInfo);
			model.addAttribute("isError", false);
			return new ModelAndView("account_settings", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displayAccountSettings");
		}
	}

	/**
	 * Secured uri
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/account/billing", method = RequestMethod.GET)
	public ModelAndView displayBilling(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering");
		try {
			model.addAttribute("canonicalPlanIdFree",
					CanonicalPlan.FREE.getId());
			model.addAttribute("canonicalPlanIdMicro",
					CanonicalPlan.MICRO.getId());
			model.addAttribute("canonicalPlanIdSmall",
					CanonicalPlan.SMALL.getId());
			model.addAttribute("canonicalPlanIdMedium",
					CanonicalPlan.MEDIUM.getId());
			model.addAttribute("canonicalPlanIdLarge",
					CanonicalPlan.LARGE.getId());
			User lUser = userService.getLoggedInUser();
			StripeCustomer lStripeCustomer = stripeCustomerService.get(lUser
					.getAccountId());
			boolean lIsUpdateCCInfo = false;

			if (lStripeCustomer == null) {
				model.addAttribute("isSubscribed", false);
				// default to free
				model.addAttribute("subscribedCanonicalPlanId",
						CanonicalPlan.FREE.getId());
			} else {
				// check to see if the user's CC is expiring or has expired
				if (Utils.isCCExpired(lStripeCustomer.getCardExpYear(),
						lStripeCustomer.getCardExpMonth())
						|| Utils.isCCExpiring(lStripeCustomer.getCardExpYear(),
								lStripeCustomer.getCardExpMonth())) {
					lIsUpdateCCInfo = true;
				}
				model.addAttribute("isSubscribed", true);
				model.addAttribute("subscribedCanonicalPlanId",
						lStripeCustomer.getCanonicalPlanId());
			}
			model.addAttribute("isUpdateCCInfo", lIsUpdateCCInfo);
			model.addAttribute("isError", false);
			return new ModelAndView("settings_billing", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	/**
	 * Secured uri
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/account/usage", method = RequestMethod.GET)
	public ModelAndView displayAccountUsage(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering");
		try {
			model.addAttribute("canonicalPlanIdFree",
					CanonicalPlan.FREE.getId());
			model.addAttribute("canonicalPlanIdMicro",
					CanonicalPlan.MICRO.getId());
			model.addAttribute("canonicalPlanIdSmall",
					CanonicalPlan.SMALL.getId());
			model.addAttribute("canonicalPlanIdMedium",
					CanonicalPlan.MEDIUM.getId());
			model.addAttribute("canonicalPlanIdLarge",
					CanonicalPlan.LARGE.getId());
			User lUser = userService.getLoggedInUser();
			StripeCustomer lStripeCustomer = stripeCustomerService.get(lUser
					.getAccountId());
			boolean lIsUpdateCCInfo = false;

			if (lStripeCustomer == null) {
				model.addAttribute("isSubscribed", false);
				// default to free
				model.addAttribute("subscribedCanonicalPlanId",
						CanonicalPlan.FREE.getId());
			} else {
				// check to see if the user's CC is expiring or has expired
				if (Utils.isCCExpired(lStripeCustomer.getCardExpYear(),
						lStripeCustomer.getCardExpMonth())
						|| Utils.isCCExpiring(lStripeCustomer.getCardExpYear(),
								lStripeCustomer.getCardExpMonth())) {
					lIsUpdateCCInfo = true;
				}
				model.addAttribute("isSubscribed", true);
				model.addAttribute("subscribedCanonicalPlanId",
						lStripeCustomer.getCanonicalPlanId());
			}
			model.addAttribute("isUpdateCCInfo", lIsUpdateCCInfo);
			model.addAttribute("isError", false);
			return new ModelAndView("settings_account_usage", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	/**
	 * Secured uri
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/account/plans", method = RequestMethod.GET)
	public ModelAndView displayPlansAndPricing(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering");
		try {
			User lUser = userService.getLoggedInUser();
			setupPlanAndPricing(model);

			StripeCustomer lStripeCustomer = stripeCustomerService.get(lUser
					.getAccountId());
			boolean lIsUpdateCCInfo = false;

			// user logged in but not yet subscribed to any other plans
			if (lStripeCustomer == null) {
				model.addAttribute("isSubscribed", false);
				// default to free
				model.addAttribute("subscribedCanonicalPlanId",
						CanonicalPlan.FREE.getId());
			} else {
				// check to see if the user's CC is expiring or has expired
				if (Utils.isCCExpired(lStripeCustomer.getCardExpYear(),
						lStripeCustomer.getCardExpMonth())
						|| Utils.isCCExpiring(lStripeCustomer.getCardExpYear(),
								lStripeCustomer.getCardExpMonth())) {
					lIsUpdateCCInfo = true;
				}
				model.addAttribute("isSubscribed", true);
				model.addAttribute("subscribedCanonicalPlanId",
						lStripeCustomer.getCanonicalPlanId());
			}
			model.addAttribute("isUpdateCCInfo", lIsUpdateCCInfo);
			model.addAttribute("isError", false);

			return new ModelAndView("settings_plans_and_pricing_post_login",
					model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	private void setupPlanAndPricing(ModelMap model) {
		model.addAttribute("stripePublicKey",
				Configuration.STRIPE_PUBLIC_API_KEY);

		model.addAttribute("canonicalPlanIdFree", CanonicalPlan.FREE.getId());
		model.addAttribute("canonicalPlanFreeNetworkBandwidth",
				CanonicalPlan.FREE.getDisplayNetworkBandwidth());
		model.addAttribute("canonicalPlanFreeStorage",
				CanonicalPlan.FREE.getDisplayStorage());
		model.addAttribute("canonicalPlanFreePrice",
				CanonicalPlan.FREE.getDisplayPrice());
		model.addAttribute("canonicalPlanFreePriceInCents",
				CanonicalPlan.FREE.getPriceInCents());

		model.addAttribute("canonicalPlanIdMicro", CanonicalPlan.MICRO.getId());
		model.addAttribute("canonicalPlanMicroNetworkBandwidth",
				CanonicalPlan.MICRO.getDisplayNetworkBandwidth());
		model.addAttribute("canonicalPlanMicroStorage",
				CanonicalPlan.MICRO.getDisplayStorage());
		model.addAttribute("canonicalPlanMicroPrice",
				CanonicalPlan.MICRO.getDisplayPrice());
		model.addAttribute("canonicalPlanMicroPriceInCents",
				CanonicalPlan.MICRO.getPriceInCents());

		model.addAttribute("canonicalPlanIdSmall", CanonicalPlan.SMALL.getId());
		model.addAttribute("canonicalPlanSmallNetworkBandwidth",
				CanonicalPlan.SMALL.getDisplayNetworkBandwidth());
		model.addAttribute("canonicalPlanSmallStorage",
				CanonicalPlan.SMALL.getDisplayStorage());
		model.addAttribute("canonicalPlanSmallPrice",
				CanonicalPlan.SMALL.getDisplayPrice());
		model.addAttribute("canonicalPlanSmallPriceInCents",
				CanonicalPlan.SMALL.getPriceInCents());

		model.addAttribute("canonicalPlanIdMedium",
				CanonicalPlan.MEDIUM.getId());
		model.addAttribute("canonicalPlanMediumNetworkBandwidth",
				CanonicalPlan.MEDIUM.getDisplayNetworkBandwidth());
		model.addAttribute("canonicalPlanMediumStorage",
				CanonicalPlan.MEDIUM.getDisplayStorage());
		model.addAttribute("canonicalPlanMediumPrice",
				CanonicalPlan.MEDIUM.getDisplayPrice());
		model.addAttribute("canonicalPlanMediumPriceInCents",
				CanonicalPlan.MEDIUM.getPriceInCents());

		model.addAttribute("canonicalPlanIdLarge", CanonicalPlan.LARGE.getId());
		model.addAttribute("canonicalPlanLargeNetworkBandwidth",
				CanonicalPlan.LARGE.getDisplayNetworkBandwidth());
		model.addAttribute("canonicalPlanLargeStorage",
				CanonicalPlan.LARGE.getDisplayStorage());
		model.addAttribute("canonicalPlanLargePrice",
				CanonicalPlan.LARGE.getDisplayPrice());
		model.addAttribute("canonicalPlanLargePriceInCents",
				CanonicalPlan.LARGE.getPriceInCents());
	}

	@RequestMapping(value = "/plans", method = RequestMethod.GET)
	public ModelAndView displayPlansAndPricingUnsecured(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering");
		try {
			setupPlanAndPricing(model);
			return new ModelAndView("settings_plans_and_pricing_no_login",
					model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	/**
	 * Secured uri
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/account/changepassword", method = RequestMethod.GET)
	public ModelAndView displayChangePassword(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering");
		try {
			model.addAttribute("canonicalPlanIdFree",
					CanonicalPlan.FREE.getId());
			model.addAttribute("canonicalPlanIdMicro",
					CanonicalPlan.MICRO.getId());
			model.addAttribute("canonicalPlanIdSmall",
					CanonicalPlan.SMALL.getId());
			model.addAttribute("canonicalPlanIdMedium",
					CanonicalPlan.MEDIUM.getId());
			model.addAttribute("canonicalPlanIdLarge",
					CanonicalPlan.LARGE.getId());
			User lUser = userService.getLoggedInUser();
			StripeCustomer lStripeCustomer = stripeCustomerService.get(lUser
					.getAccountId());
			boolean lIsUpdateCCInfo = false;

			if (lStripeCustomer == null) {
				model.addAttribute("isSubscribed", false);
				// default to free
				model.addAttribute("subscribedCanonicalPlanId",
						CanonicalPlan.FREE.getId());
			} else {
				// check to see if the user's CC is expiring or has expired
				if (Utils.isCCExpired(lStripeCustomer.getCardExpYear(),
						lStripeCustomer.getCardExpMonth())
						|| Utils.isCCExpiring(lStripeCustomer.getCardExpYear(),
								lStripeCustomer.getCardExpMonth())) {
					lIsUpdateCCInfo = true;
				}
				model.addAttribute("isSubscribed", true);
				model.addAttribute("subscribedCanonicalPlanId",
						lStripeCustomer.getCanonicalPlanId());
			}
			model.addAttribute("isUpdateCCInfo", lIsUpdateCCInfo);
			model.addAttribute("isError", false);
			return new ModelAndView("settings_change_password", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	/******** FORGOT PASSWORD ****************/
	/**
	 * unsecured uri
	 * 
	 * @param pForgotPasswordRequest
	 * @param response
	 */
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void processForgotPasswordRequest(
			@RequestBody ForgotPasswordRequest pForgotPasswordRequest,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering processForgotPasswordRequest");
			// check the email address for existence
			com.cm.usermanagement.user.User lUser = userService
					.getUserByUserName(pForgotPasswordRequest.getEmail());
			if (lUser != null
					&& lUser.getUsername().equals(
							pForgotPasswordRequest.getEmail())) {
				// convert to domain object
				com.cm.usermanagement.user.ForgotPasswordRequest lForgotPasswordRequest = new com.cm.usermanagement.user.ForgotPasswordRequest();
				// generate the guid
				String lGuid = UUID.randomUUID().toString();
				lForgotPasswordRequest.setGuid(lGuid);

				lForgotPasswordRequest.setEmail(lUser.getEmail());
				lForgotPasswordRequest.setAccountId(lUser.getAccountId());
				lForgotPasswordRequest.setUsername(lUser.getUsername());

				// standardize to utc
				lForgotPasswordRequest.setTimeCreatedMs(System
						.currentTimeMillis());
				lForgotPasswordRequest
						.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
								.getDefault().getRawOffset());
				// save the request
				userService.save(lForgotPasswordRequest);

				// send out the email
				Utils.triggerForgotPasswordEmailMessage(lGuid, 0);
			} else {
				LOGGER.log(Level.WARNING, "The user does not exist");
			}
			// always
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting processForgotPasswordRequest");
		}

	}

	@RequestMapping(value = "/tasks/email/sendforgotpasswordemail/{guid}", method = RequestMethod.POST)
	public void sendForgotPasswordEmail(@PathVariable String guid,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering sendForgotPasswordEmail");
			// validate the guid
			com.cm.usermanagement.user.ForgotPasswordRequest lRequest = userService
					.get(guid);

			String lEmailMessage = forgotPasswordEmailBuilder.build(guid);

			StringBuilder htmlBody = new StringBuilder();
			htmlBody.append(lEmailMessage);
			try {
				Utils.sendEmail(Configuration.FROM_EMAIL_ADDRESS,
						Configuration.FROM_NAME, lRequest.getEmail(), "",
						Configuration.SITE_NAME, htmlBody.toString(),
						TEXT_HTML_CHARSET_UTF_8);
			} catch (UnsupportedEncodingException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			} catch (MessagingException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}

			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting sendForgotPasswordEmail");

		}
	}

	/**
	 * unsecured uri
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/forgotpassword/{guid}", method = RequestMethod.GET)
	public ModelAndView displayChangePassword(ModelMap model,
			@PathVariable String guid, HttpServletRequest request,
			HttpServletResponse response) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displaySignup");
		try {
			// validate the guid
			com.cm.usermanagement.user.ForgotPasswordRequest lRequest = userService
					.get(guid);
			if (lRequest != null) {
				// check the validity of the request period
				long lTimeNow = System.currentTimeMillis();
				// calculate 24hrs from request creation
				if (lTimeNow < lRequest.getTimeCreatedMs()
						+ (24 * 60 * 60 * 1000)) {

					// pass the guid to the jsp
					model.addAttribute("guid", guid);
					model.addAttribute("isRequestExpired", false);
					return new ModelAndView("change_password", model);
				}
			}

			// For better UX, all other requests are defaulted to expired
			model.addAttribute("isRequestExpired", true);
			return new ModelAndView("change_password", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displaySignup");
		}
	}

	/**
	 * unsecured uri
	 * 
	 * @param pForgotPasswordRequest
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody List<ValidationError> processForgotPassword(
			@RequestBody ForgotPasswordRequest pForgotPasswordRequest,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering processForgotPassword");
			List<ValidationError> errors = new ArrayList<ValidationError>();

			// validate the guid
			com.cm.usermanagement.user.ForgotPasswordRequest lRequest = userService
					.get(pForgotPasswordRequest.getGuid());
			if (lRequest != null) {
				// check the validity of the request period
				long lTimeNow = System.currentTimeMillis();
				// calculate 24hrs from request creation
				if (lRequest.getTimeCreatedMs() > lTimeNow
						+ (24 * 60 * 60 * 1000)) {
					ValidationError error = new ValidationError();
					error.setCode("password");
					error.setDescription("Change password request has expired");
					errors.add(error);
					LOGGER.log(Level.WARNING,
							"Change password request has expired");
				}
			}
			if ((!Utils.isEmpty(pForgotPasswordRequest.getPassword()))
					&& (!pForgotPasswordRequest.getPassword().equals(
							pForgotPasswordRequest.getPassword2()))) {
				ValidationError error = new ValidationError();
				error.setCode("password");
				error.setDescription("Passwords do not match");
				errors.add(error);
				LOGGER.log(Level.WARNING, "Passwords do not match");
			}

			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				// get email from the forgot password request
				com.cm.usermanagement.user.User lUser = userService
						.getUserByUserName(lRequest.getEmail());

				lUser.setPassword(new BCryptPasswordEncoder()
						.encode(pForgotPasswordRequest.getPassword()));
				// standardize to utc
				lUser.setTimeUpdatedMs(System.currentTimeMillis());
				lUser.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
				userService.updateUser(lUser);

				// update the request
				long lCurrentTimeMs = System.currentTimeMillis();
				lRequest.setTimeUpdatedMs(lCurrentTimeMs);
				lRequest.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
				userService.update(lRequest);

				response.setStatus(HttpServletResponse.SC_OK);
				return null;

			}
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting processForgotPassword");
		}

	}

	/******** FORGOT PASSWORD ****************/
	/******** CHANGE PASSWORD ****************/

	@RequestMapping(value = "/secured/changepassword", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody List<ValidationError> doChangePassword(
			@RequestBody PasswordChangeRequest passwordChangeRequest,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doChangePassword");
			// get the logged in user
			com.cm.usermanagement.user.User lLoggedInUser = userService
					.getLoggedInUser();

			List<ValidationError> errors = validateOnPasswordChange(
					passwordChangeRequest, lLoggedInUser);
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				// convert to domain object
				com.cm.usermanagement.user.PasswordChangeRequest lRequest = new com.cm.usermanagement.user.PasswordChangeRequest();
				lRequest.setAccountId(lLoggedInUser.getAccountId());
				lRequest.setUsername(lLoggedInUser.getUsername());

				long lCurrentTimeMs = System.currentTimeMillis();
				lRequest.setTimeCreatedMs(lCurrentTimeMs);
				lRequest.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
				lRequest.setTimeUpdatedMs(lCurrentTimeMs);
				lRequest.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());

				// save the request
				userService.save(lRequest);

				lLoggedInUser.setPassword(new BCryptPasswordEncoder()
						.encode(passwordChangeRequest.getPassword()));
				lLoggedInUser.setTimeUpdatedMs(System.currentTimeMillis());
				lLoggedInUser.setTimeUpdatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
				userService.updateUser(lLoggedInUser);
				response.setStatus(HttpServletResponse.SC_OK);
				return null;

			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doChangePassword");
		}

	}

	/******** CHANGE PASSWORD ****************/

	/**
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/secured/loggedinuser", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody com.cm.usermanagement.user.transfer.User getLoggedInUser(
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getUser");
			com.cm.usermanagement.user.User lUser = userService
					.getLoggedInUser();
			if (lUser == null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No User Found!");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
			// send only the minimally required information
			com.cm.usermanagement.user.transfer.User lUserTfr = new com.cm.usermanagement.user.transfer.User();
			lUserTfr.setId(lUser.getId());

			response.setStatus(HttpServletResponse.SC_OK);
			return lUserTfr;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getUsers");
		}
	}

	private List<com.cm.usermanagement.user.transfer.User> convert(
			List<com.cm.usermanagement.user.User> users) {
		List<com.cm.usermanagement.user.transfer.User> userAccounts = new ArrayList<com.cm.usermanagement.user.transfer.User>();
		for (com.cm.usermanagement.user.User user : users) {
			com.cm.usermanagement.user.transfer.User userAccount = new com.cm.usermanagement.user.transfer.User();
			userAccount.setId(user.getId());
			userAccount.setEmail(user.getEmail());
			userAccount.setEnabled(user.isEnabled());
			if (user.getRole() != null) {
				if (user.getRole().equals(
						com.cm.usermanagement.user.User.ROLE_ADMIN))
					userAccount.setRole("admin");
				if (user.getRole().equals(
						com.cm.usermanagement.user.User.ROLE_USER))
					userAccount.setRole("user");
			}
			userAccount.setAccountId(user.getAccountId());
			userAccount.setFirstName(user.getFirstName());
			userAccount.setLastName(user.getLastName());
			userAccount.setUserName(user.getUsername());
			userAccounts.add(userAccount);
		}
		return userAccounts;
	}

	private com.cm.usermanagement.user.transfer.User convert(
			com.cm.usermanagement.user.User user) {
		com.cm.usermanagement.user.transfer.User userAccount = new com.cm.usermanagement.user.transfer.User();
		userAccount.setId(user.getId());
		userAccount.setEmail(user.getEmail());
		userAccount.setEnabled(user.isEnabled());
		if (user.getRole() != null) {
			if (user.getRole().equals(
					com.cm.usermanagement.user.User.ROLE_ADMIN))
				userAccount.setRole("admin");
			if (user.getRole()
					.equals(com.cm.usermanagement.user.User.ROLE_USER))
				userAccount.setRole("user");
		}
		userAccount.setAccountId(user.getAccountId());
		userAccount.setFirstName(user.getFirstName());
		userAccount.setLastName(user.getLastName());
		userAccount.setUserName(user.getUsername());
		return userAccount;
	}

	private List<ValidationError> validateOnPasswordChange(
			PasswordChangeRequest pPasswordChangeRequest,
			com.cm.usermanagement.user.User pLoggedInUser) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering validate");
		List<ValidationError> errors = new ArrayList<ValidationError>();
		boolean doExistingPasswordsMatch = new BCryptPasswordEncoder().matches(
				pPasswordChangeRequest.getOldPassword(),
				pLoggedInUser.getPassword());

		if (pLoggedInUser.getId() != pPasswordChangeRequest.getUserId()) {
			ValidationError error = new ValidationError();
			error.setCode("id");
			error.setDescription("Your session has expired or invalid request");
			errors.add(error);
			LOGGER.log(Level.WARNING,
					"Your session has expired or invalid request");
		}
		if (!doExistingPasswordsMatch) {
			ValidationError error = new ValidationError();
			error.setCode("old_password");
			error.setDescription("Your old password is invalid");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Your old password is invalid");
		}
		if ((!Utils.isEmpty(pPasswordChangeRequest.getPassword()))
				&& (!pPasswordChangeRequest.getPassword().equals(
						pPasswordChangeRequest.getPassword2()))) {
			ValidationError error = new ValidationError();
			error.setCode("password");
			error.setDescription("Passwords do not match");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Passwords do not match");
		}
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting validate");
		return errors;
	}

	/*****************************************************************************/
	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/um", method = RequestMethod.GET)
	public ModelAndView doLogin(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering doLogin");
		try {
			return new ModelAndView("user_management", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doLogin");
		}
	}

	@RequestMapping(value = "/secured/loggedinuser", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody List<ValidationError> doUpdateLoggedInUser(
			@RequestBody com.cm.usermanagement.user.transfer.User userAccount,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doUpdateUser");
			List<ValidationError> errors = validateOnUpdateLoggedInUser(userAccount);
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				com.cm.usermanagement.user.User lUser = new com.cm.usermanagement.user.User();
				lUser.setId(userAccount.getId());
				lUser.setUsername(userAccount.getUserName());
				lUser.setEmail(userAccount.getEmail());
				lUser.setFirstName(userAccount.getFirstName());
				lUser.setLastName(userAccount.getLastName());
				lUser.setPassword(new BCryptPasswordEncoder()
						.encode(userAccount.getPassword()));
				lUser.setTimeUpdatedMs(userAccount.getTimeUpdatedMs());
				lUser.setTimeUpdatedTimeZoneOffsetMs(userAccount
						.getTimeUpdatedTimeZoneOffsetMs());

				userService.updateUser(lUser);
				response.setStatus(HttpServletResponse.SC_OK);
				return null;
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doUpdateUser");
		}

	}

	/**
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/um/users", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<com.cm.usermanagement.user.transfer.User> getUsers(
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getUsers");
			List<com.cm.usermanagement.user.User> users = userService
					.getUsers();
			if (users != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info(users.size() + " users found");
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Users Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return convert(users);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getUsers");
		}
	}

	/**
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/um/user/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody com.cm.usermanagement.user.transfer.User getUser(
			@PathVariable Long id, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getUser");
			com.cm.usermanagement.user.User user = userService.getUser(id);
			if (user == null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No User Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return convert(user);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getUsers");
		}
	}

	@RequestMapping(value = "/um/user", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody List<ValidationError> doCreateUser(
			@RequestBody com.cm.usermanagement.user.transfer.User user,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doCreateAccount");
			List<ValidationError> errors = validateOnCreate(user);
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				com.cm.usermanagement.user.User lUser = new com.cm.usermanagement.user.User();
				lUser.setEmail(user.getEmail());
				lUser.setFirstName(user.getFirstName());
				lUser.setLastName(user.getLastName());
				// default to true
				lUser.setEnabled(true);
				if (user.getRole().equals("admin")) {
					lUser.setRole(com.cm.usermanagement.user.User.ROLE_ADMIN);
					// use the associated account id. only super admins can
					// create an admin user.
					lUser.setAccountId(user.getAccountId());
				}
				if (user.getRole().equals("user")) {
					lUser.setRole(com.cm.usermanagement.user.User.ROLE_USER);
					/**
					 * For a user role, tie the account id to the logged in
					 * admin account, as only admins can create a user
					 */
					Long lAccountId = userService.getLoggedInUser()
							.getAccountId();
					lUser.setAccountId(lAccountId);
				}

				lUser.setUsername(user.getUserName());
				lUser.setPassword(new StandardPasswordEncoder().encode(user
						.getPassword()));

				lUser.setTimeCreatedMs(user.getTimeCreatedMs());
				lUser.setTimeCreatedTimeZoneOffsetMs(user
						.getTimeCreatedTimeZoneOffsetMs());
				lUser.setTimeUpdatedMs(user.getTimeUpdatedMs());
				lUser.setTimeUpdatedTimeZoneOffsetMs(user
						.getTimeUpdatedTimeZoneOffsetMs());

				userService.saveUser(lUser);
				response.setStatus(HttpServletResponse.SC_CREATED);
				return null;
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doCreateAccount");
		}

	}

	@RequestMapping(value = "/um/user", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody List<ValidationError> doUpdateUser(
			@RequestBody com.cm.usermanagement.user.transfer.User user,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doUpdateUser");
			List<ValidationError> errors = validateOnUpdate(user);
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				com.cm.usermanagement.user.User lUser = new com.cm.usermanagement.user.User();
				lUser.setId(user.getId());
				lUser.setEmail(user.getEmail());
				lUser.setFirstName(user.getFirstName());
				lUser.setLastName(user.getLastName());
				lUser.setEnabled(user.getEnabled());
				lUser.setUsername(user.getUserName());
				lUser.setPassword(new StandardPasswordEncoder().encode(user
						.getPassword()));
				if (user.getRole().equals("admin")) {
					lUser.setRole(com.cm.usermanagement.user.User.ROLE_ADMIN);
					// use the associated account id. only super admins can
					// create an admin user.
					lUser.setAccountId(user.getAccountId());
				}
				if (user.getRole().equals("user")) {
					lUser.setRole(com.cm.usermanagement.user.User.ROLE_USER);
					/**
					 * For a user role, tie the account id to the logged in
					 * admin account, as only admins can create a user
					 */
					Long lAccountId = userService.getLoggedInUser()
							.getAccountId();
					lUser.setAccountId(lAccountId);
				}
				lUser.setTimeUpdatedMs(user.getTimeUpdatedMs());
				lUser.setTimeUpdatedTimeZoneOffsetMs(user
						.getTimeUpdatedTimeZoneOffsetMs());

				userService.updateUser(lUser);
				response.setStatus(HttpServletResponse.SC_CREATED);
				return null;
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doUpdateUser");
		}

	}

	private List<ValidationError> validateOnCreate(
			com.cm.usermanagement.user.transfer.User userAccount) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering validate");
		List<ValidationError> errors = new ArrayList<ValidationError>();
		// Verify user info submission
		// if (Util.isEmpty(userAccount.getFirstName())) {
		// ValidationError error = new ValidationError();
		// error.setCode("firstName");
		// error.setDescription("First Name cannot be blank");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "First Name cannot be blank");
		// }
		// if (Util.isEmpty(userAccount.getLastName())) {
		// ValidationError error = new ValidationError();
		// error.setCode("lastName");
		// error.setDescription("Last Name cannot be blank");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "Last Name cannot be blank");
		// }
		// if (Util.isEmpty(userAccount.getEmail())) {
		// ValidationError error = new ValidationError();
		// error.setCode("email");
		// error.setDescription("Email cannot be blank");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "Email cannot be blank");
		// }
		if (Utils.isEmpty(userAccount.getUserName())) {
			ValidationError error = new ValidationError();
			error.setCode("username");
			error.setDescription("Username cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Username cannot be blank");
		}
		if (Utils.isEmpty(userAccount.getPassword())) {
			ValidationError error = new ValidationError();
			error.setCode("password");
			error.setDescription("Password cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Password cannot be blank");
		}
		// if (!userAccount.getPassword().equals(userAccount.getPassword2())) {
		// ValidationError error = new ValidationError();
		// error.setCode("password");
		// error.setDescription("Passwords do not match");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "Passwords do not match");
		// }
		// check to make sure we don't have a user account already
		com.cm.usermanagement.user.User user = userService
				.getUserByUserName(userAccount.getUserName());
		if (user != null) {
			ValidationError error = new ValidationError();
			error.setCode("username");
			error.setDescription("The user already exists");
			errors.add(error);
			LOGGER.log(Level.WARNING, "The user already exists");
		}
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting validate");
		return errors;
	}

	private List<ValidationError> validateOnUpdate(
			com.cm.usermanagement.user.transfer.User userAccount) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering validate");
		List<ValidationError> errors = new ArrayList<ValidationError>();
		// Verify user info submission
		// if (Util.isEmpty(userAccount.getFirstName())) {
		// ValidationError error = new ValidationError();
		// error.setCode("firstName");
		// error.setDescription("First Name cannot be blank");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "First Name cannot be blank");
		// }
		// if (Util.isEmpty(userAccount.getLastName())) {
		// ValidationError error = new ValidationError();
		// error.setCode("lastName");
		// error.setDescription("Last Name cannot be blank");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "Last Name cannot be blank");
		// }
		// if (Util.isEmpty(userAccount.getEmail())) {
		// ValidationError error = new ValidationError();
		// error.setCode("email");
		// error.setDescription("Email cannot be blank");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "Email cannot be blank");
		// }
		if (Utils.isEmpty(userAccount.getUserName())) {
			ValidationError error = new ValidationError();
			error.setCode("userName");
			error.setDescription("Username cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Username cannot be blank");
		}
		if (Utils.isEmpty(userAccount.getPassword())) {
			ValidationError error = new ValidationError();
			error.setCode("password");
			error.setDescription("Password cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Password cannot be blank");
		}
		// if ((!Util.isEmpty(userAccount.getPassword()))
		// && (!userAccount.getPassword().equals(
		// userAccount.getPassword2()))) {
		// ValidationError error = new ValidationError();
		// error.setCode("password");
		// error.setDescription("Passwords do not match");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "Passwords do not match");
		// }
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting validate");
		return errors;
	}

	private List<ValidationError> validateOnUpdateLoggedInUser(
			com.cm.usermanagement.user.transfer.User userAccount) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering validate");
		List<ValidationError> errors = new ArrayList<ValidationError>();
		// Verify user info submission
		if (Utils.isEmpty(userAccount.getFirstName())) {
			ValidationError error = new ValidationError();
			error.setCode("firstName");
			error.setDescription("First Name cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "First Name cannot be blank");
		}
		if (Utils.isEmpty(userAccount.getLastName())) {
			ValidationError error = new ValidationError();
			error.setCode("lastName");
			error.setDescription("Last Name cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Last Name cannot be blank");
		}
		if (Utils.isEmpty(userAccount.getEmail())) {
			ValidationError error = new ValidationError();
			error.setCode("email");
			error.setDescription("Email cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Email cannot be blank");
		}
		if ((!Utils.isEmpty(userAccount.getPassword()))
				&& (!userAccount.getPassword().equals(
						userAccount.getPassword2()))) {
			ValidationError error = new ValidationError();
			error.setCode("password");
			error.setDescription("Passwords do not match");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Passwords do not match");
		}
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting validate");
		return errors;
	}

}
