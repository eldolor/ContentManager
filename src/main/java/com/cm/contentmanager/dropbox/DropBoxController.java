package com.cm.contentmanager.dropbox;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@Controller
public class DropBoxController {
//	@Autowired
//	// private AdService adService;
//	private BlobstoreService blobstoreService = BlobstoreServiceFactory
//			.getBlobstoreService();
//	private final BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
//
//	private static final Logger LOGGER = Logger
//			.getLogger(DropBoxController.class.getName());
//
//	@RequestMapping(value = "/secured/dropbox/url", method = RequestMethod.GET, produces = "application/json")
//	public @ResponseBody
//	String getDropboxUrl(HttpServletResponse response) {
//		try {
//			if (LOGGER.isLoggable(Level.INFO))
//				LOGGER.info("Entering getDropboxUrl");
//			String url = blobstoreService.createUploadUrl("/secured/dropbox");
//
//			if (LOGGER.isLoggable(Level.INFO))
//				LOGGER.info("Returning Url " + url);
//			return "{\"url\":\"" + url + "\"}";
//
//		} catch (Exception e) {
//			LOGGER.log(Level.SEVERE, e.getMessage(), e);
//		} finally {
//			if (LOGGER.isLoggable(Level.INFO))
//				LOGGER.info("Exiting getDropboxUrl");
//		}
//		return null;
//	}
//
//	@RequestMapping(value = "/secured/dropbox", method = RequestMethod.POST, produces = "application/json", headers = { "content-type=multipart/form-data" })
//	public @ResponseBody
//	String doUpload(HttpServletRequest req, HttpServletResponse response) {
//		try {
//			if (LOGGER.isLoggable(Level.INFO))
//				LOGGER.info("Entering doUpload");
//			Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
//			List<BlobKey> keys = blobs.get("file");
//
//			if (keys != null && keys.size() > 0) {
//				response.setStatus(HttpServletResponse.SC_CREATED);
//				String _keyString = keys.get(0).getKeyString().trim();
//				if (LOGGER.isLoggable(Level.INFO))
//					LOGGER.info("Returning Key " + _keyString);
//				return "{\"uri\":\"" + _keyString + "\"}";
//			} else {
//				LOGGER.log(Level.SEVERE, "Unable to upload file!");
//				response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//				return null;
//			}
//
//		} catch (Exception e) {
//			LOGGER.log(Level.SEVERE, e.getMessage(), e);
//		} finally {
//			if (LOGGER.isLoggable(Level.INFO))
//				LOGGER.info("Exiting doUpload");
//		}
//		return null;
//	}
//
//	/**
//	 * Access to this API is unsecured
//	 * 
//	 * @param key
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/dropbox/{key}", method = RequestMethod.GET)
//	public @ResponseBody
//	String doServe(@PathVariable String key, HttpServletResponse response) {
//		try {
//			if (LOGGER.isLoggable(Level.INFO))
//				LOGGER.info("Entering doServe");
//			BlobKey blobKey = new BlobKey(key);
//			final BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
//			if (blobInfo != null) {
//				response.setHeader("Content-Disposition",
//						"attachment; filename=" + blobInfo.getFilename());
//				BlobstoreServiceFactory.getBlobstoreService().serve(blobKey,
//						response);
//			} else {
//				LOGGER.info("No content found for key " + key);
//				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//			}
//		} catch (Exception e) {
//			LOGGER.log(Level.SEVERE, e.getMessage(), e);
//		} finally {
//			if (LOGGER.isLoggable(Level.INFO))
//				LOGGER.info("Exiting doServe");
//		}
//		return null;
//	}

}
