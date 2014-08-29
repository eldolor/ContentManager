package com.cm.usermanagement.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cm.config.CanonicalApplicationQuota;
import com.cm.config.CanonicalContentType;
import com.cm.config.CanonicalPlanName;
import com.cm.config.CanonicalStorageQuota;
import com.cm.config.Configuration;
import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.application.ApplicationService;
import com.cm.contentmanager.content.Content;
import com.cm.contentmanager.content.ContentService;
import com.cm.contentmanager.contentgroup.ContentGroup;
import com.cm.contentmanager.contentgroup.ContentGroupService;
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

	private static final Logger LOGGER = Logger
			.getLogger(UserManagementController.class.getName());

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
	public @ResponseBody
	List<ValidationError> doSignup(
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
				ContentGroup lContentGroup = createDemoContentGroup(
						lDomainUser, lApplication);
				createDemoContent(lDomainUser, lApplication, lContentGroup);

				// assign them the free quota
				Quota lQuota = new Quota();
				lQuota.setAccountId(lUser.getAccountId());
				// default to free
				lQuota.setCanonicalPlanName(CanonicalPlanName.FREE.getValue());
				lQuota.setStorageLimitInBytes(CanonicalStorageQuota.FREE
						.getValue());
				lQuota.setApplicationLimit(CanonicalApplicationQuota.FREE
						.getValue());

				lQuota.setTimeCreatedMs(System.currentTimeMillis());
				lQuota.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getRawOffset());
				quotaService.create(lQuota);

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

	private Application createDemoApplication(User pUser) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering createDemoApplication");
			Application lApplication = new Application();
			lApplication.setAccountId(pUser.getAccountId());
			lApplication
					.setDescription("We've created a demo application for illustrative purposes only. The application contains a demo content group, and a few Image and Video type contents. The demo application is enabled by default, and is configured such that the users can only download the contents (images & videos) over a Wi-Fi network, and not over a Cellular Network. This helps conserve the cellular data usage.");
			lApplication.setEnabled(true);
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

	private ContentGroup createDemoContentGroup(User pUser,
			Application pApplication) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering createDemoContentGroup");
			ContentGroup lContentGroup = new ContentGroup();
			lContentGroup.setAccountId(pUser.getAccountId());
			lContentGroup
					.setDescription("We've created a demo application for illustrative purposes only. The application contains a demo content group, and a few Image and Video type contents. The demo application is enabled by default, and is configured such that the users can only download the contents (images & videos) over a Wi-Fi network, and not over a Cellular Network. This helps conserve the cellular data usage.");
			lContentGroup.setEnabled(true);
			lContentGroup.setName("Demo Content Group");
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
			return lContentGroup;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting createDemoContentGroup");
		}

	}

	private void createDemoContent(User pUser, Application pApplication,
			ContentGroup pContentGroup) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering createDemoContent");
			{
				Content lContent = new Content();
				lContent.setAccountId(pUser.getAccountId());
				lContent.setDescription("We've created a demo application for illustrative purposes only. The application contains a demo content group, and a few Image and Video type contents. The demo application is enabled by default, and is configured such that the users can only download the contents (images & videos) over a Wi-Fi network, and not over a Cellular Network. This helps conserve the cellular data usage. Please upload any image of your choice.");
				lContent.setEnabled(true);
				lContent.setName("Demo Image - with no attached image");
				long lTime = System.currentTimeMillis();
				lContent.setTimeCreatedMs(lTime);
				lContent.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getOffset(lTime));
				lContent.setUserId(pUser.getId());
				lContent.setApplicationId(pApplication.getId());
				lContent.setContentGroupId(pContentGroup.getId());
				lContent.setType(CanonicalContentType.IMAGE.getValue());

				DateTime lDt = new DateTime();
				DateTimeFormatter lFmt = ISODateTimeFormat.dateTime();
				String lStartDateIso8601 = lFmt.print(lDt);
				lContent.setStartDateIso8601(lStartDateIso8601);

				// set high date
				lContent.setEndDateMs(Long.MAX_VALUE);

				lContent = contentService.save(pUser, lContent);
			}

			{
				Content lContent = new Content();
				lContent.setAccountId(pUser.getAccountId());
				lContent.setDescription("We've created a demo application for illustrative purposes only. The application contains a demo content group, and a few Image and Video type contents. The demo application is enabled by default, and is configured such that the users can only download the contents (images & videos) over a Wi-Fi network, and not over a Cellular Network. This helps conserve the cellular data usage. Please upload any video of your choice.");
				lContent.setEnabled(true);
				lContent.setName("Demo Video - with no attached video");
				long lTime = System.currentTimeMillis();
				lContent.setTimeCreatedMs(lTime);
				lContent.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getDefault().getOffset(lTime));
				lContent.setUserId(pUser.getId());
				lContent.setApplicationId(pApplication.getId());
				lContent.setContentGroupId(pContentGroup.getId());
				lContent.setType(CanonicalContentType.VIDEO.getValue());

				DateTime lDt = new DateTime();
				DateTimeFormatter lFmt = ISODateTimeFormat.dateTime();
				String lStartDateIso8601 = lFmt.print(lDt);
				lContent.setStartDateIso8601(lStartDateIso8601);

				// set high date
				lContent.setEndDateMs(Long.MAX_VALUE);

				lContent = contentService.save(pUser, lContent);
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting createDemoContentGroup");
		}

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

			String lTrackingId = "AI_" + lAccountId + "_"
					+ (lApplications.size() + 1); // the collection will have
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
			model.addAttribute("canonicalPlanNameFree",
					CanonicalPlanName.FREE.getValue());
			model.addAttribute("canonicalPlanNameMicro",
					CanonicalPlanName.MICRO.getValue());
			model.addAttribute("canonicalPlanNameSmall",
					CanonicalPlanName.SMALL.getValue());
			model.addAttribute("canonicalPlanNameMedium",
					CanonicalPlanName.MEDIUM.getValue());
			model.addAttribute("canonicalPlanNameLarge",
					CanonicalPlanName.LARGE.getValue());
			User lUser = userService.getLoggedInUser();
			StripeCustomer lStripeCustomer = stripeCustomerService.get(lUser
					.getAccountId());
			boolean lIsUpdateCCInfo = false;

			if (lStripeCustomer == null) {
				model.addAttribute("isSubscribed", false);
				// default to free
				model.addAttribute("subscribedCanonicalPlanName",
						CanonicalPlanName.FREE.getValue());
			} else {
				// check to see if the user's CC is expiring or has expired
				if (Utils.isCCExpired(lStripeCustomer.getCardExpirationYear(),
						lStripeCustomer.getCardExpirationMonth())
						|| Utils.isCCExpiring(
								lStripeCustomer.getCardExpirationYear(),
								lStripeCustomer.getCardExpirationMonth())) {
					lIsUpdateCCInfo = true;
				}
				model.addAttribute("isSubscribed", true);
				model.addAttribute("subscribedCanonicalPlanName",
						lStripeCustomer.getCanonicalPlanName());
			}
			model.addAttribute("isUpdateCCInfo", lIsUpdateCCInfo);
			model.addAttribute("isError", false);
			return new ModelAndView("account_settings", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displayAccountSettings");
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
				Utils.sendEmail(
						Configuration.FORGOT_PASSWORD_FROM_EMAIL_ADDRESS,
						Configuration.FORGOT_PASSWORD_FROM_NAME,
						lRequest.getEmail(), "", Configuration.SITE_NAME,
						htmlBody.toString(), null);
			} catch (UnsupportedEncodingException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			} catch (MessagingException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}

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
	public @ResponseBody
	List<ValidationError> processForgotPassword(
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
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting processForgotPassword");
		}

	}

	/******** FORGOT PASSWORD ****************/
	/******** CHANGE PASSWORD ****************/

	@RequestMapping(value = "/secured/changepassword", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doChangePassword(
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
	public @ResponseBody
	com.cm.usermanagement.user.transfer.User getLoggedInUser(
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
	public @ResponseBody
	List<ValidationError> doUpdateLoggedInUser(
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
	public @ResponseBody
	List<com.cm.usermanagement.user.transfer.User> getUsers(
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
	public @ResponseBody
	com.cm.usermanagement.user.transfer.User getUser(@PathVariable Long id,
			HttpServletResponse response) {
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
	public @ResponseBody
	List<ValidationError> doCreateUser(
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
	public @ResponseBody
	List<ValidationError> doUpdateUser(
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
