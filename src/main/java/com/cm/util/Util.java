package com.cm.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;

public class Util {
	private static final Logger LOGGER = Logger.getLogger(Util.class.getName());

	public static boolean isEmpty(String string) {
		return ((string != null) && (!string.equals(""))) ? false : true;
	}

	public static String getBlobFileName(String key) {
		BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
		BlobKey blobKey = new BlobKey(key);
		final BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
		if (blobInfo != null)
			return blobInfo.getFilename();
		else
			return null;

	}

	public static void LogInfo(String log) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info(log);
	}
}
