package com.cm.accountmanagement.account;

import java.util.List;
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

}
