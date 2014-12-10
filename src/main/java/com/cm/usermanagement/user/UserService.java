package com.cm.usermanagement.user;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private ForgotPasswordDao forgotPasswordDao;
	@Autowired
	private PasswordChangeRequestDao passwordChangeRequestDao;
	private static final Logger LOGGER = Logger.getLogger(UserService.class
			.getName());

	public void save(PasswordChangeRequest request) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			passwordChangeRequestDao.save(request);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public void save(ForgotPasswordRequest request) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			forgotPasswordDao.save(request);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public void update(ForgotPasswordRequest pRequest) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			forgotPasswordDao.update(pRequest);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public ForgotPasswordRequest get(String guid) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			return forgotPasswordDao.get(guid);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public List<User> getUsers() {

		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			User lLoggedInUser = this.getLoggedInUser();
			List<User> lUsers = null;
			if (lLoggedInUser.getRole().equals(User.ROLE_SUPER_ADMIN)) {
				lUsers = userDao.getAllUsers();
			} else if (lLoggedInUser.getRole().equals(User.ROLE_ADMIN)) {
				// filter out the logged in user
				lUsers = userDao.getUsersByAccountId(lLoggedInUser
						.getAccountId());
			}
			// filter out the logged in user
			if (lUsers != null) {
				List<User> lFilteredUsers = new ArrayList<User>();
				for (User lUser : lUsers) {
					if (!lUser.getUsername()
							.equals(lLoggedInUser.getUsername())) {
						lFilteredUsers.add(lUser);
					}
				}
				return lFilteredUsers;
			}
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public User getUserByUserName(String userName) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			return userDao.getUserByUserName(userName);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public User getUserByAccountId(Long accountId, Long userId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			return userDao.getUserByAccountId(accountId, userId);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public void saveUser(User user) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			// hard wired
			if (user.getUsername().equals(User.DEFAULT_SUPER_ADMIN_USER_NAME))
				user.setRole(User.ROLE_SUPER_ADMIN);
			userDao.saveUser(user);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public User signUpUser(User user) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			// hard wired
			if (user.getUsername().equals(User.DEFAULT_SUPER_ADMIN_USER_NAME))
				user.setRole(User.ROLE_SUPER_ADMIN);
			return userDao.signUpUser(user);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}

	}

	public void updateUser(User user) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			// hard wired
			if (user.getUsername().equals(User.DEFAULT_SUPER_ADMIN_USER_NAME))
				user.setRole(User.ROLE_SUPER_ADMIN);
			userDao.updateUser(user);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public User getUser(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			return userDao.getUser(id);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public User getLoggedInUser() {
		User user = null;
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");

			SecurityContext lSc = SecurityContextHolder.getContext();
			if (lSc == null)
				return null;
			Authentication lAuth = lSc.getAuthentication();
			if (lAuth == null)
				return null;
			Object lPrincipal = lAuth.getPrincipal();
			if (lPrincipal != null && lPrincipal instanceof User)
				user = (User) lPrincipal;
			return user;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public Coupon getCoupon(String promoCode) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			return userDao.getCoupon(promoCode);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public Coupon saveCoupon(Coupon coupon) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			return userDao.saveCoupon(coupon);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

	public void updateCouponRedemption(Coupon coupon) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			userDao.updateCouponRedemption(coupon);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

}
