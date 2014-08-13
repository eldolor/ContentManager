jQuery(function($) {
	try {
		log("function($)", "Entering");
		setup();
		// call this post setup
		// $(document).foundation('joyride', 'start');
	} catch (err) {
		handleError("function($)", err);
	} finally {
		log("function($)", "Exiting");
	}
});

function setup() {
	try {
		log("setup", "Entering");
		setupLeftNavBar();
		setupBreadcrumbs();
		// enable abide form validation
		$(document).foundation('abide', 'events');
		getLoggedInUser();

		// not using valid.fndtn.abide & invalid.fndtn.abide as it
		// causes the form to be submitted twice. Instead use the
		// deprecated valid & invalid
		$('#changePasswordForm').on('invalid', function() {
			var invalid_fields = $(this).find('[data-invalid]');
			log(invalid_fields);
		}).on('valid', function() {
			log('changePasswordForm: valid!');
			changePassword();
		});
		$('#change_password_errors').hide();

		// forgot password
		$('#user_forgot_password_errors').hide();
		$('#user_forgot_password').unbind();
		$('#user_forgot_password').bind('click', function() {
			$('#forgot_password_modal').foundation('reveal', 'open');
		});
		// not using valid.fndtn.abide & invalid.fndtn.abide as it
		// causes the form to be submitted twice. Instead use the
		// deprecated valid & invalid
		$('#forgotPasswordForm').on('invalid', function() {
			var invalid_fields = $(this).find('[data-invalid]');
			console.log(invalid_fields);
		}).on('valid', function() {
			submitForgotPasswordRequest();
		});

	} catch (err) {
		handleError("setup", err);
	} finally {
		log("function($)", "Exiting");
	}
}

function setupLeftNavBar() {
	log("setupLeftNavBar", "Entering");
	try {
		$('#left_nav_bar')
				.empty()
				.html(
						'<li><a id=\"left_nav_bar_link_1\" href=\"javascript:void(0);\" >Change Password</a></li>'
								+ '<li><a id=\"left_nav_bar_link_1\" href=\"javascript:void(0);\" >Billing</a></li>');
		$('#left_nav_bar_link_1').unbind();
		$('#left_nav_bar_link_1').click(function() {
			// $('#user_billing').hide();
			// default
			$('#change_password').show();
		});

	} catch (err) {
		handleError("setupLeftNavBar", err);
	} finally {
		log("setupLeftNavBar", "Exiting");
	}
}

function setupBreadcrumbs() {
	log("setupBreadcrumbs", "Entering");
	try {
		var lHtml = $('#breadcrumbs').html();
		$('#breadcrumbs')
				.html(
						lHtml
								+ "<a id=\"breadcrumb_applications\" href=\"/account\">Account Settings</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}
function getLoggedInUser() {
	log("getLoggedInUser", "Entering");
	try {

		var jqxhr = $.ajax({
			url : "/secured/loggedinuser",
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(user) {
					mLoggedInUser = user;
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

	} catch (err) {
		handleError("getLoggedInUser", err);
		// close wait div
		;
	} finally {
		log("getLoggedInUser", "Exiting");
	}
}

function changePassword() {
	log("changePassword", "Entering");

	try {
		var userObj = {
			// get it from global variable
			userId : mLoggedInUser.id,
			oldPassword : $('#user_old_password').val(),
			password : $('#user_new_password').val(),
			password2 : $('#user_confirm_new_password').val()
		};
		var userObjString = JSON.stringify(userObj, null, 2);
		var jqxhr = $
				.ajax({
					url : "/secured/changepassword",
					type : "PUT",
					data : userObjString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : false,
					statusCode : {
						200 : function() {
							$('#user_message').show();
							$('#change_password_errors').hide();
						},
						400 : function(text) {
							try {
								$('#user_message').error();
								$('#change_password_errors').html(
										getErrorMessages(text));
							} catch (err) {
								handleError("updateContentEnabled", err);
							}
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						console.log(errorThrown);
						$('#user_message').hide();
						$('#change_password_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#change_password_errors').show();
					}

				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

		return false;
	} catch (err) {
		handleError("changePassword", err);
	} finally {
		log("changePassword", "Exiting");
	}
}
function submitForgotPasswordRequest() {
	log("submitForgotPasswordRequest", "Entering");
	try {

		var obj = {
			email : $('#user_forgot_password_email').val()
		};
		var objString = JSON.stringify(obj, null, 2);
		// alert(contentgroupObjString);
		// create via sync call
		var jqxhr = $
				.ajax({
					url : "/forgotpassword",
					type : "POST",
					data : objString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : false,
					statusCode : {
						202 : function() {
							$('#forgot_password_modal').foundation('reveal',
									'close');
							$('#forgot_password_request_submitted_message')
									.show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						console.log(errorThrown);
						$('#forgot_password_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#forgot_password_errors').show();
					}
				});

		return false;
	} catch (err) {
		handleError("submitForgotPasswordRequest", err);
	} finally {
		log("submitForgotPasswordRequest", "Exiting");
	}
}
/** *End***************************************** */