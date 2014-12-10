package com.cm.usermanagement.user;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Component;

import com.cm.accountmanagement.account.Account;
import com.cm.util.PMF;
import com.cm.util.Utils;

@Component
class UserDao {
	// private static final Logger LOGGER =
	// Logger.getLogger(UserDao.class.getName());

	List<Account> getAccounts() {
		PersistenceManager pm = null;

		try {
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Account.class);

			return (List<Account>) q.execute();
		} finally {
			if (pm != null) {
				pm.close();
			}
		}
	}

	List<User> getAllUsers() {
		PersistenceManager pm = null;

		try {
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(User.class);

			return (List<User>) q.execute();
		} finally {
			if (pm != null) {
				pm.close();
			}
		}
	}

	List<User> getUsersByAccountId(Long accountId) {
		PersistenceManager pm = null;

		try {
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(User.class);
			q.setFilter("accountId == accountIdParam");
			q.declareParameters("String accountIdParam");
			return (List<User>) q.execute(accountId);
		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (pm != null) {
				pm.close();
			}
		}
	}

	User getUserByUserName(String userName) {
		PersistenceManager pm = null;

		try {
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(User.class);
			q.setFilter("username == userNameParam");
			q.declareParameters("String userNameParam");
			List<User> users = (List<User>) q.execute(userName);
			User user = null;
			if (users != null && (users.size() > 0)) {
				user = users.get(0);
			}
			return user;
		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (pm != null) {
				pm.close();
			}
		}
	}

	User getUserByAccountId(Long accountId, Long userId) {
		PersistenceManager pm = null;

		try {
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(User.class);
			q.setFilter("accountId == accountIdParam");
			q.declareParameters("String accountIdParam");
			List<User> users = (List<User>) q.execute(accountId);
			User lUser = null;
			for (User user : users) {
				if (user.getId().equals(userId)) {
					lUser = user;
					break;
				}
			}
			return lUser;
		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (pm != null) {
				pm.close();
			}
		}
	}

	User getUserByPromoCode(String promoCode) {
		PersistenceManager pm = null;

		try {
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(User.class);
			q.setFilter("promoCode == promoCodeParam");
			q.declareParameters("String promoCodeParam");
			List<User> users = (List<User>) q.execute(promoCode);
			User user = null;
			if (users != null && (users.size() > 0)) {
				user = users.get(0);
			}
			return user;
		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (pm != null) {
				pm.close();
			}
		}

	}

	User signUpUser(User user) {
		PersistenceManager pm = null;
		Transaction tx = null;
		try {
			pm = PMF.get().getPersistenceManager();
			tx = pm.currentTransaction();
			tx.begin();

			// create the account
			long lTime = System.currentTimeMillis();
			Account lAccount = new Account();
			lAccount.setName(user.getUsername());
			lAccount.setDescription("This is the default account for the user");
			lAccount.setTimeCreatedMs(lTime);
			lAccount.setTimeCreatedTimeZoneOffsetMs(0L);
			lAccount.setTimeUpdatedMs(lTime);
			lAccount.setTimeUpdatedTimeZoneOffsetMs(0L);
			// returns the saved Account
			lAccount = pm.makePersistent(lAccount);
			// set the account id
			user.setAccountId(lAccount.getId());
			// save the user
			User lUser = pm.makePersistent(user);

			tx.commit();

			return lUser;

		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			if (pm != null) {
				pm.close();
			}
		}

	}

	void saveUser(User user) {
		PersistenceManager pm = null;
		try {
			pm = PMF.get().getPersistenceManager();
			pm.makePersistent(user);

		} finally {
			if (pm != null) {
				pm.close();
			}
		}
	}

	void updateUser(User user) {
		PersistenceManager pm = null;
		try {
			pm = PMF.get().getPersistenceManager();
			User _user = pm.getObjectById(User.class, user.getId());
			_user.setEmail(user.getEmail());
			_user.setEnabled(user.isEnabled());
			_user.setFirstName(user.getFirstName());
			_user.setLastName(user.getLastName());
			if (!Utils.isEmpty(user.getPassword())) {
				_user.setPassword(user.getPassword());
			}
			_user.setRole(user.getRole());
			_user.setAccountId(user.getAccountId());
			_user.setTimeUpdatedMs(user.getTimeUpdatedMs());
			_user.setTimeUpdatedTimeZoneOffsetMs(user
					.getTimeUpdatedTimeZoneOffsetMs());

		} finally {
			if (pm != null) {
				pm.close();
			}
		}
	}

	User getUser(Long id) {
		PersistenceManager pm = null;

		try {
			pm = PMF.get().getPersistenceManager();
			User _user = pm.getObjectById(User.class, id);
			return _user;

		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (pm != null) {
				pm.close();
			}
		}
	}

}
