package com.cm.accountmanagement.account;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	@Autowired
	private AccountDao accountDao;
	private static final Logger LOGGER = Logger.getLogger(AccountService.class
			.getName());

	public List<Account> getAccounts() {
		return accountDao.getAccounts();
	}

	public Account getAccountByAccountName(String accountName) {
		return accountDao.getAccountByAccountName(accountName);
	}

	public void saveAccount(Account account) {
		accountDao.saveAccount(account);
	}

	public void updateAccount(Account account) {
		accountDao.updateAccount(account);
	}

	public Account getAccount(Long id) {
		return accountDao.getAccount(id);
	}

	public Account getLoggedInAccount() {
		Account account = (Account) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return account;
	}

	public boolean generateClientKey(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			Account account = getAccount(id);
			if (account != null) {
				// generate an api key
				account.setClientKey(UUID.randomUUID().toString());
				accountDao.updateClientKey(account);
				return true;
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Account Found!");
				return false;
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");

		}
	}

}
