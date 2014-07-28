package com.cm.usermanagement.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public List<User> getUsers() {

		User lLoggedInUser = this.getLoggedInUser();
		List<User> lUsers = null;
		if (lLoggedInUser.getRole().equals(User.ROLE_SUPER_ADMIN)) {
			lUsers = userDao.getAllUsers();
		} else if (lLoggedInUser.getRole().equals(User.ROLE_ADMIN)) {
			// filter out the logged in user
			lUsers = userDao.getUsersByAccountId(lLoggedInUser.getAccountId());
		}
		// filter out the logged in user
		if (lUsers != null) {
			List<User> lFilteredUsers = new ArrayList<User>();
			for (User lUser : lUsers) {
				if (!lUser.getUsername().equals(lLoggedInUser.getUsername())) {
					lFilteredUsers.add(lUser);
				}
			}
			return lFilteredUsers;
		}
		return null;
	}

	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	public User getUserByAccountId(Long accountId, Long userId) {
		return userDao.getUserByAccountId(accountId, userId);
	}

	public void saveUser(User user) {
		// hard wired
		if (user.getUsername().equals(User.DEFAULT_SUPER_ADMIN_USER_NAME))
			user.setRole(User.ROLE_SUPER_ADMIN);
		userDao.saveUser(user);
	}
	
	public void signUpUser(User user){
		// hard wired
		if (user.getUsername().equals(User.DEFAULT_SUPER_ADMIN_USER_NAME))
			user.setRole(User.ROLE_SUPER_ADMIN);
		userDao.signUpUser(user);
		
	}

	public void updateUser(User user) {
		// hard wired
		if (user.getUsername().equals(User.DEFAULT_SUPER_ADMIN_USER_NAME))
			user.setRole(User.ROLE_SUPER_ADMIN);
		userDao.updateUser(user);
	}

	public User getUser(Long id) {
		return userDao.getUser(id);
	}

	public User getLoggedInUser() {
		User user = null;

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
	}

}
