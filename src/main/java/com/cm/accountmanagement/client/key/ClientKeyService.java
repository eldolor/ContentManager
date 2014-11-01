package com.cm.accountmanagement.client.key;

import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.accountmanagement.account.Account;
import com.cm.accountmanagement.account.AccountService;
import com.cm.contentmanager.application.Application;
import com.cm.contentmanager.application.ApplicationService;
import com.cm.util.Utils;

@Service
public class ClientKeyService {
	@Autowired
	private ClientKeyDao clientKeyDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ApplicationService applicationService;

	private static final Logger LOGGER = Logger
			.getLogger(ClientKeyService.class.getName());

	public List<ClientKey> getClientKeys(Long accountId) {
		return clientKeyDao.getClientKeys(accountId);
	}

	public void deleteClientKey(Long id, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		clientKeyDao.delete(id, timeUpdatedMs, timeUpdatedTimeZoneOffsetMs);
	}

	public ClientKey generateClientKey(Long accountId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			ClientKey lClientKey = null;
			Account lAccount = accountService.getAccount(accountId);
			if (lAccount != null) {
				// generate a client key
				String lKey = UUID.randomUUID().toString();
				lClientKey = new ClientKey();
				lClientKey.setKey(lKey);
				lClientKey.setAccountId(accountId);
				lClientKey.setTimeCreatedMs(System.currentTimeMillis());
				lClientKey.setTimeCreatedTimeZoneOffsetMs((long) TimeZone
						.getTimeZone("UTC").getRawOffset());
				lClientKey = clientKeyDao.add(lClientKey);

				return lClientKey;
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Account Found!");
			}
			return lClientKey;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

	public boolean validateClientKey(String pClientKey, String pTrackingId) {
		if (Utils.isEmpty(pClientKey) || Utils.isEmpty(pTrackingId)) {
			LOGGER.log(Level.SEVERE,
					"Either the Client Key or the Tracking id is Null");
			return false;
		}
		Application pApplication = applicationService
				.getApplicationByTrackingId(pTrackingId, false);
		if (pApplication == null) {
			LOGGER.log(Level.SEVERE, "No application found for Tracking Id: "
					+ pTrackingId);
			return false;
		}
		long lAccountId = pApplication.getAccountId();
		List<ClientKey> lClientKeys = clientKeyDao.getClientKeys(lAccountId);
		for (ClientKey clientKey : lClientKeys) {
			if (pClientKey.equals(clientKey.getKey())) {
				return true;
			}

		}
		return false;
	}

}
