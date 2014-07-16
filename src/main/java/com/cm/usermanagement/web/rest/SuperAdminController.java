package com.cm.usermanagement.web.rest;

import org.springframework.stereotype.Controller;

@Controller
public class SuperAdminController {
	// @Autowired
	// private MavinUserService mavinUserService;
	//
	// private static final Logger LOGGER = Logger
	// .getLogger(SuperAdminController.class.getName());
	//
	// /**
	// * @param model
	// * @return
	// */
	// @RequestMapping(value = "/super", method = RequestMethod.GET)
	// public ModelAndView doLogin(ModelMap model) {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Entering doLogin");
	// try {
	// return new ModelAndView("super", model);
	// } finally {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Exiting doLogin");
	// }
	// }
	//
	// @RequestMapping(value = "/super/account", method = RequestMethod.POST,
	// consumes = "application/json", produces = "application/json")
	// public @ResponseBody
	// List<ValidationError> doCreateAccount(@RequestBody
	// com.mavin.um.user.transfer.User userAccount,
	// HttpServletResponse response) {
	// try {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Entering doCreateAccount");
	// List<ValidationError> errors = validate(userAccount);
	// if (!errors.isEmpty()) {
	// response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	// return errors;
	// } else {
	// User _user = new User();
	// _user.setUsername(userAccount.getUserName());
	// _user.setPassword(userAccount.getPassword());
	// mavinUserService.saveUser(_user);
	// response.setStatus(HttpServletResponse.SC_CREATED);
	// return null;
	// }
	// } finally {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Exiting doCreateAccount");
	// }
	//
	// }
	//
	// private List<ValidationError> validate(User userAccount) {
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Entering validate");
	// List<ValidationError> errors = new ArrayList<ValidationError>();
	// // Verify user info submission
	// if (userAccount.getUserName().equals("")) {
	// ValidationError error = new ValidationError();
	// error.setCode("username");
	// error.setDescription("Username cannot be blank");
	// errors.add(error);
	// LOGGER.log(Level.WARNING, "Username cannot be blank");
	// }
	// if (userAccount.getPassword().equals("")) {
	// ValidationError error = new ValidationError();
	// error.setCode("password");
	// error.setDescription("Password cannot be blank");
	// errors.add(error);
	// LOGGER.log(Level.WARNING, "Password cannot be blank");
	// }
	// if (!userAccount.getPassword().equals(userAccount.getPassword2())) {
	// ValidationError error = new ValidationError();
	// error.setCode("password");
	// error.setDescription("Passwords do not match");
	// errors.add(error);
	// LOGGER.log(Level.WARNING, "Passwords do not match");
	// }
	// // check to make sure we don't have a user account already
	// User user = mavinUserService.getUserByUserName(userAccount
	// .getUserName());
	// if (user != null) {
	// ValidationError error = new ValidationError();
	// error.setCode("username");
	// error.setDescription("The user already exists");
	// errors.add(error);
	// LOGGER.log(Level.WARNING, "The user already exists");
	// }
	// if (LOGGER.isLoggable(Level.INFO))
	// LOGGER.info("Exiting validate");
	// return errors;
	// }

}
