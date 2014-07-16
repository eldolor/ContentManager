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

package com.cm.contentmanager.auth.web.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cm.accountmanagement.account.entity.Account;
import com.cm.accountmanagement.account.service.AccountService;
import com.cm.usermanagement.user.entity.User;
import com.cm.usermanagement.user.service.UserService;

@Service
public class SpringAuthenticator implements UserDetailsService {
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;

	private static final Logger LOGGER = Logger
			.getLogger(SpringAuthenticator.class.getName());

	public SpringAuthenticator() throws Exception {
		super();
	}

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering loadUserByUsername");
		try {
			User lUser = userService.getUserByUserName(username);
			if (lUser == null) {
				LOGGER.log(Level.WARNING, "Unknown User Name: " + username);
				throw new UsernameNotFoundException("Unknown user: " + username);
			}
			if (!lUser.isEnabled()) {
				LOGGER.log(Level.WARNING, "User is DISABLED: " + username);
				throw new UsernameNotFoundException("User disabled: "
						+ username);
			}
			if (lUser.getAccountId() != null) {
				Account lAccount = accountService.getAccount(lUser
						.getAccountId());
				if (!lAccount.isEnabled()) {
					LOGGER.log(Level.WARNING, "Account is DISABLED: "
							+ username);
					throw new UsernameNotFoundException("Account disabled: "
							+ username);
				}
			}

			return lUser;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting loadUserByUsername");
		}
	}

}
