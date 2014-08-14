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

package com.cm.admin;

import java.util.Iterator;
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
import com.cm.admin.plan.CanonicalPlanName;
import com.cm.admin.plan.Plan;
import com.cm.common.entity.Result;
import com.cm.usermanagement.user.User;
import com.cm.usermanagement.user.UserService;
import com.cm.util.PMF;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@Controller
public class AdminController {

	private static final Logger LOGGER = Logger.getLogger(AdminController.class
			.getName());

	@Autowired
	private UserService userService;
	@Autowired
	private com.cm.accountmanagement.account.AccountService accountService;
	@Autowired
	private com.cm.admin.plan.PlanDao planDao;

	@RequestMapping(value = "/tasks/deleteusers", method = RequestMethod.GET, produces = "application/json")
	public void deleteUsers(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteUsers");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(User.class);
				q.deletePersistentAll();
				response.setStatus(HttpServletResponse.SC_OK);
			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteUsers");
		}
	}

	@RequestMapping(value = "/tasks/deleteblobstore", method = RequestMethod.GET, produces = "application/json")
	public void deleteBlobStore(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteBlobStore");
			BlobstoreService blobstoreService = BlobstoreServiceFactory
					.getBlobstoreService();
			Iterator<BlobInfo> iterator = new BlobInfoFactory()
					.queryBlobInfos();

			while (iterator.hasNext()) {

				blobstoreService.delete(iterator.next().getBlobKey());

			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteBlobStore");
		}
	}

	@RequestMapping(value = "/tasks/createsu", method = RequestMethod.GET, produces = "application/json")
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
				lUser.setEmail("su@coconutmartini.com");
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

	@RequestMapping(value = "/tasks/createplans", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result createPlans(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering createPlans");
			long lTime = System.currentTimeMillis();
			long lTimezoneOffset = (long) TimeZone.getDefault()
					.getOffset(lTime);
			CanonicalPlanName[] lCanonicalPlanNames = CanonicalPlanName
					.values();
			for (int i = 0; i < lCanonicalPlanNames.length; i++) {
				Plan lPlan = new Plan();
				lPlan.setCanonicalPlanName(lCanonicalPlanNames[i].getValue());
				lPlan.setCurrency("usd");
				long lAmountInCents = 0;
				if (lCanonicalPlanNames[i].equals(CanonicalPlanName.FREE))
					lAmountInCents = 0;
				else if (lCanonicalPlanNames[i].equals(CanonicalPlanName.LARGE))
					lAmountInCents = 5000;
				else if (lCanonicalPlanNames[i]
						.equals(CanonicalPlanName.MEDIUM))
					lAmountInCents = 2500;
				else if (lCanonicalPlanNames[i].equals(CanonicalPlanName.MICRO))
					lAmountInCents = 1500;
				else if (lCanonicalPlanNames[i].equals(CanonicalPlanName.SMALL))
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
}
