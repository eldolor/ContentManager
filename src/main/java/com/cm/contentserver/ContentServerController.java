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

package com.cm.contentserver;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.contentmanager.content.Content;

@Controller
public class ContentServerController {
	@Autowired
	private ContentServerService contentServerService;

	private static final Logger LOGGER = Logger
			.getLogger(ContentServerController.class.getName());

	/**
	 * @param adGroupUuid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentserver/contentlist", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<com.cm.contentserver.transfer.Content> getContent(
			@RequestBody com.cm.contentserver.transfer.ContentRequest pContentRequest,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContent");
			if (pContentRequest == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				LOGGER.warning("No Content Request Found!");
				return null;
			}

			List<com.cm.contentserver.transfer.Content> contentList = convertToTransferFormat(contentServerService
					.getContent(convertToDomainFormat(pContentRequest)));
			if (contentList != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info(contentList.size() + " Content found");
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return contentList;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContent");
		}
	}

	@RequestMapping(value = "/contentserver/contentlist/{trackingId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<com.cm.contentserver.transfer.Content> getContentTest(
			@PathVariable String trackingId, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getContent");
			if (trackingId == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				LOGGER.warning("No Content Request Found!");
				return null;
			}
			ContentRequest lContentRequest = new ContentRequest();
			lContentRequest.setApplicationId(trackingId);
			List<com.cm.contentserver.transfer.Content> contentList = convertToTransferFormat(contentServerService
					.getContent(lContentRequest));
			if (contentList != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info(contentList.size() + " Content found");
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			// TODO: convert to transfer format and remove any
			// unnecessary/sensitive fields
			return contentList;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContent");
		}
	}

	private List<com.cm.contentserver.transfer.Content> convertToTransferFormat(
			List<Content> pContentList) {
		List<com.cm.contentserver.transfer.Content> lContentListTransferFormat = new ArrayList<com.cm.contentserver.transfer.Content>();
		for (Content lContent : pContentList) {
			lContentListTransferFormat.add(convertToTransferFormat(lContent));
		}
		return lContentListTransferFormat;
	}

	private com.cm.contentserver.transfer.Content convertToTransferFormat(
			Content pContent) {
		com.cm.contentserver.transfer.Content lContent = new com.cm.contentserver.transfer.Content();
		lContent.setApplicationId(pContent.getApplicationId());
		lContent.setId(pContent.getId());
		lContent.setName(pContent.getName());
		lContent.setTimeCreatedMs(pContent.getTimeCreatedMs());
		lContent.setTimeUpdatedMs(pContent.getTimeUpdatedMs());
		lContent.setType(pContent.getType());
		lContent.setUri(pContent.getUri());
		return lContent;

	}

	private ContentRequest convertToDomainFormat(
			com.cm.contentserver.transfer.ContentRequest pContentRequest) {
		ContentRequest lContentRequest = new ContentRequest();
		lContentRequest.setAccuracy(pContentRequest.getAccuracy());
		lContentRequest.setAltitude(pContentRequest.getAltitude());
		lContentRequest.setApplicationId(pContentRequest.getApplicationId());
		lContentRequest.setBearing(pContentRequest.getBearing());
		lContentRequest.setDeviceId(pContentRequest.getDeviceId());
		lContentRequest.setLatitude(pContentRequest.getLatitude());
		lContentRequest.setLongitude(pContentRequest.getLongitude());
		lContentRequest.setProvider(pContentRequest.getProvider());
		lContentRequest.setSpeed(pContentRequest.getSpeed());
		lContentRequest.setTimeMs(pContentRequest.getTimeMs());
		lContentRequest.setTimeZoneOffsetMs(pContentRequest
				.getTimeZoneOffsetMs());

		return lContentRequest;
	}

}
