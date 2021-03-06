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

package com.cm.usermanagement.user;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cm.config.CanonicalCouponTypes;
import com.cm.contentmanager.content.ContentHelper;
import com.cm.quota.QuotaService;

@Controller
public class InviteController {
	@Autowired
	private UserService userService;
	@Autowired
	private ContentHelper contentHelper;
	@Autowired
	private QuotaService quotaService;

	private static final Logger LOGGER = Logger
			.getLogger(InviteController.class.getName());

	public InviteController() {
		super();
	}

	@RequestMapping(value = "/invite/{promoCode}", method = RequestMethod.GET)
	public ModelAndView invite(ModelMap model, @PathVariable String promoCode, HttpServletRequest request) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering");
		try {
			// validate the promo code
			Coupon lCoupon = userService.getCoupon(promoCode);
			if (lCoupon != null
					&& lCoupon.getType().equals(
							CanonicalCouponTypes.REFER_A_FRIEND.getValue())
					&& lCoupon.getRedeemByMs() >= System.currentTimeMillis()) {
				model.addAttribute("validatedPromoCode", promoCode);
			}
			model.addAttribute("ogUrl", request.getRequestURL());
			return new ModelAndView("invite_signup", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

}
