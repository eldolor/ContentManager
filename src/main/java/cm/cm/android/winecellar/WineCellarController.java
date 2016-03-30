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

package cm.cm.android.winecellar;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import com.cm.config.Configuration;
//import com.cm.contentmanager.application.Application;
//import com.cm.contentmanager.application.ApplicationService;
//import com.cm.usermanagement.user.UserService;
//import com.cm.util.Utils;
//import com.cm.util.ValidationError;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

@Controller
public class WineCellarController {
	@Autowired
	private WineService wineService;

	private static final Logger LOGGER = Logger
			.getLogger(WineCellarController.class.getName());
	private BlobstoreService mBlobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	private final BlobInfoFactory mBlobInfoFactory = new BlobInfoFactory();
	private final GcsService mGcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());

	@RequestMapping(value = "/winecellar/wine", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void doCreateContent(@RequestBody Wine wine,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doCreateContent");

			Wine _wine = wineService.save(wine);
			response.setStatus(HttpServletResponse.SC_OK);

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doCreateContentGroup");
		}
	}

	@RequestMapping(value = "/winecellar/wine", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public void doUpdateContent(@RequestBody Wine wine,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doUpdateContent");

			Wine _wine = wineService.update(wine);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doUpdateContentGroup");
		}
	}

	@RequestMapping(value = "/winecellar/wine/{rowId}/{timeUpdatedMs}/{timeUpdatedTimeZoneOffsetMs}", method = RequestMethod.DELETE, produces = "application/json")
	public void deleteContent(@PathVariable Long rowId,
			@PathVariable Long timeUpdatedMs,
			@PathVariable Long timeUpdatedTimeZoneOffsetMs,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteContent");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Row ID: " + rowId);
			if (rowId == null || rowId.equals("")) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Id Found!");
			}
			wineService.delete(rowId, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);
			
			response.setStatus(HttpServletResponse.SC_OK);

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteContent");
		}
	}

}
