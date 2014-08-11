package com.cm.usermanagement.user;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cm.util.Utils;
import com.cm.util.ValidationError;

@Controller
public class UserManagementController {
	@Autowired
	private UserService userService;

	private static final Logger LOGGER = Logger
			.getLogger(UserManagementController.class.getName());

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView displaySignup(ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displaySignup");
		try {
			return new ModelAndView("signup", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displaySignup");
		}
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doSignup(
			@RequestBody com.cm.usermanagement.user.transfer.User pUser,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doSignup");
			List<ValidationError> errors = validateOnCreate(pUser);
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				// create the basic user object, additional work will be
				// performed in the DAO (transactionally)
				com.cm.usermanagement.user.User lUser = new com.cm.usermanagement.user.User();
				// standardize to lower case
				lUser.setUsername(pUser.getUserName().toLowerCase());
				// userName is the email address
				lUser.setEmail(pUser.getUserName());
				lUser.setPassword(new BCryptPasswordEncoder().encode(pUser
						.getPassword()));
				lUser.setRole(User.ROLE_USER);
				// default to true
				lUser.setEnabled(true);
				lUser.setTimeCreatedMs(pUser.getTimeCreatedMs());
				lUser.setTimeCreatedTimeZoneOffsetMs(pUser
						.getTimeCreatedTimeZoneOffsetMs());
				lUser.setTimeUpdatedMs(pUser.getTimeUpdatedMs());
				lUser.setTimeUpdatedTimeZoneOffsetMs(pUser
						.getTimeUpdatedTimeZoneOffsetMs());

				userService.signUpUser(lUser);
				response.setStatus(HttpServletResponse.SC_CREATED);
				return null;
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doSignup");
		}

	}

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/um/accountsettings", method = RequestMethod.GET)
	public ModelAndView displayAccountSettings(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displayAccountSettings");
		try {
			return new ModelAndView("account_settings", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displayAccountSettings");
		}
	}

	@RequestMapping(value = "/um/password", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doUpdatePassword(
			@RequestBody com.cm.usermanagement.user.transfer.User pUser,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doUpdatePassword");
			// get the logged in user
			com.cm.usermanagement.user.User lLoggedInUser = userService
					.getLoggedInUser();

			List<ValidationError> errors = validateOnPasswordChange(pUser,
					lLoggedInUser);
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {

				lLoggedInUser.setPassword(new BCryptPasswordEncoder()
						.encode(pUser.getPassword()));
				lLoggedInUser.setTimeUpdatedMs(pUser.getTimeUpdatedMs());
				lLoggedInUser.setTimeUpdatedTimeZoneOffsetMs(pUser
						.getTimeUpdatedTimeZoneOffsetMs());
				userService.updateUser(lLoggedInUser);
				response.setStatus(HttpServletResponse.SC_CREATED);
				return null;

			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doUpdatePassword");
		}

	}

	/**
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/um/loggedinuser", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	com.cm.usermanagement.user.transfer.User getLoggedInUser(
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getUser");
			com.cm.usermanagement.user.User lUser = userService
					.getLoggedInUser();
			if (lUser == null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No User Found!");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
			// send only the minimally required information
			com.cm.usermanagement.user.transfer.User lUserTfr = new com.cm.usermanagement.user.transfer.User();
			lUserTfr.setId(lUser.getId());

			response.setStatus(HttpServletResponse.SC_OK);
			return lUserTfr;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getUsers");
		}
	}

	private List<com.cm.usermanagement.user.transfer.User> convert(
			List<com.cm.usermanagement.user.User> users) {
		List<com.cm.usermanagement.user.transfer.User> userAccounts = new ArrayList<com.cm.usermanagement.user.transfer.User>();
		for (com.cm.usermanagement.user.User user : users) {
			com.cm.usermanagement.user.transfer.User userAccount = new com.cm.usermanagement.user.transfer.User();
			userAccount.setId(user.getId());
			userAccount.setEmail(user.getEmail());
			userAccount.setEnabled(user.isEnabled());
			if (user.getRole() != null) {
				if (user.getRole().equals(
						com.cm.usermanagement.user.User.ROLE_ADMIN))
					userAccount.setRole("admin");
				if (user.getRole().equals(
						com.cm.usermanagement.user.User.ROLE_USER))
					userAccount.setRole("user");
			}
			userAccount.setAccountId(user.getAccountId());
			userAccount.setFirstName(user.getFirstName());
			userAccount.setLastName(user.getLastName());
			userAccount.setUserName(user.getUsername());
			userAccounts.add(userAccount);
		}
		return userAccounts;
	}

	private com.cm.usermanagement.user.transfer.User convert(
			com.cm.usermanagement.user.User user) {
		com.cm.usermanagement.user.transfer.User userAccount = new com.cm.usermanagement.user.transfer.User();
		userAccount.setId(user.getId());
		userAccount.setEmail(user.getEmail());
		userAccount.setEnabled(user.isEnabled());
		if (user.getRole() != null) {
			if (user.getRole().equals(
					com.cm.usermanagement.user.User.ROLE_ADMIN))
				userAccount.setRole("admin");
			if (user.getRole()
					.equals(com.cm.usermanagement.user.User.ROLE_USER))
				userAccount.setRole("user");
		}
		userAccount.setAccountId(user.getAccountId());
		userAccount.setFirstName(user.getFirstName());
		userAccount.setLastName(user.getLastName());
		userAccount.setUserName(user.getUsername());
		return userAccount;
	}

	private List<ValidationError> validateOnPasswordChange(
			com.cm.usermanagement.user.transfer.User pUser,
			com.cm.usermanagement.user.User pLoggedInUser) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering validate");
		List<ValidationError> errors = new ArrayList<ValidationError>();

		if (pLoggedInUser.getId() != pUser.getId()) {
			ValidationError error = new ValidationError();
			error.setCode("id");
			error.setDescription("Your session has expired or invalid request");
			errors.add(error);
			LOGGER.log(Level.WARNING,
					"Your session has expired or invalid request");
		}
		if ((!Utils.isEmpty(pUser.getPassword()))
				&& (!pUser.getPassword().equals(pUser.getPassword2()))) {
			ValidationError error = new ValidationError();
			error.setCode("password");
			error.setDescription("Passwords do not match");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Passwords do not match");
		}
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting validate");
		return errors;
	}

	/*****************************************************************************/
	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/um", method = RequestMethod.GET)
	public ModelAndView doLogin(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering doLogin");
		try {
			return new ModelAndView("user_management", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doLogin");
		}
	}

	@RequestMapping(value = "/secured/loggedinuser", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doUpdateLoggedInUser(
			@RequestBody com.cm.usermanagement.user.transfer.User userAccount,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doUpdateUser");
			List<ValidationError> errors = validateOnUpdateLoggedInUser(userAccount);
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				com.cm.usermanagement.user.User lUser = new com.cm.usermanagement.user.User();
				lUser.setId(userAccount.getId());
				lUser.setUsername(userAccount.getUserName());
				lUser.setEmail(userAccount.getEmail());
				lUser.setFirstName(userAccount.getFirstName());
				lUser.setLastName(userAccount.getLastName());
				lUser.setPassword(new BCryptPasswordEncoder()
						.encode(userAccount.getPassword()));
				lUser.setTimeUpdatedMs(userAccount.getTimeUpdatedMs());
				lUser.setTimeUpdatedTimeZoneOffsetMs(userAccount
						.getTimeUpdatedTimeZoneOffsetMs());

				userService.updateUser(lUser);
				response.setStatus(HttpServletResponse.SC_CREATED);
				return null;
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doUpdateUser");
		}

	}

	/**
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/um/users", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<com.cm.usermanagement.user.transfer.User> getUsers(
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getUsers");
			List<com.cm.usermanagement.user.User> users = userService
					.getUsers();
			if (users != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info(users.size() + " users found");
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Users Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return convert(users);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getUsers");
		}
	}

	/**
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/um/user/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	com.cm.usermanagement.user.transfer.User getUser(@PathVariable Long id,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getUser");
			com.cm.usermanagement.user.User user = userService.getUser(id);
			if (user == null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No User Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return convert(user);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getUsers");
		}
	}

	@RequestMapping(value = "/um/user", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doCreateUser(
			@RequestBody com.cm.usermanagement.user.transfer.User user,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doCreateAccount");
			List<ValidationError> errors = validateOnCreate(user);
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				com.cm.usermanagement.user.User lUser = new com.cm.usermanagement.user.User();
				lUser.setEmail(user.getEmail());
				lUser.setFirstName(user.getFirstName());
				lUser.setLastName(user.getLastName());
				// default to true
				lUser.setEnabled(true);
				if (user.getRole().equals("admin")) {
					lUser.setRole(com.cm.usermanagement.user.User.ROLE_ADMIN);
					// use the associated account id. only super admins can
					// create an admin user.
					lUser.setAccountId(user.getAccountId());
				}
				if (user.getRole().equals("user")) {
					lUser.setRole(com.cm.usermanagement.user.User.ROLE_USER);
					/**
					 * For a user role, tie the account id to the logged in
					 * admin account, as only admins can create a user
					 */
					Long lAccountId = userService.getLoggedInUser()
							.getAccountId();
					lUser.setAccountId(lAccountId);
				}

				lUser.setUsername(user.getUserName());
				lUser.setPassword(new StandardPasswordEncoder().encode(user
						.getPassword()));

				lUser.setTimeCreatedMs(user.getTimeCreatedMs());
				lUser.setTimeCreatedTimeZoneOffsetMs(user
						.getTimeCreatedTimeZoneOffsetMs());
				lUser.setTimeUpdatedMs(user.getTimeUpdatedMs());
				lUser.setTimeUpdatedTimeZoneOffsetMs(user
						.getTimeUpdatedTimeZoneOffsetMs());

				userService.saveUser(lUser);
				response.setStatus(HttpServletResponse.SC_CREATED);
				return null;
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doCreateAccount");
		}

	}

	@RequestMapping(value = "/um/user", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doUpdateUser(
			@RequestBody com.cm.usermanagement.user.transfer.User user,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doUpdateUser");
			List<ValidationError> errors = validateOnUpdate(user);
			if (!errors.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return errors;
			} else {
				com.cm.usermanagement.user.User lUser = new com.cm.usermanagement.user.User();
				lUser.setId(user.getId());
				lUser.setEmail(user.getEmail());
				lUser.setFirstName(user.getFirstName());
				lUser.setLastName(user.getLastName());
				lUser.setEnabled(user.getEnabled());
				lUser.setUsername(user.getUserName());
				lUser.setPassword(new StandardPasswordEncoder().encode(user
						.getPassword()));
				if (user.getRole().equals("admin")) {
					lUser.setRole(com.cm.usermanagement.user.User.ROLE_ADMIN);
					// use the associated account id. only super admins can
					// create an admin user.
					lUser.setAccountId(user.getAccountId());
				}
				if (user.getRole().equals("user")) {
					lUser.setRole(com.cm.usermanagement.user.User.ROLE_USER);
					/**
					 * For a user role, tie the account id to the logged in
					 * admin account, as only admins can create a user
					 */
					Long lAccountId = userService.getLoggedInUser()
							.getAccountId();
					lUser.setAccountId(lAccountId);
				}
				lUser.setTimeUpdatedMs(user.getTimeUpdatedMs());
				lUser.setTimeUpdatedTimeZoneOffsetMs(user
						.getTimeUpdatedTimeZoneOffsetMs());

				userService.updateUser(lUser);
				response.setStatus(HttpServletResponse.SC_CREATED);
				return null;
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doUpdateUser");
		}

	}

	private List<ValidationError> validateOnCreate(
			com.cm.usermanagement.user.transfer.User userAccount) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering validate");
		List<ValidationError> errors = new ArrayList<ValidationError>();
		// Verify user info submission
		// if (Util.isEmpty(userAccount.getFirstName())) {
		// ValidationError error = new ValidationError();
		// error.setCode("firstName");
		// error.setDescription("First Name cannot be blank");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "First Name cannot be blank");
		// }
		// if (Util.isEmpty(userAccount.getLastName())) {
		// ValidationError error = new ValidationError();
		// error.setCode("lastName");
		// error.setDescription("Last Name cannot be blank");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "Last Name cannot be blank");
		// }
		// if (Util.isEmpty(userAccount.getEmail())) {
		// ValidationError error = new ValidationError();
		// error.setCode("email");
		// error.setDescription("Email cannot be blank");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "Email cannot be blank");
		// }
		if (Utils.isEmpty(userAccount.getUserName())) {
			ValidationError error = new ValidationError();
			error.setCode("username");
			error.setDescription("Username cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Username cannot be blank");
		}
		if (Utils.isEmpty(userAccount.getPassword())) {
			ValidationError error = new ValidationError();
			error.setCode("password");
			error.setDescription("Password cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Password cannot be blank");
		}
		// if (!userAccount.getPassword().equals(userAccount.getPassword2())) {
		// ValidationError error = new ValidationError();
		// error.setCode("password");
		// error.setDescription("Passwords do not match");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "Passwords do not match");
		// }
		// check to make sure we don't have a user account already
		com.cm.usermanagement.user.User user = userService
				.getUserByUserName(userAccount.getUserName());
		if (user != null) {
			ValidationError error = new ValidationError();
			error.setCode("username");
			error.setDescription("The user already exists");
			errors.add(error);
			LOGGER.log(Level.WARNING, "The user already exists");
		}
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting validate");
		return errors;
	}

	private List<ValidationError> validateOnUpdate(
			com.cm.usermanagement.user.transfer.User userAccount) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering validate");
		List<ValidationError> errors = new ArrayList<ValidationError>();
		// Verify user info submission
		// if (Util.isEmpty(userAccount.getFirstName())) {
		// ValidationError error = new ValidationError();
		// error.setCode("firstName");
		// error.setDescription("First Name cannot be blank");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "First Name cannot be blank");
		// }
		// if (Util.isEmpty(userAccount.getLastName())) {
		// ValidationError error = new ValidationError();
		// error.setCode("lastName");
		// error.setDescription("Last Name cannot be blank");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "Last Name cannot be blank");
		// }
		// if (Util.isEmpty(userAccount.getEmail())) {
		// ValidationError error = new ValidationError();
		// error.setCode("email");
		// error.setDescription("Email cannot be blank");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "Email cannot be blank");
		// }
		if (Utils.isEmpty(userAccount.getUserName())) {
			ValidationError error = new ValidationError();
			error.setCode("userName");
			error.setDescription("Username cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Username cannot be blank");
		}
		if (Utils.isEmpty(userAccount.getPassword())) {
			ValidationError error = new ValidationError();
			error.setCode("password");
			error.setDescription("Password cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Password cannot be blank");
		}
		// if ((!Util.isEmpty(userAccount.getPassword()))
		// && (!userAccount.getPassword().equals(
		// userAccount.getPassword2()))) {
		// ValidationError error = new ValidationError();
		// error.setCode("password");
		// error.setDescription("Passwords do not match");
		// errors.add(error);
		// LOGGER.log(Level.WARNING, "Passwords do not match");
		// }
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting validate");
		return errors;
	}

	private List<ValidationError> validateOnUpdateLoggedInUser(
			com.cm.usermanagement.user.transfer.User userAccount) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering validate");
		List<ValidationError> errors = new ArrayList<ValidationError>();
		// Verify user info submission
		if (Utils.isEmpty(userAccount.getFirstName())) {
			ValidationError error = new ValidationError();
			error.setCode("firstName");
			error.setDescription("First Name cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "First Name cannot be blank");
		}
		if (Utils.isEmpty(userAccount.getLastName())) {
			ValidationError error = new ValidationError();
			error.setCode("lastName");
			error.setDescription("Last Name cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Last Name cannot be blank");
		}
		if (Utils.isEmpty(userAccount.getEmail())) {
			ValidationError error = new ValidationError();
			error.setCode("email");
			error.setDescription("Email cannot be blank");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Email cannot be blank");
		}
		if ((!Utils.isEmpty(userAccount.getPassword()))
				&& (!userAccount.getPassword().equals(
						userAccount.getPassword2()))) {
			ValidationError error = new ValidationError();
			error.setCode("password");
			error.setDescription("Passwords do not match");
			errors.add(error);
			LOGGER.log(Level.WARNING, "Passwords do not match");
		}
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Exiting validate");
		return errors;
	}

}
