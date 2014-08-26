package com.cm.contentserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.application.ApplicationService;
import com.cm.contentmanager.content.Content;
import com.cm.contentmanager.content.ContentService;
import com.cm.contentmanager.contentgroup.ContentGroup;
import com.cm.contentmanager.contentgroup.ContentGroupService;
import com.cm.gcm.DeviceHasMultipleRegistrations;
import com.cm.gcm.DeviceNotRegisteredException;
import com.cm.gcm.GcmRegistrationRequest;
import com.cm.gcm.GcmService;
import com.cm.util.Utils;
import com.cm.util.ValidationError;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

@Controller
public class ContentServerController {
	@Autowired
	private ContentServerService contentServerService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ContentGroupService contentGroupService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private GcmService gcmService;

	private final BlobInfoFactory mBlobInfoFactory = new BlobInfoFactory();

	private Cache mCache;

	private static final Logger LOGGER = Logger
			.getLogger(ContentServerController.class.getName());

	public ContentServerController() {
		super();
		try {
			CacheFactory cacheFactory = CacheManager.getInstance()
					.getCacheFactory();
			mCache = cacheFactory.createCache(Collections.emptyMap());
		} catch (Throwable t) {
			LOGGER.log(Level.SEVERE, "Memcache not initialized!", t);
		}
	}

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
			ContentRequest lContentRequest = convertToDomainFormat(pContentRequest);
			List<com.cm.contentserver.transfer.Content> lContentList = Utils
					.convertToTransferFormat(contentServerService
							.getContent(lContentRequest));
			if (lContentList != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info(lContentList.size() + " Content found");
				// add an additional attribute to indicate wifi only download
				// status, which is being managed at the content level here,
				// instead of application level in the application
				if (contentServerService.isUpdateOverWifiOnly(lContentRequest)) {
					for (com.cm.contentserver.transfer.Content lContent : lContentList) {
						lContent.setUpdateOverWifiOnly(true);
					}
				}
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return lContentList;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getContent");
		}
	}

	@RequestMapping(value = "/contentserver/handshake", method = RequestMethod.POST, consumes = "application/json")
	public List<ValidationError> doHandshakePost(
			@RequestBody com.cm.contentserver.transfer.Handshake pHandshake,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doHandshake");
			// convert to domain format, and save/update
			Handshake lHandshake = convertToDomainFormat(pHandshake);
			{

				// device sends its stored gcm id
				GcmRegistrationRequest lGcmRegistrationRequest = gcmService
						.getGcmRegistrationRequest(pHandshake
								.getGcmRegistrationId());
				// validate GCM registration id
				List<ValidationError> lErrors = gcmService
						.evaluateGcmRegistrationStatus(lGcmRegistrationRequest);
				if (lErrors != null && (!lErrors.isEmpty())) {
					response.setStatus(HttpServletResponse.SC_CONFLICT);
					return lErrors;
				}

				contentServerService.upsert(lHandshake);
			}

			{
				Boolean lChangesStaged = null;

				try {
					if (mCache != null) {
						lChangesStaged = (Boolean) mCache.get(lHandshake
								.getTrackingId() + "changes_staged");
						if (LOGGER.isLoggable(Level.INFO))
							LOGGER.info("Memcache lookup: " + lChangesStaged);

					}
				} catch (Throwable t) {
					LOGGER.log(Level.SEVERE, "Unable to fetch from Memcache", t);
				}

				// changes have been staged. Do not auto-update the devices
				if (lChangesStaged != null && lChangesStaged == true) {
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Memcache lookup: Changes have been staged for "
								+ lHandshake.getTrackingId()
								+ ". Not auto-updating the devices");
					response.setStatus(HttpServletResponse.SC_OK);
					return null;
				} else {
					// double check by looking up the database as the value in
					// memcache might have been purged;
					Application lApplication = applicationService
							.getApplicationByTrackingId(
									lHandshake.getTrackingId(), true/**
							 * included
							 * deleted application, as that might have been the
							 * change
							 **/
							);
					if (lApplication != null) {
						if (lApplication.getChangesStaged()) {
							LOGGER.warning("DATABASE LOOKUP: Changes have been staged for "
									+ lHandshake.getTrackingId()
									+ ". Not auto-updating the devices");
							try {
								if (mCache != null) {
									mCache.put(lHandshake.getTrackingId()
											+ "changes_staged", true /**
									 * then
									 * update memcache
									 **/
									);
									if (LOGGER.isLoggable(Level.INFO))
										LOGGER.info("updating memcache");

								}
							} catch (Throwable t) {
								LOGGER.log(Level.SEVERE,
										"Unable to save in  Memcache", t);
							}
							// default
							response.setStatus(HttpServletResponse.SC_OK);
							return null;

						}
					}

				}
			}
			{

				long lLastKnownTimestamp = 0L;
				try {
					if (mCache != null) {
						lLastKnownTimestamp = (Long) mCache.get(lHandshake
								.getTrackingId());
						if (LOGGER.isLoggable(Level.INFO))
							LOGGER.info("Memcache lookup: "
									+ lLastKnownTimestamp);

					}
				} catch (Throwable t) {
					LOGGER.log(Level.SEVERE, "Unable to fetch from Memcache", t);
				}

				// not in cache; create and pin
				if (lLastKnownTimestamp == 0L) {
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Last known timestamp not found in Memcache. Triggering message to update...");
					Queue queue = QueueFactory.getQueue("contentqueue");
					TaskOptions taskOptions = TaskOptions.Builder
							.withUrl(
									"/tasks/contentserver/updatelastknowntimestamp/"
											+ lHandshake.getTrackingId())
							.param("trackingId", lHandshake.getTrackingId())
							.method(Method.POST);
					queue.add(taskOptions);

				}
				// send the GCM message to the device; greater than or equal to
				// in
				// case both values are zero (uninitialized)
				if (lLastKnownTimestamp >= lHandshake.getLastKnownTimestamp()) {
					Utils.triggerSendContentListMessages(lHandshake
							.getTrackingId());
				}
			}
			// always
			response.setStatus(HttpServletResponse.SC_OK);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doHandshake");
		}
	}

	/**
	 * Access to this API is unsecured
	 * 
	 * @param key
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contentserver/dropbox/{key}", method = RequestMethod.GET)
	public @ResponseBody
	String doServe(@PathVariable String key, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doServe");
			BlobKey blobKey = new BlobKey(key);
			final BlobInfo blobInfo = mBlobInfoFactory.loadBlobInfo(blobKey);
			if (blobInfo != null) {
				response.setHeader("Content-Disposition",
						"attachment; filename=" + blobInfo.getFilename());
				BlobstoreServiceFactory.getBlobstoreService().serve(blobKey,
						response);
			} else {
				LOGGER.info("No content found for key " + key);
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doServe");
		}
		return null;
	}

	@RequestMapping(value = "/contentserver/dropbox", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody
	String doServePost(@RequestParam String key, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doServePost");
			BlobKey blobKey = new BlobKey(key);
			final BlobInfo blobInfo = mBlobInfoFactory.loadBlobInfo(blobKey);
			if (blobInfo != null) {
				response.setHeader("Content-Disposition",
						"attachment; filename=" + blobInfo.getFilename());
				BlobstoreServiceFactory.getBlobstoreService().serve(blobKey,
						response);
			} else {
				LOGGER.info("No content found for key " + key);
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doServePost");
		}
		return null;
	}

	@RequestMapping(value = "/tasks/contentserver/updatelastknowntimestamp/{trackingId}", method = RequestMethod.POST)
	public void updateLastKnownTimestamp(@PathVariable String trackingId,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering updateLastKnownTimestamp");

			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("updateLastKnownTimestamp: Tracking Id: "
						+ trackingId);
			// include deleted
			Application lApplication = applicationService
					.getApplicationByTrackingId(trackingId, true);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("updateLastKnownTimestamp: Application Id: "
						+ (lApplication != null ? lApplication.getId() : "NULL"));
			// include all deleted
			List<ContentGroup> lContentGroups = contentGroupService.get(
					lApplication.getId(), true);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("updateLastKnownTimestamp: Content Groups Count: "
						+ (lContentGroups != null ? lContentGroups.size()
								: "ZERO"));
			List<Content> lContents = contentService.get(lApplication.getId(),
					true);
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("updateLastKnownTimestamp: Content List Count: "
						+ (lContents != null ? lContents.size() : "ZERO"));

			long lLastKnownTimestamp = Utils.getLastKnownTimestamp(
					lApplication, lContentGroups, lContents);
			if (lLastKnownTimestamp == 0L)
				LOGGER.warning("Last known timestamp for tracking id "
						+ trackingId + "  is ZERO");
			try {
				if (mCache != null) {
					mCache.put(trackingId, lLastKnownTimestamp);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info("Added to Memcache: " + lLastKnownTimestamp);
				}
			} catch (Throwable t) {
				LOGGER.log(Level.SEVERE, "Unable to add to Memcache", t);
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting updateLastKnownTimestamp");
		}
	}

	private ContentRequest convertToDomainFormat(
			com.cm.contentserver.transfer.ContentRequest pContentRequest) {
		ContentRequest lContentRequest = new ContentRequest();
		lContentRequest.setAccuracy(pContentRequest.getAccuracy());
		lContentRequest.setAltitude(pContentRequest.getAltitude());
		lContentRequest.setTrackingId(pContentRequest.getTrackingId());
		lContentRequest.setBearing(pContentRequest.getBearing());
		lContentRequest.setDeviceId(pContentRequest.getDeviceId());
		lContentRequest.setLatitude(pContentRequest.getLatitude());
		lContentRequest.setLongitude(pContentRequest.getLongitude());
		lContentRequest.setProvider(pContentRequest.getProvider());
		lContentRequest.setSpeed(pContentRequest.getSpeed());
		lContentRequest.setTimeCreatedMs(pContentRequest.getTimeCreatedMs());
		lContentRequest.setTimeCreatedTimeZoneOffsetMs(pContentRequest
				.getTimeCreatedTimeZoneOffsetMs());

		return lContentRequest;
	}

	private Handshake convertToDomainFormat(
			com.cm.contentserver.transfer.Handshake pHandshake) {
		Handshake lHandshake = new Handshake();
		lHandshake.setTrackingId(pHandshake.getTrackingId());
		lHandshake.setGcmRegistrationId(pHandshake.getGcmRegistrationId());
		lHandshake.setLastKnownTimestamp(pHandshake.getLastKnownTimestamp());
		lHandshake.setAccuracy(pHandshake.getAccuracy());
		lHandshake.setAltitude(pHandshake.getAltitude());
		lHandshake.setTrackingId(pHandshake.getTrackingId());
		lHandshake.setBearing(pHandshake.getBearing());
		lHandshake.setDeviceId(pHandshake.getDeviceId());
		lHandshake.setLatitude(pHandshake.getLatitude());
		lHandshake.setLongitude(pHandshake.getLongitude());
		lHandshake.setProvider(pHandshake.getProvider());
		lHandshake.setSpeed(pHandshake.getSpeed());
		lHandshake.setTimeCreatedMs(pHandshake.getTimeCreatedMs());
		lHandshake.setTimeCreatedTimeZoneOffsetMs(pHandshake
				.getTimeCreatedTimeZoneOffsetMs());

		return lHandshake;
	}

}
