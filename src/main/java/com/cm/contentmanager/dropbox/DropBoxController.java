package com.cm.contentmanager.dropbox;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.config.Configuration;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;

@Controller
public class DropBoxController {
	private BlobstoreService mBlobstoreFactory = BlobstoreServiceFactory
			.getBlobstoreService();

	private static final Logger LOGGER = Logger
			.getLogger(DropBoxController.class.getName());

	@RequestMapping(value = "/secured/dropbox", method = RequestMethod.POST, produces = "application/json", headers = { "content-type=multipart/form-data" })
	public @ResponseBody
	String doUpload(HttpServletRequest req, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doUpload");
			Map<String, List<BlobKey>> blobs = mBlobstoreFactory
					.getUploads(req);
			List<BlobKey> keys = blobs.get("file");

			if (keys != null && keys.size() > 0) {
				response.setStatus(HttpServletResponse.SC_CREATED);
				String _keyString = keys.get(0).getKeyString().trim();

				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("Returning Key " + _keyString);
				return "{\"uri\":\"" + _keyString + "\"}";
			} else {
				LOGGER.log(Level.SEVERE, "Unable to upload file!");
				response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
				return null;
			}

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doUpload");
		}
	}

	@RequestMapping(value = "/secured/dropbox/url", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String getDropboxUrl(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getDropboxUrl");
			// String url =
			// mBlobstoreFactory.createUploadUrl("/secured/dropbox");
			String url = mBlobstoreFactory.createUploadUrl("/secured/dropbox",
					UploadOptions.Builder
							.withGoogleStorageBucketName(Configuration.GCS_STORAGE_BUCKET));

			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Returning Url " + url);
			return "{\"url\":\"" + url + "\"}";

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getDropboxUrl");
		}
	}

}
