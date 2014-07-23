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

package com.cm.eventtracker;

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
public class EventTrackerController {
	@Autowired
	private EventTrackerService eventTrackerService;

	private static final Logger LOGGER = Logger
			.getLogger(EventTrackerController.class.getName());

	@RequestMapping(value = "/eventtracker/events", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	Result processTrackingEvents(@RequestBody TrackingEvents pTrackingEvents,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering processTrackingEvents");
			if (pTrackingEvents == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				LOGGER.warning("No Tracking Events Found!");
				return null;
			}

			response.setStatus(HttpServletResponse.SC_OK);

			Result result = new Result();
			try {

				eventTrackerService.saveTrackingEvents(pTrackingEvents
						.getTrackingEvents());
				result.setResult(Result.SUCCESS);

			} catch (Exception e) {
				result.setResult(Result.FAILURE);
			}

			return result;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting processTrackingEvents");
		}
	}

}
